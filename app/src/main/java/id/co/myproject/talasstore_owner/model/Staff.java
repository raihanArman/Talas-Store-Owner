package id.co.myproject.talasstore_owner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Staff {
    @SerializedName("kode_staff")
    @Expose
    private String kodeStaff;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("pekerjaan")
    @Expose
    private String pekerjaan;

    @SerializedName("password")
    @Expose
    private String password;

    public Staff(String kodeStaff, String username, String nama, String pekerjaan, String password) {
        this.kodeStaff = kodeStaff;
        this.username = username;
        this.nama = nama;
        this.pekerjaan = pekerjaan;
        this.password = password;
    }

    public String getKodeStaff() {
        return kodeStaff;
    }

    public void setKodeStaff(String kodeStaff) {
        this.kodeStaff = kodeStaff;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
