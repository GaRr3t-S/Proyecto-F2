import java.util.List;
import java.util.ArrayList;

// Esta es una clase llamada LispList que extiende a LispValue.
//public class LispList extends LispValue {
//    private final List<LispValue> elements; // Esta es una lista de elementos de tipo LispValue.

// Este es el constructor de la clase LispList.
//    public LispList(List<LispValue> elements) {
//        this.elements = elements; // Aquí se asignan los elementos pasados como parámetros a la variable de instancia elements.
//    }

// Este es un método llamado eval que toma un objeto de tipo Environment como parámetro.
//    public LispValue eval(Environment environment) throws Exception {
//        if (elements.isEmpty()) { // Si la lista de elementos está vacía, se devuelve el objeto actual.
//            return this;
//        }

// Aquí se obtiene el primer elemento de la lista y se verifica si es una instancia de LispAtom.
//        LispValue first = elements.get(0);
//        if (first instanceof LispAtom) {
//            LispAtom atom = (LispAtom) first;
//            if (atom.value.equals("quote")) { // Si el valor del átomo es "quote", se devuelve el segundo elemento de la lista.
//                return elements.get(1);
//            } else if (atom.value.equals("if")) { // Si el valor del átomo es "if", se evalúa la condición y se devuelve el resultado correspondiente.
//                LispValue condition = elements.get(1).eval(environment);
//                if (condition instanceof LispAtom && ((LispAtom) condition).value.equals(true)) {
//                    return elements.get(2).eval(environment);
//                } else {
//                    return elements.get(3).eval(environment);
//                }
//            } else if (atom.value.equals("def")) { // Si el valor del átomo es "def", se define una nueva variable en el entorno y se devuelve su valor.
//                LispAtom symbol = (LispAtom) elements.get(1);
//                LispValue value = elements.get(2).eval(environment);
//                environment.add(symbol.value, value);
//                return value;
//            }
//        }

// Si el primer elemento no es un átomo de Lisp o no es una de las formas especiales que conocemos,
// asumimos que es una función y evaluamos sus argumentos.
//        List<LispValue> args = new ArrayList<>();
//        for (int i = 1; i < elements.size(); i++) {
//            args.add(elements.get(i).eval(environment));
//        }

// Buscamos la función en el entorno y la aplicamos a los argumentos.
//        LispValue func = environment.get(first.toString());
//        if (func instanceof LispFunction){
//            return ((LispFunction) func).apply(args);
//        } else {
//            throw new Exception("Unknown function: " + first);
//        }
//    }

// Este es el método toString que devuelve una representación de cadena de la lista de Lisp.
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append('(');
//        for (int i = 0; i < elements.size(); i++) {
//            if (i > 0) {
//                sb.append(' ');
//            }
//            sb.append(elements.get(i).toString());
//        }
//        sb.append(')');
//        return sb.toString();
//    }
//}
