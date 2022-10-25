package activity;

import static activity.StartActivity.diaChi_key;
import static activity.StartActivity.email_key;
import static activity.StartActivity.hinhAnh_key;
import static activity.StartActivity.image_key;
import static activity.StartActivity.ten_key;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.User;
import ultil.Server;

public class SignUpActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword, edtTen, edtDiaChi, edtNamSinh;
    private ImageView imgHinhKhachHang, imgLibrary;
    private Button btnDangKi;
    private static final int GALLLER_REQUEST = 1234;
    private String image_uri = "";
    private List<User> userList;
    private boolean flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        init();
        btnDangKi.setOnClickListener(v -> signUp());
        imgLibrary.setOnClickListener(v -> setImageUser());


    }

    private void setImageUser() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLLER_REQUEST);
    }

    private void signUp() {
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String ten = edtTen.getText().toString();
        String diaChi = edtDiaChi.getText().toString();
        String namSinh = edtNamSinh.getText().toString();
        String hinhAnh = image_uri;
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.getKhachHang, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                if (response != null) {
//                    for (int i = 0; i < response.length(); i++) {
//                        try {
//                            JSONObject jsonObject = response.getJSONObject(i);
//                            userList.add(new User(jsonObject.getInt("id"), jsonObject.getString("email"), jsonObject.getString("password"), jsonObject.getString("ten"), jsonObject.getString("dia_chi"), jsonObject.getInt("nam_sinh"),jsonObject.getString("anh_khach_hang")));
//                            for (User user : userList) {
//                                if (user.getEmail().equals(email)) {
//                                    Toast.makeText(getApplicationContext(), "Tài khoản đã được sử dụng", Toast.LENGTH_SHORT).show();
//                                    flag = false;
//                                }
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
//            }
//        });
//        requestQueue.add(jsonArrayRequest);
        if (!email.equals("") && !password.equals("") && !ten.equals("") && !diaChi.equals("") && !namSinh.equals("")) {
            RequestQueue requestQueue1 = Volley.newRequestQueue(getApplicationContext());
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.insertKhachHang, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d("response",response);
                    Toast.makeText(getApplicationContext(),"Đăng kí tài khoản thành công",Toast.LENGTH_SHORT).show();
                    gotoMainActivity();
                    sendData(email,ten,diaChi,hinhAnh);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("response",error.toString());
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("email",email);
                    hashMap.put("password",password);
                    hashMap.put("ten",ten);
                    hashMap.put("dia_chi",diaChi);
                    hashMap.put("nam_sinh",namSinh);
                    hashMap.put("anh_khach_hang",hinhAnh);
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
            requestQueue1.add(stringRequest);
        }
    }

    private void sendData(String email, String ten, String diaChi,String hinhAnh) {
        Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
        intent.putExtra(email_key,email);
        intent.putExtra(ten_key,ten);
        intent.putExtra(diaChi_key,diaChi);
        intent.putExtra(hinhAnh_key,hinhAnh);
        startActivity(intent);
    }

    private void gotoMainActivity() {
        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
    }

    private void init() {
        edtEmail = findViewById(R.id.editTextTaiKhoanSignUp);
        edtPassword = findViewById(R.id.editTextMatKhauSignUp);
        edtTen = findViewById(R.id.editTextTenSignUp);
        edtDiaChi = findViewById(R.id.editTextDiaChiSignUp);
        edtNamSinh = findViewById(R.id.editTextNamSinhSignUp);
        imgHinhKhachHang = findViewById(R.id.imageViewHinhKhachHangSignUp);
        imgLibrary = findViewById(R.id.imageViewLibrary);
        btnDangKi = findViewById(R.id.buttonDangKi);
        userList = new ArrayList<>();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null && resultCode == RESULT_OK) {
            if (requestCode == GALLLER_REQUEST) {
                Uri uri = data.getData();
                imgHinhKhachHang.setImageURI(uri);
                image_uri = uri.toString();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}