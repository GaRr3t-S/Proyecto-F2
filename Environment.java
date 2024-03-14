import java.util.HashMap;
import java.util.Map;

public class Environment {
    private final Map<String, LispValue> bindings;

    public Environment() {
        bindings = new HashMap<>();
    }

    public void addBinding(String name, LispValue value) {
        bindings.put(name, value);
    }

    public LispValue lookup(LispAtom atom) throws Exception {
        LispValue value = bindings.get(atom.toString());
        if (value == null) {
            throw new Exception("Undefined variable: " + atom);
        }
        return value;
    }
}