package com.newyear.newer.addvalues;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity {
    ListView list = null;
    ArrayList<HashMap<String,String>> arrlist = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notcan);
        list = (ListView)findViewById(R.id.listView2);
        arrlist = new ArrayList<>();
        method();
        String[] from = new String[]{"name","yuanying","age"};
        int[] to = new int[]{R.id.shwo_name,R.id.show_qian,R.id.shwo_time};
        SimpleAdapter sma = new SimpleAdapter(this,arrlist,R.layout.moban,from,to);
        System.out.println(sma);
        list.setAdapter(sma);
    }
    public void method(){
        for(int i=0;i<10;i++){
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("name","hulang"+i);
            hashMap.put("age",i+"");
            hashMap.put("yuanying","10000223");
            System.out.println(hashMap);
            arrlist.add(hashMap);
        }
    }
    public void kankan(){
       /* String[] forms = new String[]{"name","time","content","image"};
        //下面的int数组中放的就是view所对应的id
        int[] to = new int[]{R.id.txt_name,R.id.txt_data,R.id.txt_value,R.id.iv_pic};
        list_sp_adapter = new SimpleAdapter(this,map_list,R.layout.list_row2,forms,to);
        list.setAdapter(list_sp_adapter);
        //将spinner和适配器关联
        spinner.setAdapter(adapter);*/
    }
}
