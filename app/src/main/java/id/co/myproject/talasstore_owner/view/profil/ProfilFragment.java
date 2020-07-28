package id.co.myproject.talasstore_owner.view.profil;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import id.co.myproject.talasstore_owner.R;
import id.co.myproject.talasstore_owner.model.Admin;
import id.co.myproject.talasstore_owner.request.ApiRequest;
import id.co.myproject.talasstore_owner.request.RetrofitRequest;
import id.co.myproject.talasstore_owner.util.Helper;
import id.co.myproject.talasstore_owner.view.MainActivity;
import id.co.myproject.talasstore_owner.view.login.LoginActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfilFragment extends Fragment {


    TextView tv_nama, tv_email;
    Button btn_logout;
    ImageView ivSetting;
    ApiRequest apiRequest;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    int idAdmin;
    public static boolean statusUpdate = false;

    public ProfilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profil, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_nama = view.findViewById(R.id.tv_user);
        tv_email = view.findViewById(R.id.tv_email);
        btn_logout = view.findViewById(R.id.btn_log_out);
        ivSetting = view.findViewById(R.id.iv_setting);
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        sharedPreferences = getActivity().getSharedPreferences(Helper.KEY_LOGIN_SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        idAdmin = sharedPreferences.getInt(Helper.KEY_ID_ADMIN, 0);


        loadDataAdmin();

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });


        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditProfilFragment editProfilFragment = new EditProfilFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(Helper.KEY_ID_ADMIN, idAdmin);
                editProfilFragment.setArguments(bundle);
                ((MainActivity)view.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_home, editProfilFragment)
                        .addToBackStack("")
                        .commit();
            }
        });

    }

    private void signOut() {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Proses ...");
        progressDialog.show();
        editor.putInt(Helper.KEY_ID_ADMIN, 0);
        editor.putBoolean(Helper.KEY_LOGIN_STATUS, false);
        editor.commit();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    private void loadDataAdmin() {
        Call<Admin> getAdmin = apiRequest.getAdminRequest(idAdmin);
        getAdmin.enqueue(new Callback<Admin>() {
            @Override
            public void onResponse(Call<Admin> call, Response<Admin> response) {
                if (response.isSuccessful()){
                    Admin admin = response.body();
                    tv_nama.setText(admin.getNama());
                    tv_email.setText(admin.getUsername());
                }
            }

            @Override
            public void onFailure(Call<Admin> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        if (statusUpdate) {
            loadDataAdmin();
        }
    }
}
