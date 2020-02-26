package com.martinboy.database;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class MaskRepository {
    private MaskDao mMaskDao;
    private LiveData<List<MaskEntity>> mMaskLiveData;
    private List<MaskEntity> mMaskData;

    public MaskRepository(Application application){
        mMaskDao = AppDatabase.getInstance(application).getMaskDao();
        mMaskLiveData = mMaskDao.getAllLiveData();
        mMaskData = mMaskDao.getAll();
    }

    public LiveData<List<MaskEntity>> getLiveMaskData() {
        return mMaskLiveData;
    }

    public List<MaskEntity> getMaskData() {
        mMaskData = mMaskDao.getAll();
        return mMaskData;
    }

    public List<MaskEntity> getMaskDataByCity(String city) {
        mMaskData = mMaskDao.findByCity(city);
        return mMaskData;
    }

    public List<MaskEntity> getMaskDataByCityAndSearchQuery(String city, String searchQuery) {
        mMaskData = mMaskDao.findByCityAndSearchQuery(city, searchQuery);
        return mMaskData;
    }

    public int getMaskDataCount(){
        return mMaskDao.getMaskDataCount();
    }

    public MaskEntity getMaskDataByUid(int uid){
        return mMaskDao.getMaskDataByUid(uid);
    }

    public void addUsers(MaskEntity... maskEntities){
        new AddMaskDataAsyncTask(mMaskDao).execute(maskEntities);
    }

    public void deleteUsers(MaskEntity... maskEntities){
        new DeleteUserAsyncTask(mMaskDao).execute(maskEntities);
    }

    public void deleteUserById(int uID){
        new DeleteUserByUidAsyncTask(mMaskDao).execute(uID);
    }

    public void deleteUserByUserEntity(MaskEntity... maskEntities){
        new DeleteMaskDataByMaskEntityAsyncTask(mMaskDao).execute(maskEntities);
    }

    public void updateUsers(MaskEntity... maskEntities){
        new UpdateUserAsyncTask(mMaskDao).execute(maskEntities);
    }

    private static class AddMaskDataAsyncTask extends AsyncTask<MaskEntity[], Void, Void>{
        private MaskDao mAsyncMaskDao;

        AddMaskDataAsyncTask(MaskDao dao){
            mAsyncMaskDao = dao;
        }

        @Override
        protected Void doInBackground(MaskEntity[]... userEntities) {
            mAsyncMaskDao.insertMaskData(userEntities[0]);
            return null;
        }
    }

    private static class DeleteUserAsyncTask extends AsyncTask<MaskEntity[], Void, Void>{
        private MaskDao mAsyncMaskDao;

        DeleteUserAsyncTask(MaskDao dao){
            mAsyncMaskDao = dao;
        }

        @Override
        protected Void doInBackground(MaskEntity[]... maskEntities) {
            mAsyncMaskDao.deleteMaskData(maskEntities[0]);
            return null;
        }
    }

    private static class DeleteUserByUidAsyncTask  extends AsyncTask<Integer, Void, Void> {
        private MaskDao mAsyncMaskDao;

        DeleteUserByUidAsyncTask(MaskDao dao){
            mAsyncMaskDao = dao;
        }

        @Override
        protected Void doInBackground(Integer... memberId) {
            mAsyncMaskDao.deleteMaskData(memberId[0]);
            return null;
        }
    }

    private static class DeleteMaskDataByMaskEntityAsyncTask  extends AsyncTask<MaskEntity, Void, Void> {
        private MaskDao mAsyncMaskDao;

        DeleteMaskDataByMaskEntityAsyncTask(MaskDao dao){
            mAsyncMaskDao = dao;
        }

        @Override
        protected Void doInBackground(MaskEntity... maskEntities) {
            mAsyncMaskDao.deleteMaskData(maskEntities);
            return null;
        }
    }

    private static class UpdateUserAsyncTask extends AsyncTask<MaskEntity[], Void, Void>{
        private MaskDao mAsyncMaskDao;

        UpdateUserAsyncTask(MaskDao dao){
            mAsyncMaskDao = dao;
        }

        @Override
        protected Void doInBackground(MaskEntity[]... maskEntities) {
            mAsyncMaskDao.updateMaskData(maskEntities[0]);
            return null;
        }
    }
}