package uvg.edu.gt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/**
 * Clase para interpretar operaciones Simple Lisp,
 */

public class LispInterpreter {
    private static LispInterpreter interpreter;
    private Map<String, Object> globalEnv = new HashMap<>();
    private Stack<Map<String, Object>> localEnvs = new Stack<>();

    /**
     * Constructor privado para inicializar el intérprete y configurar el entorno global.
     */
    private LispInterpreter() {
        globalEnv.put("T", true);
        globalEnv.put("NIl", false);
        globalEnv.put("+", null);
        globalEnv.put("-", null);
        globalEnv.put("*", null);
        globalEnv.put("/", null);
        globalEnv.put("<", null);
        globalEnv.put(">", null);
        globalEnv.put("=", null);
        globalEnv.put("QUOTE", null);
        globalEnv.put("ATOM", null);
        globalEnv.put("LIST", null);
        globalEnv.put("EQUAL", null);
        globalEnv.put("COND", null);
        globalEnv.put("SETQ", null);
        globalEnv.put("DEFUN", null);

        localEnvs.push(globalEnv);

        // Carga de funciones lógicas predefinidas
        String or = "defun or (h k) (cond ((= h t) t) ((= k t) t) (t nil))";
        String and = "defun and (h k) (cond ((= h nil) nil) ((= k nil) nil) (t t))";
        String not = "defun not (h) (cond (h nil) (t t))";
        try {
            eval(Str2Lisp
                    (or)); eval(Str2Lisp
                    (and)); eval(Str2Lisp
                    (not));
        } catch (Exception e) {
            System.err.println("Error de carga. No se puede usar puertas lógicas.");
        }

    }

    /**
     * Obtiene una instancia única del intérprete (Singleton).
     *
     * @return La instancia del intérprete.
     */
    public static LispInterpreter getInterpreter() {
        if (interpreter == null) {
            interpreter = new LispInterpreter();
        }
        return interpreter;
    }

    /**
     * Obtiene el ambiente local actual del intérprete.
     *
     * @return El ambiente local actual.
     */
    public Stack<Map<String, Object>> getLocalEnvs() {
        return localEnvs;
    }

    /**
     * Actualiza el ambiente global con el ambiente local actual.
     */
    public void updateGlobalEnv() {
        globalEnv = localEnvs.peek();
    }

    /**
     * Convierte una expresión en formato de cadena a una lista de elementos Lisp.
     *
     * @param expression La expresión en formato de cadena.
     * @return La lista de elementos Lisp resultante.
     * @throws Exception Si ocurre un error durante la conversión.
     */
    public List<?> Str2Lisp(String expression) throws Exception {
        boolean inList = false;
        boolean inArg = false;
        boolean inString = false;
        boolean inComentary = false;
        int nlists = 0;
        String arg = "";
        List<Object> list = new ArrayList<>();


        for (String c : expression.split("")) {
            if (c.isBlank()) {
                if (inArg && !inList && !inString) {
                    list.add(parseObject(arg));
                    inArg = false;
                    arg = "";

                } else if (inComentary && c.equals("\n")) {
                    inComentary = false;
                } else if (inArg) {
                    arg = arg.concat(c);
                } else {
                    continue;
                }
            } else {
                if (c.equals(";")) {
                    inComentary = true;
                    if (inArg) {
                        list.add(parseObject(arg));
                        arg = "";
                        inArg = false;
                    }
                    continue;
                }
                if (inComentary) {
                    continue;
                }
                if (c.equals("(") && !inString) {
                    if (inList) {
                        nlists += 1;
                        arg = arg.concat(c);
                    } else if (inArg) {
                        inArg = false; list.add(parseObject(arg)); arg = "";
                    }
                    inList = true;
                    inArg = true;
                    continue;
                }
                inArg = true;


                if (c.equals("\"") && !inList) {
                    inString = !inString;
                    arg = arg.concat(c);
                    if (!inString) {
                        list.add(arg); arg = ""; inArg = false;
                    }
                } else if (c.equals(")") && !inString) {
                    if (!inList) {
                        throw new Exception();
                    }
                    if (nlists > 0) {
                        nlists -= 1;
                        arg = arg.concat(c); continue;
                    }
                    inList = false; inArg = false;
                    list.add(Str2Lisp
                            (arg));
                    arg = "";

                } else {
                    arg = arg.concat(c);
                }
            }
        }
        if (inList || inString){// Muestra un error si una lista no se cierra
            throw new Exception();
        }
        if (inArg) {
            list.add(parseObject(arg));
        }
        return list;
    }

