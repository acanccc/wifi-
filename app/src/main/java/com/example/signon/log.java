package com.example.signon;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.MobSDK;

import cn.smssdk.SMSSDK;

public class log extends AppCompatActivity implements View.OnClickListener {


    private TextView registertext;
    private TextView phonelog_text;
    private Button log;
    private TextView yonghuxieyi;
    private CheckBox checkBox;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private CheckBox rememberPass;
    private EditText accountEdit;
    private EditText passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        registertext=findViewById(R.id.registerText);
        registertext.setOnClickListener(this);
        phonelog_text=findViewById(R.id.phonelogText);
        phonelog_text.setOnClickListener(this);
        log=findViewById(R.id.log_log);
        log.setOnClickListener(this);
        yonghuxieyi=findViewById(R.id.yinsi);
        yonghuxieyi.setOnClickListener(this);
        checkBox=findViewById(R.id.gouxuan);
        accountEdit=findViewById(R.id.log_usernameText);
        passwordEdit=findViewById(R.id.log_passwordText);
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        rememberPass=findViewById(R.id.rememberpawd);
        boolean isRemember =pref.getBoolean("remember_password",false);
        if(isRemember){
            String accout =pref.getString("account","");
            String password=pref.getString("password","");
            accountEdit.setText(accout);
            passwordEdit.setTag(password);
            rememberPass.setChecked(true);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences pref=getSharedPreferences("data",MODE_PRIVATE);
        int gouxuan=pref.getInt("gouxuan",0);
        if(gouxuan==1)
        {
            checkBox.setChecked(true);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkBox.isChecked()) {

            boolean granted=true;
            MobSDK.submitPolicyGrantResult(granted,null);
        }

    }

    public static void actionStart(Context context){
        Intent intent=new Intent(context,log.class);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.registerText:
                register.actionStart(log.this);
                break;
            case R.id.phonelogText:
                phonelog.actionStart(log.this);
                break;
            case R.id.log_log:
                if (checkBox.isChecked()) {
                    SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
                    editor.putInt("gouxuan",1);
                    editor.apply();
                    homepageActivity.actionStart(log.this,1);
                    finish();
                }else {

                    AlertDialog.Builder dialog=new AlertDialog.Builder(log.this);

                    dialog.setMessage("您需要阅读并同意《用户使用协议》后才能使用本软件");
                    dialog.setCancelable(false);
                    dialog.setPositiveButton("同意", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            checkBox.setChecked(true);
                        }
                    });
                    dialog.setNegativeButton("阅读", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            yonghuxuzhi.actionStart(log.this);
                        }
                    });
                    dialog.show();

                }
                break;
            case R.id.yinsi:
                yonghuxuzhi.actionStart(log.this);
                break;
            default:
                break;

        }
    }

}
