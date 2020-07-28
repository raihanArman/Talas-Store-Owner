package id.co.myproject.talasstore_owner.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.talasstore_owner.BuildConfig;
import id.co.myproject.talasstore_owner.R;
import id.co.myproject.talasstore_owner.model.User;
import id.co.myproject.talasstore_owner.view.MainActivity;
import id.co.myproject.talasstore_owner.view.detail.DetailMemberFragment;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.ViewHolder> {
    List<User> userList = new ArrayList<>();
    Context context;

    public MemberAdapter(Context context) {
        this.context = context;
    }

    public void setUserList(List<User> userList){
        this.userList.clear();
        this.userList.addAll(userList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MemberAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_member, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MemberAdapter.ViewHolder holder, int position) {
        Glide.with(context).load(BuildConfig.BASE_URL_GAMBAR+"member/"+userList.get(position).getGambarMember()).into(holder.ivMember);
        holder.tvMember.setText(userList.get(position).getNamaMember());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailMemberFragment detailMemberFragment = new DetailMemberFragment();
                Bundle bundle = new Bundle();
                bundle.putString("kode_member", userList.get(position).getKodeMember());
                detailMemberFragment.setArguments(bundle);
                ((MainActivity)view.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_home, detailMemberFragment)
                        .addToBackStack("")
                        .commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivMember;
        TextView tvMember;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMember = itemView.findViewById(R.id.iv_member);
            tvMember = itemView.findViewById(R.id.tv_member);
        }
    }
}
