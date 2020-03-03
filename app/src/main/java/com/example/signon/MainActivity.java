package com.example.signon;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button getinfo;
    private TextView setinfo;

    private String bssid=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getinfo=findViewById(R.id.getWiFiinfo);
        setinfo=findViewById(R.id.setWiFiinfo);
        getinfo.setOnClickListener(this);
    }

    public static void actionStart(Context context){
        Intent intent=new Intent(context,MainActivity.class);
        context.startActivity(intent);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.getWiFiinfo:
                getWiFiMAC();
                setinfo.setText("ip " + getWifiIp() + " " + getWiFiName() + " "+bssid);
                break;
            default:
                break;
        }
    }

    /*
     * 获取 WIFI 的名称
     * */
    public String getWiFiName() {
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        if (wm != null) {
            WifiInfo winfo = wm.getConnectionInfo();
            if (winfo != null) {
                String s = winfo.getSSID();
                if (s.length() > 2 && s.charAt(0) == '"' && s.charAt(s.length() - 1) == '"') {
                    return s.substring(1, s.length() - 1);
                }
            }
        }
        return "Wifi 未获取到";
    }


    /*
     * 获取 WiFi 的 IP 地址
     * */
    public String getWifiIp() {
        Context myContext = getApplicationContext();
        if (myContext == null) {
            throw new NullPointerException("上下文 context is null");
        }
        WifiManager wifiMgr = (WifiManager) myContext.getSystemService(Context.WIFI_SERVICE);
        if (isWifiEnabled()) {
            int ipAsInt = wifiMgr.getConnectionInfo().getIpAddress();
            String ip = Formatter.formatIpAddress(ipAsInt);
            if (ipAsInt == 0) {
                return "未能获取到IP地址";
            } else {
                return ip;
            }
        } else {
            return "WiFi 未连接";
        }
    }

    /*
     * 判断当前 WIFI 是否连接
     * */
    public boolean isWifiEnabled() {
        Context myContext = getApplicationContext();
        if (myContext == null) {
            throw new NullPointerException("上下文 context is null");
        }
        WifiManager wifiMgr = (WifiManager) myContext.getSystemService(Context.WIFI_SERVICE);
        if (wifiMgr.getWifiState() == WifiManager.WIFI_STATE_ENABLED) {
            ConnectivityManager connManager = (ConnectivityManager) myContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            return wifiInfo.isConnected();
        } else {
            return false;
        }
    }

    /*
     * 获取 WiFi 的 Mac 地址
     *
     * */



    public  void getWiFiMAC() {
        String sdk= Build.VERSION.SDK;
        int anInt=Integer.parseInt(sdk);
        if(anInt>=27){
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }else{
                WifiManager wifiManager=(WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                bssid=wifiManager.getConnectionInfo().getBSSID();
            }
        }else{
            WifiManager wifiManager=(WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
            bssid=wifiManager.getConnectionInfo().getBSSID();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        switch (requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    getWiFiMAC();
                }else{
                    Toast.makeText(this,"You denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:

        }
    }

}
