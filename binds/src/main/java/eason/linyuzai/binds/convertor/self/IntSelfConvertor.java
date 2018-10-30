package eason.linyuzai.binds.convertor.self;

import eason.linyuzai.binds.convertor.IntConvertor;

public class IntSelfConvertor implements IntConvertor<Integer> {

    @Override
    public Integer fromValue(Integer integer) {
        return integer;
    }

    @Override
    public Integer toValue(Integer v) {
        return v;
    }

    @Override
    public Integer stringToValue(String v) {
        return Integer.valueOf(v);
    }

    @Override
    public String valueToString(Integer integer) {
        return String.valueOf(integer.intValue());
    }
}
