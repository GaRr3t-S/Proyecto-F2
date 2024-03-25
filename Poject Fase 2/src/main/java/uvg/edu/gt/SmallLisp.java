package uvg.edu.gt;
import java.util.Stack;

/**
 * La clase SmallLisp representa un intérprete LISP simplificado.
 * Utiliza una pila para almacenar y evaluar las expresiones LISP.
 */
public class SmallLisp {

    /**
     * Una pila que almacena los tokens de la expresión LISP.
     */
    Stack<String> stack;

    /**
     * Constructor de la clase SmallLisp.
     * Inicializa la pila con una expresión LISP y la evalúa.
     */
    public SmallLisp(){
        String[] tokens = new String[]{"(","+","2","(","+","3","2",")",")"};
        stack = new Stack<String>();
        for (int i=0;i<tokens.length;i++){
            stack.push(tokens[i]);
            if(tokens[i].equals(")")) Interprete();
        }
    }

    /**
     * Evalúa la expresión LISP en la pila.
     * Cuando encuentra un paréntesis cerrado, extrae los tokens hasta el paréntesis abierto correspondiente y los evalúa.
     */
    public void Interprete(){
        String tok;
        Stack<String> callStack = new Stack<String>();
        tok = stack.pop();
        while(!(tok=stack.pop()).equals("(")){
            callStack.push(tok);
        }
        Call(callStack);
    }

    /**
     * Evalúa una llamada a función LISP.
     * @param callStack Una pila que contiene los tokens de la llamada a función.
     */
    public void Call(Stack<String> callStack){
        String func = callStack.pop();
        if(func.equals("+")) {
            double result = Plus(callStack);
            stack.push(String.valueOf(result));
        }
    }

    /**
     * Realiza una operación de suma en los dos primeros elementos de la pila.
     * @param callStack Una pila que contiene los operandos de la suma.
     * @return El resultado de la suma.
     */
    public double Plus(Stack<String> callStack){
        double a = Double.parseDouble(callStack.pop());
        double b = Double.parseDouble(callStack.pop());
        System.out.println("Answer is "+(a+b));
        return(a+b);
    }

    /**
     * Método principal que crea una nueva instancia de SmallLisp y comienza la interpretación.
     * @param args Los argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        new SmallLisp();
    }
}
