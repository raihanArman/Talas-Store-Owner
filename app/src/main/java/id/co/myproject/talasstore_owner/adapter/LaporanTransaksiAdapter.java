package id.co.myproject.talasstore_owner.adapter;

import android.text.format.DateFormat;
import android.widget.TextView;

import com.app.feng.fixtablelayout.inter.IDataAdapter;

import java.util.List;

import id.co.myproject.talasstore_owner.model.RequestStaff;


public class LaporanTransaksiAdapter implements IDataAdapter {
    public String[] title = {"Tanggal","Nama Pemesan", "Jumlah", "Total Harga", "Bayar", "Kembalian"};
    public List<RequestStaff> requestStaffList;

    public LaporanTransaksiAdapter(List<RequestStaff> zakatHistoryList) {
        this.requestStaffList = zakatHistoryList;
    }
    @Override
    public String getTitleAt(int i) {
        return title[i];
    }

    @Override
    public int getTitleCount() {
        return title.length;
    }

    @Override
    public int getItemCount() {
        return requestStaffList.size();
    }

    @Override
    public void convertData(int i, List<TextView> list) {
        RequestStaff zakatHistory = requestStaffList.get(i);
        list.get(1).setText(zakatHistory.getNamaMember());
        list.get(2).setText(zakatHistory.getJumlahPesan());
        list.get(3).setText(zakatHistory.getTotalHarga());
        list.get(4).setText(zakatHistory.getUangBayar());
        list.get(5).setText(zakatHistory.getUang_kembali());
    }

    @Override
    public void convertLeftData(int i, TextView textView) {

        String date = DateFormat.format("dd MMM yyyy", requestStaffList.get(i).getTanggal()).toString();
        textView.setText(date);

    }
}
