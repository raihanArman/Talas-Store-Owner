package id.co.myproject.talasstore_owner.database;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_TRANSAKSI = "tb_transaksi";
    static final class PesananColumns implements BaseColumns {
        static String ID_ADMIN = "id_admin";
        static String ID_TRANSAKSI = "id_transaksi";
        static String NAMA_MEMBER = "nama_member";
        static String JUMLAH_PESANAN = "jumlah_pesanan";
        static String TOTAL = "total";
        static String BAYAR = "bayar";
        static String KEMBALIAN = "kembalian";
        static String TANGGAL = "tanggal";
    }
}
