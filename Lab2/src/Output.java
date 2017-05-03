import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kray on 2016/10/25.
 */
public class Output {

    List<String> outputStack;
    boolean isError;

    public ArrayList<Token> tokens = new ArrayList<>();

    public Output() {
        outputStack = new ArrayList<String>();
    }

    protected void outputIntoBuffer(String string) {
        outputStack.add(string);
    }

    protected ArrayList<Token> output() {
        if (isError) {
            System.out.println("Error");
            return null;
        }
        for (String string : outputStack) {

            int code = -1;
            String value = "";

            String[] strings = string.split(",");
            switch (strings[0]) {
                case "Operator":
                    switch (strings[1]) {
                        case "(":
                            code = Token.LEFT_BRACE;
                            break;
                        case ")":
                            code = Token.RIGHT_BRACE;
                            break;
                        case "{":
                            code = Token.LEFT_CURLY;
                            break;
                        case "}":
                            code = Token.RIGHT_CURLY;
                            break;
                        case "+":
                            code = Token.PLUS;
                            break;
                        case "*":
                            code = Token.MULTIPLY;
                            break;
                        case "==":
                            code = Token.EQUAL;
                            break;
                        case "&&":
                            code = Token.AND;
                            break;
                        case ";":
                            code = Token.SEMICOLON;
                            break;
                        case "=":
                            code = Token.ASSIGN;
                            break;
                    }
                    break;
                case "Identifier":
                    code = Token.ID;
                    break;
                case "Keyword":
                    switch (strings[1]) {
                        case "if":
                            code = Token.IF;
                            break;
                        case "else":
                            code = Token.ELSE;
                            break;
                        case "while":
                            code = Token.WHILE;
                            break;
                    }
                    break;
                case "Integer":
                case "Decimal":
                    code = Token.NUM;
                    break;
            }
            value = strings[1];
            tokens.add(new Token(code, value));
        }
        tokens.add(new Token(Token.END, "$R"));

        return tokens;
    }

    protected void error() {
        isError = true;
    }
}
