package com.newyear.newer.newyear_operate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2016/1/23.
 */
public class MyTop extends Activity {
    HashMap<String, ArrayList<HashMap<String, String>>> thisValue = null;
    TextView txt_showname = null;
    TextView txt_gun = null;
    Button btn_select = null;
    Button btn_readimg = null;
    Button btn_add = null;
    Button btn_set = null;
    String name = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top);
        txt_showname = (TextView) findViewById(R.id.txt_showName);
        txt_gun = (TextView)findViewById(R.id.txt_gun);
        txt_gun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MyTop.this,"设置公示内容只能由管理员进行操作",Toast.LENGTH_SHORT).show();
            }
        });
        btn_select = (Button) findViewById(R.id.btn_select);
        btn_readimg = (Button) findViewById(R.id.btn_readimg);
        btn_add = (Button) findViewById(R.id.btn_add);
        btn_set = (Button) findViewById(R.id.btn_set);
        Intent it = getIntent();
        name = it.getExtras().get("username").toString();
        txt_showname.setText("欢迎您：" + name);
        btn_select.setOnClickListener(new MyOnclick());
        btn_readimg.setOnClickListener(new MyOnclick());
        btn_add.setOnClickListener(new MyOnclick());
        btn_set.setOnClickListener(new MyOnclick());
        getValues();
    }


    public class MyOnclick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent it = null;
            if (v.getId() == R.id.btn_select) {
                it = new Intent(MyTop.this, ShowBanFei.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("thisValue", thisValue);
                it.putExtras(bundle);
                startActivityForResult(it,2333);
            } else if (v.getId() == R.id.btn_add) {
                it = new Intent(MyTop.this, add_vlaues.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("thisValue", thisValue);
                it.putExtras(bundle);
                startActivityForResult(it, 2333);
            } else if (v.getId() == R.id.btn_readimg) {
                it = new Intent(MyTop.this, show_image.class);
                it.putExtra("username", name);
                startActivity(it);
            } else if (v.getId() == R.id.btn_set) {
                it = new Intent(MyTop.this, maneger.class);
                it.putExtra("username", name);
                startActivity(it);
            }
        }

    }

    public void getValues() {
        if (thisValue == null) {
            File sdfile = null;
            //获得SD卡的状态
            String start = Environment.getExternalStorageState();
            //判断外部储存是否已挂载
            if (Environment.MEDIA_MOUNTED.equals(start)) {
                //获得根目录文件对象
                sdfile = Environment.getExternalStorageDirectory();
            } else {
                Toast.makeText(this, "SD卡为插入，请插入SD卡", Toast.LENGTH_SHORT).show();
                finish();
            }
            File filess = new File(sdfile, "/wishfist");
            if (!filess.exists()) {
                try {
//                Toast.makeText(this,filess.toString(),Toast.LENGTH_SHORT).show();
                    filess.mkdir();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            File timeFile = new File(sdfile, "wishfist/thisValue.object");
            if (!timeFile.exists()) {
                thisValue = new HashMap<>();
            } else {
                FileInputStream timeFis = null;
                ObjectInputStream timeOis = null;
                try {
                    timeFis = new FileInputStream(timeFile);
                    timeOis = new ObjectInputStream(timeFis);
                    thisValue = (HashMap) timeOis.readObject();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==Activity.RESULT_OK){
            Bundle bundle = data.getExtras();
            if(bundle.size()!=0) {
                thisValue = (HashMap) bundle.getSerializable("thisValue");
                File sdfile = null;
                //获得SD卡的状态
                String start = Environment.getExternalStorageState();
                //判断外部储存是否已挂载
                if (Environment.MEDIA_MOUNTED.equals(start)) {
                    //获得根目录文件对象
                    sdfile = Environment.getExternalStorageDirectory();
                } else {
                    Toast.makeText(this, "SD卡为插入，请插入SD卡", Toast.LENGTH_SHORT).show();
                    finish();
                }
                File filess = new File(sdfile, "/wishfist");
                if (!filess.exists()) {
                    try {
//                Toast.makeText(this,filess.toString(),Toast.LENGTH_SHORT).show();
                        filess.mkdir();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                File timeFile = new File(sdfile, "wishfist/thisValue.object");
                FileOutputStream timeFos = null;
                ObjectOutputStream timeOos = null;
                try {
                    timeFos = new FileOutputStream(timeFile);
                    timeOos = new ObjectOutputStream(timeFos);
                    timeOos.writeObject(thisValue);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
