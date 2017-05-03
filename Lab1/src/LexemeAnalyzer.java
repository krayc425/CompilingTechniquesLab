/**
 * Created by Kray on 2016/10/25.
 */
public class LexemeAnalyzer {

    private Input input;
    private Output output;
    private STATE state;

    private StringBuffer charBuffer;
    private StringBuffer digitBuffer;
    private StringBuffer annotationBuffer;
    private StringBuffer quoteMarkBuffer;

    public LexemeAnalyzer(){
        this.input = new Input();
        this.output = new Output();
        this.state = STATE.DONE;
        this.charBuffer = new StringBuffer();
        this.digitBuffer = new StringBuffer();
        this.annotationBuffer = new StringBuffer();
        this.quoteMarkBuffer = new StringBuffer();
    }

    public void startAnalyze(){
        while (true) {
            char c = input.getChar();
            if(c == Input.EOF){
                break;
            }
            switch (this.state){
                case DONE:
                    readWord(c);
                    break;
                case F_LETTER:
                    if(Judgement.isDigit(c) || Judgement.isLetter(c)){
                        charBuffer.append(c);
                    }else{
                        if(Judgement.isKeyword(charBuffer.toString())){
                            output.outputIntoBuffer("Keyword            " + charBuffer.toString());
                        }else{
                            output.outputIntoBuffer("Identifier         " + charBuffer.toString());
                        }

                        state = STATE.DONE;
                        charBuffer = new StringBuffer();
                        readWord(c);
                    }
                    break;
                case F_ADD:
                    if(c == '+' || c == '='){
                        output.outputIntoBuffer("Operator           " + "+" + c);
                        state = STATE.DONE;
                    }else{
                        output.outputIntoBuffer("Operator           " + "+");
                        state = STATE.DONE;
                        readWord(c);
                    }
                    break;
                case F_SUB:
                    if(c == '-' || c == '='){
                        output.outputIntoBuffer("Operator           " + "-" + c);
                        state = STATE.DONE;
                    }else{
                        output.outputIntoBuffer("Operator           " + "-");
                        state = STATE.DONE;
                        readWord(c);
                    }
                    break;
                case F_LT:
                    if(c == '<' || c == '='){
                        output.outputIntoBuffer("Operator           " + "<" + c);
                        state = STATE.DONE;
                    }else{
                        output.outputIntoBuffer("Operator           " + "<");
                        state = STATE.DONE;
                        readWord(c);
                    }
                    break;
                case F_GT:
                    if(c == '>' || c == '='){
                        output.outputIntoBuffer("Operator           " + ">" + c);
                        state = STATE.DONE;
                    }else{
                        output.outputIntoBuffer("Operator           " + ">");
                        state = STATE.DONE;
                        readWord(c);
                    }
                    break;
                case F_REM:
                    if(c == '%' || c == '='){
                        output.outputIntoBuffer("Operator           " + "%" + c);
                        state = STATE.DONE;
                    }else{
                        output.outputIntoBuffer("Operator           " + "%");
                        state = STATE.DONE;
                        readWord(c);
                    }
                    break;
                case F_MUL:
                    if(c == '-' || c == '='){
                        output.outputIntoBuffer("Operator           " + "-" + c);
                        state = STATE.DONE;
                    }else{
                        output.outputIntoBuffer("Operator           " + "-");
                        state = STATE.DONE;
                        readWord(c);
                    }
                    break;
                case F_DIV:
                    if(c == '='){
                        output.outputIntoBuffer("Operator           " + "/" + c);
                        state = STATE.DONE;
                    }else if(c == '/'){
                        output.outputIntoBuffer("Annotation Mark    " + "//");
                        state = STATE.F_ANNO_ONE;
                    }else if(c == '*'){
                        output.outputIntoBuffer("Annotation Mark    " + "/*");
                        state = STATE.F_ANNO_MULTI;
                    }else{
                        output.outputIntoBuffer("Operator           " + "=");
                        state = STATE.DONE;
                        readWord(c);
                    }
                    break;
                case F_EQUAL:
                    if(c == '='){
                        output.outputIntoBuffer("Operator           " + "=" + c);
                        state = STATE.DONE;
                    }else{
                        output.outputIntoBuffer("Operator           " + "=");
                        state = STATE.DONE;
                        readWord(c);
                    }
                    break;
                case F_EXCLA:
                    if(c == '='){
                        output.outputIntoBuffer("Operator           " + "!" + c);
                        state = STATE.DONE;
                    }else{
                        output.outputIntoBuffer("Operator           " + "!");
                        state = STATE.DONE;
                        readWord(c);
                    }
                    break;
                case F_AND:
                    if(c == '&'){
                        output.outputIntoBuffer("Operator           " + "&" + c);
                        state = STATE.DONE;
                    }else{
                        output.outputIntoBuffer("Operator " + "&");
                        state = STATE.DONE;
                        readWord(c);
                    }
                    break;
                case F_OR:
                    if(c == '|'){
                        output.outputIntoBuffer("Operator           " + "|" + c);
                        state = STATE.DONE;
                    }else{
                        output.outputIntoBuffer("Operator           " + "|");
                        state = STATE.DONE;
                        readWord(c);
                    }
                    break;
                case F_ANNO_ONE:
                    if(c == '\n'){  //说明读完了一行注释
                        state = STATE.DONE;
                        output.outputIntoBuffer("Annotation         "+annotationBuffer);
                        annotationBuffer = new StringBuffer();
                    }else{
                        annotationBuffer.append(c);
                    }
                    break;
                case F_ANNO_MULTI:
                    if(c == '*'){
                        state = STATE.F_ANNO_STAR;
                    }
                    annotationBuffer.append(c);
                    break;
                case F_ANNO_STAR:
                    if(c == '/'){   //说明读完了多行注释
                        state = STATE.DONE;
                        output.outputIntoBuffer("Annotation         " + annotationBuffer.substring(0, annotationBuffer.length()-2));
                        output.outputIntoBuffer("Annotation Mark    "  + "*/");
                        annotationBuffer=new StringBuffer();
                    }else if(c == '*'){
                        annotationBuffer.append(c);
                    }else{
                        state=STATE.F_ANNO_MULTI;
                        annotationBuffer.append(c);
                    }
                    break;
                case F_SINGLE_QUOTE:
                    if(c == '\''){
                        state = STATE.DONE;
                        output.outputIntoBuffer("Character          " + quoteMarkBuffer);
                        output.outputIntoBuffer("Operator           " + "'");
                        quoteMarkBuffer = new StringBuffer();
                    }else{
                        quoteMarkBuffer.append(c);
                    }
                    break;
                case F_DOUBLE_QUOTE:
                    if(c == '"'){
                        state = STATE.DONE;
                        output.outputIntoBuffer("String             " + quoteMarkBuffer);
                        output.outputIntoBuffer("Operator           " + "\"");
                        quoteMarkBuffer = new StringBuffer();
                    }else{
                        quoteMarkBuffer.append(c);
                    }
                    break;
                case F_DOT:
                    state = STATE.F_DOUBLE;
                    digitBuffer.append(c);
                    break;
                case F_INT:
                    if(Judgement.isDigit(c)){
                        digitBuffer.append(c);
                    }else if(c == '.'){
                        digitBuffer.append(c);
                        state = STATE.F_DOT;
                    }else if(Judgement.isLetter(c)){
                        output.error();
                        state = STATE.DONE;
                        readWord(c);
                    }else{
                        state = STATE.DONE;
                        output.outputIntoBuffer("Integer            " + digitBuffer);
                        digitBuffer = new StringBuffer();
                        readWord(c);
                    }
                    break;
                case F_DOUBLE:
                    if(Judgement.isDigit(c)){
                        digitBuffer.append(c);
                    }else if(Judgement.isLetter(c)){
                        output.error();
                        state = STATE.DONE;
                        readWord(c);
                    }else{
                        state = STATE.DONE;
                        output.outputIntoBuffer("Double             " + digitBuffer);
                        digitBuffer = new StringBuffer();
                        readWord(c);
                    }
                    break;
            }
        }

        System.out.println("-----------------------------------");
        System.out.println("Beginning of output:");
        System.out.println("-----------------------------------");
        output.output();
        System.out.println("-----------------------------------");
        System.out.println("End of output.");
        System.out.println("-----------------------------------");

    }

