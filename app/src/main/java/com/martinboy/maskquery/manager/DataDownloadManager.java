package com.martinboy.maskquery.manager;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import java.io.File;

public class DataDownloadManager {

    public static DataDownloadManager INSTANCE;
    private static String tmpDbDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
    private DownloadManager downloadManager;
    private static final String url = "https://data.nhi.gov.tw/Datasets/Download.ashx?rid=A21030000I-D50001-001&l=https://data.nhi.gov.tw/resource/mask/maskdata.csv";
    private long downloadId = -1;

    private DataDownloadManager(Context context) {
        downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
    }

    public static synchronized DataDownloadManager getInstance(Context context) {

        if (INSTANCE == null)
            INSTANCE = new DataDownloadManager(context);

        return INSTANCE;

    }

    public DownloadManager getDownloadManager() {
        return downloadManager;
    }

    public long getDownloadId() {
        return downloadId;
    }

    public void startDownloadData() {

        File dir = new File(tmpDbDir, "MaskQuery");
        if (!dir.exists()) {
            dir.mkdir();
        }

        File file = new File(tmpDbDir + "/MaskQuery", "mask.csv");
        if (file.exists())
            file.delete();

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url))
                .setAllowedNetworkTypes(
                        DownloadManager.Request.NETWORK_MOBILE
                                | DownloadManager.Request.NETWORK_WIFI)
                .setAllowedOverRoaming(true)
                .setDestinationUri(Uri.fromFile(file));
        downloadId = downloadManager.enqueue(request);
    }

    public void cancelDownload() {
        if (downloadId != -1)
            downloadManager.remove(downloadId);
    }

}
