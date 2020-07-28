package id.co.myproject.talasstore_owner.view;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.talasstore_owner.R;
import id.co.myproject.talasstore_owner.adapter.RequestAdapter;
import id.co.myproject.talasstore_owner.model.User;
import id.co.myproject.talasstore_owner.request.ApiRequest;
import id.co.myproject.talasstore_owner.request.RetrofitRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotifFragment extends Fragment {

    ImageView ivBack;
    RecyclerView rvNotif;
    RequestAdapter requestAdapter;
    ApiRequest apiRequest;

    public NotifFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notif, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ivBack = view.findViewById(R.id.iv_back);
        rvNotif = view.findViewById(R.id.rv_notif);
        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);
        requestAdapter = new RequestAdapter(getActivity(), apiRequest);

        rvNotif.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvNotif.setAdapter(requestAdapter);

        loadNotif();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });

    }

    private void loadNotif() {
        Call<List<User>> getNotif = apiRequest.getNotifAllRequest();
        getNotif.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()){
                    List<User> userList = response.body();
                    requestAdapter.setUserList(userList);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
