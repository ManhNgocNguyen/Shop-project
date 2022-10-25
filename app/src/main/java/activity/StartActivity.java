package activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appproject.R;

import org.apache.http.params.HttpConnectionParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import model.User;
import ultil.Server;

public class StartActivity extends AppCompatActivity {
    private EditText edtTaiKhoan, edtMatKhau;
    private TextView txtDangKi;
    private Button btnDangNhap;
    private List<User> userList;
    private boolean flag = false;
    public static final String email_key = "key";
    public static final String ten_key ="tenKey";
    public static final String diaChi_key ="diaChiKey";
    public static final String image_key = "image";
    public static final String hinhAnh_key = "hinhAnhKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        init();
        btnDangNhap.setOnClickListener(v-> getInformation());
        txtDangKi.setOnClickListener(v->signUpUser());
    }

    private void signUpUser() {
        startActivity(new Intent(StartActivity.this,SignUpActivity.class));
    }

    public void getInformation() {
        String email = edtTaiKhoan.getText().toString().trim();
        String matKhau = edtMatKhau.getText().toString().trim();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.getKhachHang, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            userList.add(new User(jsonObject.getInt("id"), jsonObject.getString("email"), jsonObject.getString("password"), jsonObject.getString("ten"), jsonObject.getString("dia_chi"), jsonObject.getInt("nam_sinh"),jsonObject.getString("anh_khach_hang")));
                            for (User user : userList) {
                                if (user.getEmail().equals(email) && user.getPassword().equals(matKhau)) {
                                    flag = true;
                                    goToMainActivity();
                                    sendData(user.getEmail(),user.getTen(),user.getDiaChi(),user.getHinhKhachHang());
                                }
                            }
                            if (!flag) {
                                Toast.makeText(StartActivity.this, "Tài khoản hoặc mật khẩu sai!", Toast.LENGTH_SHORT).show();
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

        jsonArrayRequest.setRetryPolicy(new RetryPolicy() {
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
        requestQueue.add(jsonArrayRequest);
    }

    private void sendData(String email,String ten, String diaChi,String hinhAnh) {
        Intent intent = new Intent(StartActivity.this,MainActivity.class);
        intent.putExtra(email_key,email);
        intent.putExtra(ten_key,ten);
        intent.putExtra(diaChi_key,diaChi);
        intent.putExtra(hinhAnh_key,hinhAnh);
        startActivity(intent);
    }

    private void goToMainActivity() {
        startActivity(new Intent(StartActivity.this, MainActivity.class));
    }

    private void init() {
        edtMatKhau = findViewById(R.id.editTextMatKhau);
        edtTaiKhoan = findViewById(R.id.editTextEmail);
        btnDangNhap = findViewById(R.id.buttonDangNhap);
        txtDangKi = findViewById(R.id.textViewDangKi);
        userList = new ArrayList<>();
    }
}