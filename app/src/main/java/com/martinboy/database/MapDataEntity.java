//package com.martinboy.database;
//
//import androidx.room.ColumnInfo;
//import androidx.room.Entity;
//import androidx.room.PrimaryKey;
//
//@Entity(tableName = DataBaseManager.TABLE_MAP)
//public class MapDataEntity {
//
//    @PrimaryKey(autoGenerate = true)
//    @ColumnInfo(name = "uid")
//    private int uid;
//
//    @ColumnInfo(name = "code")
//    private String code;
//
//    @ColumnInfo(name = "name")
//    private String name;
//
//    @ColumnInfo(name = "latitude")
//    private double latitude;
//
//    @ColumnInfo(name = "longitude")
//    private double longitude;
//
//    public MapDataEntity(String code, String name, double latitude, double longitude) {
//        this.code = code;
//        this.name = name;
//        this.latitude = latitude;
//        this.longitude = longitude;
//    }
//
//    public int getUid() {
//        return uid;
//    }
//
//    public void setUid(int uid) {
//        this.uid = uid;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public double getLatitude() {
//        return latitude;
//    }
//
//    public void setLatitude(double latitude) {
//        this.latitude = latitude;
//    }
//
//    public double getLongitude() {
//        return longitude;
//    }
//
//    public void setLongitude(double longitude) {
//        this.longitude = longitude;
//    }
//}
