package eason.linyuzai.binds;

import android.support.annotation.NonNull;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import eason.linyuzai.binds.target.ValueTarget;

/**
 * Mapping control value and views target value
 *
 * @param <V> Control value type
 * @param <T> Target value type
 */
class ValueTargetImpl<V, T> implements ValueTarget<V, T> {
    private V value;
    private boolean ignoreSame;
    private List<Setter<T>> setters = new ArrayList<>();
    private Convertor<V, T> convertor;
    private List<Listener> listeners = new ArrayList<>();
    private List<WeakReference<? extends View>> viewReferences = new ArrayList<>();

    /**
     * Get control value
     *
     * @return Control value
     */
    @Override
    public V getValue() {
        return value;
    }

    /**
     * Set control value
     *
     * @param value Control value
     */
    @Override
    public void setValue(V value) {
        setValue(value, true);
    }

    /**
     * Set control value
     *
     * @param value Control value
     * @param set   If set Target source
     */
    public void setValue(V value, boolean set) {
        setValueFinal(value, convertor.fromValue(value), set);
    }

    @Override
    public T getTargetValue() {
        return convertor.fromValue(value);
    }

    /**
     * Set target value
     *
     * @param value Target value
     */
    @Override
    public void setTargetValue(T value) {
        setTargetValue(value, true);
    }

    /**
     * Set target value
     *
     * @param value Target value
     * @param set   If set Target source
     */
    public void setTargetValue(T value, boolean set) {
        setValueFinal(convertor.toValue(value), value, set);
    }

    /**
     * To support Map<String, String> convert
     *
     * @return Map string value
     */
    @Override
    public String getStringValue() {
        return convertor.valueToString(value);
    }

    /**
     * To support Map<String, String> convert
     *
     * @param value Map string value
     */
    @Override
    public void setStringValue(String value) {
        setValue(convertor.stringToValue(value));
    }

    @Override
    public void syncValue() {
        setValue(value);
    }

    @Override
    public boolean isIgnoreSame() {
        return ignoreSame;
    }

    @Override
    public void setIgnoreSame(boolean ignoreSame) {
        this.ignoreSame = ignoreSame;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<WeakReference<? extends View>> getViewReferences() {
        return viewReferences;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <U extends View> U getView(int index) {
        return (U) viewReferences.get(index).get();
    }

    @Override
    public <U extends View> U getView() {
        return getView(0);
    }

    @Override
    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    @Override
    public List<Listener> getListeners() {
        return listeners;
    }

    public void addViewReference(WeakReference<? extends View> viewReference) {
        this.viewReferences.add(viewReference);
    }

    public void addSetter(Setter<T> setter) {
        setters.add(setter);
    }

    @Override
    public void setConvertor(Convertor<V, T> convertor) {
        this.convertor = convertor;
    }

    private void setValueFinal(V v, T t, boolean set) {
        if (ignoreSame && checkSame(v))
            return;
        this.value = v;
        if (!set) return;
        for (Setter<T> setter : setters) {
            if (null == setter)
                continue;
            setter.onSet(t);
        }
    }

    private boolean checkSame(V value) {
        return this.value == value || (null != this.value && this.value.equals(value));
    }

    @NonNull
    @Override
    public String toString() {
        return value + "";
    }
}
