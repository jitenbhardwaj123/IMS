package com.oak.validator;

import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = AnalyticsEngineTemplateParamValuesSizeValidator.class)
@Target({ METHOD, CONSTRUCTOR })
@Retention(RUNTIME)
@Documented
public @interface AnalyticsEngineTemplateParamValuesSize {

	String message() default "{analytics-engine-template.param.values.mis-match}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
