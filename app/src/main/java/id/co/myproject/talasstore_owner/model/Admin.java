package id.co.myproject.talasstore_owner.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Admin {
    @SerializedName("id_admin")
    @Expose
    private String idAdmin;

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("password")
    @Expose
    private String password;

    public Admin(String idAdmin, String username, String nama, String password) {
        this.idAdmin = idAdmin;
        this.username = username;
        this.nama = nama;
        this.password = password;
    }

    public String getIdAdmin() {
        return idAdmin;
    }

    public String getUsername() {
        return username;
    }

    public String getNama() {
        return nama;
    }

    public String getPassword() {
        return password;
    }
}
