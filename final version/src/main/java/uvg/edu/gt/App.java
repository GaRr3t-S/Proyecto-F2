package uvg.edu.gt;
import java.util.List;
import java.util.Scanner;

/**
 * Clase principal que permite al usuario interactuar con el interprete de Lisp
 */


/**
 * Método principal de la aplicación.
 * @param args Argumentos de la línea de comandos (no se utilizan en esta aplicación).
 * @throws Exception Si ocurre un error durante la ejecución del intérprete Lisp.
 */
public class App {
    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);
        LispInterpreter lispInterpreter = LispInterpreter.getInterpreter();

        String input;
        while (true) {
            System.out.print(">> ");
            input = s.nextLine();
            if (input.equals("")) {
                break;
            }

            try {
                List<?> argList = lispInterpreter.Str2Lisp(input);

                for (Object arg : argList) {
                    if (List.class.isAssignableFrom(arg.getClass())) {
                        try {
                            Object output = lispInterpreter.eval((List<?>) arg);
                            if (output != null) {
                                System.out.println(output);
                            }
                        } catch (Exception e) {
                            System.err.println("* Exception: Error al ejecutar comando!  " + e.getMessage());
                        }

                    }
                }
            }
            catch (Exception e1) {
                System.err.println("* Exception: Error de sintáxis en la instrucción");
            }

        }


        s.close();
    }
}
