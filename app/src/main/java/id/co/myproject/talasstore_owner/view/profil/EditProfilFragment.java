package id.co.myproject.talasstore_owner.view.profil;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import id.co.myproject.talasstore_owner.R;
import id.co.myproject.talasstore_owner.model.Admin;
import id.co.myproject.talasstore_owner.model.Value;
import id.co.myproject.talasstore_owner.request.ApiRequest;
import id.co.myproject.talasstore_owner.request.RetrofitRequest;
import id.co.myproject.talasstore_owner.util.Helper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditProfilFragment extends Fragment {

    ImageView ivBack;
    Button btnUpdate;
    EditText etNama, etUsername;
    int idAdmin;
    ApiRequest apiRequest;
    public EditProfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        idAdmin = getArguments().getInt(Helper.KEY_ID_ADMIN);

        ivBack = view.findViewById(R.id.iv_back);
        etNama = view.findViewById(R.id.et_nama);
        etUsername = view.findViewById(R.id.et_username);
        btnUpdate = view.findViewById(R.id.btn_update);

        loadDataAdmin();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editProfile();
            }
        });

    }

    private void editProfile() {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Proses ...");
        progressDialog.show();
        Call<Value> editProfil = apiRequest.editProfilAdminRequest(
                idAdmin,
                etUsername.getText().toString(),
                etNama.getText().toString()
        );
        editProfil.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()){
                    Toast.makeText(getActivity(), ""+response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    if (response.body().getValue() == 1){
                        progressDialog.dismiss();
                        ProfilFragment.statusUpdate = true;
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

    private void loadDataAdmin() {
        Call<Admin> getAdmin = apiRequest.getAdminRequest(idAdmin);
        getAdmin.enqueue(new Callback<Admin>() {
            @Override
            public void onResponse(Call<Admin> call, Response<Admin> response) {
                if (response.isSuccessful()){
                    Admin admin = response.body();
                    etNama.setText(admin.getNama());
                    etUsername.setText(admin.getUsername());
                }
            }

            @Override
            public void onFailure(Call<Admin> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
