package goeuro.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validator interface for Station id's
 * <p>
 * Created by manoj on 20/09/16.
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StationIdValidator.class)
public @interface StationId {
    String message() default "Invalid input query parameters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
