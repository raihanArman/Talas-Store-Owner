package id.co.myproject.talasstore_owner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class RequestStaff {
    @SerializedName("id_transaksi")
    @Expose
    private String idRequest;

    @SerializedName("kode_member")
    @Expose
    private String kodeMember;

    @SerializedName("total_harga")
    @Expose
    private String totalHarga;

    @SerializedName("tanggal")
    @Expose
    private Date tanggal;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("uang_bayar")
    @Expose
    private String uangBayar;

    @SerializedName("uang_kembali")
    @Expose
    private String uang_kembali;

    @SerializedName("nama_member")
    @Expose
    private String namaMember;

    @SerializedName("gambar_member")
    @Expose
    private String gambarMember;

    @SerializedName("jumlah_pesanan")
    @Expose
    private String jumlahPesan;

    public RequestStaff() {
    }

    public RequestStaff(String idRequest, String kodeMember, String totalHarga, Date tanggal, String status, String uangBayar, String uang_kembali, String namaMember, String gambarMember, String jumlahPesan) {
        this.idRequest = idRequest;
        this.kodeMember = kodeMember;
        this.totalHarga = totalHarga;
        this.tanggal = tanggal;
        this.status = status;
        this.uangBayar = uangBayar;
        this.uang_kembali = uang_kembali;
        this.namaMember = namaMember;
        this.gambarMember = gambarMember;
        this.jumlahPesan = jumlahPesan;
    }

    public String getIdRequest() {
        return idRequest;
    }

    public String getKodeMember() {
        return kodeMember;
    }

    public String getTotalHarga() {
        return totalHarga;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public String getStatus() {
        return status;
    }

    public String getUangBayar() {
        return uangBayar;
    }

    public String getUang_kembali() {
        return uang_kembali;
    }

    public String getNamaMember() {
        return namaMember;
    }

    public String getGambarMember() {
        return gambarMember;
    }

    public String getJumlahPesan() {
        return jumlahPesan;
    }

    public void setIdRequest(String idRequest) {
        this.idRequest = idRequest;
    }

    public void setKodeMember(String kodeMember) {
        this.kodeMember = kodeMember;
    }

    public void setTotalHarga(String totalHarga) {
        this.totalHarga = totalHarga;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUangBayar(String uangBayar) {
        this.uangBayar = uangBayar;
    }

    public void setUang_kembali(String uang_kembali) {
        this.uang_kembali = uang_kembali;
    }

    public void setNamaMember(String namaMember) {
        this.namaMember = namaMember;
    }

    public void setGambarMember(String gambarMember) {
        this.gambarMember = gambarMember;
    }

    public void setJumlahPesan(String jumlahPesan) {
        this.jumlahPesan = jumlahPesan;
    }
}
