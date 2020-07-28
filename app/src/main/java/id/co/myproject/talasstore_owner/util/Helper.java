package id.co.myproject.talasstore_owner.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class Helper {

    public static final int GALLERY_REQUEST = 22;
    public static final int TYPE_ADD = 1;
    public static final int TYPE_EDIT = 2;
    public static final String KEY_LOGIN_STATUS = "login_status";
    public static final String KEY_ID_ADMIN = "id_admin";
    public static final String KEY_LOGIN_SHARED_PREF = "data_admin";
    public static final int FILTER_HARI_INI = 0;
    public static final int FILTER_SEMUA = 1;

    public static String rupiahFormat(int harga){
        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("Rp. ");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');

        kursIndonesia.setDecimalFormatSymbols(formatRp);

        return kursIndonesia.format(harga);
    }
}
