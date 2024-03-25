package uvg.edu.gt;

/**
 * La clase abstracta LispValue representa un valor en LISP.
 * Un valor puede ser un átomo, una lista, una función, etc.
 */
public abstract class LispValue {
    /**
     * Evalúa este valor en el entorno dado.
     * El comportamiento exacto de este método depende de la subclase concreta de LispValue.
     * @param environment El entorno en el que se evalúa el valor.
     * @return El resultado de evaluar el valor.
     * @throws Exception Si ocurre un error durante la evaluación.
     */
    public abstract LispValue eval(Environment environment) throws Exception;

    /**
     * Devuelve una representación en string de este valor.
     * El comportamiento exacto de este método depende de la subclase concreta de LispValue.
     * @return Una representación en string de este valor.
     */
    public abstract String toString();
}
