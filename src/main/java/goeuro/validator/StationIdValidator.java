package goeuro.validator;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator for input query parameters. We can add more constraints on the
 * input parameters , eg. Input range etc.
 * Created by manoj on 20/09/16.
 */
class StationIdValidator implements ConstraintValidator<StationId, String> {
    @Override
    public void initialize(StationId stationId) {
    }

    @Override
    public boolean isValid(String stationIdString, ConstraintValidatorContext constraintValidatorContext) {

        //Return false in case of null or incorrect number format.
        return stationIdString != null && StringUtils.isNumeric(stationIdString);

    }
}
