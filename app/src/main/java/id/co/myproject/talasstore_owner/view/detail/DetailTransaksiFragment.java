package id.co.myproject.talasstore_owner.view.detail;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.talasstore_owner.BuildConfig;
import id.co.myproject.talasstore_owner.R;
import id.co.myproject.talasstore_owner.adapter.MakananPesananAdapter;
import id.co.myproject.talasstore_owner.model.ListPesanan;
import id.co.myproject.talasstore_owner.model.Pesanan;
import id.co.myproject.talasstore_owner.model.User;
import id.co.myproject.talasstore_owner.request.ApiRequest;
import id.co.myproject.talasstore_owner.request.RetrofitRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.co.myproject.talasstore_owner.util.Helper.rupiahFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailTransaksiFragment extends Fragment {

    TextView tvUser, tvTotalBayar, tvJumlahPesanan, tvuangBayar, tvKembalian;
    ImageView ivUser, ivBack;
    RecyclerView rvrequest;
    ApiRequest apiRequest;
    MakananPesananAdapter makananPesananAdapter;

    public DetailTransaksiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_transaksi, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        tvUser = view.findViewById(R.id.tv_user);
        tvTotalBayar = view.findViewById(R.id.tv_total_bayar);
        tvJumlahPesanan = view.findViewById(R.id.tv_jumlah_pesanan);
        rvrequest = view.findViewById(R.id.rv_request);
        tvuangBayar = view.findViewById(R.id.tv_uang_bayar);
        tvKembalian = view.findViewById(R.id.tv_kembalian);
        ivBack = view.findViewById(R.id.iv_back);
        ivUser = view.findViewById(R.id.iv_user);

        int idRequest = getArguments().getInt("id_request");
        loadPesanan(idRequest);
        loadListPesanan(idRequest);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });


        rvrequest.setLayoutManager(new LinearLayoutManager(getActivity()));
        makananPesananAdapter = new MakananPesananAdapter(getActivity());
        rvrequest.setAdapter(makananPesananAdapter);

    }

    private void loadListPesanan(int idRequest) {
        Call<List<ListPesanan>> getListPesanan = apiRequest.getListPesananRequest(idRequest);
        getListPesanan.enqueue(new Callback<List<ListPesanan>>() {
            @Override
            public void onResponse(Call<List<ListPesanan>> call, Response<List<ListPesanan>> response) {
                if (response.isSuccessful()){
                    List<ListPesanan> listPesanans = response.body();
                    makananPesananAdapter.setRequestList(listPesanans);
                }
            }

            @Override
            public void onFailure(Call<List<ListPesanan>> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void loadPesanan(int idRequest) {
        Call<Pesanan> pesananCall = apiRequest.getOrderDetailRequest(idRequest);
        pesananCall.enqueue(new Callback<Pesanan>() {
            @Override
            public void onResponse(Call<Pesanan> call, Response<Pesanan> response) {
                if (response.isSuccessful()){
                    Pesanan pesanan = response.body();
                    tvuangBayar.setText("Uang bayar : "+rupiahFormat(Integer.parseInt(pesanan.getUangBayar())));
                    tvuangBayar.setText("Kembalian : "+rupiahFormat(Integer.parseInt(pesanan.getUangKembali())));
                    tvTotalBayar.setText("Total : "+rupiahFormat(Integer.parseInt(pesanan.getTotalHarga())));
                    tvJumlahPesanan.setText("Jumlah pesanan : "+pesanan.getJumlah_pesanan());
                    Call<User> userCall = apiRequest.getMemberItemRequest(pesanan.getKodeMember());
                    userCall.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            User user = response.body();
                            tvUser.setText(user.getNamaMember());
                            Glide.with(getActivity()).load(BuildConfig.BASE_URL_GAMBAR+"member/"+user.getGambarMember()).into(ivUser);
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Pesanan> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