    /**
     * Convierte un elemento de tipo String en su correspondiente tipo de datos en Lisp (Integer, Double, Boolean o String).
     * @param element El elemento String que se va a convertir.
     * @return El elemento convertido al tipo correspondiente en Lisp (Integer, Double, Boolean o String).
     */
    public Object parseObject(String element) {
        Object toType = null;
        try {
            toType = Integer.parseInt(element);
        } catch (Exception e) {
            try {
                toType = Double.parseDouble(element);
            } catch (Exception e2) {
                if (element.toUpperCase().equals("T")) {
                    toType = true;
                } else if (element.toUpperCase().equals("NIL")){
                    toType = false;
                } else {
                    toType = (String) element;
                }
            }
        }
        return toType;
    }

    /**
     * Evalúa una lista de expresiones Lisp y ejecuta la operación correspondiente.
     * @param list La lista de expresiones Lisp a evaluar.
     * @return El resultado de la evaluación de la lista de expresiones.
     * @throws Exception Si ocurre un error durante la evaluación.
     */
    public Object eval(List<?> list) throws Exception {
        String first = (String) list.get(0); // Se obtiene el comando inicial
        int numberOfParams = list.size() - 1;
        globalEnv = localEnvs.peek();
        switch (first.toUpperCase()) { // evalúa si es una función predeterminada, sino llama al ambiente para una nueva
            case "+":
                return sum(list.subList(1, list.size()));
            case "-":
                return substract(list.subList(1, list.size()));
            case "*":
                return product(list.subList(1, list.size()));
            case "/":
                return divide(list.subList(1, list.size()));
            case ">":
                return moreThan(list.subList(1, list.size()));
            case "<":
                return lessThan(list.subList(1, list.size()));
            case "=":
                return equal(list.subList(1, list.size()));
            case "EQUAL":
                if (numberOfParams != 2) {
                    throw new Exception();
                }
                return equal(list.subList(1, list.size()));
            case "ATOM":
                if (numberOfParams != 1) {
                    throw new Exception();
                }
                return atom(list.subList(1, list.size()));
            case "LIST":
                return list(list.subList(1, list.size()));
            case "COND":
                return cond(list.subList(1, list.size()));
            case "QUOTE":
                if (numberOfParams != 1) {
                    throw new Exception();
                }
                return quote(list.subList(1, list.size()));
            case "SETQ":
                if (numberOfParams != 2) {
                    throw new Exception();
                }
                setq(list.subList(1, list.size()));
                return null;
            case "DEFUN":
                if (numberOfParams != 3) {
                    throw new Exception();
                }
                defun(list.subList(1, list.size()));
                return null;

            default:
                Object item = globalEnv.get(first);
                if (item == null) {
                    throw new Exception("Variable no definida: " + first);
                }
                if (item.getClass() == LispFunction.class) {
                    if (numberOfParams == ((LispFunction) item).getParams().size()) {
                        Object result = ((LispFunction) item).apply(list.subList(1, list.size()));
                        return result;
                    } else {
                        throw new Exception("Número incorrecto de parámetros para la función: " + first);
                    }
                } else {
                    throw new Exception("Nombre no reconocido: " + first);
                }
        }

    }
    /**
     * Obtiene el valor real de un argumento en Lisp, ya sea evaluándolo como una lista o buscándolo en el ambiente global.
     * @param arg El argumento a procesar.
     * @return El valor real del argumento en Lisp.
     * @throws Exception Si ocurre un error al obtener el argumento.
     */
    public Object getArg(Object arg) throws Exception {
        if (List.class.isAssignableFrom(arg.getClass())) {
            return eval((List<?>) arg);
        } else if (arg.getClass() == String.class && globalEnv.containsKey(arg)) {
            return globalEnv.get(arg);
        }
        return arg;
    }

    /**
     * Realiza la operación de suma en Lisp para una lista de argumentos.
     * @param args La lista de argumentos para sumar.
     * @return El resultado de la suma de los argumentos.
     * @throws Exception Si ocurre un error durante la suma.
     */
    public Object sum(List<?> args) throws Exception {
        Object result = 0; boolean stillInt = true;
        for (Object o : args) {
            Object tempResult;
            if (List.class.isAssignableFrom(o.getClass())) {
                tempResult = eval((List<?>) o );
            } else if (o.getClass() == String.class && globalEnv.containsKey(o)) {
                tempResult = globalEnv.get(o);
            } else {
                tempResult = o;
            }
            if (tempResult.getClass() == Integer.class) {
                if (stillInt) {
                    result = (Integer) result + (Integer) tempResult;
                } else {
                    result = (Double) result + (Integer) tempResult;
                }

            } else {
                if (stillInt) {
                    result = (Integer) result + (Double) tempResult;
                    stillInt = false;
                } else {
                    result = (Double) result + (Double) tempResult;
                }
            }


        }
        return result;
    }

