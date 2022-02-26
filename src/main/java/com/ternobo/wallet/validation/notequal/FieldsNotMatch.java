package com.ternobo.wallet.validation.notequal;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Constraint(validatedBy = FieldsNotMatchValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FieldsNotMatch {

    String message() default "Fields values can't be same";

    String field();

    String fieldMatch();

    Class<?>[] groups() default {};
    Class<?>[] payload() default {};

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        FieldsNotMatch[] value();
    }
}