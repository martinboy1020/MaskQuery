//package com.martinboy.database;
//
//import android.app.Application;
//import android.os.AsyncTask;
//
//import java.util.List;
//
//import androidx.lifecycle.LiveData;
//
//public class MapRepository {
//    private MapDataDao mMapDataDao;
//    private LiveData<List<MapDataEntity>> mMapLiveData;
//    private List<MapDataEntity> mMapDataList;
//    private MapDataEntity mMapData;
//
//    public MapRepository(Application application) {
//        mMapDataDao = AppDatabase.getInstance(application).getMapDataDao();
//        mMapLiveData = mMapDataDao.getAllLiveData();
//        mMapDataList = mMapDataDao.getAll();
//    }
//
//    public LiveData<List<MapDataEntity>> getLiveMapData() {
//        return mMapLiveData;
//    }
//
//    public List<MapDataEntity> getMapData() {
//        mMapDataList = mMapDataDao.getAll();
//        return mMapDataList;
//    }
//
//    public MapDataEntity getMapDataByCode(String code) {
//        mMapData = mMapDataDao.findByCode(code);
//        return mMapData;
//    }
//
//    public int getMapDataCount() {
//        return mMapDataDao.getMapDataCount();
//    }
//
//    public void addUsers(MapDataEntity... maskEntities) {
//        new AddMaskDataAsyncTask(mMapDataDao).execute(maskEntities);
//    }
//
//    public void deleteUsers(MapDataEntity... maskEntities) {
//        new DeleteUserAsyncTask(mMapDataDao).execute(maskEntities);
//    }
//
//    public void deleteUserById(int uID) {
//        new DeleteUserByUidAsyncTask(mMapDataDao).execute(uID);
//    }
//
//    public void deleteUserByUserEntity(MapDataEntity... maskEntities) {
//        new DeleteMaskDataByMapDataEntityAsyncTask(mMapDataDao).execute(maskEntities);
//    }
//
//    public void updateUsers(MapDataEntity... maskEntities) {
//        new UpdateUserAsyncTask(mMapDataDao).execute(maskEntities);
//    }
//
//    private static class AddMaskDataAsyncTask extends AsyncTask<MapDataEntity[], Void, Void> {
//        private MapDataDao mAsyncMapDao;
//
//        AddMaskDataAsyncTask(MapDataDao dao) {
//            mAsyncMapDao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(MapDataEntity[]... mapDataEntities) {
//            mAsyncMapDao.insertMapData(mapDataEntities[0]);
//            return null;
//        }
//    }
//
//    private static class DeleteUserAsyncTask extends AsyncTask<MapDataEntity[], Void, Void> {
//        private MapDataDao mAsyncMapDao;
//
//        DeleteUserAsyncTask(MapDataDao dao) {
//            mAsyncMapDao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(MapDataEntity[]... maskEntities) {
//            mAsyncMapDao.deleteMapData(maskEntities[0]);
//            return null;
//        }
//    }
//
//    private static class DeleteUserByUidAsyncTask extends AsyncTask<Integer, Void, Void> {
//        private MapDataDao mAsyncMapDao;
//
//        DeleteUserByUidAsyncTask(MapDataDao dao) {
//            mAsyncMapDao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(Integer... memberId) {
//            mAsyncMapDao.deleteMapData(memberId[0]);
//            return null;
//        }
//    }
//
//    private static class DeleteMaskDataByMapDataEntityAsyncTask extends AsyncTask<MapDataEntity, Void, Void> {
//        private MapDataDao mAsyncMapDao;
//
//        DeleteMaskDataByMapDataEntityAsyncTask(MapDataDao dao) {
//            mAsyncMapDao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(MapDataEntity... maskEntities) {
//            mAsyncMapDao.deleteMapData(maskEntities);
//            return null;
//        }
//    }
//
//    private static class UpdateUserAsyncTask extends AsyncTask<MapDataEntity[], Void, Void> {
//        private MapDataDao mAsyncMapDao;
//
//        UpdateUserAsyncTask(MapDataDao dao) {
//            mAsyncMapDao = dao;
//        }
//
//        @Override
//        protected Void doInBackground(MapDataEntity[]... maskEntities) {
//            mAsyncMapDao.updateMapData(maskEntities[0]);
//            return null;
//        }
//    }
//}