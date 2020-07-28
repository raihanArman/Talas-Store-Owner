package id.co.myproject.talasstore_owner.view.detail;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.talasstore_owner.BuildConfig;
import id.co.myproject.talasstore_owner.R;
import id.co.myproject.talasstore_owner.adapter.RiwayatPembayaranAdapter;
import id.co.myproject.talasstore_owner.model.Pesanan;
import id.co.myproject.talasstore_owner.model.User;
import id.co.myproject.talasstore_owner.model.Value;
import id.co.myproject.talasstore_owner.request.ApiRequest;
import id.co.myproject.talasstore_owner.request.RetrofitRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailMemberFragment extends Fragment {

    ImageView ivUser, ivSetting, ivBakc, ivNonaktif;
    TextView tvUser, tvEmail, tvAlamat, tvNoTelp;
    Button btnLogOut;
    RecyclerView rv_pembayaran;
    RiwayatPembayaranAdapter riwayatPembayaranAdapter;
    SharedPreferences sharedPreferences;
    String kodemember;
    boolean loginStatus;
    SharedPreferences.Editor editor;
    int idUser;
    ApiRequest apiRequest;

    public DetailMemberFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_member, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        kodemember = getArguments().getString("kode_member");

        ivUser = view.findViewById(R.id.iv_user);
        tvUser = view.findViewById(R.id.tv_user);
        ivBakc = view.findViewById(R.id.iv_back);
        ivNonaktif = view.findViewById(R.id.iv_nonaktif);
        tvEmail = view.findViewById(R.id.tv_email);
        tvAlamat = view.findViewById(R.id.tv_alamat);
        tvNoTelp = view.findViewById(R.id.tv_no_telp);

        rv_pembayaran = view.findViewById(R.id.rv_pembayaran);

        loadDataUser();

        riwayatPembayaranAdapter = new RiwayatPembayaranAdapter(getActivity());
        rv_pembayaran.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_pembayaran.setAdapter(riwayatPembayaranAdapter);
        loadDataPembayaran();

        ivBakc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });

        ivNonaktif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nonaktifMember();
            }
        });

    }

    private void nonaktifMember() {
        Call<Value> nonaktif = apiRequest.getNonaktifMember(kodemember);
        nonaktif.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    if (response.body().getValue() == 1){
                        getActivity().getSupportFragmentManager().popBackStackImmediate();
                    }
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataPembayaran() {
        Call<List<Pesanan>> getRiwayat = apiRequest.getPembayaranRequest(kodemember);
        getRiwayat.enqueue(new Callback<List<Pesanan>>() {
            @Override
            public void onResponse(Call<List<Pesanan>> call, Response<List<Pesanan>> response) {
                if (response.isSuccessful()){
                    List<Pesanan> pesanans = response.body();
                    riwayatPembayaranAdapter.setPesananList(pesanans);
                }
            }

            @Override
            public void onFailure(Call<List<Pesanan>> call, Throwable t) {
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadDataUser() {
        Call<User> userCall = apiRequest.getMemberItemRequest(kodemember);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    User user = response.body();
                    tvUser.setText(user.getNamaMember());
                    tvEmail.setText(user.getUsername());
                    tvAlamat.setText(user.getAlamatMember());
                    tvNoTelp.setText(user.getNoTelpMember());
                    Glide.with(getActivity()).load(BuildConfig.BASE_URL_GAMBAR+"member/"+user.getGambarMember()).into(ivUser);
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
