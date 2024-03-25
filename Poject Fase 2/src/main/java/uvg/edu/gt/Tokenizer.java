package uvg.edu.gt;

public class Tokenizer {
    /**
     * Este método separa un string en un array de strings usando espacios como delimitador.
     * @param input El string a separar.
     * @return Un array de strings.
     */
    public static String[] separaString (String input) {
        return input.split(" ");
    }

    /**
     * Este método tokeniza una expresión LISP.
     * Añade espacios alrededor de los paréntesis para poder separarlos correctamente,
     * luego separa la expresión en tokens usando espacios como delimitador.
     * @param input La expresión LISP a tokenizar.
     * @return Un array de tokens.
     */
    public static String[] tokenizaLisp (String input) {
        String conEspacios = input.replace("(", " ( ").replace(")", " ) ");
        return conEspacios.trim().split("\\s+");
    }
}
