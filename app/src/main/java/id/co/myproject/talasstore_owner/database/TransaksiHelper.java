package id.co.myproject.talasstore_owner.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import id.co.myproject.talasstore_owner.model.RequestStaff;

import static id.co.myproject.talasstore_owner.database.DatabaseContract.PesananColumns.BAYAR;
import static id.co.myproject.talasstore_owner.database.DatabaseContract.PesananColumns.ID_ADMIN;
import static id.co.myproject.talasstore_owner.database.DatabaseContract.PesananColumns.ID_TRANSAKSI;
import static id.co.myproject.talasstore_owner.database.DatabaseContract.PesananColumns.JUMLAH_PESANAN;
import static id.co.myproject.talasstore_owner.database.DatabaseContract.PesananColumns.KEMBALIAN;
import static id.co.myproject.talasstore_owner.database.DatabaseContract.PesananColumns.NAMA_MEMBER;
import static id.co.myproject.talasstore_owner.database.DatabaseContract.PesananColumns.TANGGAL;
import static id.co.myproject.talasstore_owner.database.DatabaseContract.PesananColumns.TOTAL;
import static id.co.myproject.talasstore_owner.database.DatabaseContract.TABLE_TRANSAKSI;
import static id.co.myproject.talasstore_owner.util.Helper.rupiahFormat;


public class TransaksiHelper {
    public static DatabaseHelper databaseHelper;
    private static TransaksiHelper INSTANCE;

    private static SQLiteDatabase database;
    Context context;

    public TransaksiHelper(Context context) {
        this.context = context;
        databaseHelper = new DatabaseHelper(context);
    }

    public static TransaksiHelper getINSTANCE(Context context){
        if (INSTANCE == null){
            synchronized (SQLiteOpenHelper.class){
                if (INSTANCE == null){
                    INSTANCE = new TransaksiHelper(context);
                }
            }
        }

        return INSTANCE;
    }

    public void open(){
        database = databaseHelper.getWritableDatabase();
    }

    public void close(){
        databaseHelper.close();
        if (database.isOpen()){
            database.close();
        }
    }

    public long addToTransaksi(int idStaff, RequestStaff requestStaff){
        ContentValues args = new ContentValues();
        args.put(ID_ADMIN, idStaff);
        args.put(ID_TRANSAKSI, requestStaff.getIdRequest());
        args.put(NAMA_MEMBER, requestStaff.getNamaMember());
        args.put(JUMLAH_PESANAN, requestStaff.getJumlahPesan());
        args.put(TOTAL, rupiahFormat(Integer.parseInt(requestStaff.getTotalHarga())));
        args.put(BAYAR, rupiahFormat(Integer.parseInt(requestStaff.getUangBayar())));
        args.put(KEMBALIAN, rupiahFormat(Integer.parseInt(requestStaff.getUang_kembali())));
        args.put(TANGGAL, DateFormat.format("dd MMM yyyy", requestStaff.getTanggal()).toString());
        return database.insert(TABLE_TRANSAKSI, null, args);
    }

    public List<RequestStaff> getAllTransaksi(){
        List<RequestStaff> requestStaffList = new ArrayList<>();
        Cursor cursor = database.query(TABLE_TRANSAKSI, null,
                null,
                null,
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        RequestStaff requestStaff;
        if (cursor.getCount() > 0){
            do{
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MM yyyy");
                Date date = new Date();
                try {
                    date = simpleDateFormat.parse(cursor.getString(cursor.getColumnIndex(TANGGAL)));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                requestStaff = new RequestStaff();
                requestStaff.setIdRequest(cursor.getString(cursor.getColumnIndex(ID_TRANSAKSI)));
                requestStaff.setNamaMember(cursor.getString(cursor.getColumnIndex(NAMA_MEMBER)));
                requestStaff.setJumlahPesan(cursor.getString(cursor.getColumnIndex(JUMLAH_PESANAN)));
                requestStaff.setTotalHarga(cursor.getString(cursor.getColumnIndex(TOTAL)));
                requestStaff.setUangBayar(cursor.getString(cursor.getColumnIndex(BAYAR)));
                requestStaff.setUang_kembali(cursor.getString(cursor.getColumnIndex(KEMBALIAN)));
                requestStaff.setTanggal(date);
                requestStaffList.add(requestStaff);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return requestStaffList;
    }

    public long cleanTransaksi(int id_staff){
        return database.delete(TABLE_TRANSAKSI, ID_ADMIN+" = "+id_staff, null);
    }

}
