package additionalClasses;

public class Validator {

    public void checkName(String text) {
        if(text == null || text.trim().length() <= 4) {
            throw new IllegalArgumentException("A név túl rövid!");
        }
    }


}
