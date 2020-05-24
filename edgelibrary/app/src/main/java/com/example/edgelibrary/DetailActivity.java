package com.example.edgelibrary;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private ImageView imageView;
    private static final String ImageURLbase = "https://covers.openlibrary.org/b/id/";
    String mImageURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageView = findViewById(R.id.bookimg);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String coverID = this.getIntent().getExtras().getString("coverID");
        if(coverID.length()>0){
            mImageURL = ImageURLbase+coverID+"-L.jpg";
            Picasso.with(DetailActivity.this).load(mImageURL).
                    placeholder(R.mipmap.ic_launcher).into(imageView);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        Log.d("toolbar","Toolbar初始化成功");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.shareMenu:
                Log.i("TouchShare","touched share icon");
                shareIt();
                return true;
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void shareIt() {
        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);
        share_intent.setType("text/plain");
        String t = this.getString(R.string.sharesays);
        share_intent.putExtra(Intent.EXTRA_TEXT, t + mImageURL);
        share_intent = Intent.createChooser(share_intent, "分享本书");
        startActivity(share_intent);
    }

}






