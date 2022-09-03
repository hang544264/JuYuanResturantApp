package com.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

    EditText edt_name,edt_pwd;
    Button btn_send,btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        intViews();
    }

    private void intViews() {
        edt_name = findViewById(R.id.edt_name);
        edt_pwd = findViewById(R.id.edt_pwd);
        btn_send = findViewById(R.id.btn_send);
        btn_register =findViewById(R.id.btn_register);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edt_name.getText().toString();
                String pwd = edt_pwd.getText().toString();
                login(name,pwd);
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,RegistActivity.class);
                startActivity(intent);
            }
        });
    }

    private void login(String name, String pwd) {
//        转化为Json数据
        Map<String,String> map = new HashMap<>();
        map.put("name",name);
        map.put("password",pwd);
        JSONObject jsonObject = new JSONObject(map);

        String url = "http://120.76.210.221/SkillExam/jylogin";
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Gson gson = new Gson();
                        LoginBean loginBean = gson.fromJson(jsonObject.toString(),LoginBean.class);
                        Toast.makeText(LoginActivity.this,getJson(jsonObject.toString()),Toast.LENGTH_SHORT).show();
                        if (loginBean.result){
                            Intent intent = new Intent();
                            intent.setClass(LoginActivity.this,ProductListActivity.class);
                            startActivity(intent);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(LoginActivity.this,"数据解析失败，原因是"+volleyError,Toast.LENGTH_SHORT).show();
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
            result="登录成功！";
        }else {
            result="登陆失败，请重新输入账号和密码！";
        }
        return result;
    }
}
