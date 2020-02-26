package com.martinboy.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface MaskDao {

    @Query("SELECT * FROM " + DataBaseManager.TABLE_MASK + " ORDER BY adult_mask DESC")
    List<MaskEntity> getAll();

    @Query("SELECT * FROM " + DataBaseManager.TABLE_MASK + " ORDER BY adult_mask DESC")
    LiveData<List<MaskEntity>> getAllLiveData();

    @Query("SELECT * FROM " + DataBaseManager.TABLE_MASK + " WHERE uid IN (:userIds)")
    List<MaskEntity> getAllByUids(int[] userIds);

    @Query("SELECT * FROM " + DataBaseManager.TABLE_MASK + " WHERE uid=:uid")
    MaskEntity getMaskDataByUid(int uid);

//    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
//            + "last_name LIKE :last LIMIT 1")
//    User findByName(String first, String last);

    @Query("SELECT * FROM " + DataBaseManager.TABLE_MASK + " WHERE code LIKE :code LIMIT 1")
    MaskEntity findByCode(String code);

    @Query("SELECT * FROM " + DataBaseManager.TABLE_MASK + " WHERE address LIKE '%' || :city || '%' ORDER BY adult_mask DESC")
    List<MaskEntity> findByCity(String city);

    @Query("SELECT * FROM " + DataBaseManager.TABLE_MASK + " WHERE address LIKE '%' || :city || '%' AND address LIKE '%' || :searchQuery || '%' ORDER BY adult_mask DESC")
    List<MaskEntity> findByCityAndSearchQuery(String city, String searchQuery);

    @Query("SELECT COUNT(*) FROM " + DataBaseManager.TABLE_MASK)
    int getMaskDataCount();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMaskData(MaskEntity... maskEntities);

    @Update
    void updateMaskData(MaskEntity... maskEntities);

    @Delete
    void deleteMaskData(MaskEntity... maskEntities);

    @Query("DELETE FROM " + DataBaseManager.TABLE_MASK + " WHERE uid=:uid")
    void deleteMaskData(int uid);

}
