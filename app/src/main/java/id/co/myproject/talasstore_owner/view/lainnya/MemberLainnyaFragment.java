package id.co.myproject.talasstore_owner.view.lainnya;


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
import id.co.myproject.talasstore_owner.adapter.MemberAdapter;
import id.co.myproject.talasstore_owner.adapter.TransaksiAdapter;
import id.co.myproject.talasstore_owner.model.User;
import id.co.myproject.talasstore_owner.request.ApiRequest;
import id.co.myproject.talasstore_owner.request.RetrofitRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MemberLainnyaFragment extends Fragment {
    ImageView ivBack;
    RecyclerView rvDataMember;
    MemberAdapter memberAdapter;
    ApiRequest apiRequest;


    public MemberLainnyaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_member_lainnya, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        ivBack = view.findViewById(R.id.iv_back);
        rvDataMember = view.findViewById(R.id.rv_data_member);

        apiRequest = RetrofitRequest.getRetrofitInstance().create(ApiRequest.class);

        rvDataMember.setLayoutManager(new LinearLayoutManager(getActivity()));
        memberAdapter = new MemberAdapter(getActivity());
        rvDataMember.setAdapter(memberAdapter);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStackImmediate();
            }
        });

        loadDataMember();
    }

    private void loadDataMember() {
        Call<List<User>> getMember = apiRequest.getMemberRequest();
        getMember.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> users = response.body();
                memberAdapter.setUserList(users);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
