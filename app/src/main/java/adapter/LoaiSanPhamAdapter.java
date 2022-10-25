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

import model.LoaiSanPham;

public class LoaiSanPhamAdapter extends BaseAdapter {
    private Context context;
    private List<LoaiSanPham> loaiSanPhamList;
    private int layout;

    public LoaiSanPhamAdapter(Context context, List<LoaiSanPham> loaiSanPhamList, int layout) {
        this.context = context;
        this.loaiSanPhamList = loaiSanPhamList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return loaiSanPhamList.size();
    }

    @Override
    public Object getItem(int i) {
        return loaiSanPhamList.get(i);
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
            holder.txtTenLoaiSanPham = view.findViewById(R.id.textViewTenLoaiSanPham);
            holder.imgSanPham = view.findViewById(R.id.imageViewLoaiSanPham);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        LoaiSanPham loaiSanPham = loaiSanPhamList.get(i);
        holder.txtTenLoaiSanPham.setText(loaiSanPham.getTenSanPham());
        Picasso.get().load(loaiSanPham.getHinhSanPham()).placeholder(R.drawable.ic_baseline_image_not_supported_24).error(R.drawable.ic_baseline_error_24).into(holder.imgSanPham);
        holder.imgSanPham.setScaleType(ImageView.ScaleType.FIT_XY);
        return view;
    }

    private class ViewHolder{
        TextView txtTenLoaiSanPham;
        ImageView imgSanPham;
    }
}
