package com.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductListActivity extends AppCompatActivity {

    String url="http://120.76.210.221/SkillExam/foodlist";
    ListView lis_prod;
    Button btn_number,btn_pay;

//    标题栏
    ImageView img_title_back;
    TextView txt_title_headline;

    ArrayList<HashMap<String, Object>> data;
    int pay[] = new int[]{0,0,0,0,0,0};

//    下面的按钮的数据
    int sum,price;

    Myadapter simpleAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        intViews();
        GetData();
    }

    private void GetData() {
        RequestQueue requestQueue = Volley.newRequestQueue(ProductListActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        data = new ArrayList<HashMap<String, Object>>();
                        HashMap<String,Object> Promap;

                        Gson gson = new Gson();
                        ProductBean productBean = gson.fromJson(jsonObject.toString(),ProductBean.class);
                        List<ProductBean.ListBean> list = productBean.getList();
                        for (int i = 0; i < list.size(); i++) {
                            ProductBean.ListBean listBean = list.get(i);
                            String name = listBean.getName();
                            String description = listBean.getDescription();
                            String price = String.valueOf(listBean.getPrice());
                            String icon = listBean.getIcon();

                            Promap = new HashMap<>();
                            Promap.put("name",name);
                            Promap.put("description",description);
                            Promap.put("price",price);
                            Promap.put("icon",icon);
                            data.add(Promap);
//                            data.get(1).get
                        }
                        setapadter();
                        for(int i=0;i<data.size();i++){
                            loadImages((String)data.get(i).get("icon"), i);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(ProductListActivity.this,"数据解析失败，原因是"+volleyError,Toast.LENGTH_SHORT).show();
                    }
                }

        );
        requestQueue.add(jsonObjectRequest);
    }

//    图片解析
    private void loadImages(String icon, int i) {
        final int flag= i;
        RequestQueue queue=Volley.newRequestQueue(ProductListActivity.this);
        ImageRequest request=new ImageRequest(
                icon,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        data.get(flag).put("images", bitmap);
                        Log.d("000000000000", "onResponse: "+bitmap.toString());
                        simpleAdapter.notifyDataSetChanged();
                    }
                },
                0,
                0,
                Bitmap.Config.RGB_565,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(ProductListActivity.this, "图片解析失败，原因是"+volleyError, Toast.LENGTH_SHORT).show();
                    }
                });
        queue.add(request);
    }

    private void setapadter() {
        simpleAdapter = new Myadapter(
                ProductListActivity.this,
                data,
                R.layout.item_product_layout,
                new String[]{"name","description","price","images"},
                new int[]{R.id.txt_pro_name,R.id.txt_pro_details,R.id.txt_pro_pay,R.id.img_pro_pic}
        );
        lis_prod.setAdapter(simpleAdapter);
        lis_prod.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
//              获取单机ListView中item的位置
                final int firstVisiblePosition = lis_prod.getFirstVisiblePosition();
                Log.d("lh", "onItemClick: "+firstVisiblePosition);
                if (position - firstVisiblePosition >= 0 ){
//                    获取当前点击的item的view
                    View itemView = lis_prod.getChildAt(position - firstVisiblePosition);
                    Log.d("lah", "onItemClick: "+position);
//                    查找出相应的控件

                    final TextView txt_pro_number =itemView.findViewById(R.id.txt_pro_number);
                    final ImageView img_pro_add = itemView.findViewById(R.id.img_pro_add);
                    final ImageView img_pro_sub = itemView.findViewById(R.id.img_pro_sub);
                    final TextView txt_pro_pay = itemView.findViewById(R.id.txt_pro_pay);
                    img_pro_add.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int numb = position - firstVisiblePosition;
                            Log.d("11111111111", "onClick: "+numb);
                            pay[numb]++;
                            Log.d("TAG", "onClick: "+pay[numb]);
                            txt_pro_number.setText(String.valueOf(pay[numb]));

//                           pay 总价
//                           sum 总数

                            sum = sum + Integer.valueOf(txt_pro_pay.getText().toString());
                            btn_pay.setText(sum+""+"元 立即支付");

                            int payzongshu = 0;
                            for (int i = 0; i < pay.length; i++) {
                                payzongshu = payzongshu+pay[i];
                            }

                            btn_number.setText("数量： "+payzongshu+"");
                        }
                    });

                    img_pro_sub.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int numb = position - firstVisiblePosition;
                            if (pay[numb]>0){
                                pay[numb]--;
                                Log.d("TAG", "onClick: "+pay[numb]);
                                txt_pro_number.setText(String.valueOf(pay[numb]));
                            }

                            if(sum > 0){
                                sum = sum - Integer.valueOf(txt_pro_pay.getText().toString());
                                btn_pay.setText(sum+""+"元 立即支付");

                            }
                            int payzongshu = 0;
                            for (int i = 0; i < pay.length; i++) {
                                payzongshu = payzongshu+pay[i];
                            }

                            btn_number.setText("数量： "+payzongshu+"");
                        }
                    });
                }
            }
        });

    }



    private void intViews() {
//        标题栏初始化
        txt_title_headline = findViewById(R.id.txt_title_headline);
        img_title_back = findViewById(R.id.img_title_back);

        btn_pay = findViewById(R.id.btn_pay);
        btn_number = findViewById(R.id.btn_number);
        lis_prod = findViewById(R.id.lis_prod);

//        更改标题栏样式
        txt_title_headline.setText("订餐");
        img_title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
