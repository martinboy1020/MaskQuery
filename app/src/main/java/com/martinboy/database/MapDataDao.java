//package com.martinboy.database;
//
//import java.util.List;
//
//import androidx.lifecycle.LiveData;
//import androidx.room.Dao;
//import androidx.room.Delete;
//import androidx.room.Insert;
//import androidx.room.OnConflictStrategy;
//import androidx.room.Query;
//import androidx.room.Update;
//
//@Dao
//public interface MapDataDao {
//
//    @Query("SELECT * FROM " + DataBaseManager.TABLE_MAP)
//    List<MapDataEntity> getAll();
//
//    @Query("SELECT * FROM " + DataBaseManager.TABLE_MAP)
//    LiveData<List<MapDataEntity>> getAllLiveData();
//
//    @Query("SELECT * FROM " + DataBaseManager.TABLE_MAP + " WHERE uid IN (:userIds)")
//    List<MapDataEntity> getAllByUids(int[] userIds);
//
//    @Query("SELECT * FROM " + DataBaseManager.TABLE_MAP + " WHERE uid=:uid")
//    MapDataEntity getMaskDataByUid(int uid);;
//
//    @Query("SELECT * FROM " + DataBaseManager.TABLE_MAP + " WHERE code LIKE :code LIMIT 1")
//    MapDataEntity findByCode(String code);
//
//    @Query("SELECT COUNT(*) FROM " + DataBaseManager.TABLE_MAP)
//    int getMapDataCount();
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    void insertMapData(MapDataEntity... mapDataEntities);
//
//    @Update
//    void updateMapData(MapDataEntity... mapDataEntities);
//
//    @Delete
//    void deleteMapData(MapDataEntity... mapDataEntities);
//
//    @Query("DELETE FROM " + DataBaseManager.TABLE_MAP + " WHERE uid=:uid")
//    void deleteMapData(int uid);
//
//}