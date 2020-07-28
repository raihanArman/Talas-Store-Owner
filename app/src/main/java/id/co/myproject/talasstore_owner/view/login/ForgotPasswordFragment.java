package id.co.myproject.talasstore_owner.view.login;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.TransitionManager;
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
public class ForgotPasswordFragment extends Fragment {

    private EditText etRegisteredEmail;
    private Button btnResetPassword;
    private TextView tvKembali;

    private FrameLayout parentFragment;
    private ViewGroup emailIconContainer;
    private ImageView emailIcon;
    private TextView emailIconText;
    private ProgressBar progressBar;

    ApiRequest apiRequest;


    public ForgotPasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_forgot_password, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etRegisteredEmail = view.findViewById(R.id.et_forgot_password_email);
        btnResetPassword = view.findViewById(R.id.btn_reset_password);
        tvKembali = view.findViewById(R.id.tv_reset_password_go_back);
        parentFragment = getActivity().findViewById(R.id.frame_login);
        emailIconContainer = view.findViewById(R.id.forgot_password_email_container);
        emailIcon = view.findViewById(R.id.forgot_password_email_icon);
        emailIconText = view.findViewById(R.id.forgot_password_email_text);
        progressBar = view.findViewById(R.id.forgot_password_progress);

        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);

        tvKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFragment(new SignInFragment());
            }
        });

        etRegisteredEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(emailIconContainer);
                emailIconText.setVisibility(View.GONE);

                TransitionManager.beginDelayedTransition(emailIconContainer);
                emailIcon.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);

                btnResetPassword.setEnabled(false);
                cekEmail(etRegisteredEmail.getText().toString());
            }
        });

    }

    private void setFragment(Fragment fragment){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(parentFragment.getId(), fragment);
        transaction.commit();
    }

    private void checkInputs() {
        if (TextUtils.isEmpty(etRegisteredEmail.getText())){
            btnResetPassword.setEnabled(false);
        }else {
            btnResetPassword.setEnabled(true);
        }
    }
    private void cekEmail(String email){
        Call<Value> cekUserRequest = apiRequest.cekAdminRequest(email);
        cekUserRequest.enqueue(new Callback<Value>() {
            @Override
            public void onResponse(Call<Value> call, Response<Value> response) {
                if (response.isSuccessful()){
                    if(response.body().getValue() == 1){
                        ScaleAnimation scaleAnimation = new ScaleAnimation(1,0,1,0, emailIcon.getWidth()/2, emailIcon.getHeight()/2);
                        scaleAnimation.setDuration(100);
                        scaleAnimation.setInterpolator(new AccelerateInterpolator());
                        scaleAnimation.setRepeatMode(Animation.REVERSE);
                        scaleAnimation.setRepeatCount(1);
                        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                emailIconText.setVisibility(View.VISIBLE);
                                emailIconText.setText("Email berhasil ditemukan");
                                emailIconText.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                TransitionManager.beginDelayedTransition(emailIconContainer);
                                FragmentManager fm = getActivity().getSupportFragmentManager();
                                int idAdmin = response.body().getIdAdmin();

                                LupaPasswordDialogFragment lupaPasswordDialogFragment = new LupaPasswordDialogFragment(idAdmin);
                                lupaPasswordDialogFragment.show(fm, "");
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {
                                emailIcon.setImageResource(R.drawable.mail_green);
                            }
                        });
                        emailIcon.startAnimation(scaleAnimation);
                    }else {
                        String error = response.body().getMessage();
                        btnResetPassword.setEnabled(true);
                        emailIconText.setText(error);
                        emailIconText.setTextColor(getResources().getColor(R.color.colorPrimary));
                        TransitionManager.beginDelayedTransition(emailIconContainer);
                        emailIconText.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onFailure(Call<Value> call, Throwable t) {
                Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
