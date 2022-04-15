package com.demo.admin.custom.annotations;
import com.demo.admin.custom.helpers.Utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RFCValidator implements ConstraintValidator<RFCMx, String> {
    protected String regExp;

    @Override
    public void initialize(RFCMx rfcmx) {
        this.regExp = rfcmx.regexp();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        return Utils.esRFCValido(s);
    }
}
