# AndroidBinds
An Android MVVM Library

## Annotation
- @BindText(R.id)
- @BindVisible(R.id)
- @BindChecked(R.id)

## Target
- TextTarget&lt;String&gt;
- VisibleTarget&lt;Integer&gt;
- CheckedTarget&lt;Boolean&gt;

## Support Attrbutes
- Text for views extends TextView, effect getText() and setText().
- Visible for views extends View, effect getVisibility() and setVisibility().
- Checked for views extends CompoundButton, effect isChecked() and setChecked().

## Use
```
    @BindText(R.id.text)
    private TextTarget<String> title;

    @BindVisible(R.id.visiable)
    private VisibleTarget<Integer> visiable;
    
    @BindChecked(R.id.checked)
    private CheckedTarget<Boolean> checked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ValueBinds.inject(this);
        title.setValue("Android Binds");
        visiable.setValue(View.VISIBLE);
        checked.setValue(true);
    }
```
- use ValueBinds.inject() to effect binds.
- use target.setValue() and target.getValue() to handle view attrbutes.

## Convertor
```
    @BindVisible(value = R.id.view, convertor = VisibleToBooleanConvertor.class)
    private VisibleTarget<Boolean> show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ValueBinds.inject(this);
        show.setValue(true);
    }
    
    public class VisibleToBooleanConvertor implements IntConvertor<Boolean> {

        @Override
        public Integer fromValue(Boolean aBoolean) {
            return aBoolean != null && aBoolean ? View.VISIBLE : View.GONE;
        }

        @Override
        public Boolean toValue(Integer v) {
            return v != null && v == View.VISIBLE;
        }
    }
```
- use boolean to map visibility.
- set true to show, false to hide.

## Support Data Class
```
    public class User implements TargetMapSupport {

        @BindText(R.id.username)
        TextTarget<String> username;

        @BindText(R.id.password)
        TextTarget<String> password;
    }
```
```
    @TargetObject
    public class User {

        @BindText(R.id.username)
        TextTarget<String> username;

        @BindText(R.id.password)
        TextTarget<String> password;
    }
```
```
    @TargetObject
    private User user;
    
    public class User {

        @BindText(R.id.username)
        TextTarget<String> username;

        @BindText(R.id.password)
        TextTarget<String> password;
    }
```
- implements TargetMapSupport.
- add @TargetObject on class.
- add @TargetObject on field.

## Support Map Convertor
```
    @TargetObject
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ValueBinds.inject(this);
        
        Map<String, String> map = new HashMap<>();
        map.put("username", "12345");
        map.put("password", "12345678");
        user.fromMap(map);
        
        Map<String, String> map1 = user.toMap();
    }
    
    public class User implements TargetMapSupport {

        @BindText(R.id.username)
        TextTarget<String> username;

        @BindText(R.id.password)
        TextTarget<String> password;
    }
```
## Listener
```
    @BindText(R.id.text)
    private TextTarget<String> title;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ValueBinds.inject(this);
        title.addListener(() -> Toast.makeText(MainActivity.this, title.getValue(), Toast.LENGTH_SHORT).show());
        title.setValue("Android Bind");
    }
```
- it will be called when value changed
- by default, it will not be called if value is the same, use @BindXXX(ignoreSame = false) to force call.
## View Reference
```
    @BindText(value = R.id.text, viewReference = true)
    private TextTarget<String> title;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ValueBinds.inject(this);
        TextView t = title.getView();
    }
```
- use target.getView() to get view reference.
