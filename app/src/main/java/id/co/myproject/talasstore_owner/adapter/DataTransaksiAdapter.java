package id.co.myproject.talasstore_owner.adapter;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.talasstore_owner.R;
import id.co.myproject.talasstore_owner.model.RequestStaff;

import static id.co.myproject.talasstore_owner.util.Helper.rupiahFormat;


public class DataTransaksiAdapter extends RecyclerView.Adapter<DataTransaksiAdapter.ViewHolder> {
    List<RequestStaff> requestStaffList = new ArrayList<>();
    Context context;

    public DataTransaksiAdapter(Context context) {
        this.context = context;
    }

    public void setRequestStaffList(List<RequestStaff> requestStaffList) {
        this.requestStaffList = requestStaffList;
    }

    @NonNull
    @Override
    public DataTransaksiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_transaksi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataTransaksiAdapter.ViewHolder holder, int position) {
        String date = DateFormat.format("dd MMM yyyy", requestStaffList.get(position).getTanggal()).toString();
        holder.tv_tanggal.setText(date);
        holder.tv_nama_pemesan.setText(requestStaffList.get(position).getNamaMember());
        holder.tv_jumlah_pesanan.setText(requestStaffList.get(position).getJumlahPesan()+" pesanan");
        holder.tv_total.setText(rupiahFormat(Integer.parseInt(requestStaffList.get(position).getTotalHarga())));
        holder.tv_bayar.setText(rupiahFormat(Integer.parseInt(requestStaffList.get(position).getUangBayar())));
    }

    @Override
    public int getItemCount() {
        return requestStaffList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_tanggal, tv_nama_pemesan, tv_jumlah_pesanan, tv_total, tv_bayar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_tanggal = itemView.findViewById(R.id.tv_tanggal);
            tv_nama_pemesan = itemView.findViewById(R.id.tv_nama_pemesan);
            tv_jumlah_pesanan = itemView.findViewById(R.id.tv_jumlah_pesanan);
            tv_total = itemView.findViewById(R.id.tv_total);
            tv_bayar = itemView.findViewById(R.id.tv_bayar);
        }
    }
}
