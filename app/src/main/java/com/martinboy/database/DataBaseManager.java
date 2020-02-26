package com.martinboy.database;

import android.content.Context;
import android.util.Log;

import com.martinboy.http.MackDataBean;
import com.martinboy.maskquery.MainActivity;
import com.martinboy.maskquery.manager.ReadCvsManager;

import java.util.List;

public class DataBaseManager {

    private final String TAG = DataBaseManager.class.getSimpleName();

    public static final String TABLE_MASK = "mask_table";
    public static final String TABLE_MAP = "mask_map_table";

    private MaskDao getMaskDao;
    //    private MapDataDao getMapDataDao;
    private boolean refreshData = false;

    private static DataBaseManager INSTANCE;

    private DataBaseManager(Context context) {
        this.getMaskDao = AppDatabase.getInstance(context).getMaskDao();
//        this.getMapDataDao = AppDatabase.getInstance(context).getMapDataDao();
    }


    public static DataBaseManager getManager(Context context) {

        if (INSTANCE == null) {
            INSTANCE = new DataBaseManager(context);
        }

        return INSTANCE;
    }

    // It is old function refreshData from cvs file. Now this app is refreshed data from http api, this function is unused;
    public void refreshData(final MainActivity mainActivity, final List<ReadCvsManager.MaskData> list) {

        refreshData = true;

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {


                for (ReadCvsManager.MaskData data : list) {

                    if (!refreshData)
                        break;

                    MaskEntity maskEntity = getMaskDao.findByCode(data.getCode());

                    if (maskEntity != null) {
                        maskEntity.setAdult_mask(Integer.valueOf(data.getAdultMask()));
                        maskEntity.setChild_mask(Integer.valueOf(data.getChildMask()));
                        maskEntity.setDate(data.getDate());
                        getMaskDao.updateMaskData(maskEntity);
                    } else {
                        String address = data.getAddress();
                        if (address.startsWith("台")) {
                            StringBuilder replaceAddress = new StringBuilder(data.getAddress());
                            replaceAddress.setCharAt(0, '臺');
                            address = replaceAddress.toString();
                        }
                        maskEntity = new MaskEntity(data.getCode(), data.getName(), address, data.getPhone(), Integer.valueOf(data.getAdultMask()), Integer.valueOf(data.getChildMask()), data.getDate());
                        getMaskDao.insertMaskData(maskEntity);
                    }

                }

                Log.d("tag1", "mainActivity: " + mainActivity);
                if (mainActivity != null)
                    mainActivity.finishRefreshMaskData();

            }
        });

        thread.start();

    }

    public void refreshMackData(final MainActivity mainActivity, final List<MackDataBean.MaskData> list) {

        final Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                for (MackDataBean.MaskData data : list) {

                    MaskEntity maskEntity = getMaskDao.findByCode(data.getId());

                    if (maskEntity != null) {
                        maskEntity.setAdult_mask(data.getMaskAdult());
                        maskEntity.setChild_mask(data.getChildAdult());
                        maskEntity.setDate(data.getUpdate());
                        getMaskDao.updateMaskData(maskEntity);
                    } else {

                        if (data.getAddress().startsWith("台")) {
                            StringBuilder replaceAddress = new StringBuilder(data.getAddress());
                            replaceAddress.setCharAt(0, '臺');
                            data.setAddress(replaceAddress.toString());
                        }

                        maskEntity = new MaskEntity(data.getId(),
                                data.getName(),
                                data.getAddress(),
                                data.getPhone(),
                                data.getMaskAdult(),
                                data.getChildAdult(),
                                data.getUpdate(),
                                data.getNote(),
                                data.getCustom_note(),
                                data.getLatitude(),
                                data.getLongitude());
                        getMaskDao.insertMaskData(maskEntity);
                    }

                }

                if (mainActivity != null)
                    mainActivity.finishRefreshMaskData();

            }
        });

        thread.start();

    }

//    public void refreshMapData(final List<MackDataBean.MackData> list) {
//
//        refreshMapData = true;
//
//        final Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//
//                for (MackDataBean.MackData data : list) {
//
//                    if (!refreshMapData)
//                        break;
//
//                    MapDataEntity mapDataEntity = getMapDataDao.findByCode(data.getId());
//
//                    if (mapDataEntity != null) {
//                        mapDataEntity.setCode(data.getId());
//                        mapDataEntity.setName(data.getName());
//                        mapDataEntity.setLatitude(data.getLatitude());
//                        mapDataEntity.setLongitude(data.getLongitude());
//                        getMapDataDao.updateMapData(mapDataEntity);
//                    } else {
//                        mapDataEntity = new MapDataEntity(data.getId(), data.getName(), data.getLatitude(), data.getLongitude());
//                        getMapDataDao.insertMapData(mapDataEntity);
//                    }
//
//                }
//
//            }
//        });
//
//        thread.start();
//
//    }

    public void stopRefreshData(boolean refreshData) {
        this.refreshData = refreshData;
    }


}
