import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by Kray on 2016/11/13.
 */
public class LL1Analyzer {

    private ArrayList<Token> tokenArrayList = new ArrayList<>();
    private static Stack<Token> tokenStack = new Stack<>();
    private static Queue<Token> tokenQueue = new LinkedList<>();
    private static int[][] ppt = PPT.getPPT();
    private static String[] generations = {
            "S->id=E;",
            "S->if(C){S}else{S}",
            "S->while(C){S}",
            "E->TE'",
            "E'->+TE'",
            "E'->ε",
            "T->FT'",
            "T'->*FT'",
            "T'->ε",
            "F->(E)",
            "F->num",
            "F->id",
            "C->DC'",
            "C'->&&DC'",
            "C'->ε",
            "D->(C)",
            "D->id==num"
    };
    private ArrayList<String> output = new ArrayList<>();

    public LL1Analyzer(ArrayList<Token> tokens) {
        this.tokenArrayList = tokens;
        System.out.println("-----------------------------------");
        System.out.println("Tokens :");
        System.out.println("-----------------------------------");
        for(Token token : this.tokenArrayList){
            System.out.println("Token : <" + token.getCode() + "," + token.getValue() + ">");
        }
    }

    public ArrayList<Token> getTokens() {
        return tokenArrayList;
    }

    public void startAnalyze() {
        tokenStack = new Stack();
        tokenStack.push(new Token(Token.START, "$L"));
        tokenStack.push(new Token(Token.S, "S"));
        for (Token token : tokenArrayList) {
            tokenQueue.add(token);
        }
        Token token1 = null;
        Token token2 = null;
        while (tokenQueue.peek().getCode() != Token.END) {
            token1 = tokenStack.peek();
            token2 = tokenQueue.peek();
            if (token1.getCode() >= 100) {
                if (!generate(token1, token2.getCode())) {
                    return;
                }
            } else {
                if (token1.getCode() == token2.getCode()) {
                    tokenStack.pop();
                    tokenQueue.remove();
                } else {
                    return;
                }
            }
        }
    }

    private boolean generate(Token token, int next) {
        try {
            int nextGenerator = ppt[token.getCode() - 100][next-1];
            if (nextGenerator < 0) {
                return false;
            }
            output.add(generations[nextGenerator]);
            tokenStack.pop();
            switch (nextGenerator) {
                case 0:
                    tokenStack.push(new Token(Token.SEMICOLON, token.getValue()));
                    tokenStack.push(new Token(Token.E, token.getValue()));
                    tokenStack.push(new Token(Token.ASSIGN, token.getValue()));
                    tokenStack.push(new Token(Token.ID, token.getValue()));
                    break;
                case 1:
                    tokenStack.push(new Token(Token.RIGHT_CURLY, token.getValue()));
                    tokenStack.push(new Token(Token.S, token.getValue()));
                    tokenStack.push(new Token(Token.LEFT_CURLY, token.getValue()));
                    tokenStack.push(new Token(Token.ELSE, token.getValue()));
                    tokenStack.push(new Token(Token.RIGHT_CURLY, token.getValue()));
                    tokenStack.push(new Token(Token.S, token.getValue()));
                    tokenStack.push(new Token(Token.LEFT_CURLY, token.getValue()));
                    tokenStack.push(new Token(Token.RIGHT_BRACE, token.getValue()));
                    tokenStack.push(new Token(Token.C, token.getValue()));
                    tokenStack.push(new Token(Token.LEFT_BRACE, token.getValue()));
                    tokenStack.push(new Token(Token.IF, token.getValue()));
                    break;
                case 2:
                    tokenStack.push(new Token(Token.RIGHT_CURLY, token.getValue()));
                    tokenStack.push(new Token(Token.S, token.getValue()));
                    tokenStack.push(new Token(Token.LEFT_CURLY, token.getValue()));
                    tokenStack.push(new Token(Token.RIGHT_BRACE, token.getValue()));
                    tokenStack.push(new Token(Token.C, token.getValue()));
                    tokenStack.push(new Token(Token.LEFT_BRACE, token.getValue()));
                    tokenStack.push(new Token(Token.WHILE, token.getValue()));
                    break;
                case 3:
                    tokenStack.push(new Token(Token.E1, token.getValue()));
                    tokenStack.push(new Token(Token.T, token.getValue()));
                    break;
                case 4:
                    tokenStack.push(new Token(Token.E1, token.getValue()));
                    tokenStack.push(new Token(Token.T, token.getValue()));
                    tokenStack.push(new Token(Token.PLUS, token.getValue()));
                    break;
                case 5:
                    break;
                case 6:
                    tokenStack.push(new Token(Token.T1, token.getValue()));
                    tokenStack.push(new Token(Token.F, token.getValue()));
                    break;
                case 7:
                    tokenStack.push(new Token(Token.T1, token.getValue()));
                    tokenStack.push(new Token(Token.F, token.getValue()));
                    tokenStack.push(new Token(Token.MULTIPLY, token.getValue()));
                    break;
                case 8:
                    break;
                case 9:
                    tokenStack.push(new Token(Token.RIGHT_BRACE, token.getValue()));
                    tokenStack.push(new Token(Token.E, token.getValue()));
                    tokenStack.push(new Token(Token.LEFT_BRACE, token.getValue()));
                    break;
                case 10:
                    tokenStack.push(new Token(Token.NUM, token.getValue()));
                    break;
                case 11:
                    tokenStack.push(new Token(Token.ID, token.getValue()));
                    break;
                case 12:
                    tokenStack.push(new Token(Token.C1, token.getValue()));
                    tokenStack.push(new Token(Token.D, token.getValue()));
                    break;
                case 13:
                    tokenStack.push(new Token(Token.C1, token.getValue()));
                    tokenStack.push(new Token(Token.D, token.getValue()));
                    tokenStack.push(new Token(Token.AND, token.getValue()));
                    break;
                case 14:
                    break;
                case 15:
                    tokenStack.push(new Token(Token.RIGHT_BRACE, token.getValue()));
                    tokenStack.push(new Token(Token.C, token.getValue()));
                    tokenStack.push(new Token(Token.LEFT_BRACE, token.getValue()));
                    break;
                case 16:
                    tokenStack.push(new Token(Token.NUM, token.getValue()));
                    tokenStack.push(new Token(Token.EQUAL, token.getValue()));
                    tokenStack.push(new Token(Token.ID, token.getValue()));
                    break;
                default:
                    return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void output(){
        System.out.println("-----------------------------------");
        System.out.println("Generators :");
        System.out.println("-----------------------------------");
        for (String string : output){
            System.out.println("Generator : " + string);
        }
    }
}
