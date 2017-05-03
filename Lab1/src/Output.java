import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kray on 2016/10/25.
 */
public class Output {

    List<String> outputStack;
    boolean isError;

    public Output() {
        outputStack = new ArrayList<String>();
    }

    protected void outputIntoBuffer(String string){
        outputStack.add(string);
    }

    protected void output(){
        if(isError){
            System.out.println("Error");
            return;
        }
        for (String string : outputStack) {
            System.out.println(string);
        }
    }

    protected void error(){
        isError = true;
    }
}
