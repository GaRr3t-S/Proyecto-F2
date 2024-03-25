package uvg.edu.gt;

/**
 * La clase LispAtom representa un átomo en LISP, que es una entidad indivisible.
 * Un átomo puede ser un número, un string, un booleano, etc.
 * Esta clase extiende a LispValue, lo que significa que el atom es un tipo de valor en LISP.
 */
public class LispAtom extends LispValue {
    /**
     * El valor del atom. Puede ser cualquier objeto.
     */
    public final Object value;

    /**
     * Constructor de la clase LispAtom.
     * @param value El valor del atom.
     */
    public LispAtom(Object value) {
        this.value = value;
    }

    /**
     * Evalúa el atom en el entorno dado.
     * Si el atom es una variable, busca su valor en el entorno.
     * Si el atom es una constante, devuelve el atom mismo.
     * @param environment El entorno en el que se evalúa el atom.
     * @return El valor del atom.
     * @throws Exception Si el atom es una variable y no está definida en el entorno.
     */
    @Override
    public LispValue eval(Environment environment) throws Exception {
        return environment.lookup(this);
    }

    /**
     * Devuelve una representación en string del atom.
     * @return Una representación en string del atom.
     */
    @Override
    public String toString() {
        return value.toString();
    }
}
