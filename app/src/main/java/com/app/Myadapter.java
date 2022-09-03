package com.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Map;

public class Myadapter extends SimpleAdapter {
    /**
     * Constructor
     *
     * @param context  The context where the View associated with this SimpleAdapter is running
     * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
     *                 Maps contain the data for each row, and should include all the entries specified in
     *                 "from"
     * @param resource Resource identifier of a view layout that defines the views for this list
     *                 item. The layout file should include at least those named views defined in "to"
     * @param from     A list of column names that will be added to the Map associated with each
     *                 item.
     * @param to       The views that should display column in the "from" parameter. These should all be
     *                 TextViews. The first N views in this list are given the values of the first N columns
     */

    LayoutInflater inflater;
    List<? extends Map<String,?>> data;
    int resource;

    TextView txt_pro_name,txt_pro_details,txt_pro_pay;
    ImageView img_pro_pic;

//    ImageView img_pro_add,img_pro_sub;
//    TextView txt_pro_number;
//    int pay = 0;

    public Myadapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.resource = resource;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public Object getItem(int position) {
        return super.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = inflater.inflate(resource,null);
        }

        Map<String,?> map = data.get(position);

        txt_pro_name = convertView.findViewById(R.id.txt_pro_name);
        txt_pro_name.setText((String) map.get("name"));
        txt_pro_details = convertView.findViewById(R.id.txt_pro_details);
        txt_pro_details.setText((String) map.get("description"));
        txt_pro_pay = convertView.findViewById(R.id.txt_pro_pay);
        txt_pro_pay.setText((String) map.get("price"));
        img_pro_pic = convertView.findViewById(R.id.img_pro_pic);
        img_pro_pic.setImageBitmap((Bitmap) map.get("images"));

        return convertView;
    }
}
