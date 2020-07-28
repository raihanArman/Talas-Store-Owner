package id.co.myproject.talasstore_owner.view.login;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import id.co.myproject.talasstore_owner.R;
import id.co.myproject.talasstore_owner.model.Value;
import id.co.myproject.talasstore_owner.request.ApiRequest;
import id.co.myproject.talasstore_owner.request.RetrofitRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class LupaPasswordDialogFragment extends DialogFragment {

    EditText et_password, et_confirm_password;
    ApiRequest apiRequest;
    TextView tv_batal, tv_selesai;
    int idAdmin;
    public LupaPasswordDialogFragment(int idAdmin) {
        // Required empty public constructor
        this.idAdmin = idAdmin;
        
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lupa_password_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        et_password = view.findViewById(R.id.et_password);
        et_confirm_password = view.findViewById(R.id.et_confirm_password);
        tv_selesai = view.findViewById(R.id.tv_selesai);
        tv_batal = view.findViewById(R.id.tv_batal);
        
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        
        tv_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        
        tv_selesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(et_password.getText().toString()) && !TextUtils.isEmpty(et_confirm_password.getText().toString())){
                    if(et_password.getText().toString().equals(et_confirm_password.getText().toString())){
                        Call<Value> lupaPasswordRequest = apiRequest.lupaPasswordAdminRequest(idAdmin, et_password.getText().toString());
                        lupaPasswordRequest.enqueue(new Callback<Value>() {
                            @Override
                            public void onResponse(Call<Value> call, Response<Value> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(getActivity(), "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    if (response.body().getValue() == 1) {
                                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.frame_login, new SignInFragment());
                                        fragmentTransaction.commit();
                                        dismiss();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<Value> call, Throwable t) {
                                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else {
                        Toast.makeText(getActivity(), "Password tidak cocok", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(getActivity(), "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
