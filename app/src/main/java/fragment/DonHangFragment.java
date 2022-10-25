package fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import adapter.DonHangApdater;
import model.DonHang;
import ultil.Server;


public class DonHangFragment extends Fragment {
    private ListView listView;
    private List<DonHang> donHangList;
    private DonHangApdater donHangApdater;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_don_hang, container, false);
            listView = view.findViewById(R.id.listViewDonHang);
            donHangList = new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.getDonHang, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            donHangList.add(new DonHang(jsonObject.getInt("id_don"), jsonObject.getString("tai_khoan_khach_hang"), jsonObject.getString("ten_khach_hang"), jsonObject.getString("dia_chi"), Integer.parseInt(jsonObject.getString("id_san_pham")), jsonObject.getString("hinh_anh_san_pham"), jsonObject.getString("mo_ta_san_pham"), Integer.parseInt(jsonObject.getString("so_luong"))));
                            donHangApdater = new DonHangApdater(getContext(), donHangList, R.layout.list_view_don_hang_row);
                            listView.setAdapter(donHangApdater);
                            donHangApdater.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
        return view;
    }
}