package eason.linyuzai.binds.convertor.attach;

import android.view.View;

import eason.linyuzai.binds.convertor.IntConvertor;

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
