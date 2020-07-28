package id.co.myproject.talasstore_owner.adapter;

import android.content.Context;
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
import id.co.myproject.talasstore_owner.model.Staff;
import id.co.myproject.talasstore_owner.model.User;

public class StaffAdapter extends RecyclerView.Adapter<StaffAdapter.ViewHolder> {
    List<Staff> staffList = new ArrayList<>();
    Context context;

    public StaffAdapter(Context context) {
        this.context = context;
    }

    public void setUserList(List<Staff> staffList){
        this.staffList.clear();
        this.staffList.addAll(staffList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public StaffAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staff_home, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StaffAdapter.ViewHolder holder, int position) {
//        Glide.with(context).load(BuildConfig.BASE_URL_GAMBAR+"member/"+staffList.get(position).ge()).into(holder.ivMember);
        holder.tvMember.setText(staffList.get(position).getNama());
    }

    @Override
    public int getItemCount() {
        return staffList.size();
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
