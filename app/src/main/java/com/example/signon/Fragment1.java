package com.example.signon;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.content.Context.WIFI_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment1 extends Fragment implements View.OnClickListener{


    private Button getinfo;
    private TextView setinfo;
    private String bssid=null;


    private ImageButton btn1;
    private ImageButton btn2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_fragment1, container, false);

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        getinfo=getView().findViewById(R.id.getWiFiinfo);
//        setinfo=getView().findViewById(R.id.setWiFiinfo);
//        getinfo.setOnClickListener(this);
            btn1=getView().findViewById(R.id.btn_qiandao);
             btn2=getView().findViewById(R.id.btn_faqi);
             btn1.setOnClickListener(this);
             btn2.setOnClickListener(this);

    }


    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.getWiFiinfo:
                getWiFiMAC();
                setinfo.setText("ip " + getWifiIp() + " " + getWiFiName() + " "+bssid);
                break;
            case R.id.btn_qiandao:
                signinpage.actionStart(getActivity());
                break;
            case R.id.btn_faqi:
                signinpage.actionStart(getActivity());
                break;
            default:
                break;
        }
    }

    /*
     * 获取 WIFI 的名称
     * */
    public String getWiFiName() {
        WifiManager wm = (WifiManager) getActivity().getApplicationContext().getSystemService(WIFI_SERVICE);
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
        Context myContext = getActivity().getApplicationContext();
        if (myContext == null) {
            throw new NullPointerException("上下文 context is null");
        }
        WifiManager wifiMgr = (WifiManager) myContext.getSystemService(WIFI_SERVICE);
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
        Context myContext = getActivity().getApplicationContext();
        if (myContext == null) {
            throw new NullPointerException("上下文 context is null");
        }
        WifiManager wifiMgr = (WifiManager) myContext.getSystemService(WIFI_SERVICE);
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
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }else{
                WifiManager wifiManager=(WifiManager) getActivity().getApplicationContext().getSystemService(WIFI_SERVICE);
                bssid=wifiManager.getConnectionInfo().getBSSID();
            }
        }else{
            WifiManager wifiManager=(WifiManager)getActivity(). getApplicationContext().getSystemService(WIFI_SERVICE);
            bssid=wifiManager.getConnectionInfo().getBSSID();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getWiFiMAC();
                } else {
                    Toast.makeText(getActivity(), "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:

        }
    }

}