    /**
     * Realiza la operación de resta en Lisp para una lista de argumentos.
     * @param args La lista de argumentos para restar.
     * @return El resultado de la resta de los argumentos.
     * @throws Exception Si ocurre un error durante la resta.
     */
    public Object substract(List<?> args) throws Exception {
        if (args.isEmpty()) {
            throw new Exception("No arguments for subtraction");
        }

        Object result = getArg(args.get(0));
        if (! (result instanceof Number)) {
            throw new Exception("No retorna un número: " + args.get(0));
        }

        for (int i = 1; i < args.size(); i++) {
            Object next = getArg(args.get(i));
            if (! (next instanceof Number)) {
                throw new Exception("No retorna un número: " + args.get(i));
            }
            result = ((Number) result).doubleValue() - ((Number) next).doubleValue();
        }

        return result;
    }

    /**
     * Realiza la operación de multiplicación en Lisp para una lista de argumentos.
     * @param args La lista de argumentos para multiplicar.
     * @return El resultado de la multiplicación de los argumentos.
     * @throws Exception Si ocurre un error durante la multiplicación.
     */
    public Object product(List<?> args) throws Exception {
        if (args.isEmpty()) {
            throw new Exception("No arguments for multiplication");
        }

        double result = 1.0;

        for (Object arg : args) {
            result *= ((Number) getArg(arg)).doubleValue();
        }

        return result;
    }

    /**
     * Realiza la operación de división en Lisp para una lista de argumentos.
     * @param args La lista de argumentos para dividir.
     * @return El resultado de la división de los argumentos.
     * @throws Exception Si ocurre un error durante la división.
     */
    public Object divide(List<?> args) throws Exception {
        if (args.size() != 2) {
            throw new Exception("Expected exactly two arguments for division");
        }

        double numerator = ((Number) getArg(args.get(0))).doubleValue();
        double denominator = ((Number) getArg(args.get(1))).doubleValue();

        if (denominator == 0) {
            throw new Exception("Cannot divide by zero");
        }

        return numerator / denominator;
    }
    /**
     * Realiza la comparación de mayor que en Lisp para dos argumentos.
     * @param args La lista de argumentos a comparar.
     * @return true si el primer argumento es mayor que el segundo, false en caso contrario.
     * @throws Exception Si ocurre un error durante la comparación.
     */
    public boolean moreThan(List<?> args) throws Exception {
        if (args.size() != 2) {
            throw new Exception();
        }
        Object arg1 = getArg(args.get(0));
        Object arg2 = getArg(args.get(1));
        if (arg1 instanceof Number && arg2 instanceof Number) {
            return ((Number) arg1).doubleValue() > ((Number) arg2).doubleValue();
        } else {
            throw new Exception();
        }
    }

    /**
     * Realiza la comparación de menor que en Lisp para dos argumentos.
     * @param args La lista de argumentos a comparar.
     * @return true si el primer argumento es menor que el segundo, false en caso contrario.
     * @throws Exception Si ocurre un error durante la comparación.
     */
    public boolean lessThan(List<?> args) throws Exception {
        if (args.size() != 2) {
            throw new Exception();
        }
        Object arg1 = getArg(args.get(0));
        Object arg2 = getArg(args.get(1));
        if (arg1 instanceof Number && arg2 instanceof Number) {
            return ((Number) arg1).doubleValue() < ((Number) arg2).doubleValue();
        } else {
            throw new Exception();
        }
    }
    /**
     * Comprueba si dos argumentos en Lisp son iguales.
     * @param args La lista de argumentos a comparar.
     * @return true si los argumentos son iguales, false en caso contrario.
     * @throws Exception Si ocurre un error durante la comparación.
     */
    public boolean equal(List<?> args) throws Exception {
        if (args.size() != 2) {
            throw new Exception();
        }
        Object arg1 = getArg(args.get(0));
        Object arg2 = getArg(args.get(1));
        return arg1.equals(arg2);
    }

