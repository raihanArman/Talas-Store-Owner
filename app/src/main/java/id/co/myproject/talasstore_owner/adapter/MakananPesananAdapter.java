package id.co.myproject.talasstore_owner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.talasstore_owner.R;
import id.co.myproject.talasstore_owner.model.ListPesanan;

import static id.co.myproject.talasstore_owner.util.Helper.rupiahFormat;

public class MakananPesananAdapter extends RecyclerView.Adapter<MakananPesananAdapter.ViewHolder> {

    List<ListPesanan> requestList = new ArrayList<>();
    Context context;


    public MakananPesananAdapter(Context context) {
        this.context = context;
    }

    public void setRequestList(List<ListPesanan> requestList){
        this.requestList.clear();
        this.requestList.addAll(requestList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MakananPesananAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_makanan_pesanan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MakananPesananAdapter.ViewHolder holder, int position) {
        holder.tvMakanan.setText(requestList.get(position).getNamaMakanan());
        holder.tvQty.setText(requestList.get(position).getQty());
        holder.tvSubTotal.setText(rupiahFormat(Integer.parseInt(requestList.get(position).getSubTotal())));
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvMakanan, tvQty, tvSubTotal;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSubTotal = itemView.findViewById(R.id.tv_sub_total);
            tvMakanan = itemView.findViewById(R.id.tv_nama_makanan);
            tvQty = itemView.findViewById(R.id.tv_qty);
        }
    }
}
