﻿package soft1509081602625.androidlabs.hzuapps.edu.soft1509081602625activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import soft1714080902407.androidlabs.hzuapps.edu.soft1509081602625activity.R;


public class MyCollectionActivity extends Activity {
    protected static final int CHANGE_UI=1;
    protected static final int ERROR=2;
    private ImageView iv;
    private Handler handler = new Handler(){
        public void handleMessage(android.os.Message msg){
            if(msg.what == CHANGE_UI){
                Bitmap bitmap = (Bitmap) msg.obj;
                iv.setImageBitmap(bitmap);
            }else if (msg.what == ERROR){
                Toast.makeText(MyCollectionActivity.this,"显示图片错误",Toast.LENGTH_SHORT).show();
            }
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_collection_activity);
        iv=(ImageView) findViewById(R.id.iv);
    }
    public void onClick5(View view){

            new Thread() {
                private HttpURLConnection conn;
                private Bitmap bitmap;
                public void run() {
                    try {
                        URL url = new URL("https://gss0.bdstatic.com/-4o3dSag_xI4khGkpoWK1HF6hhy/baike/s%3D220/sign=8f0d9a9b56afa40f38c6c9df9b66038c/a8014c086e061d95f6861a9177f40ad163d9ca53.jpg");
                        conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setConnectTimeout(5000);
                        conn.setRequestProperty("User-Agent", "Mozilla/4.0(compatible;MSIE 6.0;Windows NT 5.1;" + "SV1;.NET4.0C;.NET4.0E;.NET CLR 2.0.50727;" + ".NET CLR 3.0.4506.2152;.NET CLR 3.5.30729;Shuame)");
                        int code = conn.getResponseCode();
                        if (code == 200) {
                            InputStream is = conn.getInputStream();
                            bitmap = BitmapFactory.decodeStream(is);
                            Message msg = new Message();
                            msg.what = CHANGE_UI;
                            msg.obj= bitmap;
                            handler.sendMessage(msg);
                        }else{
                            Message msg = new Message();
                            msg.what=ERROR;
                            handler.sendMessage(msg);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Message msg = new Message();
                        msg.what = ERROR;
                        handler.sendMessage(msg);
                    }
                };
            }.start();
        }
    }

