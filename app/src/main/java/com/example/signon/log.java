package com.example.signon;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class log extends AppCompatActivity implements View.OnClickListener {


    private TextView registertext;
    private TextView phonelog_text;
    private Button log;

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
                homepageActivity.actionStart(log.this);
                break;
            default:
                break;

        }
    }

}
