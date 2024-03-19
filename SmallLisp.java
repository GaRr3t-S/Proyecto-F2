import java.util.Stack;

public class SmallLisp {

  Stack<String> stack;
  
  public SmallLisp(){
    String[] tokens = new String[]{"(","+","2","(","+","3","2",")",")"};
    stack = new Stack<String>();
    for (int i=0;i<tokens.length;i++){
      stack.push(tokens[i]);
      if(tokens[i].equals(")")) Interprete(); 
    }
  }
  
  public void Interprete(){
    String tok;
    Stack<String> callStack = new Stack<String>();
    tok = stack.pop(); 
    while(!(tok=stack.pop()).equals("(")){
      callStack.push(tok);
    }
    Call(callStack);
  }
  
  public void Call(Stack<String> callStack){
    String func = callStack.pop(); 
    if(func.equals("+")) {
      double result = Plus(callStack);
      stack.push(String.valueOf(result));
    }

  }
  
  public double Plus(Stack<String> callStack){
    double a = Double.parseDouble(callStack.pop());
    double b = Double.parseDouble(callStack.pop());
    System.out.println("Answer is "+(a+b));
    return(a+b);
  } 
  
  public static void main(String[] args) {
    new SmallLisp();
  }
}