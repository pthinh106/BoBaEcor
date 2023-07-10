package TMDTBoBa.BoBaEcor.Utilities;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Contains {
    public static final String SECRET_KEY = "BOBA20222023";
    public static final Integer TIME_ACCESS_TOKEN = 24*60*60*1000;
    public static final Integer TIME_REFRESH_TOKEN = 26*60*60*1000;

    public static String formatDate(Timestamp date){
        String pattern = "dd-M-yyyy hh:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }
}
