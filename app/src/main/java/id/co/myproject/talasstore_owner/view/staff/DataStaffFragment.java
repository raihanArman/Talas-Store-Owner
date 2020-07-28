package id.co.myproject.talasstore_owner.view.staff;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.talasstore_owner.R;
import id.co.myproject.talasstore_owner.adapter.DataStaffAdapter;
import id.co.myproject.talasstore_owner.model.Staff;
import id.co.myproject.talasstore_owner.request.ApiRequest;
import id.co.myproject.talasstore_owner.request.RetrofitRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static id.co.myproject.talasstore_owner.util.Helper.TYPE_ADD;


/**
 * A simple {@link Fragment} subclass.
 */
public class DataStaffFragment extends Fragment {

    ImageView iv_back;
    LinearLayout lv_empty;
    RecyclerView rv_kasir;
    EditText et_cari;
    FloatingActionButton fb_tambah;
    ApiRequest apiRequest;
    DataStaffAdapter dataStaffAdapter;

    public DataStaffFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_staff, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);

        iv_back = view.findViewById(R.id.iv_back);
        rv_kasir = view.findViewById(R.id.rv_staff);
        et_cari = view.findViewById(R.id.et_cari);
        fb_tambah = view.findViewById(R.id.fb_tambah);
        lv_empty = view.findViewById(R.id.lv_empty);
        lv_empty.setVisibility(View.GONE);

        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });

        fb_tambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), TambahStaffActivity.class);
                intent.putExtra("type", TYPE_ADD);
                startActivity(intent);
            }
        });

        rv_kasir.setLayoutManager(new LinearLayoutManager(getActivity()));
        dataStaffAdapter = new DataStaffAdapter(getActivity());
        rv_kasir.setAdapter(dataStaffAdapter);

        et_cari.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                loadCariStaff(editable.toString());
            }
        });
    }

    private void loadCariStaff(String toString) {
        Call<List<Staff>> getCariStaff = apiRequest.cariStaffCariCallback(toString);
        getCariStaff.enqueue(new Callback<List<Staff>>() {
            @Override
            public void onResponse(Call<List<Staff>> call, Response<List<Staff>> response) {
                if(response.isSuccessful()){
                    List<Staff> staffList = response.body();
                    dataStaffAdapter.setUserList(staffList);
                }
            }

            @Override
            public void onFailure(Call<List<Staff>> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadStaff(){
        Call<List<Staff>> listCall = apiRequest.getStaffRequest();
        listCall.enqueue(new Callback<List<Staff>>() {
            @Override
            public void onResponse(Call<List<Staff>> call, Response<List<Staff>> response) {
                if (response.isSuccessful()){
                    List<Staff> staffList = response.body();
                    dataStaffAdapter.setUserList(staffList);
                    if (staffList.size() <= 0){
                        lv_empty.setVisibility(View.VISIBLE);
                        et_cari.setVisibility(View.INVISIBLE);
                        rv_kasir.setVisibility(View.INVISIBLE);
                    }else{
                        lv_empty.setVisibility(View.GONE);
                        et_cari.setVisibility(View.VISIBLE);
                        rv_kasir.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Staff>> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        loadStaff();
    }
}
