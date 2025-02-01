package com.oak.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.List;

import org.codehaus.commons.compiler.CompilerFactoryFactory;
import org.codehaus.commons.compiler.IExpressionEvaluator;
import org.codehaus.commons.compiler.IScriptEvaluator;
import org.springframework.stereotype.Service;

import com.oak.common.exception.model.ApiException;
import com.oak.dto.AnalyticsEngineTemplateDto;
import com.oak.dto.ExecutionResult;
import com.oak.service.AnalyticsEngineTemplateExecutor;

@Service
public class AnalyticsEngineTemplateExecutorImpl implements AnalyticsEngineTemplateExecutor {

	public static IScriptEvaluator validate(String template, String[] imports, Class<?>[] exceptions, Class<?> returnType, String[] params, Class<?>[] paramTypes) throws Exception {
		IScriptEvaluator se = CompilerFactoryFactory.getDefaultCompilerFactory().newScriptEvaluator();
		se.setDefaultImports(imports);
		se.setThrownExceptions(exceptions);
		se.setReturnType(returnType);
		se.setParameters(params, paramTypes);
		se.cook(template);
		return se;
	}
	
	@Override
	public IScriptEvaluator validateTemplate(AnalyticsEngineTemplateDto template) {
		try {
			IScriptEvaluator se = CompilerFactoryFactory.getDefaultCompilerFactory().newScriptEvaluator();
			se.setDefaultImports(template.getImports().toArray(new String[template.getImports().size()]));
			Class[] array = template.getExceptions().stream().map(this::getClassFromName).toArray(Class[]::new);
			se.setThrownExceptions(array);
			se.setReturnType(getClassFromName(template.getReturnType()));
			se.setParameters(template.getParams().toArray(new String[template.getParams().size()]),
					template.getParamTypes().stream().map(this::getClassFromName).toArray(Class[]::new));
			se.cook(template.getTemplate());
			return se;
		} catch (Exception e) {
			throw new ApiException(e.getMessage());
		}
	}

	@Override
	public ExecutionResult executeTemplate(AnalyticsEngineTemplateDto template, List<Object> paramValues) {
        // Validate template
        IScriptEvaluator se = this.validateTemplate(template);

        // Execute template
		try {
			return (ExecutionResult) se.evaluate(paramValues.toArray(new Object[paramValues.size()]));
		} catch (InvocationTargetException e) {
			throw new ApiException(e.getMessage());
		}
	}
	
	private final Class<?> getClassFromName(String name) {
		try {
			return Class.forName(name);
		} catch (ClassNotFoundException e) {
			throw new ApiException(e.getMessage());
		}
	}
	
	public static void main(String[] args) throws Exception {
		trial();
//		test("22");
//		testReadingWithinRange(new BigDecimal("1500"), new BigDecimal("100"), new BigDecimal("200"));
//		System.out.println(readingWithinRange(new BigDecimal("150"), new BigDecimal("100"), new BigDecimal("200")));
//		System.out.println(readingWithinRange(new BigDecimal("100"), new BigDecimal("100"), new BigDecimal("200")));
//		System.out.println(readingWithinRange(new BigDecimal("200"), new BigDecimal("100"), new BigDecimal("200")));
//		System.out.println(readingWithinRange(new BigDecimal("99"), new BigDecimal("100"), new BigDecimal("200")));
//		System.out.println(readingWithinRange(new BigDecimal("201"), new BigDecimal("100"), new BigDecimal("200")));
	}
	
	public static void testReadingWithinRange(BigDecimal reading, BigDecimal range1, BigDecimal range2) throws Exception {
		// Convert command line argument to call argument "total".
		Object[] arguments = { reading, range1, range2 };

		// Create "ExpressionEvaluator" object.
		IExpressionEvaluator ee = CompilerFactoryFactory.getDefaultCompilerFactory().newExpressionEvaluator();
		ee.setExpressionType(String.class);
		ee.setParameters(new String[] { "reading", "range1", "range2" }, new Class[] { BigDecimal.class, BigDecimal.class, BigDecimal.class });
		ee.cook("(reading.compareTo(range1) >= 0 && reading.compareTo(range2) <= 0) ? \"GREEN\" : null");
		System.out.println("Validated");

		// Evaluate expression with actual parameter values.
		Object res = ee.evaluate(arguments);

		// Print expression result.
		System.out.println("Result = " + String.valueOf(res));
		
	}
	
	public static String readingWithinRange(BigDecimal reading, BigDecimal range1, BigDecimal range2) {
		if (reading == null) return "NO_DATA";
		if (range1 == null) return "NO_DATA";
		if (range2 == null) return "NO_DATA";
		if (reading.compareTo(range1) < 0) return "AMBER";
		if (reading.compareTo(range2) > 0) return "RED";
		if (reading.compareTo(range1) >= 0 && reading.compareTo(range2) <= 0) return "GREEN";
		return null;
	}
	
	public static void test(String arg) throws Exception {

		if (arg == null) {
			System.err.println("Usage: <total>");
			System.err.println("Computes the shipping costs from the double value \"total\".");
			System.err.println("If \"total\" is less than 100.0, then the result is 7.95, else the result is 0.");
			System.exit(1);
		}
		// Convert command line argument to call argument "total".
		Object[] arguments = { arg };

		// Create "ExpressionEvaluator" object.
		IExpressionEvaluator ee = CompilerFactoryFactory.getDefaultCompilerFactory().newExpressionEvaluator();
		ee.setExpressionType(BigDecimal.class);
		ee.setParameters(new String[] { "total" }, new Class[] { String.class });
		ee.cook("new java.math.BigDecimal(total).compareTo(new java.math.BigDecimal(100.0))>= 0 ? new java.math.BigDecimal(0.0) : new java.math.BigDecimal(7.95)");
		System.out.println("Validated");

		// Evaluate expression with actual parameter values.
		Object res = ee.evaluate(arguments);

		// Print expression result.
		System.out.println("Result = " + String.valueOf(res));
	}
	
	public static void trial() throws Exception {
		System.out.println(execute("		if (reading == null) return \"NO_DATA\";\r\n" + 
				"		if (range1 == null) return \"NO_DATA\";\r\n" + 
				"		if (range2 == null) return \"NO_DATA\";\r\n" + 
				"		if (reading.compareTo(range1) < 0) return \"AMBER\";\r\n" + 
				"		if (reading.compareTo(range2) > 0) return \"RED\";\r\n" + 
				"		if (reading.compareTo(range1) >= 0 && reading.compareTo(range2) <= 0) return \"GREEN\";\r\n" + 
				"		return null;", 
				new String[] {}, new Class[] {}, String.class, 
				new String[] { "reading", "range1", "range2" },
				new Class[] { BigDecimal.class, BigDecimal.class, BigDecimal.class },
				new Object[] { new BigDecimal("1500"), new BigDecimal("100"), new BigDecimal("200") }));
	}
	
	// check null cast
	public static <T> T execute(String template, String[] imports, Class<?>[] exceptions, Class<T> returnType, String[] params, Class<?>[] paramTypes, Object[] paramValues) throws Exception {
        // Validate template
        IScriptEvaluator se = validate(template, imports, exceptions, returnType, params, paramTypes);

        // Execute template
		return (T) se.evaluate(paramValues);
	}

	// add valid annotations
}
