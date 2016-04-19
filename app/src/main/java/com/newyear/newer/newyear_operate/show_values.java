package com.newyear.newer.newyear_operate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import java.util.HashMap;

/**
 * Created by Administrator on 2016/1/25.
 */
public class show_values extends Activity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{
    Button btn_returns = null;
    TextView txt_name = null;
    TextView txt_qian = null;
    TextView txt_time = null;
    TextView txt_yy = null;
    Switch sw_if = null;
    Button btn_true = null;
    Button btn_delete = null;
    String iftrue = null;
    HashMap<String,String> myMap = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_values);
        setValues();
        btn_returns.setOnClickListener(this);
        btn_true.setOnClickListener(this);
        btn_delete.setOnClickListener(this);
        show();
    }

    @Override
    public void onClick(View v) {
        Intent it = new Intent();
        if(v.getId()==R.id.btn_true){
            it.putExtra("ifhuan", iftrue);
        }else if(v.getId()==R.id.btn_delete){
            it.putExtra("delete",true);
        }else{
            finish();
        }
        setResult(Activity.RESULT_OK, it);
        finish();
    }
    public void setValues(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        myMap = (HashMap<String,String>)bundle.get("myMap");
        txt_name = (TextView)findViewById(R.id.txt_name);
        txt_qian = (TextView)findViewById(R.id.txt_qian);
        txt_yy = (TextView)findViewById(R.id.txt_yuanying);
        txt_time = (TextView)findViewById(R.id.txt_time);
        sw_if = (Switch)findViewById(R.id.switch1);
        btn_true = (Button)findViewById(R.id.btn_true);
        btn_delete = (Button)findViewById(R.id.btn_delete);
        btn_returns = (Button)findViewById(R.id.btn_returns);
        sw_if.setOnCheckedChangeListener(this);
    }
    public void show(){
        txt_name.setText("姓名:  "+myMap.get("name"));
        txt_qian.setText("金额:  "+myMap.get("qian"));
        txt_yy.setText("原因:  "+myMap.get("yy"));
        txt_time.setText("时间:  "+myMap.get("time"));
        if(myMap.get("ifjq").equals("y")){
            sw_if.setChecked(true);
        }else {
            sw_if.setChecked(false);
        }
        iftrue = myMap.get("ifjq");
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(isChecked){
            iftrue = "y";
        }else{
            iftrue = "n";
        }
    }
}
