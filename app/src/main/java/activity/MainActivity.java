package activity;

import static activity.StartActivity.diaChi_key;
import static activity.StartActivity.email_key;
import static activity.StartActivity.hinhAnh_key;
import static activity.StartActivity.ten_key;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appproject.R;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import adapter.DienThoaiApdater;
import adapter.LoaiSanPhamAdapter;
import adapter.MayTinhAdapter;
import adapter.SanPhamMoiAdapter;
import de.hdodenhof.circleimageview.CircleImageView;
import fragment.DonHangFragment;
import model.DienThoai;
import model.LoaiSanPham;
import model.MayTinh;
import model.SanPham;
import ultil.Server;

public class MainActivity extends AppCompatActivity {
    private ViewFlipper viewFlipper;
    private ImageView imageView, imageDonHang;
    private CircleImageView imageKhachHang;
    private DrawerLayout drawerLayout;
    private List<SanPham> sanPhamList, sanPhamList1;
    private List<DienThoai> dienThoaiList;
    private List<MayTinh> mayTinhList;
    private List<LoaiSanPham> tenChucNang;
    private ListView listView;
    private SanPhamMoiAdapter sanPhamMoiAdapter;
    private GridView gridView;
    private TextView txtDangXuat, txtSanPhamMoi;
    private TextView emailKhachHang;
    private int soLuong = 0;
    private Dialog dialog;
    private DonHangFragment fragment = new DonHangFragment();
    private int index = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        menuBar();
        sanPhamMoi();
        actionBar();
        viewFlipper();
        dangXuat();
        clickItem();
        setUserData();
        kiemTraDonHang();
        funtions();
    }

    private void funtions() {
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               switch (i){
                   case 0:
                       txtSanPhamMoi.setVisibility(View.VISIBLE);
                       gridView.setVisibility(View.VISIBLE);
                       FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                       fragmentTransaction.remove(fragment);
                       fragmentTransaction.commit();
                       break;
                   case 1:
                       for(SanPham sanPham : sanPhamList1){
                           if(sanPham.getIdLoaiSanPham() == 0){
                               dienThoaiList.add(new DienThoai(sanPham.getIdSanPham(),sanPham.getTenSanPham(),sanPham.getGiaSanPham(),sanPham.getHinhSanPham(),sanPham.getMoTaSanPham()));
                               DienThoaiApdater dienThoaiApdater = new DienThoaiApdater(getApplicationContext(),dienThoaiList,R.layout.san_pham_moi_layout);
                               dienThoaiApdater.notifyDataSetChanged();
                               gridView.setAdapter(dienThoaiApdater);

                           }
                       }
                       break;
                   case 2:
                       for(SanPham sanPham : sanPhamList1){
                           if(sanPham.getIdLoaiSanPham() == 1){
                               mayTinhList.add(new MayTinh(sanPham.getIdSanPham(),sanPham.getTenSanPham(),sanPham.getGiaSanPham(),sanPham.getHinhSanPham(),sanPham.getMoTaSanPham()));
                               MayTinhAdapter mayTinhAdapter = new MayTinhAdapter(getApplicationContext(),mayTinhList,R.layout.san_pham_moi_layout);
                               mayTinhAdapter.notifyDataSetChanged();
                               gridView.setAdapter(mayTinhAdapter);
                           }
                       }
                       break;
                   case 3:
                       Dialog dialog = new Dialog(MainActivity.this);
                       dialog.setContentView(R.layout.lien_he_dialog);
                       dialog.show();
                       break;
                   case 4:
                       Dialog dialog1 = new Dialog(MainActivity.this);
                       dialog1.setContentView(R.layout.thong_tin_dialog);
                       dialog1.show();
                       break;
               }
           }
       });
    }


    private void kiemTraDonHang() {
        imageDonHang.setOnClickListener(v->donHang());
    }

    private void donHang() {
        txtSanPhamMoi.setVisibility(View.GONE);
        gridView.setVisibility(View.GONE);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }

    private void setUserData() {
        Intent intent = getIntent();
        String email = intent.getStringExtra(email_key);
        String hinhAnh = intent.getStringExtra(hinhAnh_key);
        if(!email.equals("") && !hinhAnh.equals("")) {
            imageKhachHang.setImageURI(Uri.parse(hinhAnh));
            emailKhachHang.setText(email);
        }else if(hinhAnh.equals("")){
             imageKhachHang.setImageResource(R.drawable.ic_baseline_person_24);
            emailKhachHang.setText(email);
        }else{
            imageKhachHang.setImageURI(Uri.parse(hinhAnh));
            emailKhachHang.setText("Chưa có dữ liệu khách hàng");
        }
    }

    private void clickItem() {
        dialog = new Dialog(MainActivity.this);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
                dialog.setContentView(R.layout.san_pham_dialog);
                ImageView img = dialog.findViewById(R.id.imageViewSanPhamDialog);
                ImageView imgTang = dialog.findViewById(R.id.imageViewTangDialog);
                ImageView imgGiam = dialog.findViewById(R.id.imageViewGiamDialog);
                TextView txtMoTa = dialog.findViewById(R.id.textViewSanPhamDialog);
                TextView txtSoLuong = dialog.findViewById(R.id.textViewSoluongSanPhamDialog);
                Button btnMua = dialog.findViewById(R.id.buttonMuaSanPhamDialog);
                SanPham sanPham = sanPhamList.get(i);
                Picasso.get().load(sanPham.getHinhSanPham()).into(img);
                img.setBackgroundResource(R.drawable.edit_text_custom);
                imgTang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        soLuong++;
                        txtSoLuong.setText(String.valueOf(soLuong));
                    }
                });
                imgGiam.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(soLuong <=0 ){
                        soLuong = 1;
                        }
                        soLuong--;
                        txtSoLuong.setText(String.valueOf(soLuong));
                    }
                });
                txtMoTa.setText(sanPham.getMoTaSanPham());
               btnMua.setOnClickListener(v->chotDon());
                dialog.show();
                Window window = dialog.getWindow();
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
            }
        });
    }

    private void chotDon() {
        Intent intent = getIntent();
        String ten = intent.getStringExtra(ten_key);
        String diaChi = intent.getStringExtra(diaChi_key);
        String email = intent.getStringExtra(email_key);
        String idSanPham = String.valueOf(sanPhamList.get(index).getIdSanPham());
        String hinhAnhSanPham = sanPhamList.get(index).getHinhSanPham();
        String moTaSanPham = sanPhamList.get(index).getMoTaSanPham();
        int amount = soLuong;
        if(!ten.equals("") && !diaChi.equals("") && amount > 0) {
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.insertDonHang, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("response", response);
                    Toast.makeText(getApplicationContext(), "Đã thêm đơn hàng", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("response", error.toString());
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("ten_khach_hang", ten);
                    hashMap.put("dia_chi", diaChi);
                    hashMap.put("so_luong", String.valueOf(amount));
                    hashMap.put("tai_khoan_khach_hang", email);
                    hashMap.put("id_san_pham", idSanPham);
                    hashMap.put("hinh_anh_san_pham", hinhAnhSanPham);
                    hashMap.put("mo_ta_san_pham", moTaSanPham);
                    return hashMap;
                }
            };
            stringRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });
            requestQueue.add(stringRequest);
        }else if(amount == 0){
            Toast.makeText(getApplicationContext(),"Số lượng sản phẩm phải lớn hơn 0",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getApplicationContext(),"Chưa có dữ liệu khách hàng",Toast.LENGTH_SHORT).show();
        }
    }


    private void dangXuat() {
        txtDangXuat.setOnClickListener(v -> goToStartAcivity());
    }

    private void goToStartAcivity() {
        startActivity(new Intent(MainActivity.this, StartActivity.class));
    }

    private void sanPhamMoi() {
        getSanPham();
    }

    private void menuBar() {
        tenChucNang.add(new LoaiSanPham(1, "Trang chính", "https://cdn2.iconfinder.com/data/icons/media-icons-23/24/24pt_home-512.png"));
        tenChucNang.add(new LoaiSanPham(2, "Điện thoại", "https://i.fbcd.co/products/resized/resized-750-500/1389161287be1a069d065b8a99ec0ce8c9dc815cfbc5e9a23f9ddaca6e75834c.jpg"));
        tenChucNang.add(new LoaiSanPham(3, "Laptop", "https://media.istockphoto.com/vectors/laptop-icon-isolated-notebook-computer-symbol-vector-vector-id1281220336?k=20&m=1281220336&s=170667a&w=0&h=F7krJEz1sq_8RO1h9b_PgDcRC9qS08Ifx7fW_0m3R24="));
        tenChucNang.add(new LoaiSanPham(4, "Liên hệ", "https://cdn.pixabay.com/photo/2016/03/31/15/33/contact-1293388_960_720.png"));
        tenChucNang.add(new LoaiSanPham(5, "Thông tin", "https://media.istockphoto.com/vectors/information-icon-information-symbol-vector-illustration-vector-id1203186042?k=20&m=1203186042&s=170667a&w=0&h=BREZn1uWoC1rWUkh7X7CePg1WYXMWeqwMzVq5aP698U="));

        LoaiSanPhamAdapter adapter = new LoaiSanPhamAdapter(MainActivity.this, tenChucNang, R.layout.loai_san_pham_row);
        listView.setAdapter(adapter);

    }

    private void getSanPham() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.getSanPham, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            int index = new Random().nextInt(response.length() - 1);
                            JSONObject jsonObject = response.getJSONObject(index);
                            JSONObject jsonObject1 = response.getJSONObject(i);
                            sanPhamList1.add(new SanPham(jsonObject1.getInt("id_san_pham"), jsonObject1.getInt("id_loai_san_pham"), jsonObject1.getString("ten_san_pham"), jsonObject1.getString("gia_san_pham"), jsonObject1.getString("hinh_san_pham"), jsonObject1.getString("mo_ta_san_pham")));
                            sanPhamList.add(new SanPham(jsonObject.getInt("id_san_pham"), jsonObject.getInt("id_loai_san_pham"), jsonObject.getString("ten_san_pham"), jsonObject.getString("gia_san_pham"), jsonObject.getString("hinh_san_pham"), jsonObject.getString("mo_ta_san_pham")));
                            sanPhamMoiAdapter = new SanPhamMoiAdapter(getApplicationContext(), sanPhamList, R.layout.san_pham_moi_layout);
                            gridView.setAdapter(sanPhamMoiAdapter);
                            if (i == 9) {
                                break;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);

    }

    private void viewFlipper() {
        List<String> mangDiaChi = new ArrayList<>();
        mangDiaChi.add("https://i0.wp.com/www.techsignin.com/wp-content/uploads/2022/03/5964A970-4F3A-4519-98E3-C8CFEA054762.jpeg?resize=1021%2C570&ssl=1");
        mangDiaChi.add("https://media-cdn-v2.laodong.vn/storage/newsportal/2022/7/10/1066577/Ip14.jpg");
        mangDiaChi.add("https://cdn1.viettelstore.vn/images/Product/ProductImage/medium/1286205353.jpeg");
        for (String diaChi : mangDiaChi) {
            ImageView imageView = new ImageView(getApplicationContext());
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Picasso.get().load(diaChi).into(imageView);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(2000);
        viewFlipper.setAutoStart(true);
        Animation animation_silde_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        viewFlipper.setAnimation(animation_silde_in);
    }

    private void actionBar() {
        imageView.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
    }

    private void init() {
        tenChucNang = new ArrayList<>();
        viewFlipper = findViewById(R.id.viewFlipper);
        imageView = findViewById(R.id.imageViewNavigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        sanPhamList = new ArrayList<>();
        listView = findViewById(R.id.listViewTrangChinh);
        gridView = findViewById(R.id.gridViewSanPhamMoi);
        txtDangXuat = findViewById(R.id.textViewDangXuat);
        emailKhachHang = findViewById(R.id.textViewEmailUser);
        imageKhachHang = findViewById(R.id.imageViewUser);
        imageDonHang = findViewById(R.id.imageViewDonHang);
        txtSanPhamMoi = findViewById(R.id.sanPhamMoi);
        dienThoaiList = new ArrayList<>();
        mayTinhList = new ArrayList<>();
        sanPhamList1 = new ArrayList<>();
    }

}