    private void readWord(char c){
        if(Judgement.isLetter(c)){
            charBuffer.append(c);
            state = STATE.F_LETTER;
        }else if(Judgement.isDigit(c)){
            digitBuffer.append(c);
            state = STATE.F_INT;
        }else{
            switch (c) {
                case '+':
                    state = STATE.F_ADD;
                    break;
                case '-':
                    state = STATE.F_SUB;
                    break;
                case '*':
                    state = STATE.F_MUL;
                    break;
                case '/':
                    state = STATE.F_DIV;
                    break;
                case '%':
                    state = STATE.F_REM;
                    break;
                case '<':
                    state = STATE.F_LT;
                    break;
                case '>':
                    state = STATE.F_GT;
                    break;
                case '=':
                    state = STATE.F_EQUAL;
                    break;
                case '!':
                    state = STATE.F_EXCLA;
                    break;
                case '&':
                    state = STATE.F_AND;
                    break;
                case '|':
                    state = STATE.F_OR;
                    break;
                case '\'':
                    state = STATE.F_SINGLE_QUOTE;
                    output.outputIntoBuffer("Operator           " + c);
                    break;
                case '"':
                    state = STATE.F_DOUBLE_QUOTE;
                    output.outputIntoBuffer("Operator           " + c);
                    break;
                case ';':
                case ':':
                case '{':
                case '}':
                case ',':
                case '.':
                case '(':
                case ')':
                case '[':
                case ']':
                case '?':
                case '~':
                case '^':
                    output.outputIntoBuffer("Operator           " + c);
                    break;
                default:
                    if(c == ' ' || c == '\t' || c == '\n' || c == '\r'){

                    }else{
                        output.outputIntoBuffer("Can't recognize " + c);
                    }
                    break;
            }
        }
    }

}
