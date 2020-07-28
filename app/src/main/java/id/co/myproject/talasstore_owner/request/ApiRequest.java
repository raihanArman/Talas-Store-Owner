package id.co.myproject.talasstore_owner.request;

import java.util.List;

import id.co.myproject.talasstore_owner.model.Admin;
import id.co.myproject.talasstore_owner.model.ListPesanan;
import id.co.myproject.talasstore_owner.model.Pesanan;
import id.co.myproject.talasstore_owner.model.RequestStaff;
import id.co.myproject.talasstore_owner.model.Staff;
import id.co.myproject.talasstore_owner.model.User;
import id.co.myproject.talasstore_owner.model.Value;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiRequest {
    @GET("tampil_member.php")
    Call<List<User>> getMemberRequest();

    @GET("tampil_member.php")
    Call<User> getMemberItemRequest(
            @Query("kode_member") String kodeMember
    );

    @GET("tampil_staff_item.php")
    Call<List<Staff>> getStaffRequest();

    @GET("tampil_data_transaksi.php")
    Call<List<Pesanan>> gettransaksiRequest();

    @GET("tampil_transaksi.php")
    Call<Pesanan> getOrderDetailRequest(
            @Query("id_transaksi") int idTransaksi
    );

    @GET("tampil_pesanan.php")
    Call<List<ListPesanan>> getListPesananRequest(
            @Query("id_transaksi") int idTransaksi
    );

    @GET("tampil_riwayat_pembayaran.php")
    Call<List<Pesanan>> getPembayaranRequest(
            @Query("kode_member") String kodeMember
    );

    @FormUrlEncoded
    @POST("login_admin.php")
    Call<Value> loginAdminRequest(
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("tampil_jumlah_notif.php")
    Call<Value> getNotifRequest();

    @GET("tampil_notif.php")
    Call<List<User>> getNotifAllRequest();


    @FormUrlEncoded
    @POST("update_notif.php")
    Call<Value> getUpdateNotif(
            @Field("kode_member") String kodeMember
    );

    @FormUrlEncoded
    @POST("nonaktif_member.php")
    Call<Value> getNonaktifMember(
            @Field("kode_member") String kodeMember
    );

    @GET("tampil_data_transaksi.php")
    Call<List<RequestStaff>> getDataTransaksiByTanggal(
            @Query("tanggal_1") String tanggal1,
            @Query("tanggal_2") String tanggal2
    );
    @GET("tampil_data_transaksi.php")
    Call<List<RequestStaff>> getDataTransaksi();

    @GET("tampil_staff_item.php")
    Call<List<Staff>> cariStaffCariCallback(
            @Query("cari") String cari
    );

    @GET("tampil_staff_item.php")
    Call<Staff> getStaffItemCallback(
            @Query("id_staff") String idStaff
    );

    @FormUrlEncoded
    @POST("hapus_staff.php")
    Call<Value> hapusStaffCallback(
            @Field("id_staff") String idStaff
    );

    @FormUrlEncoded
    @POST("edit_staff.php")
    Call<Value> editStaffCallback(
            @Field("id_staff") String idStaff,
            @Field("username") String username,
            @Field("nama") String nama,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("tambah_staff.php")
    Call<Value> inpuStaffRequest(
            @Field("username") String usernameKasir,
            @Field("nama") String namaKasir,
            @Field("password") String password,
            @Field("pekerjaan") String pekerjaan
    );

    @FormUrlEncoded
    @POST("lupa_password_admin.php")
    Call<Value> lupaPasswordAdminRequest(
            @Field("id_admin") int idAdmin,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("cek_admin.php")
    Call<Value> cekAdminRequest(
            @Field("username") String username
    );

    @GET("tampil_admin.php")
    Call<Admin> getAdminRequest(
            @Query("id_admin") int idAdmin
    );

    @FormUrlEncoded
    @POST("edit_profil_admin.php")
    Call<Value> editProfilAdminRequest(
            @Field("id_admin") int idAdmin,
            @Field("username") String username,
            @Field("nama") String nama
    );


}
