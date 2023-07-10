package TMDTBoBa.BoBaEcor.Utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validate {

    private static Matcher matcher;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)$";
    private static final String PHONE_REGEX = "^(0?)(3[2-9]|5[6|8|9]|7[0|6-9]|8[0-6|8|9]|9[0-4|6-9])[0-9]{7}$";
    private final static Pattern patternEmail = Pattern.compile(EMAIL_REGEX);
    private final static Pattern patternPhone = Pattern.compile(PHONE_REGEX);

    public Validate() {
    }

    public static boolean validateEmail(String regex) {
        matcher = patternEmail.matcher(regex);
        return matcher.matches();
    }
    public static boolean validatePhone(String regex) {
        matcher = patternPhone.matcher(regex);
        return matcher.matches();
    }
}
