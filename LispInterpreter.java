import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class LispInterpreter {
    private Map<String, Object> env = new HashMap<>();

    public Object eval(Object x) {
        if (x instanceof String) {  // variable reference
            return env.get(x);
        } else if (!(x instanceof List)) {  // constant
            return x;
        } else {
            List xs = (List) x;
            String command = (String) xs.get(0);
            if ("quote".equals(command)) {  // (quote exp)
                return xs.get(1);
            } else if ("atom?".equals(command)) {  // (atom? exp)
                return eval(xs.get(1)) instanceof String;
            } else if ("eq?".equals(command)) {  // (eq? exp1 exp2)
                return eval(xs.get(1)).equals(eval(xs.get(2)));
            } else if ("setq".equals(command)) {  // (setq var exp)
                Object var = xs.get(1);
                Object exp = xs.get(2);
                return env.put((String) var, eval(exp));
            } else if ("defun".equals(command)) {  // (defun name args body)
                Object name = xs.get(1);
                Object args = xs.get(2);
                Object body = xs.get(3);
                return env.put((String) name, new Function<List, Object>() {
                    @Override
                    public Object apply(List params) {
                        Map<String, Object> savedEnv = new HashMap<>(env);
                        for (int i = 0; i < ((List) args).size(); i++) {
                            env.put((String) ((List) args).get(i), params.get(i));
                        }
                        try {
                            return eval(body);
                        } finally {
                            env = savedEnv;
                        }
                    }
                });
            } else {
                // TODO: implement other commands
                throw new UnsupportedOperationException("Not implemented: " + command);
            }
        }
    }
}