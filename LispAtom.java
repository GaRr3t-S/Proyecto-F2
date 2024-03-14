public class LispAtom extends LispValue {
    public final Object value;

    public LispAtom(Object value) {
        this.value = value;
    }

    @Override
    public LispValue eval(Environment environment) throws Exception {
        return environment.lookup(this);
    }

    @Override
    public String toString() {
        return value.toString();
    }
}