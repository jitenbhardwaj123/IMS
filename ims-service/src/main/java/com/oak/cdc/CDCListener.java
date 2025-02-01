package com.oak.cdc;

import static io.debezium.data.Envelope.FieldName.AFTER;
import static io.debezium.data.Envelope.FieldName.BEFORE;
import static io.debezium.data.Envelope.FieldName.OPERATION;
import static java.util.stream.Collectors.toMap;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.oak.service.impl.AnalyticsEngineChangeEventExecutor;

import io.debezium.config.Configuration;
import io.debezium.embedded.Connect;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;

@Component
public class CDCListener {
	private final Logger logger;
	
    /**
     * Single thread pool which will run the Debezium engine asynchronously.
     */
    private final Executor executor = Executors.newSingleThreadExecutor();

    /**
     * The Debezium engine which needs to be loaded with the configurations, Started and Stopped - for the
     * CDC to work.
     */
    private final DebeziumEngine<ChangeEvent<SourceRecord, SourceRecord>> engine;

    /**
     * Handle to the Service layer.
     */
    private final AnalyticsEngineChangeEventExecutor analyticsEngineChangeEventExecutor;

    /**
     * Constructor which loads the configurations and sets a callback method 'handleEvent', which is invoked when
     * a DataBase transactional operation is performed.
     *
     */
    @Autowired
    public CDCListener(Logger logger, Configuration dbzConfig, AnalyticsEngineChangeEventExecutor analyticsEngineChangeEventExecutor) {
    	this.logger = logger;
		this.engine = DebeziumEngine
				.create(Connect.class)
				.using(dbzConfig.asProperties())
				.notifying(this::handleEvent)
				.build();
		this.analyticsEngineChangeEventExecutor = analyticsEngineChangeEventExecutor;
    }

    /**
     * The method is called after the Debezium engine is initialized and started asynchronously using the Executor.
     */
    @PostConstruct
    private void start() {
        this.executor.execute(engine);
    }

    /**
     * This method is called when the container is being destroyed. This stops the debezium, merging the Executor.
     * @throws IOException 
     */
    @PreDestroy
    private void stop() throws IOException {
        if (this.engine != null) {
            this.engine.close();
        }
    }

    /**
     * This method is invoked when a transactional action is performed on any of the tables that were configured.
     *
     * @param ChangeEvent
     */
    private void handleEvent(ChangeEvent<SourceRecord, SourceRecord> ce) {
    	SourceRecord sourceRecord = ce.value();
        Struct sourceRecordValue = (Struct) sourceRecord.value();

        if(sourceRecordValue != null && !sourceRecordValue.schema().name().contains("Heartbeat")) {
            Operation operation = Operation.forCode((String) sourceRecordValue.get(OPERATION));

            Map<String, Object> message;
            String record = AFTER; //For Read, Update & Insert operations.

            if (operation == Operation.DELETE) {
                record = BEFORE; //For Delete operations.
            }
            // get the table name
            Struct sourceStruct = (Struct) sourceRecordValue.get("source");
            String tableName = sourceStruct.getString("table");
            
            //Build a map with all row data received.
            Struct struct = (Struct) sourceRecordValue.get(record);
            message = struct.schema().fields().stream()
                    .map(Field::name)
                    .filter(fieldName -> struct.get(fieldName) != null)
                    .map(fieldName -> Pair.of(fieldName, struct.get(fieldName)))
                    .collect(toMap(Pair::getKey, Pair::getValue));

            //Call the service to handle the data change.
            try {
				this.analyticsEngineChangeEventExecutor.process(operation, tableName, message);
			} catch (Exception e) {
				logger.error("Error executing change event", e);
			}
        }
    }
}