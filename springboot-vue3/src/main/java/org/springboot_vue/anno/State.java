package org.springboot_vue.anno;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.springboot_vue.validation.StateValidation;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
//自定義State
@Documented //元註解
@Target({FIELD}) //元註解
@Retention(RUNTIME)//元註解
@Constraint(validatedBy = {StateValidation.class})//指定提供校驗的class
public @interface State {
    /*提供校驗失敗的訊息*/
    String message() default "state參數得值只能是已發布或是草稿";
    /*指定分組*/
    Class<?>[] groups() default {};
    /*負載 獲取到state註解的附加訊息*/
    Class<? extends Payload>[] payload() default {};
}
