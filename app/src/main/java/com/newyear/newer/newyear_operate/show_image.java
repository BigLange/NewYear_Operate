package com.newyear.newer.newyear_operate;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/1/25.
 */
public class show_image extends Activity{
    Button btn_returns = null;
    String name = null;
    ListView listView = null;
    TextView txt_none = null;
    List<Map<String,Object>> list = null;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imgshow);
        name = getIntent().getExtras().get("username").toString();
        btn_returns = (Button)findViewById(R.id.btn_returns);
        listView = (ListView)findViewById(R.id.list_show_img);
        btn_returns.setOnClickListener(new SetButton());
        setImage();
        if(list.size()>0) {
            listView.setAdapter(new MyAdapter());
        }else{
            txt_none = (TextView)findViewById(R.id.txt_img_none);
            txt_none.setText("暂无数据");
        }
    }
    public void setImage(){
        list = new ArrayList<>();
        File sdfile = null;
        //获得SD卡的状态
        String start = Environment.getExternalStorageState();
        //判断外部储存是否已挂载
        if (Environment.MEDIA_MOUNTED.equals(start)) {
            //获得根目录文件对象
            sdfile = Environment.getExternalStorageDirectory();
            setFile1(sdfile);
        } else {
            Toast.makeText(this, "SD卡为插入，请插入SD卡", Toast.LENGTH_SHORT).show();
            finish();
        }
        /*ath = Environment.getExternalStorageDirectory() ; //获得SDCard目录
        Bitmap bmpDefaultPic;
        ImageView  iv = (ImageView) contentView.findViewById(R.id.x);
        if(bmpDefaultPic==null)
            bmpDefaultPic = BitmapFactory.decodeFile(path+"xx.jpg",null);
        iv.setImageBitmap(bmpDefaultPic);
        R.id.x  代表你的ImageView
        xx.jpg 你的图片*/
    }
    public void setFile1(File file){
        File[] files = file.listFiles();
        MyFileFilter myfilefilter = new MyFileFilter();
        for(File filee:files){
            if(filee.isDirectory()){
                setFile2(filee);
            }else{
                if(myfilefilter.accept(filee)){
                    setMap(filee);
                }
            }
        }
    }
    public void setFile2(File file){
        File[] files = file.listFiles(new MyFileFilter());
        if(files.length>1) {
            setMap(files[0]);
            setMap(files[1]);
        }
    }
    public void setMap(File file){
        Map myMap = new HashMap();
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        myMap.put("name",file.getName());
        myMap.put("bitmap",bitmap);
        list.add(myMap);
    }



    public class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if(convertView==null){
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.imgmoban,null);
                viewHolder = new ViewHolder();
                viewHolder.img_show = (ImageView)convertView.findViewById(R.id.img_show);
                viewHolder.txt_name = (TextView)convertView.findViewById(R.id.txt_name);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }
            Map<String,Object> myMap = list.get(position);
            String name = (String)myMap.get("name");
            Bitmap bitmap = (Bitmap)myMap.get("bitmap");
            viewHolder.img_show.setImageBitmap(bitmap);
            viewHolder.txt_name.setText(name);
            return convertView;
        }
    }

    public  class ViewHolder{
        //这里写布局中的所有组件的定义
        public ImageView img_show;
        public TextView txt_name;
    }
    public class SetButton implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            finish();
        }
    }
}


//创建文件过滤器
class MyFileFilter implements FileFilter{

    @Override
    public boolean accept(File pathname) {
        String[] ends = new String[] {".jpg",".png",".bmp",".gif",".pcx",".tga"};
        String fileName = pathname.getName();
        for(int i=0;i<ends.length;i++){
            if(fileName.endsWith(ends[i])){
                return true;
            }
        }
        return false;
    }
}