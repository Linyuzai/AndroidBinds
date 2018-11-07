package eason.linyuzai.binds.target;

import java.util.List;

/**
 * Mapping control value and views target value
 *
 * @param <V> Control value type
 * @param <T> Target value type
 */
public interface ValueTarget<V, T> {
    /**
     * Get control value
     *
     * @return Control value
     */
    V getValue();

    /**
     * Set control value
     *
     * @param value Control value
     */
    void setValue(V value);

    T getTargetValue();

    /**
     * Set target value
     *
     * @param value Target value
     */
    void setTargetValue(T value);

    String getStringValue();

    void setStringValue(String value);

    void syncValue();

    boolean isIgnoreSame();

    void setIgnoreSame(boolean ignoreSame);

    void setConvertor(Convertor<V, T> convertor);

    void addListener(Listener<V, T> listener);

    List<Listener<V, T>> getListeners();

    /**
     * An interface to notify target source
     *
     * @param <T> Target value
     */
    interface Setter<T> {

        /**
         * Set target value to target source
         *
         * @param v Target value
         */
        void onSet(T v);
    }

    /**
     * convert target value to control value
     * and
     * convert control value to target value
     *
     * @param <V>Type of control value
     * @param <T>Type of target value
     */
    interface Convertor<V, T> {

        /**
         * convert control value to target value
         *
         * @param v Control value
         * @return Target value
         */
        T fromValue(V v);

        /**
         * convert target value to control value
         *
         * @param v Target source
         * @return Control value
         */
        V toValue(T v);

        /**
         * To support Map convert
         *
         * @param v Map string value
         * @return Control value
         */
        default V stringToValue(String v) {
            return null;
        }

        /**
         * To support Map convert
         *
         * @param v Control value
         * @return Map string value
         */
        default String valueToString(V v) {
            return null;
        }
    }

    /**
     * Listener for value changed
     *
     * @param <V>Type of control value
     * @param <T>Type of target value
     */
    interface Listener<V, T> {
        /**
         * Call this method when value changed
         *
         * @param target ValueTarget
         */
        void onListen(ValueTarget<V, T> target);
    }
}
