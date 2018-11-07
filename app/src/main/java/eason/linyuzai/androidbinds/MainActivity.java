package eason.linyuzai.androidbinds;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

import eason.linyuzai.binds.ValueBinds;
import eason.linyuzai.binds.annotation.BindText;
import eason.linyuzai.binds.annotation.BindVisible;
import eason.linyuzai.binds.annotation.TargetObject;
import eason.linyuzai.binds.convertor.IntConvertor;
import eason.linyuzai.binds.reflect.TargetMapSupport;
import eason.linyuzai.binds.target.ValueTarget;
import eason.linyuzai.binds.target.def.TextTarget;
import eason.linyuzai.binds.target.def.VisibleTarget;

public class MainActivity extends AppCompatActivity {

    @BindText(R.id.text)
    private TextTarget<String> title;

    @TargetObject
    private User user;

    @BindVisible(value = R.id.view, convertor = MyConvertor.class)
    private VisibleTarget<String> view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ValueBinds.inject(this);
        title.setValue("Android Bind");
        view.setValue("0");
        user.username.addListener(ValueTarget::syncValue);
        //user.username.addListener(() -> Toast.makeText(MainActivity.this, user.toString(), Toast.LENGTH_SHORT).show());
        findViewById(R.id.button).setOnClickListener(v -> {
            view.setValue(view.getValue().equals("0") ? "8" : "0");
            //Toast.makeText(MainActivity.this, view.toString(), Toast.LENGTH_SHORT).show();
            Map<String, String> map = new HashMap<>();
            map.put("username", "12345");
            map.put("password", "12345678");
            user.fromMap(map);
            //Toast.makeText(MainActivity.this, title.getView().toString(), Toast.LENGTH_SHORT).show();
        });
    }

    private static class MyConvertor implements IntConvertor<String> {

        @Override
        public Integer fromValue(String s) {
            return Integer.valueOf(s);
        }

        @Override
        public String toValue(Integer v) {
            return String.valueOf(v.intValue());
        }
    }

    private static class User implements TargetMapSupport {

        @BindText({R.id.username, R.id.username2})
        TextTarget<String> username;

        @BindText(R.id.password)
        TextTarget<String> password;

        @NonNull
        @Override
        public String toString() {
            return "User{" +
                    "username=" + username +
                    ", password=" + password +
                    '}';
        }
    }
}
