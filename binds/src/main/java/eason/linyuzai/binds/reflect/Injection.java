package eason.linyuzai.binds.reflect;

import android.app.Activity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import eason.linyuzai.binds.ValueBinds;
import eason.linyuzai.binds.annotation.BindChecked;
import eason.linyuzai.binds.annotation.BindText;
import eason.linyuzai.binds.annotation.BindVisible;
import eason.linyuzai.binds.annotation.TargetObject;
import eason.linyuzai.binds.exception.ValueBindException;
import eason.linyuzai.binds.target.BooleanTarget;
import eason.linyuzai.binds.target.IntTarget;
import eason.linyuzai.binds.target.StringTarget;
import eason.linyuzai.binds.target.ValueTarget;
import eason.linyuzai.binds.target.def.CheckedTarget;
import eason.linyuzai.binds.target.def.TextTarget;
import eason.linyuzai.binds.target.def.VisibleTarget;

public class Injection {
    /**
     * Do inject with reflect
     *
     * @param activity Activity context
     * @param object   Object need inject
     * @throws IllegalAccessException    IllegalAccessException
     * @throws InstantiationException    InstantiationException
     * @throws NoSuchMethodException     NoSuchMethodException
     * @throws InvocationTargetException InvocationTargetException
     * @throws ValueBindException        ValueBindException
     */
    public static void injectAnnotation(Activity activity, Object object) throws IllegalAccessException,
            InvocationTargetException, InstantiationException, NoSuchMethodException {
        Class<?> objCls = object.getClass();
        Field[] fields = objCls.getDeclaredFields();
        for (Field field : fields) {
            Class<?> fieldCls = field.getType();
            if (fieldCls == ValueTarget.class || fieldCls == StringTarget.class || fieldCls == TextTarget.class) {
                injectText(activity, field, object);
            } else if (fieldCls == ValueTarget.class || fieldCls == BooleanTarget.class || fieldCls == CheckedTarget.class) {
                injectChecked(activity, field, object);
            } else if (fieldCls == ValueTarget.class || fieldCls == IntTarget.class || fieldCls == VisibleTarget.class) {
                injectVisible(activity, field, object);
            } else {
                if (fieldCls == objCls) {
                    throw new ValueBindException("Nesting Loop!");
                }
                boolean hasInterface = false;
                boolean hasAnnotation = (null != field.getAnnotation(TargetObject.class)
                        || null != fieldCls.getAnnotation(TargetObject.class));
                if (!hasAnnotation) {
                    Class<?>[] interfaces = fieldCls.getInterfaces();
                    for (Class<?> iCls : interfaces) {
                        if (iCls == TargetMapSupport.class) {
                            hasInterface = true;
                            break;
                        }
                    }
                }
                //recurse inject object
                if (hasAnnotation || hasInterface) {
                    Constructor<?> constructor = fieldCls.getDeclaredConstructor();
                    constructor.setAccessible(true);
                    Object o = constructor.newInstance();
                    field.setAccessible(true);
                    field.set(object, o);
                    injectAnnotation(activity, o);
                }
            }
        }
    }

    /**
     * Inject text for views extends TextView
     *
     * @param activity Activity context
     * @param field    Field needs inject
     * @param object   Object instance
     * @throws IllegalAccessException IllegalAccessException
     */
    @SuppressWarnings("unchecked")
    private static void injectText(Activity activity, Field field, Object object) throws IllegalAccessException {
        BindText bta = field.getAnnotation(BindText.class);
        if (null == bta)
            return;
        int[] ids = bta.value();//views id
        int index = checkIndex(bta.valueIndex(), ids.length);//default value index
        String defaultValue = null;
        TextView[] textViews = new TextView[ids.length];
        //find view
        for (int i = 0; i < ids.length; i++) {
            textViews[i] = activity.findViewById(ids[i]);
            if (i == index) {
                defaultValue = textViews[index].getText().toString();
            }
        }
        //create target
        TextTarget tt = ValueBinds.text(textViews, bta.read(), bta.write(), bta.viewReference());
        tt.setIgnoreSame(bta.ignoreSame());
        try {
            Constructor constructor = bta.convertor().getDeclaredConstructor();
            constructor.setAccessible(true);
            tt.setConvertor((ValueTarget.Convertor) constructor.newInstance());
            tt.setTargetValue(defaultValue);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        field.setAccessible(true);
        field.set(object, tt);
    }

    /**
     * Inject checked for views extends CompoundButton
     *
     * @param activity Activity context
     * @param field    Field needs inject
     * @param object   Object instance
     * @throws IllegalAccessException IllegalAccessException
     */
    @SuppressWarnings("unchecked")
    private static void injectChecked(Activity activity, Field field, Object object) throws IllegalAccessException {
        BindChecked bca = field.getAnnotation(BindChecked.class);
        if (null == bca)
            return;
        int[] ids = bca.value();//views id
        int index = checkIndex(bca.valueIndex(), ids.length);//default value index
        boolean defaultValue = false;
        CompoundButton[] compoundButtons = new CompoundButton[ids.length];
        //find view
        for (int i = 0; i < ids.length; i++) {
            compoundButtons[i] = activity.findViewById(ids[i]);
            if (i == index) {
                defaultValue = compoundButtons[index].isChecked();
            }
        }
        //create target
        CheckedTarget ct = ValueBinds.checked(compoundButtons, bca.read(), bca.write(), bca.viewReference());
        ct.setIgnoreSame(bca.ignoreSame());
        try {
            Constructor constructor = bca.convertor().getDeclaredConstructor();
            constructor.setAccessible(true);
            ct.setConvertor((ValueTarget.Convertor) constructor.newInstance());
            ct.setTargetValue(defaultValue);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        field.setAccessible(true);
        field.set(object, ct);
    }

    /**
     * Inject visible for views extends View
     *
     * @param activity Activity context
     * @param field    Field needs inject
     * @param object   Object instance
     * @throws IllegalAccessException IllegalAccessException
     */
    @SuppressWarnings("unchecked")
    private static void injectVisible(Activity activity, Field field, Object object) throws IllegalAccessException {
        BindVisible bva = field.getAnnotation(BindVisible.class);
        if (null == bva)
            return;
        int[] ids = bva.value();//views id
        int index = checkIndex(bva.valueIndex(), ids.length);//default value index
        int defaultValue = View.VISIBLE;
        View[] views = new View[ids.length];
        //find view
        for (int i = 0; i < ids.length; i++) {
            views[i] = activity.findViewById(ids[i]);
            if (i == index) {
                defaultValue = views[index].getVisibility();
            }
        }
        //create target
        VisibleTarget<?> vt = ValueBinds.visible(views, bva.viewReference());
        vt.setIgnoreSame(bva.ignoreSame());
        try {
            Constructor constructor = bva.convertor().getDeclaredConstructor();
            constructor.setAccessible(true);
            vt.setConvertor((ValueTarget.Convertor) constructor.newInstance());
            vt.setTargetValue(defaultValue);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        field.setAccessible(true);
        field.set(object, vt);
    }

    /**
     * Check Index if out of array length
     *
     * @param index  Default index
     * @param length Array length
     * @return Index checked
     */
    private static int checkIndex(int index, int length) {
        return index >= length ? 0 : index;
    }
}
