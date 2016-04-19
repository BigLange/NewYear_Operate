package com.newyear.newer.newyear_operate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Administrator on 2016/1/25.
 */
public class maneger extends Activity implements View.OnClickListener{
    Button btn_returns = null;
    String name = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notcan);
        name = getIntent().getExtras().get("username").toString();
        btn_returns = (Button)findViewById(R.id.btn_returns);
        btn_returns.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_returns){
            finish();
        }
    }
}
