/**
 * android-async-http 已经停止维护，并且httpClient在Android 5 以上版本已经停用
 * http 将逐渐被淘汰，因此使用更安全的https 方法来进行网络连接
 * 使用了google推荐的volley 来进行https请求
 * 另外，OpenLibrary 需要国外代理才能打开
 * FEATURE_INDETERMINATE_PROGRESS 这一Windows feature 从Android 7 之后不再使用
 * 更改为text提示语
 */

package com.example.edgelibrary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.Policy;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    private RequestQueue queue;
    private TextView titleText;
    ListView mainListView;
    JSONAdapter mJSAD;

    private void Searchbooks(String quarryURL) throws UnsupportedEncodingException {
        final String quarryHead = "https://openlibrary.org/search.json?q=";
        String urlString = URLEncoder.encode(quarryURL, "UTF-8");
        String url = quarryHead + urlString;

        final JsonObjectRequest mj = new JsonObjectRequest
                (url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        System.out.println("Response: " + response.toString());
                        mJSAD.updateData(response.optJSONArray("docs"));
                        titleText.setText(R.string.title);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        titleText.setText(R.string.loadfailed);
                        Log.e("reqFailed", error.getMessage(), error);
                    }
                });
        queue.add(mj);
    }

    private void checkPermission(){
        //TODO: permission check
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);
        final Button btnGo = findViewById(R.id.searchBtn);
        final TextView keyText = findViewById(R.id.keywordText);
        mJSAD = new JSONAdapter(this, getLayoutInflater());
        titleText = findViewById(R.id.titleText);
        mainListView = findViewById(R.id.mainListView);
        mainListView.setAdapter(mJSAD);

        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                JSONObject jsonObject = (JSONObject) mJSAD.getItem(position);
                String coverID = jsonObject.optString("cover_i","");
                Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
                detailIntent.putExtra("coverID",coverID);
                startActivity(detailIntent);
            }
        });
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleText.setText(R.string.waiting);
                try {
                    String s = keyText.getText().toString();
                    Searchbooks(s);
                    System.out.println("一次查询点击");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
