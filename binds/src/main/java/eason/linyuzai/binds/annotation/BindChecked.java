package eason.linyuzai.binds.annotation;

import android.support.annotation.IdRes;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import eason.linyuzai.binds.convertor.self.BooleanSelfConvertor;
import eason.linyuzai.binds.target.ValueTarget;

/**
 * For CompoundButton
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TargetObject
public @interface BindChecked {
    @IdRes int[] value();

    Class<? extends ValueTarget.Convertor> convertor() default BooleanSelfConvertor.class;

    int valueIndex() default 0;

    boolean read() default true;

    boolean write() default true;

    boolean ignoreSame() default false;

    boolean viewReference() default false;
}
