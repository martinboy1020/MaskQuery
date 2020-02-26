package com.martinboy.maskquery;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.martinboy.database.DataBaseManager;
import com.martinboy.http.MackDataBean;
import com.martinboy.managertool.CheckConnectStatusManager;
import com.martinboy.managertool.CheckPermissionManager;
import com.martinboy.maskquery.fragment.CityFragment;
import com.martinboy.maskquery.fragment.DrugFragment;
import com.martinboy.maskquery.manager.DataDownloadManager;
import com.martinboy.maskquery.manager.MaskDataManager;
import com.martinboy.maskquery.manager.ReadCvsManager;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements ReadCvsManager.CvsCallBack {

    private BroadcastReceiver receiver;
    private DataBaseManager dataBaseManager;
    private CityFragment cityFragment;
    private DrugFragment drugFragment;
    private TextView textUpdating;
    public boolean isUpdating = false;
    private boolean isFirst = false;
    public static String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textUpdating = findViewById(R.id.text_updating);
        textUpdating.setVisibility(View.GONE);
        isFirst = true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        dataBaseManager = DataBaseManager.getManager(this);
        checkPermission();
//        setReceiver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (receiver != null)
//            unregisterReceiver(receiver);
//
//        receiver = null;
//
//        DataDownloadManager.getInstance(this).cancelDownload();

        if (dataBaseManager != null)
            dataBaseManager.stopRefreshData(false);
        isUpdating = false;
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (!CheckPermissionManager.getInstance().checkPermissionsAllGranted(this, permissions)) {
                CheckPermissionManager.getInstance().requestPermission(this, permissions);
            } else {
                initFragment();
                startRefreshMaskData(isFirst);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case CheckPermissionManager.REQUEST_CODE_CHECK_PERMISSIONS: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initFragment();
                    startRefreshMaskData(isFirst);
                } else {
                    StringBuilder no_permissions = new StringBuilder();
                    for (String per : permissions) {
                        no_permissions.append("\n").append(per);
                    }
                    Toast.makeText(MainActivity.this, "Permission Denied: " + no_permissions, Toast.LENGTH_SHORT)
                            .show();
                    finish();
                }
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void initFragment() {

        if (cityFragment == null && drugFragment == null) {

            cityFragment = new CityFragment();
            drugFragment = new DrugFragment();

            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = manager.beginTransaction();
            fragmentTransaction.add(R.id.layout_content, cityFragment, cityFragment.getClass().getSimpleName()).add(R.id.layout_content, drugFragment, drugFragment.getClass().getSimpleName());
            fragmentTransaction.disallowAddToBackStack();
            fragmentTransaction.commit();

            switchFragment("city", null);

        }

    }

    public void switchFragment(String tag, Bundle bundle) {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();

        if (tag.equals("city")) {
            fragmentTransaction.show(cityFragment);
            fragmentTransaction.hide(drugFragment);
        } else {
            fragmentTransaction.addToBackStack(drugFragment.getTag());
            fragmentTransaction.show(drugFragment);
            fragmentTransaction.hide(cityFragment);
            if (bundle != null)
                drugFragment.setArguments(bundle);
        }

        fragmentTransaction.commit();

    }

    public void setReceiver() {

        if (receiver == null) {

            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String action = intent.getAction();
                    Log.d("tag1", "action: " + action);
                    if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {
                        DownloadManager.Query query = new DownloadManager.Query();
                        query.setFilterById(DataDownloadManager.getInstance(MainActivity.this).getDownloadId());
                        Cursor c = DataDownloadManager.getInstance(MainActivity.this).getDownloadManager().query(query);
                        if (c.moveToFirst()) {
                            int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                            Log.d("tag1", "c.getInt(columnIndex): " + c.getInt(columnIndex));
                            if (DownloadManager.STATUS_SUCCESSFUL == c.getInt(columnIndex)) {
                                String downloadFilePath = (c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))).replace("file://", "");
//                            Log.d("tag1", "download file url" + downloadFilePath);
                                Toast.makeText(MainActivity.this, "更新數據下載完成", Toast.LENGTH_SHORT).show();
                                ReadCvsManager.getInstance().readCSV(downloadFilePath, MainActivity.this);
                            } else if (DownloadManager.STATUS_FAILED == c.getInt(columnIndex)) {

                            }
                        }
                    }
                }
            };

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
            intentFilter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED);
            registerReceiver(receiver, intentFilter);

        }

    }

    public void startRefreshMaskData(boolean isFirst) {

        if (isUpdating || !isFirst)
            return;

        if (CheckConnectStatusManager.checkNetWorkConnect(this)) {
            textUpdating.setVisibility(View.VISIBLE);
            isUpdating = true;
            MaskDataManager.getInstance().getMaskData(this, new MaskDataManager.MapDataCallBack() {
                @Override
                public void returnMapData(List<MackDataBean.MaskData> list) {
                    if (list != null) {
                        dataBaseManager.refreshMackData(MainActivity.this, list);
                    } else {
                        Toast.makeText(MainActivity.this, "更新資料失敗 請查看您的網路狀態", Toast.LENGTH_SHORT).show();
                    }
                }
            });
//            DataDownloadManager.getInstance(this).startDownloadData();
        } else {
            Toast.makeText(this, "目前處於無網路狀態 無法更新", Toast.LENGTH_SHORT).show();
        }

    }

    public void finishRefreshMaskData() {
        isUpdating = false;
        isFirst = false;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textUpdating.setVisibility(View.GONE);
            }
        });

    }

    @Override
    public void returnData(List<ReadCvsManager.MaskData> data) {

        if (data.size() > 0) {
            dataBaseManager.refreshData(this, data);
        }

    }

}
