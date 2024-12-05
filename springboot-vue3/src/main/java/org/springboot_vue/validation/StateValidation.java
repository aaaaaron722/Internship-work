package org.springboot_vue.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springboot_vue.anno.State;

public class StateValidation implements ConstraintValidator<State, String> {
    /**
     *
     * @param s 將來要校驗的數據
     * @param constraintValidatorContext
     * @return 如果返回false則校驗不通過
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null){
            return false;
        }
        if(s.equals("已發布") || s.equals("草稿")){
            return true;
        }
        return false;
    }
}
