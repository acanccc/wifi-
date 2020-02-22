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

import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


public class register extends AppCompatActivity implements View.OnClickListener {

    private Button back_button;
    private Button codeText;
    EventHandler eventHandler;
    private EditText telephone;
    private Button registered;
    private EditText codeed;
    boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        back_button=findViewById(R.id.back_button);
        back_button.setOnClickListener(this);
        codeText=findViewById(R.id.codebutton);
        codeText.setOnClickListener(this);
        telephone=findViewById(R.id.register_phoneText);
        codeed=findViewById(R.id.register_codeText);
        registered=findViewById(R.id.register_register);
        registered.setOnClickListener(this);
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
        Intent intent=new Intent(context,register.class);
        context.startActivity(intent);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.back_button:
                log.actionStart(register.this);
                break;
            case R.id.codebutton:
                if (!TextUtils.isEmpty(telephone.getText())) {
                if (telephone.getText().length() == 11) {
                    SMSSDK.getVerificationCode("86",telephone.getText().toString()); // 发送验证码给号码的 phoneNumber 的手机
                    codeed.requestFocus();
                    CountTimer countTimer = new CountTimer(60000, 1000);
                    countTimer.start();
                }
                else {
                    Toast.makeText(this, "请输入完整的电话号码", Toast.LENGTH_SHORT).show();
                    telephone.requestFocus();
                }
            } else {
                Toast.makeText(this, "请输入电话号码", Toast.LENGTH_SHORT).show();
                telephone.requestFocus();
            }
            break;


            case R.id.register_register:
                if (!TextUtils.isEmpty(codeed.getText())) {
                if (codeed.getText().length() == 4) {
                    SMSSDK.submitVerificationCode("86", telephone.getText().toString(), codeed.getText().toString());
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

    /**
     * 使用Handler来分发Message对象到主线程中，处理事件
     */
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {

                int event = msg.arg1;
                int result = msg.arg2;
                Object data = msg.obj;

            if (result == SMSSDK.RESULT_COMPLETE) {
                // 如果操作成功
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    // 校验验证码，返回校验的手机和国家代码
                    Toast.makeText(register.this, "注册成功", Toast.LENGTH_SHORT).show();
                    log.actionStart(register.this);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    // 获取验证码成功，true为智能验证，false为普通下发短信
                    Toast.makeText(register.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    // 返回支持发送验证码的国家列表
                }
            } else {
                // 如果操作失败
                if (flag) {
                    Toast.makeText(register.this, "验证码获取失败，请重新获取", Toast.LENGTH_SHORT).show();
                    telephone.requestFocus();
                } else {
                    ((Throwable) data).printStackTrace();
                    Toast.makeText(register.this, "验证码错误", Toast.LENGTH_SHORT).show();
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
