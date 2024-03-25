package uvg.edu.gt;

import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

public class LispTest {
    @Test
    public void testLispAtom() {
        LispAtom atom = new LispAtom("test");
        assertEquals("test", atom.toString());
    }

    @Test
    public void testEnvironment() throws Exception {
        Environment env = new Environment();
        LispAtom atom = new LispAtom("test");
        env.addBinding("test", atom);
        assertEquals(atom, env.lookup(atom));
    }

    @Test
    public void testLispReader() throws Exception {
        String input = "(setq x 10)";
        LispReader.read(input);

        Environment env = new Environment();
        LispValue value = env.lookup(new LispAtom("x"));
        assertTrue(value instanceof LispAtom);
        assertEquals("10", ((LispAtom) value).value);
    }

    @Test
    public void testTokenizer() {
        String input = "(setq x 10)";
        String[] tokens = tokenizer.tokenizaLisp(input);
        assertArrayEquals(new String[]{"(", "setq", "x", "10", ")"}, tokens);
    }

    @Test
    public void testSmallLisp() {
        SmallLisp lisp = new SmallLisp();

        String result = lisp.eval("(+ 1 2)");
        assertEquals("3", result);
    }
}
