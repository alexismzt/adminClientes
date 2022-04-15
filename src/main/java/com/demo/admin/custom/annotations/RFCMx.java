package com.demo.admin.custom.annotations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.demo.admin.custom.helpers.Utils.RFC_PATTERN;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

@Target({METHOD, FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {RFCValidator.class})
public @interface RFCMx {
    String message() default "Debe de introducir un RFC válido";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String regexp() default RFC_PATTERN;

    @Target({METHOD, FIELD})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        RFCMx[] value();
    }
}
