package com.example.signon;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class phonelog extends AppCompatActivity implements View.OnClickListener {

    private Button back_button;
    private Button codeText;
    private TextView title;
    private Button phonelog;
    private EditText phonetext;
    private EditText codeed;
    boolean flag;
    EventHandler eventHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonelog);
        back_button=findViewById(R.id.back_button);
        back_button.setOnClickListener(this);
        codeText=findViewById(R.id.phonecode_button);
        codeText.setOnClickListener(this);
        phonelog=findViewById(R.id.phonelog_log);
        phonelog.setOnClickListener(this);
        phonetext=findViewById(R.id.phonelog_Text);
        codeed=findViewById(R.id.phonecode_Text);
        title=findViewById(R.id.title_text);
        title.setText("短信登录");


        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Message msg=new Message();
                msg.arg1=event;
                msg.arg2=result;
                msg.obj=data;
                handler.sendMessage(msg);
            }
        };

        SMSSDK.registerEventHandler(eventHandler);



    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    public static void actionStart(Context context){
        Intent intent=new Intent(context,phonelog.class);
        context.startActivity(intent);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.back_button:
                log.actionStart(phonelog.this);
                break;
            case R.id.phonecode_button:
                if (!TextUtils.isEmpty( phonetext.getText())) {
                    if ( phonetext.getText().length() == 11) {
                        SMSSDK.getVerificationCode("86", phonetext.getText().toString()); // 发送验证码给号码的 phoneNumber 的手机
                        codeed.requestFocus();
                        phonelog.CountTimer countTimer = new phonelog.CountTimer(60000, 1000);
                        countTimer.start();
                    }
                    else {
                        Toast.makeText(this, "请输入完整的电话号码", Toast.LENGTH_SHORT).show();
                        phonetext.requestFocus();
                    }
                } else {
                    Toast.makeText(this, "请输入电话号码", Toast.LENGTH_SHORT).show();
                    phonetext.requestFocus();
                }
                break;
            case R.id.phonelog_log:
                if (!TextUtils.isEmpty(codeed.getText())) {
                    if (codeed.getText().length() == 4) {
                        SMSSDK.submitVerificationCode("86",  phonetext.getText().toString(), codeed.getText().toString());
                        flag = false;
                    } else {
                        Toast.makeText(this, "请输入完整的验证码", Toast.LENGTH_SHORT).show();
                        codeed.requestFocus();
                    }
                } else {
                    Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    codeed.requestFocus();
                }
                break;
            default:
                break;

        }
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;

            if (result == SMSSDK.RESULT_COMPLETE) {
                // 如果操作成功
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    // 校验验证码，返回校验的手机和国家代码
                    Toast.makeText(phonelog.this, "登录成功", Toast.LENGTH_SHORT).show();
                    MainActivity.actionStart(phonelog.this);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    // 获取验证码成功，true为智能验证，false为普通下发短信
                    Toast.makeText(phonelog.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    // 返回支持发送验证码的国家列表
                }
            } else {
                // 如果操作失败
                if (flag) {
                    Toast.makeText(phonelog.this, "验证码获取失败，请重新获取", Toast.LENGTH_SHORT).show();
                    phonetext.requestFocus();
                } else {
                    ((Throwable) data).printStackTrace();
                    Toast.makeText(phonelog.this, "验证码错误", Toast.LENGTH_SHORT).show();
                }
            }


        }

    };
    /**
     * 点击按钮后倒计时
     */
    class CountTimer extends CountDownTimer {

        public CountTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        /**
         * 倒计时过程中调用
         *
         * @param millisUntilFinished
         */
        @Override
        public void onTick(long millisUntilFinished) {
            Log.e("Tag", "倒计时=" + (millisUntilFinished/1000));
            int time = (int) (Math.round((double) millisUntilFinished / 1000) - 1);
            codeText.setText(String.valueOf(time)+"s");
            codeText.setClickable(false);//倒计时过程中将按钮设置为不可点击
        }

        /**
         * 倒计时完成后调用
         */
        @Override
        public void onFinish() {
            Log.e("Tag", "倒计时完成");
            codeText.setText("重新发送");
            codeText.setClickable(true);
        }
    }

}
