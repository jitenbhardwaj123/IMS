FROM openjdk:8u181-jre-stretch

ARG JAR_FILE
ADD target/${JAR_FILE} ims-service.war
RUN sh -c 'touch /ims-service.war'

ENV JAVA_OPTS="-Xmx2048m"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /ims-service.war" ]
