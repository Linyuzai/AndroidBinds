package eason.linyuzai.binds.convertor.self;

import eason.linyuzai.binds.convertor.StringConvertor;

/**
 * Type of target value and control value are String
 */
public class StringSelfConvertor implements StringConvertor<String> {

    @Override
    public String fromValue(String s) {
        return s;
    }

    @Override
    public String toValue(String v) {
        return v;
    }

    @Override
    public String valueToString(String v) {
        return v;
    }

    @Override
    public String stringToValue(String s) {
        return s;
    }

}
