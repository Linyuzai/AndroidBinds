package eason.linyuzai.binds.annotation;

import android.support.annotation.IdRes;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import eason.linyuzai.binds.convertor.self.IntSelfConvertor;
import eason.linyuzai.binds.target.ValueTarget;

/**
 * For View
 * Set value to target
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TargetObject
public @interface BindVisible {
    @IdRes int[] value();

    Class<? extends ValueTarget.Convertor> convertor() default IntSelfConvertor.class;

    int valueIndex() default 0;

    boolean ignoreSame() default false;

    boolean viewReference() default false;
}
