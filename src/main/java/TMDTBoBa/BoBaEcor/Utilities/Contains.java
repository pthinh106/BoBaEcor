package TMDTBoBa.BoBaEcor.Utilities;

import java.sql.Timestamp;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.regex.Pattern;

public class Contains {
    public static final String SECRET_KEY = "BOBA20222023";
    public static final Integer TIME_ACCESS_TOKEN = 24*60*60*1000;
    public static final Integer TIME_REFRESH_TOKEN = 26*60*60*1000;


    public static String formatDate(Timestamp date){
        String pattern = "dd-M-yyyy hh:mm";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }
    public static String convertToURL(String value){
        try {
            String format = Normalizer.normalize(value,Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCOMBINING_DIACRITICAL_MARKS}+");
            String result = pattern.matcher(format).replaceAll("").toLowerCase().replaceAll("[^a-z\\s]", "").replaceAll(" ","-");
            List<String> url = List.of(result.split("-"));
            final String[] data = {""};
            url.forEach(s -> {
                if(!s.isEmpty()){
                    data[0] += s + "-";
                }
            });
            return data[0].substring(0,data[0].length() - 1);
        }catch (Exception e){
            return null;
        }
    }
}
