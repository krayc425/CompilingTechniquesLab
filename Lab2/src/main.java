import java.util.ArrayList;

/**
 * Created by Kray on 2016/11/13.
 */
public class main {
    public static void main(String[] args) {

        LexemeAnalyzer lexemeAnalyzer = new LexemeAnalyzer();
        lexemeAnalyzer.startAnalyze();

        System.out.println("-----------------------------------");
        System.out.println("Beginning of output.");
        LL1Analyzer ll1Analyzer = new LL1Analyzer(lexemeAnalyzer.getTokens());
        if (ll1Analyzer.getTokens() != null) {
            ll1Analyzer.startAnalyze();
        } else {
            System.out.println("Error");
        }
        ll1Analyzer.output();
        System.out.println("-----------------------------------");
        System.out.println("End of output.");
        System.out.println("-----------------------------------");
    }
}
