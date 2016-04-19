package com.newyear.newer.newyear_operate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by Administrator on 2016/1/24.
 */
public class ShowBanFei extends Activity implements View.OnClickListener,AdapterView.OnItemClickListener{

    String nameOrTime = "姓名";
    String values = null;
    Button btn_returns = null;
    HashMap<String,ArrayList<HashMap<String,String>>> thisValue = null;
    Button btn_chaxun = null;
    Spinner sp_xuanze = null;
    EditText edt_nameOrTime = null;
    SimpleAdapter sma = null;
    ListView list_show = null;
    boolean ifReturn = true;
    TextView txt_neno = null;
    int index = -1;
    ArrayList<HashMap<String,String>> allList = null;
    ArrayList<String> arrlist = new ArrayList<>();
    ArrayAdapter<String> arradapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showbanfei);
        setMap();
        arrlist.add("姓名");
        arrlist.add("时间");
        btn_returns = (Button)findViewById(R.id.btn_returns);
        btn_chaxun = (Button)findViewById(R.id.btn_chaxun);
        sp_xuanze = (Spinner)findViewById(R.id.spinner);
        txt_neno = (TextView)findViewById(R.id.txt_neno);
        arradapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,arrlist);
        sp_xuanze.setAdapter(arradapter);
        list_show = (ListView)findViewById(R.id.list_show);
        list_show.setOnItemClickListener(this);
        edt_nameOrTime = (EditText)findViewById(R.id.edt_nameOrTime);
        showAll();
        btn_returns.setOnClickListener(this);
        btn_chaxun.setOnClickListener(this);
        sp_xuanze.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                nameOrTime = arrlist.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Intent it = new Intent(this,MyTop.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("thisValue", thisValue);
        it.putExtras(bundle);
        setResult(Activity.RESULT_OK, it);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_returns){
            onStop();
        }else if(v.getId()==R.id.btn_chaxun){
            values = edt_nameOrTime.getText().toString();
            if(values!=null&&!"".equals(values)){
                Set<String> thisSet = thisValue.keySet();
                allList = new ArrayList<>();
                HashSet<String> myHashSet = new HashSet<>();
                int weizhi = -1;
                if(nameOrTime.equals("姓名")){
                    for(String name:thisSet){
                        weizhi = name.indexOf("&");
                        if(name.indexOf(values,weizhi)!=-1){
                            myHashSet.add(name);
                        }
                    }
                }else{
                    for(String name:thisSet){
                        weizhi = name.indexOf("&");
                        if(name.lastIndexOf(values,weizhi)!=-1){
                            myHashSet.add(name);
                        }
                    }
                }
                for(String name:myHashSet){
                    allList.addAll(thisValue.get(name));
                }
                if(allList.size()==0) {
                    txt_neno.setText("暂无数据");
                }else{
                    txt_neno.setText(null);
                }
                setMyAdatper();
            }else{
                txt_neno.setText(null);
                showAll();
            }
        }
    }
    public void setMap(){
        Bundle bundle = getIntent().getExtras();
        thisValue = (HashMap)bundle.getSerializable("thisValue");
    }
    public void setMyAdatper(){
        String[] from = new String[]{"name","qian","time"};
        int[] to = new int[]{R.id.shwo_name,R.id.show_qian,R.id.shwo_time};
        sma = new SimpleAdapter(this,allList,R.layout.moban,from,to);
        list_show.setAdapter(sma);
    }
    public void showAll(){
        allList = new ArrayList<>();
        Set<String> set= thisValue.keySet();
        TreeSet<String> treeSet = new TreeSet<String>(new myCoperator());
        treeSet.addAll(set);
        for(String key:treeSet){
            ArrayList<HashMap<String,String>> myMap = (ArrayList)thisValue.get(key);
            allList.addAll(myMap);
        }
        setMyAdatper();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        HashMap<String,String> intentMap = allList.get(position);
        Intent intent = new Intent(this,show_values.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("myMap",intentMap);
        intent.putExtras(bundle);
        index = position;
        ifReturn = false;
        startActivityForResult(intent, 666);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        HashMap<String,String> myMap = allList.get(index);
        if(resultCode==Activity.RESULT_OK) {
            Bundle bun = data.getExtras();
            if (bun != null) {
                if ("y".equals(bun.get("ifhuan"))) {
                    myMap.put("ifjq", "y");
                } else if("n".equals(bun.get("ifhuan"))){
                    myMap.put("ifjq", "n");
                }else{
                    boolean bb = (boolean)bun.get("delete");
                    if(bb){
                        String name = myMap.get("name");
                        String time = myMap.get("time");
                        String timeandname = time+"&"+name;
                        List<HashMap<String,String>> getList = thisValue.get(timeandname);
                        bb = getList.remove(myMap);
                        if(bb){
                            allList.remove(index);
                            sma.notifyDataSetChanged();
                            Toast.makeText(this,"删除成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(this,"删除失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        }
        ifReturn = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(ifReturn){
            finish();
        }
    }
}
class myCoperator implements Comparator<String> {
    @Override
    public int compare(String map1,String map2) {
        int re = map1.compareTo(map2);
        if(re<0){
            re=1;
        }else if(re>0){
            re=-1;
        }
        return re;
    }
}