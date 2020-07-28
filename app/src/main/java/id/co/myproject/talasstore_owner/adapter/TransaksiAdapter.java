package id.co.myproject.talasstore_owner.adapter;

import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import id.co.myproject.talasstore_owner.BuildConfig;
import id.co.myproject.talasstore_owner.R;
import id.co.myproject.talasstore_owner.model.Pesanan;
import id.co.myproject.talasstore_owner.model.User;
import id.co.myproject.talasstore_owner.request.ApiRequest;
import id.co.myproject.talasstore_owner.view.MainActivity;
import id.co.myproject.talasstore_owner.view.detail.DetailTransaksiFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static id.co.myproject.talasstore_owner.util.Helper.rupiahFormat;

public class TransaksiAdapter extends RecyclerView.Adapter<TransaksiAdapter.ViewHolder> {
    List<Pesanan> pesananList = new ArrayList<>();
    Context context;
    ApiRequest apiRequest;

    public TransaksiAdapter(Context context, ApiRequest apiRequest) {
        this.context = context;
        this.apiRequest = apiRequest;
    }

    public void setPesananList(List<Pesanan> pesananList){
        this.pesananList.clear();
        this.pesananList.addAll(pesananList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TransaksiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaksi, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransaksiAdapter.ViewHolder holder, int position) {
        Call<User> getMemberItem = apiRequest.getMemberItemRequest(pesananList.get(position).getKodeMember());
        getMemberItem.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    User user = response.body();
                    Glide.with(context).load(BuildConfig.BASE_URL_GAMBAR+"member/"+user.getGambarMember()).into(holder.ivMember);
                    holder.tvMember.setText(user.getNamaMember());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(context, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        holder.tvJumlahPesanan.setText("Jumlah pesanan : "+pesananList.get(position).getJumlah_pesanan());
//        Toast.makeText(context, "Jumlah pesansn : "+pesananList.get(position).getJumlah_pesanan(), Toast.LENGTH_SHORT).show();
        holder.tvTotahHarga.setText("Total harga : "+rupiahFormat(Integer.parseInt(pesananList.get(position).getTotalHarga())));
        holder.tvuangBayar.setText("uang bayar : "+rupiahFormat(Integer.parseInt(pesananList.get(position).getUangBayar())));
        String date = DateFormat.format("dd MMM yyyy", pesananList.get(position).getTanggal()).toString();
        holder.tvTanggalTransaksi.setText("Tanggal transaksi : "+date);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailTransaksiFragment detailRequestFragment = new DetailTransaksiFragment();
                Bundle bundle = new Bundle();
                detailRequestFragment.setArguments(bundle);
                bundle.putInt("id_request", pesananList.get(position).getIdTransaksi());
                ((MainActivity)view.getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_home, detailRequestFragment)
                        .addToBackStack("null")
                        .commit();
            }
        });

    }

    @Override
    public int getItemCount() {
        return pesananList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivMember;
        TextView tvMember, tvJumlahPesanan, tvTotahHarga, tvuangBayar, tvTanggalTransaksi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMember = itemView.findViewById(R.id.iv_member);
            tvMember = itemView.findViewById(R.id.tv_member);
            tvTotahHarga = itemView.findViewById(R.id.tv_total_harga);
            tvJumlahPesanan = itemView.findViewById(R.id.tv_jumlah_pesanan);
            tvuangBayar = itemView.findViewById(R.id.tv_uang_bayar);
            tvTanggalTransaksi = itemView.findViewById(R.id.tv_tanggal_transaksi);
        }
    }
}
