package com.newyear.newer.newyear_operate;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class homepage extends Activity {
    EditText edt_user = null;
    EditText edt_pwd = null;
    Button btn_trues = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        edt_user = (EditText)findViewById(R.id.edt_user);
        edt_pwd = (EditText)findViewById(R.id.edt_pwd);
        btn_trues = (Button)findViewById(R.id.button2);
        btn_trues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname = edt_user.getText().toString();
                String pwd = edt_pwd.getText().toString();
                if(uname.length()!=0&&pwd.length()!=0){
                    if(uname.equals("hulang")&&pwd.equals("123")){
                        Intent it = new Intent(homepage.this,MyTop.class);
                        it.putExtra("username",uname);
                        homepage.this.startActivity(it);
                        finish();
                    }else{
                        Toast.makeText(homepage.this,"密码或账号错误",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(homepage.this,"密码或账号不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
/*
    activity的四种加载启动模式(被Intent加载的情况):添加在主配置文件中的activity android:name=".homepage"  的后面
    比如：<activity android:name=".homepage" android:launchMode="standard">

         </activity>
        1.standard  标准模式(默认模式):会创建一个新的对象加入活动栈中
        2.singleTop 栈顶模式:如果栈顶是目标对象，则不会产生新的活动对象(也就是当我们的Intent所加载的Activity的的对象已经在栈中，
            并且在栈的最顶端，就不会创建一个新的对象，而是直接调用那个栈顶的对象)
        3.singleTak 单任务模式:如果栈的里面存在该对象，不管在栈的什么位置，都会直接拿过来使用，
            不过会将其在栈中上层的所有的活动全部清掉(销毁掉),其实就是让这个活动前面的活动全部销毁，然后让这个活动直接在栈的最顶端
        4.singleStance:单例模式，待续...
*/
