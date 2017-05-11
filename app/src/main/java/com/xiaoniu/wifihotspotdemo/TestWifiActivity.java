package com.xiaoniu.wifihotspotdemo;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.xiaoniu.wifihotspotdemo.util.UIUtil;
import com.xiaoniu.wifihotspotdemo.util.WifiAPManager;
import com.xiaoniu.wifihotspotdemo.util.WifiWifiManager;


public class TestWifiActivity extends AppCompatActivity{

    private WifiManager wifiManager;
    private WifiAPManager wifiAPManager;
    private WifiWifiManager wifiWifiManager;

    private EditText mEtWifiName;
    private EditText mEtConnectName;



    private static final int WIFICIPHER_NOPASS = 1;
    private static final int WIFICIPHER_WEP = 2;
    private static final int WIFICIPHER_WPA = 3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_test_wifi);
        initTest();
    }


    /**
     * 测试wifi
     */
    private void initTest() {
        mEtWifiName = (EditText) findViewById(R.id.et_wifi_name);
        mEtConnectName = (EditText) findViewById(R.id.et_connect_name);

        wifiManager = (WifiManager) this.getSystemService(Context.WIFI_SERVICE);
        wifiAPManager = new WifiAPManager(this);
        wifiWifiManager = new WifiWifiManager(this);
    }



    /**
     * 创建热点
     */
    public void craeteNoPwd(View v) {
        UIUtil.showToastS(this,"craeteNoPwd click");
        String s = mEtWifiName.getText().toString();
        if(!TextUtils.isEmpty(s)){
            if(wifiWifiManager.isWifiActive()){
                wifiWifiManager.closeWifi();
            }
            wifiAPManager.startWifiAp(s,"",true);
        }

    }

    /**
     * 关闭热点
     */
    public void closeNoPwd(View v) {
        UIUtil.showToastS(this,"closeNoPwd click");
        if(wifiAPManager.isWifiApEnabled()){
            wifiAPManager.closeWifiAp();
        }
    }

    /**
     * 获取bssid
     */
    public void getNoPwdSSID(View v) {
    }
    /**
     * 连接指定wifi
     */
    public void connectNoPwd(View v) {
        UIUtil.showToastS(this,"connectNoPwd click");
        String s = mEtConnectName.getText().toString();
        if(TextUtils.isEmpty(s)) {
           return;
        }
        if(wifiAPManager.isWifiApEnabled()){
           wifiAPManager.closeWifiAp();
        }
        if(!wifiWifiManager.isWifiActive()){
            wifiWifiManager.openWifi();
        }
        connectNoPwdSSID(s);



    }

    /**
     * 连接指定wifi
     */
    public void connectNoPwdSSID(String ssid){
        wifiManager.disconnect();
        WifiConfiguration config = createWifiInfo(ssid);
        connect(config);
    }

    public WifiConfiguration createWifiInfo(String SSID) {
        WifiConfiguration config = new WifiConfiguration();
        config.allowedAuthAlgorithms.clear();
        config.allowedGroupCiphers.clear();
        config.allowedKeyManagement.clear();
        config.allowedPairwiseCiphers.clear();
        config.allowedProtocols.clear();
        config.SSID = "\"" + SSID + "\"";

        config.wepKeys[0] = "\"" + "\"";
        config.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
        config.wepTxKeyIndex = 0;
        return config;
    }

    private void connect(WifiConfiguration config) {
        int wcgID = wifiManager.addNetwork(config);
        wifiManager.enableNetwork(wcgID, true);
        wifiManager.saveConfiguration();
    }

}
