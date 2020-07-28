package id.co.myproject.talasstore_owner.view.staff;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import id.co.myproject.talasstore_owner.R;
import id.co.myproject.talasstore_owner.model.Staff;
import id.co.myproject.talasstore_owner.model.Value;
import id.co.myproject.talasstore_owner.request.ApiRequest;
import id.co.myproject.talasstore_owner.request.RetrofitRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static id.co.myproject.talasstore_owner.util.Helper.TYPE_ADD;
import static id.co.myproject.talasstore_owner.util.Helper.TYPE_EDIT;

public class TambahStaffActivity extends AppCompatActivity {

    Toolbar toolbar;
    ApiRequest apiRequest;
    EditText et_nama_kasir, et_password, et_username_kasir;
    TextView tv_simpan, tv_update, tv_hapus;
    int type_intent;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_staff);

        toolbar = findViewById(R.id.toolbar_menu_add);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

//        sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        type_intent = getIntent().getIntExtra("type", 0);
//        idCafe = sharedPreferences.getInt("id_cafe", 0);

        et_nama_kasir = findViewById(R.id.et_nama_kasir);
        et_password = findViewById(R.id.et_password);
        et_username_kasir = findViewById(R.id.et_username_kasir);
        tv_simpan = findViewById(R.id.tv_simpan);
        tv_update = findViewById(R.id.tv_update);
        tv_hapus = findViewById(R.id.tv_hapus);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Proses ...");


        if (type_intent == TYPE_ADD) {
            tv_simpan.setVisibility(View.VISIBLE);
            tv_hapus.setVisibility(View.GONE);
            tv_update.setVisibility(View.GONE);
            tv_simpan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressDialog.show();
                    inputStaff();
                }
            });
        }else if (type_intent == TYPE_EDIT){
            tv_simpan.setVisibility(View.GONE);
            tv_hapus.setVisibility(View.VISIBLE);
            tv_update.setVisibility(View.VISIBLE);
            String id_kasir = getIntent().getStringExtra("id_staff");
            loadStaffItem(id_kasir);
            tv_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressDialog.show();
                    updateKasir(id_kasir);
                }
            });

            tv_hapus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    progressDialog.show();
                    deleteKasir(id_kasir);
                }
            });
        }

        et_nama_kasir.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cekInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                cekInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void deleteKasir(String id_kasir) {
        Call<Value> deleteKasir = apiRequest.hapusStaffCallback(id_kasir);
        deleteKasir.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().getValue() == 1) {
                        Toast.makeText(TambahStaffActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(TambahStaffActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(TambahStaffActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateKasir(String id_kasir) {
        Call<Value> editStaff = apiRequest.editStaffCallback(
                id_kasir,
                et_username_kasir.getText().toString(),
                et_nama_kasir.getText().toString(),
                et_password.getText().toString()
        );
        editStaff.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().getValue() == 1) {
                        Toast.makeText(TambahStaffActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(TambahStaffActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(TambahStaffActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cekInput(){
        if (!TextUtils.isEmpty(et_nama_kasir.getText().toString())){
            if (!TextUtils.isEmpty(et_password.getText().toString())){
                if (et_password.length() >= 6){
                    tv_simpan.setEnabled(true);
                }else {
                    tv_simpan.setEnabled(false);
                }
            }else {
                tv_simpan.setEnabled(false);
            }
        }else {
            tv_simpan.setEnabled(false);
        }
    }

    private void loadStaffItem(String id_kasir) {
        Call<Staff> staffCall = apiRequest.getStaffItemCallback(id_kasir);
        staffCall.enqueue(new Callback<Staff>() {
            @Override
            public void onResponse(Call<Staff> call, Response<Staff> response) {
                if (response.isSuccessful()){
                    Staff staff = response.body();
                    setDataStaff(staff);
                    tv_hapus.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Staff> call, Throwable t) {
                Toast.makeText(TambahStaffActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setDataStaff(Staff staff) {
        et_username_kasir.setText(staff.getUsername());
        et_nama_kasir.setText(staff.getNama());
        et_password.setText(staff.getPassword());
    }

    private void inputStaff() {
        Call<Value> inputStaff = apiRequest.inpuStaffRequest(
                et_username_kasir.getText().toString(),
                et_nama_kasir.getText().toString(),
                et_password.getText().toString(),
                "Staff"
        );
        inputStaff.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()) {
                    progressDialog.dismiss();
                    if (response.body().getValue() == 1) {
                        Toast.makeText(TambahStaffActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(TambahStaffActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(TambahStaffActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
