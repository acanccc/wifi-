package com.example.signon;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.mob.MobSDK;
import com.mob.PrivacyPolicy;

public class yonghuxuzhi extends AppCompatActivity implements View.OnClickListener {

    private TextView xieyi;
    private TextView title;
    private Button back_button;
    private Button queren_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yonghuxuzhi);
        xieyi=findViewById(R.id.xieyi_2);
        xieyi.setOnClickListener(this);
        title=findViewById(R.id.title_text);
        title.setText("用户须知");
        back_button=findViewById(R.id.back_button);
        back_button.setOnClickListener(this);
        queren_button=findViewById(R.id.queren_button);
        queren_button.setVisibility(View.VISIBLE);
        queren_button.setOnClickListener(this);

    }

    public static void actionStart(Context context){
        Intent intent=new Intent(context,yonghuxuzhi.class);
        context.startActivity(intent);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.xieyi_2:
                mobwebview.actionStart(yonghuxuzhi.this);
                break;
            case R.id.back_button:
                log.actionStart(yonghuxuzhi.this);
                break;
            case R.id.queren_button:
            {

                SharedPreferences.Editor editor=getSharedPreferences("data",MODE_PRIVATE).edit();
                editor.putInt("gouxuan",1);
                editor.apply();
                log.actionStart(yonghuxuzhi.this);

                break;

            }

            default:
                break;

        }
    }

}
