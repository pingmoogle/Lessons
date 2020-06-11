/**
 * 踩坑大全：
 * 一、
 * android-async-http 已经停止维护，并且httpClient在Android 5 以上版本已经停用
 * httpURLConnection 也基本被淘汰了
 * http 将逐渐被淘汰，因此需要使用更安全的https 方法来进行网络连接
 * 使用了google推荐的volley 来进行https请求
 * 二、
 * OpenLibrary 需要国外代理才能打开
 * 因此，如果遇到了Timeout之类的错误，请首先检查网络连接，再给代码debug
 * 三、
 * FEATURE_INDETERMINATE_PROGRESS 这一Windows feature 从Android 7 之后不再使用
 * 操作进度，更改为text提示语
 * 四、
 * Actionbar和menu的相关特征，在Android 7之后被大改
 * 添加菜单和Toolbar按钮，建议参看官方文档
 * 五、
 * 虽然导入v7也行，但还是建议
 * 尽量使用jetbrains提供的Androidx模块
 */

package com.example.edgelibrary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {
    private RequestQueue queue;
    private TextView titleText;
    ListView mainListView;
    JSONAdapter mJSAD;

    private void Searchbooks(String quarryURL) throws UnsupportedEncodingException {
        // 定义了查询图书的方法
        // 创建https连接
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbarHome = findViewById(R.id.toolbarHome);
        setSupportActionBar(toolbarHome);
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
