package id.co.myproject.talasstore_owner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Value {
    @SerializedName("value")
    @Expose
    private int value;

    @SerializedName("kode_member")
    @Expose
    private String kodeMember;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("jumlah_notif")
    @Expose
    private String jumlahNotif;

    @SerializedName("id_transaksi")
    @Expose
    private int idTransaksi;

    @SerializedName("jumlah_pesanan")
    @Expose
    private int jumlahPesanan;

    @SerializedName("id_admin")
    @Expose
    private int idAdmin;

    public Value(int value, String kodeMember, String message, String jumlahNotif, int idTransaksi, int jumlahPesanan, int idAdmin) {
        this.value = value;
        this.kodeMember = kodeMember;
        this.message = message;
        this.jumlahNotif = jumlahNotif;
        this.idTransaksi = idTransaksi;
        this.jumlahPesanan = jumlahPesanan;
        this.idAdmin = idAdmin;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getKodeMember() {
        return kodeMember;
    }

    public void setKodeMember(String kodeMember) {
        this.kodeMember = kodeMember;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getJumlahNotif() {
        return jumlahNotif;
    }

    public void setJumlahNotif(String jumlahNotif) {
        this.jumlahNotif = jumlahNotif;
    }

    public int getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public int getJumlahPesanan() {
        return jumlahPesanan;
    }

    public void setJumlahPesanan(int jumlahPesanan) {
        this.jumlahPesanan = jumlahPesanan;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }
}
