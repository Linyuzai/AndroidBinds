package eason.linyuzai.binds;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;

import eason.linyuzai.binds.reflect.Injection;
import eason.linyuzai.binds.target.ValueTarget;
import eason.linyuzai.binds.target.attach.CheckedTarget;
import eason.linyuzai.binds.target.attach.TextTarget;
import eason.linyuzai.binds.target.attach.VisibleTarget;

public final class ValueBinds {

    /**
     * Call this on Activity#onCreate()
     *
     * @param activity Activity context
     */
    public static void inject(Activity activity) {
        try {
            Injection.injectAnnotation(activity, activity);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Call this on Fragment#onCreateView()
     *
     * @param fragment android.app.Fragment
     */
    public static void inject(android.app.Fragment fragment) {
        try {
            Injection.injectAnnotation(fragment.getActivity(), fragment);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Call this on Fragment#onCreateView()
     *
     * @param fragment android.support.v4.app.Fragment
     */
    public static void inject(android.support.v4.app.Fragment fragment) {
        try {
            Injection.injectAnnotation(fragment.getActivity(), fragment);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * Bind text text
     * Set target value by control value
     * Get control value by target source
     *
     * @param textViews     Target source
     * @param read          Can get value
     * @param write         Can set value
     * @param viewReference Hold view reference
     * @param <V>           Control value type
     * @return TextTarget
     * @see TextTarget
     */
    public static <V> TextTarget<V> text(TextView[] textViews, boolean read, boolean write, boolean viewReference) {
        final TextTargetImpl<V> target = new TextTargetImpl<>();
        for (TextView textView : textViews) {
            if (null == textView)
                continue;
            WeakReference<TextView> twr = new WeakReference<>(textView);
            if (read) {
                textView.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        target.setTargetValue(s.toString(), false);
                        performListener(target);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        //target.setTargetValue(s.toString(), false);
                        //performListener(target);
                    }
                });
            }
            if (write) {
                target.addSetter(text -> {
                    TextView t = twr.get();
                    if (null == t || t.getText().toString().equals(text))
                        return;
                    t.post(() -> t.setText(text));
                });
            }
            if (viewReference) {
                target.addViewReference(twr);
            }
        }
        return target;
    }

    /**
     * Bind view visible
     * Set target value by control value
     *
     * @param views         Target source
     * @param viewReference Hold view reference
     * @param <V>           Control value type
     * @return VisibleTarget
     * @see VisibleTarget
     */
    public static <V> VisibleTarget<V> visible(View[] views, boolean viewReference) {
        final VisibleTargetImpl<V> target = new VisibleTargetImpl<>();
        for (View view : views) {
            if (null == view)
                continue;
            WeakReference<View> vwr = new WeakReference<>(view);
            target.addSetter(visible -> {
                View v = vwr.get();
                if (null == v || null == visible || v.getVisibility() == visible)
                    return;
                v.post(() -> v.setVisibility(visible));
            });
            if (viewReference) {
                target.addViewReference(vwr);
            }
        }
        return target;
    }

    /**
     * Bind checked value
     * Set target value by control value
     * Get control value by target source
     *
     * @param compoundButtons Target source
     * @param read            Can get value
     * @param write           Can set value
     * @param viewReference   Hold view reference
     * @param <V>             Control value type
     * @return CheckedTarget
     * @see CheckedTarget
     */
    public static <V> CheckedTarget<V> checked(CompoundButton[] compoundButtons, boolean read, boolean write, boolean viewReference) {
        final CheckedTargetImpl<V> target = new CheckedTargetImpl<>();
        for (CompoundButton compoundButton : compoundButtons) {
            if (null == compoundButton)
                continue;
            WeakReference<CompoundButton> cbwr = new WeakReference<>(compoundButton);
            if (read) {
                compoundButton.setOnCheckedChangeListener((v, isChecked) -> {
                    target.setTargetValue(isChecked, false);
                    performListener(target);
                });
            }
            if (write) {
                target.addSetter(checked -> {
                    CompoundButton cb = cbwr.get();
                    if (null == cb || null == checked || cb.isChecked() == checked)
                        return;
                    cb.post(() -> cb.setChecked(checked));
                });
            }
            if (viewReference) {
                target.addViewReference(cbwr);
            }
        }
        return target;
    }

    /**
     * Perform listener
     *
     * @param target which listeners need perform
     */
    private static void performListener(ValueTarget<?, ?> target) {
        for (ValueTarget.Listener listener : target.getListeners()) {
            if (null == listener)
                continue;
            listener.onListen();
        }
    }
}
