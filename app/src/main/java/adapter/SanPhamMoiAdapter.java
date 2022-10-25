package adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appproject.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import model.SanPham;

public class SanPhamMoiAdapter extends BaseAdapter {
    private Context context;
    private List<SanPham> sanPhamList;
    private int layout;

    public SanPhamMoiAdapter(Context context, List<SanPham> sanPhamList, int layout) {
        this.context = context;
        this.sanPhamList = sanPhamList;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return sanPhamList.size();
    }

    @Override
    public Object getItem(int i) {
        return sanPhamList.get(i);
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
            holder.imgSanPham = view.findViewById(R.id.imageSanPhamMoi);
            holder.txtGia = view.findViewById(R.id.textViewGiaSanPhamMoi);
            holder.txtTen = view.findViewById(R.id.textViewTenSanPhamMoi);
            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }
        SanPham sanPham = sanPhamList.get(i);
        Picasso.get().load(sanPham.getHinhSanPham()).placeholder(R.drawable.ic_baseline_image_not_supported_24).error(R.drawable.ic_baseline_error_24).into(holder.imgSanPham);
        holder.txtTen.setText(sanPham.getTenSanPham());
        holder.txtGia.setText(sanPham.getGiaSanPham());
        holder.imgSanPham.setScaleType(ImageView.ScaleType.FIT_XY);
        return view;
    }
    private class ViewHolder{
        TextView txtGia,txtTen;
        ImageView imgSanPham;
    }
}