    /**
     * Evalua una lista de variables y devuelve true si el primero no es una lista.
     * @param args La lista de argumentos a evaluar.
     * @return true si la evaluación se da en un elemento que no es lista
     * @throws Exception Si ocurre un error durante la evaluación.
     */
    public boolean atom(List<?> args) throws Exception {
        if (args.size() != 1) {
            throw new Exception();
        }
        Object arg = getArg(args.get(0));
        return !(arg instanceof List);

    }

    /**
     * Comprueba si un argumento en Lisp es una lista.
     * @param args La lista de argumentos a evaluar.
     * @return true si el argumento es una lista, false en caso contrario.
     * @throws Exception Si ocurre un error durante la evaluación.
     */
    public boolean list(List<?> args) throws Exception {
        if (args.size() != 1) {
            throw new Exception();
        }
        Object arg = args.get(0);
        return arg instanceof List;
    }

    /**
     * Evalúa una serie de condiciones y devuelve el resultado de la primera condición verdadera.
     * @param args La lista de argumentos que contienen las condiciones y expresiones.
     * @return El resultado de la primera expresión cuya condición sea verdadera, o null si ninguna condición es verdadera.
     * @throws Exception Si ocurre un error durante la evaluación.
     */
    public Object cond(List<?> args) throws Exception {
        for (Object arg : args) {
            if (!(arg instanceof List) || ((List<?>) arg).size() != 2) {
                throw new Exception("Each argument to cond must be a pair");
            }

            List<?> pair = (List<?>) arg;
            Object condition = pair.get(0);
            Object expression = pair.get(1);

            // Evalúa la condición. Evalua y devuelve una expresión si es verdadera.
            Object conditionResult;
            if (condition instanceof List) {
                conditionResult = eval((List<?>) condition);
            } else if (condition instanceof String && globalEnv.containsKey(condition)) {
                conditionResult = globalEnv.get(condition);
            } else {
                conditionResult = condition;
            }

            if (Boolean.TRUE.equals(conditionResult)) {
                if (expression instanceof List) {
                    return eval((List<?>) expression);
                } else if (expression instanceof String && globalEnv.containsKey(expression)) {
                    return globalEnv.get(expression);
                } else {
                    return expression;
                }
            }
        }

        // Si ninguna condición es verdadera, devuelve nulo.
        return null;    }

    /**
     * Retorna el argumento sin evaluarlo, utilizado para evitar la evaluación de una expresión en Lisp.
     * @param args La lista de argumentos a evaluar.
     * @return El argumento sin evaluar.
     * @throws Exception Si se proporciona más de un argumento para la función quote.
     */
    public Object quote(List<?> args) throws Exception {

        if (args.size() != 1) {
            throw new Exception("Expected exactly one argument for quote");
        }

        // Devuelve el argumento sin hacerle cambios.
        return args.get(0);
    }

    /**
     * Asigna un valor a una variable en el ambiente global en Lisp.
     * @param args La lista de argumentos que contiene el nombre de la variable y su valor.
     * @throws Exception Si ocurre un error durante la asignación, como argumentos incorrectos o nombres de variables inválidos.
     */
    public void setq(List<?> args) throws Exception {
        if (args.get(0).getClass() != String.class) {
            throw new Exception();
        }
        String name = (String) args.get(0);
        if (name.contains("\"")) {
            throw new Exception();
        }
        if (args.get(1).getClass() == String.class) {
            String asignment = (String) args.get(1);
            if (globalEnv.get(asignment) != null) {
                if (globalEnv.get(asignment).getClass() == LispFunction.class) {
                    throw new Exception();
                } else if (!asignment.contains("\"")) {
                    setq(List.of(name, globalEnv.get(asignment)));
                    return;
                }
            } else if (!asignment.contains("\"")) {
                throw new Exception();
            }
        }

        globalEnv.put(name, getArg(args.get(1)));


        return;
    }

    /**
     * Define una nueva función en Lisp y la añade al ambiente global.
     * @param args La lista de argumentos que contiene el nombre de la función, sus parámetros y cuerpo.
     * @throws Exception Si ocurre un error durante la definición de la función, como nombres inválidos o argumentos incorrectos.
     */
    public void defun(List<?> args) throws Exception {
        String name = (String) args.get(0);
        if (name.contains("\"")) {
            throw new Exception();
        }
        List<?> params = (List<?>) args.get(1);
        List<?> body = (List<?>) args.get(2);
        globalEnv.put(name, new LispFunction(name, params, body));
        return;
    }

}