package TMDTBoBa.BoBaEcor.Utilities;

import java.sql.Timestamp;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.regex.Pattern;

public class Contains {
    public static final String SECRET_KEY = "BOBA20222023";
    public static final Integer TIME_ACCESS_TOKEN = 24*60*60*1000;
    public static final Integer TIME_REFRESH_TOKEN = 26*60*60*1000;


    public static String formatDate(Timestamp date){
        String pattern = "dd-M-yyyy hh:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }
    public static String convertToURL(String value){
        try {
            String format = Normalizer.normalize(value,Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCOMBINING_DIACRITICAL_MARKS}+");
            return pattern.matcher(format).replaceAll("").toLowerCase().replaceAll(" ","-").replaceAll("đ","d");
        }catch (Exception e){
            return null;
        }
    }
}
