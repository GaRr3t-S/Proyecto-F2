import java.util.List;
import java.util.ArrayList;

//public class LispList extends LispValue {
//    private final List<LispValue> elements;
//
//    public LispList(List<LispValue> elements) {
//        this.elements = elements;
//    }
//
////    @Override
//    public LispValue eval(Environment environment) throws Exception {
//        if (elements.isEmpty()) {
//            return this;
//        }
//
//        LispValue first = elements.get(0);
//        if (first instanceof LispAtom) {
//            LispAtom atom = (LispAtom) first;
//            if (atom.value.equals("quote")) {
//                return elements.get(1);
//            } else if (atom.value.equals("if")) {
//                LispValue condition = elements.get(1).eval(environment);
//                if (condition instanceof LispAtom && ((LispAtom) condition).value.equals(true)) {
//                    return elements.get(2).eval(environment);
//                } else {
//                    return elements.get(3).eval(environment);
//                }
//            } else if (atom.value.equals("def")) {
//                LispAtom symbol = (LispAtom) elements.get(1);
//                LispValue value = elements.get(2).eval(environment);
//                environment.add(symbol.value, value);
//                return value;
//            }
//        }
//
//        // If the first element is not a LispAtom or not one of the special forms we know about,
//        // we assume it's a function and evaluate its arguments.
//        List<LispValue> args = new ArrayList<>();
//        for (int i = 1; i < elements.size(); i++) {
//            args.add(elements.get(i).eval(environment));
//        }
//
//        // We look up the function in the environment and apply it to the arguments.
////        LispValue func = environment.get(first.toString());
////        if (func instanceof LispFunction){
////            return ((LispFunction) func).apply(args);
////        } else {
////            throw new Exception("Unknown function: " + first);
////        }
////    }
//
//    @Override
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