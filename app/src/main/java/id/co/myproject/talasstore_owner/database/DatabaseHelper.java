package id.co.myproject.talasstore_owner.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "db_zakat";
    public static final int DATABASE_VERSION = 1;
    public static final String SQL_CREATE_TABLE_ZAKAT = String.format("CREATE TABLE %s"+
            "(%s INTEGER, "+
            "%s TEXT NOT NULL, "+
            "%s TEXT NOT NULL, "+
            "%s TEXT NOT NULL, "+
            "%s TEXT NOT NULL, "+
            "%s TEXT NOT NULL, "+
            "%s TEXT NOT NULL, "+
            "%s TEXT NOT NULL)",
            DatabaseContract.TABLE_TRANSAKSI,
            DatabaseContract.PesananColumns.ID_ADMIN,
            DatabaseContract.PesananColumns.ID_TRANSAKSI,
            DatabaseContract.PesananColumns.NAMA_MEMBER,
            DatabaseContract.PesananColumns.JUMLAH_PESANAN,
            DatabaseContract.PesananColumns.TOTAL,
            DatabaseContract.PesananColumns.BAYAR,
            DatabaseContract.PesananColumns.KEMBALIAN,
            DatabaseContract.PesananColumns.TANGGAL
    );
    public DatabaseHelper(@Nullable Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_TABLE_ZAKAT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DatabaseContract.TABLE_TRANSAKSI);
        onCreate(sqLiteDatabase);
    }
}
