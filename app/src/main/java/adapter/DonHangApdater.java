package adapter;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.DonHang;
import model.SanPham;

public class DonHangApdater extends BaseAdapter {
    private Context context;
    private List<DonHang> donHangList;
    private int layout;

    public DonHangApdater(Context context, List<DonHang> donHangList, int layout) {
        this.context = context;
        this.donHangList = donHangList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return donHangList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.img = view.findViewById(R.id.imageViewSanPhamDonHang);
            holder.txtMoTa = view.findViewById(R.id.textViewSanPhamDonHang);

            DonHang donHang = donHangList.get(i);
            Picasso.get().load(donHang.getHinhAnhSanPham()).error(R.drawable.ic_baseline_error_24).into(holder.img);
            holder.txtMoTa.setText(donHang.getMoTaSanPham());
            view.setTag(holder);
        }else{
             view.getTag();
        }
        return view;
    }
    private class ViewHolder{
        ImageView img;
        TextView txtMoTa;
    }
}
