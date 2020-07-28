package id.co.myproject.talasstore_owner.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.talasstore_owner.R;
import id.co.myproject.talasstore_owner.model.Staff;
import id.co.myproject.talasstore_owner.view.staff.TambahStaffActivity;

import static id.co.myproject.talasstore_owner.util.Helper.TYPE_EDIT;

public class DataStaffAdapter extends RecyclerView.Adapter<DataStaffAdapter.ViewHolder> {
    List<Staff> staffList = new ArrayList<>();
    Context context;

    public DataStaffAdapter(Context context) {
        this.context = context;
    }

    public void setUserList(List<Staff> staffList){
        this.staffList.clear();
        this.staffList.addAll(staffList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DataStaffAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_staff, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataStaffAdapter.ViewHolder holder, int position) {
//        Glide.with(context).load(BuildConfig.BASE_URL_GAMBAR+"member/"+staffList.get(position).ge()).into(holder.ivMember);
        holder.tvMember.setText(staffList.get(position).getNama());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TambahStaffActivity.class);
                intent.putExtra("id_staff", staffList.get(position).getKodeStaff());
                intent.putExtra("type", TYPE_EDIT);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return staffList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
//        ImageView ivMember;
        TextView tvMember;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMember = itemView.findViewById(R.id.tv_staff);
        }
    }
}
