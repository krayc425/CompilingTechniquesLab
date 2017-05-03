/**
 * Created by Kray on 2016/11/13.
 */
public class Token {

    public final static int START = 0;
    public final static int ID = 1;
    public final static int IF = 2;
    public final static int ELSE = 3;
    public final static int LEFT_BRACE = 4;
    public final static int RIGHT_BRACE = 5;
    public final static int LEFT_CURLY = 6;
    public final static int RIGHT_CURLY = 7;
    public final static int WHILE = 8;
    public final static int PLUS = 9;
    public final static int MULTIPLY = 10;
    public final static int NUM = 11;
    public final static int AND = 12;
    public final static int EQUAL = 13;
    public final static int SEMICOLON = 14;
    public final static int ASSIGN = 15;
    public final static int END = 16;

    public static final int S = 100;
    public static final int E = 101;
    public static final int E1 = 102;
    public static final int T = 103;
    public static final int T1 = 104;
    public static final int F = 105;
    public static final int C = 106;
    public static final int C1 = 107;
    public static final int D = 108;

    private int code;
    private String value;

    public String getValue() {
        return value;
    }

    public int getCode() {
        return code;
    }

    public Token(int c, String s) {
        this.code = c;
        this.value = s;
    }
}
