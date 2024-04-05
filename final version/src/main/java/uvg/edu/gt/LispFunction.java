package uvg.edu.gt;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class LispFunction {
    protected String name;
    protected List<?> params;
    protected List<?> body;

    /**
     * Aplica la función a una lista de argumentos.
     *
     * @param argList La lista de argumentos necsarios para aplicar a la función.
     * @return El resultado de la funcion luego de ser evaluada.
     * @throws Exception si hay un error durante la evaluación de la función.
     */
    public Object apply(List<?> argList) throws Exception {
        Stack<Map<String, Object>> localEnvs = LispInterpreter.getInterpreter().getLocalEnvs();
        localEnvs.push(new HashMap<>(localEnvs.peek()));


        for (int i = 0; i < params.size(); i++) {
            Object arg = argList.get(i);
            if (List.class.isAssignableFrom(arg.getClass())){
                arg = LispInterpreter.getInterpreter().eval((List<?>)arg);
            } else if (arg.getClass() == String.class && localEnvs.peek().containsKey(arg)) {
                arg = LispInterpreter.getInterpreter().getLocalEnvs().peek().get(arg);
            }


            localEnvs.peek().put((String) params.get(i), arg);
        }

        Object result = null;
        result = LispInterpreter.getInterpreter().eval(body);

        localEnvs.pop();
        LispInterpreter.getInterpreter().updateGlobalEnv();
        return result;
    }

    /**
     * Constructor para inicializar una nueva función.
     *
     * @param name   El nombre de la función.
     * @param params Los parámetros de la función.
     * @param body   El cuerpo de la función.
     */
    public LispFunction(String name, List<?> params, List<?> body) {
        this.name = name;
        this.params = params;
        this.body = body;
    }

    /**
     * Obtiene los parámetros de la función.
     *
     * @return La lista de parámetros de la función.
     */
    public List<?> getParams() {
        return params;
    }
}