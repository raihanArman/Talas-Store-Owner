package id.co.myproject.talasstore_owner.view.lainnya;


import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.talasstore_owner.R;
import id.co.myproject.talasstore_owner.adapter.TransaksiAdapter;
import id.co.myproject.talasstore_owner.model.Pesanan;
import id.co.myproject.talasstore_owner.request.ApiRequest;
import id.co.myproject.talasstore_owner.request.RetrofitRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class TransaksiLainnyaFragment extends Fragment {

    ImageView ivBack;
    RecyclerView rvDataTransaksi;
    TransaksiAdapter transaksiAdapter;
    ApiRequest apiRequest;

    public TransaksiLainnyaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_transaksi_lainnya, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        ivBack = view.findViewById(R.id.iv_back);
        rvDataTransaksi = view.findViewById(R.id.rv_data_transaksi);

        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);

        rvDataTransaksi.setLayoutManager(new LinearLayoutManager(getActivity()));
        transaksiAdapter = new TransaksiAdapter(getActivity(), apiRequest);
        rvDataTransaksi.setAdapter(transaksiAdapter);

        loadDataTransaksi();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
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
