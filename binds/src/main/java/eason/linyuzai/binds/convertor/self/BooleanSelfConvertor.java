package eason.linyuzai.binds.convertor.self;

import eason.linyuzai.binds.convertor.BooleanConvertor;

/**
 * Type of target value and control value are Boolean
 */
public class BooleanSelfConvertor implements BooleanConvertor<Boolean> {

    @Override
    public Boolean fromValue(Boolean b) {
        return b;
    }

    @Override
    public Boolean toValue(Boolean v) {
        return v;
    }

    @Override
    public Boolean stringToValue(String v) {
        return Boolean.valueOf(v);
    }

    @Override
    public String valueToString(Boolean b) {
        return String.valueOf(b.booleanValue());
    }
}
