package com.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegistActivity extends AppCompatActivity {

    EditText edt_phone,edt_pwd_re,edt_pwd_re2;
    Button btn_send_re;

    ImageView img_title_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        IntViews();
    }

    private void IntViews() {
        edt_phone = findViewById(R.id.edt_phone);
        edt_pwd_re = findViewById(R.id.edt_pwd_re);
        edt_pwd_re2 = findViewById(R.id.edt_pwd_re2);
        btn_send_re = findViewById(R.id.btn_send_re);

//        标题栏
        img_title_back = findViewById(R.id.img_title_back);
        img_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_send_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = edt_phone.getText().toString();
                String pwd = edt_pwd_re.getText().toString();
                String pwd2 = edt_pwd_re2.getText().toString();

                if (pwd.equals(pwd2)){
                    login(phone,pwd);
                }else{
                    Log.d("qqq",pwd+pwd2);
                    Toast.makeText(RegistActivity.this,"输入密码不一致！",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void login(String phone, String pwd) {
        String url = "http://120.76.210.221/SkillExam/jyregist";

//        入参数据的转化
//       {"name":"17311000","password":"123456"}
        Map<String,String> map = new HashMap<>();
        map.put("name",phone);
        map.put("password",pwd);
        JSONObject jsonObject = new JSONObject(map);

        RequestQueue requestQueue = Volley.newRequestQueue(RegistActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Gson gson = new Gson();
                        RegistBean registBean = gson.fromJson(jsonObject.toString(),RegistBean.class);
                        Toast.makeText(RegistActivity.this,getJson(jsonObject.toString()),Toast.LENGTH_SHORT).show();
                        if (registBean.result){
                            Intent intent = new Intent();
                            intent.setClass(RegistActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(RegistActivity.this,"数据解析失败，原因是"+volleyError,Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(request);
    }

    private String getJson(String json) {
        String result="";
        Gson gson = new Gson();
//      tojson fromjson
        LoginBean loginBean = gson.fromJson(json,LoginBean.class);
        if (loginBean.result){
            result="注册成功！";
        }else {
            result="注册失败，请重新输入手机号和密码！";
        }
        return result;
    }
}
