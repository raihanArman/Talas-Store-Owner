package id.co.myproject.talasstore_owner.view;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.talasstore_owner.R;
import id.co.myproject.talasstore_owner.adapter.MemberHomeAdapter;
import id.co.myproject.talasstore_owner.adapter.StaffAdapter;
import id.co.myproject.talasstore_owner.adapter.TransaksiAdapter;
import id.co.myproject.talasstore_owner.model.Admin;
import id.co.myproject.talasstore_owner.model.Pesanan;
import id.co.myproject.talasstore_owner.model.Staff;
import id.co.myproject.talasstore_owner.model.User;
import id.co.myproject.talasstore_owner.model.Value;
import id.co.myproject.talasstore_owner.request.ApiRequest;
import id.co.myproject.talasstore_owner.request.RetrofitRequest;
import id.co.myproject.talasstore_owner.util.Helper;
import id.co.myproject.talasstore_owner.view.lainnya.MemberLainnyaFragment;
import id.co.myproject.talasstore_owner.view.lainnya.TransaksiLainnyaFragment;
import id.co.myproject.talasstore_owner.view.staff.DataStaffFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    ApiRequest apiRequest;
    RecyclerView rvStaff, rvMember, rvTransaksi;
    MemberHomeAdapter memberAdapter;
    TextView tvAdmin;
    SharedPreferences sharedPreferences;
    int idAdmin;
    ConstraintLayout view_notif;
    TextView tvNotif;
    ImageView tvLainnyaStaff, tvLainnyaMember, tvLainnyaTransaksi;
    TransaksiAdapter transaksiAdapter;
    StaffAdapter staffAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        rvStaff = view.findViewById(R.id.rv_staff);
        rvMember = view.findViewById(R.id.rv_member);
        rvTransaksi = view.findViewById(R.id.rv_transaksi);
        view_notif = view.findViewById(R.id.view_notif);
        tvNotif = view.findViewById(R.id.tv_notif);
        tvAdmin = view.findViewById(R.id.tv_admin);
        tvLainnyaStaff = view.findViewById(R.id.lainnya_staff);
        tvLainnyaMember = view.findViewById(R.id.lainnya_member);
        tvLainnyaTransaksi = view.findViewById(R.id.lainnya_transaksi);
        sharedPreferences = getActivity().getSharedPreferences(Helper.KEY_LOGIN_SHARED_PREF, Context.MODE_PRIVATE);
//        editor = sharedPreferences.edit();
        idAdmin = sharedPreferences.getInt(Helper.KEY_ID_ADMIN, 0);

        memberAdapter = new MemberHomeAdapter(getActivity());
        transaksiAdapter = new TransaksiAdapter(getActivity(), apiRequest);
        staffAdapter = new StaffAdapter(getActivity());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvTransaksi.setLayoutManager(layoutManager);
        rvTransaksi.setAdapter(transaksiAdapter);

        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvStaff.setLayoutManager(layoutManager1);
        rvStaff.setAdapter(staffAdapter);


        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity());
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvMember.setLayoutManager(layoutManager2);
        rvMember.setAdapter(memberAdapter);
        loadDataTransaksi();
        loadDataMember();
        loadDataStaff();
        loadNotif();
        loadDataAdmin();

        view_notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) view.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_home, new NotifFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        tvLainnyaStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) view.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_home, new DataStaffFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        tvLainnyaMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) view.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_home, new MemberLainnyaFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        tvLainnyaTransaksi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) view.getContext()).getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_home, new TransaksiLainnyaFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

    }

    private void loadDataAdmin() {
        Call<Admin> getAdmin = apiRequest.getAdminRequest(idAdmin);
        getAdmin.enqueue(new Callback<Admin>() {
            @Override
            public void onResponse(Call<Admin> call, Response<Admin> response) {
                if (response.isSuccessful()){
                    Admin admin = response.body();
                    tvAdmin.setText(admin.getNama());
                }
            }

            @Override
            public void onFailure(Call<Admin> call, Throwable t) {
                Toast.makeText(getActivity(), "Error : admin"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadNotif() {
        Call<Value> getNotif = apiRequest.getNotifRequest();
        getNotif.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()){
                    tvNotif.setText(response.body().getJumlahNotif());
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataStaff() {
        Call<List<Staff>> getStaff = apiRequest.getStaffRequest();
        getStaff.enqueue(new Callback<List<Staff>>() {
            @Override
            public void onResponse(Call<List<Staff>> call, Response<List<Staff>> response) {
                if (response.isSuccessful()){
                    List<Staff> staffList = response.body();
                    staffAdapter.setUserList(staffList);
                }
            }

            @Override
            public void onFailure(Call<List<Staff>> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataMember() {
        Call<List<User>> getMember = apiRequest.getMemberRequest();
        getMember.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> users = response.body();
                memberAdapter.setUserList(users);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataTransaksi() {
        Call<List<Pesanan>> getTransaksi = apiRequest.gettransaksiRequest();
        getTransaksi.enqueue(new Callback<List<Pesanan>>() {
            @Override
            public void onResponse(Call<List<Pesanan>> call, Response<List<Pesanan>> response) {
                if (response.isSuccessful()){
                    List<Pesanan> pesanans = response.body();
                    transaksiAdapter.setPesananList(pesanans);
                }
            }

            @Override
            public void onFailure(Call<List<Pesanan>> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
