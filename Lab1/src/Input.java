import java.io.*;

/**
 * Created by Kray on 2016/10/25.
 */
public class Input {
    private static final String INPUT_FILE = "input.txt";
    public static final char EOF = (char)0;
    private Reader reader = null;

    protected Input() {
        File file = new File(INPUT_FILE);
        try {
            reader = new InputStreamReader(new FileInputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected char getChar() {
        int tempChar;
        try {
            if ((tempChar = reader.read()) != -1) {
                return (char)tempChar;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EOF;

    }
}

