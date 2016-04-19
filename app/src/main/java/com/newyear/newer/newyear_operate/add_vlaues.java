package com.newyear.newer.newyear_operate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


/**
 * Created by Administrator on 2016/1/25.
 */
public class add_vlaues extends Activity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{
    Button btn_returns = null;
    EditText edt_name = null;
    EditText edt_qian = null;
    HashMap<String,ArrayList<HashMap<String,String>>> thisValue = null;
    EditText edt_yy = null;
    Switch sw_ifqian = null;
    Button btn_true = null;
    boolean b = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);
        btn_returns = (Button)findViewById(R.id.btn_returns);
        edt_name = (EditText)findViewById(R.id.edt_name);
        edt_qian = (EditText)findViewById(R.id.edt_qian);
        edt_yy = (EditText)findViewById(R.id.edt_yuanying);
        sw_ifqian = (Switch)findViewById(R.id.switch1);
        btn_true = (Button)findViewById(R.id.btn_true);
        setMap();
        btn_returns.setOnClickListener(this);
        btn_true.setOnClickListener(this);
        sw_ifqian.setOnCheckedChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_returns){
            onStop();
        }else if(v.getId()==R.id.btn_true) {
            String name = edt_name.getText().toString();
            Date date = new Date(System.currentTimeMillis());
            String time = DateFormat.getDateInstance().format(date);
            String yy = edt_yy.getText().toString();
            String qian = edt_qian.getText().toString();
            String ifjq = null;
            if (b) {
                ifjq = "y";
            } else {
                ifjq = "n";
            }
            HashMap<String, String> myMap = setMap(name, qian, yy, time, ifjq);
            ArrayList<HashMap<String, String>> thisList = thisValue.get(time + "&" + name);
            if (!"".equals(name.trim())&&!"".equals(yy.trim())&&!"".equals(qian.trim())&&name!=null&&yy!=null&&qian!=null) {
                if (thisList != null) {
                    thisList.add(myMap);
                } else {
                    thisList = new ArrayList<>();
                    thisList.add(myMap);
                    thisValue.put(time + "&" + name, thisList);
                }
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                setNull();
            }else{
                Toast.makeText(this, "输入数据不能为空", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void setNull(){
        edt_name.setText(null);
        edt_yy.setText(null);
        edt_qian.setText(null);
        sw_ifqian.setChecked(false);
    }
    public void setMap(){
        Bundle bundle = getIntent().getExtras();
        thisValue = (HashMap)bundle.getSerializable("thisValue");
    }

    public HashMap<String,String> setMap(String name,String qian,String yy,String time,String ifjq){
        HashMap<String,String> myMap = new HashMap<>();
        myMap.put("name",name);
        myMap.put("qian",qian);
        myMap.put("yy",yy);
        myMap.put("time",time);
        myMap.put("ifjq",ifjq);
        return myMap;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        b = isChecked;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent it = new Intent(this,MyTop.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("thisValue",thisValue);
        it.putExtras(bundle);
        setResult(Activity.RESULT_OK, it);
        finish();
    }
}
