
public abstract class LispValue {
    public abstract LispValue eval(Environment environment) throws Exception;
    public abstract String toString();
}

