package com.albb.androidutils.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/***
 * app 的 build.gradle 添加 implementation 'com.github.tbruyelle:rxpermissions:0.10.2'
 * project 的build.gradle 添加 maven { url 'https://jitpack.io' }
 *
 * 清单文件添加：
 * <uses-permission android:name="android.permission.CAMERA"/>
 *     <uses-permission android:name="android.permission.CAPTURE_VIDEO_OUTPUT"/>
 *     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 *     <uses-permission android:name="android.permission.SEND_SMS"/>
 *     <uses-permission android:name="android.permission.LOCATION_HARDWARE"/>
 *     <uses-permission android:name="android.permission.ACCESS_LOCATION_EXTRA_COMMANDS"/>
 *     <uses-permission android:name="android.permission.READ_PHONE_STATE"/>
 *     <uses-permission android:name="android.permission.REQUEST_INSTALL_PACKAGES"/>
 *
 * 权限申请的baseActivity
 */
public class BasePerMissionAcvivity extends AppCompatActivity {

    final RxPermissions rxPermissions = new RxPermissions(this); // where this is an Activity or Fragment instance
    private String TAG = "BasePerMissionAcvivity";
    protected String[] allPermissons = {Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.SEND_SMS,
            Manifest.permission.VIBRATE,
            Manifest.permission.REQUEST_INSTALL_PACKAGES};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    //申请单一权限
    protected void checkSinglePermisson(String permission, int requestCode) {
        //如果权限没有申请
        if (ContextCompat.checkSelfPermission(BasePerMissionAcvivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(BasePerMissionAcvivity.this
                    , new String[]{permission}, requestCode);
        } else {
            //权限已经申请过
        }
    }

    //申请所有权限，如果为空则为默认的所有权限
    protected void applyAllPermission(String[] permissons) {
        if (permissons.length == 0) {
            permissons = allPermissons;
        }
        rxPermissions.request(permissons)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        if (!aBoolean) {
                            Toast.makeText(BasePerMissionAcvivity.this, "有权限未同意", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    protected void applyEachPer(String s) {

        rxPermissions
                .requestEachCombined(s)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            Toast.makeText(BasePerMissionAcvivity.this, "权限已经同意", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, permission.name + " is granted.");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时。还会提示请求权限的对话框
                            Log.d(TAG, permission.name + " is denied. More info should be provided.");
                        } else {
                            Toast.makeText(BasePerMissionAcvivity.this, "请授予权限，否则将影响你的使用", Toast.LENGTH_SHORT).show();
                            // 用户拒绝了该权限，而且选中『不再询问』
                            Log.d(TAG, permission.name + " is denied.");
                            test3();
                        }
                    }
                });

    }


    //进入权限设置界面
    private void test3() {
        Intent intent = new Intent();
        intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }


}
