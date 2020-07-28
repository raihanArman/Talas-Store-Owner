package id.co.myproject.talasstore_owner.view.data;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.talasstore_owner.R;
import id.co.myproject.talasstore_owner.adapter.DataTransaksiAdapter;
import id.co.myproject.talasstore_owner.model.RequestStaff;
import id.co.myproject.talasstore_owner.request.ApiRequest;
import id.co.myproject.talasstore_owner.request.RetrofitRequest;
import id.co.myproject.talasstore_owner.view.MainActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.co.myproject.talasstore_owner.util.Helper.FILTER_HARI_INI;
import static id.co.myproject.talasstore_owner.util.Helper.FILTER_SEMUA;

/**
 * A simple {@link Fragment} subclass.
 */
public class DataTransaksiCetakFragment extends Fragment {

    RecyclerView rv_data_zakat;
    LinearLayout lv_preview;
    LinearLayout lv_cetak, lv_empty, lv_filter;
    ApiRequest apiRequest;
    DataTransaksiAdapter dataTransaksiAdapter;
    SharedPreferences sharedPreferences;
    Spinner sp_filter;
    int type_filter;
    ProgressDialog progressDialog;
    TextView tvTanggalHariIni;

    public DataTransaksiCetakFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_transaksi_cetak, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        sharedPreferences = getActivity().getSharedPreferences(Helper., Context.MODE_PRIVATE);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Proses ...");

        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
//        int idMasjid = sharedPreferences.getInt("id_masjid", 0);
        rv_data_zakat = view.findViewById(R.id.rv_data_zakat);
        lv_preview = view.findViewById(R.id.lv_preview);
        lv_empty = view.findViewById(R.id.lv_empty);
        lv_filter = view.findViewById(R.id.lv_filter);
        tvTanggalHariIni = view.findViewById(R.id.tv_tanggal_hari_ini);
        rv_data_zakat.setLayoutManager(new LinearLayoutManager(getActivity()));
        dataTransaksiAdapter = new DataTransaksiAdapter(getActivity());
        rv_data_zakat.setAdapter(dataTransaksiAdapter);
        sp_filter = view.findViewById(R.id.sp_filter);

        List<String> filterList = new ArrayList<>();
        filterList.add("Hari ini");
        filterList.add("Semua");

        String hari_ini = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());
        tvTanggalHariIni.setText(hari_ini);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.item_spinner,R.id.weekofday, filterList);
        sp_filter.setAdapter(arrayAdapter);

        sp_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type_filter = i;
                loadDataPemasukan(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        lv_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hari_ini = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(new Date());
                PreviewFragment cobahFragment = new PreviewFragment();
                Bundle bundle = new Bundle();
                bundle.putString("tanggal", hari_ini);
                bundle.putInt("type_filter", type_filter);
                cobahFragment.setArguments(bundle);
                ((MainActivity)view.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_home, cobahFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private void loadDataPemasukan(int filter_position) {
        progressDialog.show();
        if (filter_position == FILTER_HARI_INI) {
            String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            String time1 = " 00:00:00";
            String time2 = " 23:59:00";
            String tanggal1 = date + time1;
            String tanggal2 = date + time2;

            Call<List<RequestStaff>> getTransaksiToday = apiRequest.getDataTransaksiByTanggal(tanggal1, tanggal2);
            getTransaksiToday.enqueue(new Callback<List<RequestStaff>>() {
                @Override
                public void onResponse(Call<List<RequestStaff>> call, Response<List<RequestStaff>> response) {
                    if (response.isSuccessful()){
                        List<RequestStaff> zakatHistoryList = response.body();
                        dataTransaksiAdapter.setRequestStaffList(zakatHistoryList);
                        if (zakatHistoryList.size() <= 0){
                            lv_empty.setVisibility(View.VISIBLE);
                            rv_data_zakat.setVisibility(View.INVISIBLE);
                        }else{
                            lv_empty.setVisibility(View.GONE);
                            rv_data_zakat.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<RequestStaff>> call, Throwable t) {
                    Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else if (type_filter == FILTER_SEMUA){
            Call<List<RequestStaff>> getTransaksiToday = apiRequest.getDataTransaksi();
            getTransaksiToday.enqueue(new Callback<List<RequestStaff>>() {
                @Override
                public void onResponse(Call<List<RequestStaff>> call, Response<List<RequestStaff>> response) {
                    if (response.isSuccessful()){
                        List<RequestStaff> zakatHistoryList = response.body();
                        dataTransaksiAdapter.setRequestStaffList(zakatHistoryList);
                        if (zakatHistoryList.size() <= 0){
                            lv_empty.setVisibility(View.VISIBLE);
                            rv_data_zakat.setVisibility(View.INVISIBLE);
                        }else{
                            lv_empty.setVisibility(View.GONE);
                            rv_data_zakat.setVisibility(View.VISIBLE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<List<RequestStaff>> call, Throwable t) {
                    Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        progressDialog.dismiss();
    }
}
