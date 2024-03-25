package uvg.edu.gt;

import java.util.HashMap;
import java.util.Map;

/**
 * La clase Environment representa el entorno de ejecución para un intérprete LISP.
 * Mantiene un mapa de nombres de variables a sus valores correspondientes.
 */
public class Environment {
    /**
     * Un mapa de nombres de variables a sus valores correspondientes.
     */
    private final Map<String, LispValue> bindings;

    /**
     * Constructor de la clase Environment.
     * Inicializa el mapa de enlaces a un nuevo HashMap.
     */
    public Environment() {
        bindings = new HashMap<>();
    }

    /**
     * Añade un nuevo enlace al entorno.
     * @param name El nombre de la variable.
     * @param value El valor de la variable.
     */
    public void addBinding(String name, LispValue value) {
        bindings.put(name, value);
    }

    /**
     * Busca el valor de una variable en el entorno.
     * @param atom El nombre de la variable.
     * @return El valor de la variable.
     * @throws Exception Si la variable no está definida en el entorno.
     */
    public LispValue lookup(LispAtom atom) throws Exception {
        LispValue value = bindings.get(atom.toString());
        if (value == null) {
            throw new Exception("Undefined variable: " + atom);
        }
        return value;
    }
}
