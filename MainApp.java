public class MainApp{
    public static void main(String[] args) {
        HTMLValidator validator = new HTMLValidator();
        validator.ReadHTML("/mnt/1TBHD/Github repos/HTMLvalidator/balls.html");
        System.out.println(validator.InterpretHTML().toString());
    }
}