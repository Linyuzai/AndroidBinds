package eason.linyuzai.binds.reflect;

@Deprecated
public class Reflect {

    public static class ReflectTarget {
        private Object object;

        private ReflectTarget(Object object) {
            this.object = object;
        }

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }
    }

    public static ReflectTarget on(Object o) {
        return new ReflectTarget(o);
    }
}
