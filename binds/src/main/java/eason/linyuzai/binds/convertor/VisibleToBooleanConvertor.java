package eason.linyuzai.binds.convertor;

import android.view.View;

/**
 * For visible of View
 * Type of target value is Integer and type of control value is Boolean
 */
public class VisibleToBooleanConvertor implements IntConvertor<Boolean> {

    @Override
    public Integer fromValue(Boolean aBoolean) {
        return aBoolean != null && aBoolean ? View.VISIBLE : View.GONE;
    }

    @Override
    public Boolean toValue(Integer v) {
        return v != null && v == View.VISIBLE;
    }

    @Override
    public Boolean stringToValue(String v) {
        return Boolean.valueOf(v);
    }

    @Override
    public String valueToString(Boolean aBoolean) {
        return String.valueOf(aBoolean.booleanValue());
    }
}
