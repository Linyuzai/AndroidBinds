package eason.linyuzai.binds.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import eason.linyuzai.binds.annotation.TargetName;
import eason.linyuzai.binds.annotation.TargetObject;
import eason.linyuzai.binds.target.BooleanTarget;
import eason.linyuzai.binds.target.IntTarget;
import eason.linyuzai.binds.target.StringTarget;
import eason.linyuzai.binds.target.ValueTarget;
import eason.linyuzai.binds.target.attach.CheckedTarget;
import eason.linyuzai.binds.target.attach.TextTarget;
import eason.linyuzai.binds.target.attach.VisibleTarget;

@TargetObject
public interface TargetMapSupport {

    /**
     * Set value from Map
     *
     * @param map Map
     */
    default void fromMap(Map<String, String> map) {
        fromMap(map, true);
    }

    /**
     * Set value from Map
     *
     * @param map         Map
     * @param includeNull If set null when value is null
     */
    default void fromMap(Map<String, String> map, boolean includeNull) {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                TargetName tna = field.getAnnotation(TargetName.class);
                String name = null == tna ? field.getName() : tna.value();
                String mapVal = map.get(name);
                if (includeNull || mapVal != null) {
                    Class<?> fieldCls = field.getType();
                    field.setAccessible(true);
                    if (fieldCls == ValueTarget.class ||
                            fieldCls == StringTarget.class ||
                            fieldCls == IntTarget.class ||
                            fieldCls == BooleanTarget.class ||
                            fieldCls == TextTarget.class ||
                            fieldCls == VisibleTarget.class ||
                            fieldCls == CheckedTarget.class) {
                        Object o = field.get(this);
                        Method m = fieldCls.getMethod("setStringValue", String.class);
                        m.invoke(o, mapVal);
                    } else if (fieldCls == String.class) {
                        field.set(this, mapVal);
                    } else if (fieldCls == int.class || fieldCls == short.class) {
                        field.set(this, mapVal == null ? 0 : Integer.valueOf(mapVal));
                    } else if (fieldCls == long.class) {
                        field.set(this, mapVal == null ? 0L : Long.valueOf(mapVal));
                    } else if (fieldCls == float.class) {
                        field.set(this, mapVal == null ? 0f : Float.valueOf(mapVal));
                    } else if (fieldCls == double.class) {
                        field.set(this, mapVal == null ? 0.0 : Double.valueOf(mapVal));
                    } else if (fieldCls == boolean.class) {
                        field.set(this, mapVal == null ? false : Boolean.valueOf(mapVal));
                    } else if (fieldCls == Integer.class || fieldCls == Short.class) {
                        field.set(this, mapVal == null ? null : Integer.valueOf(mapVal));
                    } else if (fieldCls == Long.class) {
                        field.set(this, mapVal == null ? null : Long.valueOf(mapVal));
                    } else if (fieldCls == Float.class) {
                        field.set(this, mapVal == null ? null : Float.valueOf(mapVal));
                    } else if (fieldCls == Double.class) {
                        field.set(this, mapVal == null ? null : Double.valueOf(mapVal));
                    } else if (fieldCls == Boolean.class) {
                        field.set(this, mapVal == null ? null : Boolean.valueOf(mapVal));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Convert to Map
     *
     * @return Map
     */
    default Map<String, String> toMap() {
        Map<String, String> map = new HashMap<>();
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            TargetName tna = field.getAnnotation(TargetName.class);
            String name = null == tna ? field.getName() : tna.value();
            String mapVal = null;
            try {
                Class<?> fieldCls = field.getType();
                field.setAccessible(true);
                if (fieldCls == ValueTarget.class ||
                        fieldCls == StringTarget.class ||
                        fieldCls == IntTarget.class ||
                        fieldCls == BooleanTarget.class ||
                        fieldCls == TextTarget.class ||
                        fieldCls == VisibleTarget.class ||
                        fieldCls == CheckedTarget.class) {
                    Object o = field.get(this);
                    Method m = fieldCls.getMethod("getStringValue");
                    mapVal = (String) m.invoke(o);
                } else if (fieldCls == String.class) {
                    mapVal = (String) field.get(this);
                } else if (fieldCls == char.class) {
                    mapVal = String.valueOf((char) field.get(this));
                } else if (fieldCls == int.class || fieldCls == short.class) {
                    mapVal = String.valueOf((int) field.get(this));
                } else if (fieldCls == long.class) {
                    mapVal = String.valueOf((long) field.get(this));
                } else if (fieldCls == float.class) {
                    mapVal = String.valueOf((float) field.get(this));
                } else if (fieldCls == double.class) {
                    mapVal = String.valueOf((double) field.get(this));
                } else if (fieldCls == boolean.class) {
                    mapVal = String.valueOf((boolean) field.get(this));
                } else if (fieldCls == Integer.class || fieldCls == Short.class) {
                    Integer integer = (Integer) field.get(this);
                    if (integer != null) {
                        mapVal = String.valueOf(integer.intValue());
                    }
                } else if (fieldCls == Long.class) {
                    Long aLong = (Long) field.get(this);
                    if (aLong != null) {
                        mapVal = String.valueOf(aLong.longValue());
                    }
                } else if (fieldCls == Float.class) {
                    Float aFloat = (Float) field.get(this);
                    if (aFloat != null) {
                        mapVal = String.valueOf(aFloat.floatValue());
                    }
                } else if (fieldCls == Double.class) {
                    Double aDouble = (Double) field.get(this);
                    if (aDouble != null) {
                        mapVal = String.valueOf(aDouble.doubleValue());
                    }
                } else if (fieldCls == Boolean.class) {
                    Boolean aBoolean = (Boolean) field.get(this);
                    if (aBoolean != null) {
                        mapVal = String.valueOf(aBoolean.booleanValue());
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            if (mapVal != null) {
                map.put(name, mapVal);
            }
        }
        return map;
    }

    /**
     * Set value from Java Bean
     *
     * @param entity Java bean
     */
    default void fromEntity(Object entity) {
        fromEntity(entity, true);
    }

    /**
     * Set value from Java Bean
     *
     * @param entity      Java bean
     * @param includeNull If set null when value is null
     */
    default void fromEntity(Object entity, boolean includeNull) {
        Field[] fields = entity.getClass().getDeclaredFields();
        Map<String, String> map = new HashMap<>();
        for (Field field : fields) {
            try {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                Class<?> fieldCls = field.getType();
                field.setAccessible(true);
                Object val = field.get(entity);
                if (val == null) {
                    continue;
                }
                if (fieldCls == String.class) {
                    map.put(field.getName(), (String) val);
                } else if (fieldCls == int.class || fieldCls == Integer.class
                        || fieldCls == short.class || fieldCls == Short.class) {
                    map.put(field.getName(), String.valueOf((int) val));
                } else if (fieldCls == long.class || fieldCls == Long.class) {
                    map.put(field.getName(), String.valueOf((long) val));
                } else if (fieldCls == float.class || fieldCls == Float.class) {
                    map.put(field.getName(), String.valueOf((float) val));
                } else if (fieldCls == double.class || fieldCls == Double.class) {
                    map.put(field.getName(), String.valueOf((double) val));
                } else if (fieldCls == char.class || fieldCls == Character.class) {
                    map.put(field.getName(), String.valueOf((char) val));
                } else if (fieldCls == boolean.class || fieldCls == Boolean.class) {
                    map.put(field.getName(), String.valueOf((boolean) val));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        fromMap(map, includeNull);
    }

    /**
     * Convert to Java Bean
     *
     * @param cls Class of Java Bean
     * @param <T> Type of Java Bean
     * @return Java Bean
     */
    default <T> T toEntity(Class<T> cls) {
        T entity = null;
        try {
            entity = cls.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        Map<String, String> map = toMap();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            try {
                int mod = field.getModifiers();
                if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                    continue;
                }
                Class<?> fieldCls = field.getType();
                field.setAccessible(true);
                String val = map.get(field.getName());
                if (val != null) {
                    if (fieldCls == String.class) {
                        field.set(entity, val);
                    } else if (fieldCls == int.class || fieldCls == Integer.class
                            || fieldCls == short.class || fieldCls == Short.class) {
                        field.set(entity, Integer.valueOf(val));
                    } else if (fieldCls == long.class || fieldCls == Long.class) {
                        field.set(entity, Long.valueOf(val));
                    } else if (fieldCls == float.class || fieldCls == Float.class) {
                        field.set(entity, Float.valueOf(val));
                    } else if (fieldCls == double.class || fieldCls == Double.class) {
                        field.set(entity, Double.valueOf(val));
                    } else if (fieldCls == boolean.class || fieldCls == Boolean.class) {
                        field.set(entity, Boolean.valueOf(val));
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return entity;
    }
}
