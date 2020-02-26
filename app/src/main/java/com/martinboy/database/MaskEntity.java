package com.martinboy.database;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = DataBaseManager.TABLE_MASK)
public class MaskEntity implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    private int uid;

    @ColumnInfo(name = "code")
    private String code;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "phone")
    private String phone;

    @ColumnInfo(name = "adult_mask")
    private int adult_mask;

    @ColumnInfo(name = "child_mask")
    private int child_mask;

    @ColumnInfo(name = "date")
    private String date;

    @ColumnInfo(name = "note")
    private String note;

    @ColumnInfo(name = "custom_note")
    private String custom_note;

    @ColumnInfo(name = "latitude")
    private double latitude;

    @ColumnInfo(name = "longitude")
    private double longitude;

    protected MaskEntity(Parcel in) {
        uid = in.readInt();
        code = in.readString();
        name = in.readString();
        address = in.readString();
        phone = in.readString();
        adult_mask = in.readInt();
        child_mask = in.readInt();
        date = in.readString();
        note = in.readString();
        custom_note = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public MaskEntity() {

    }

    public MaskEntity(String code,
                      String name,
                      String address,
                      String phone,
                      int adult_mask,
                      int child_mask,
                      String date){
        this.code = code;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.adult_mask = adult_mask;
        this.child_mask = child_mask;
        this.date = date;
    }

    public MaskEntity(String code,
                      String name,
                      String address,
                      String phone,
                      int adult_mask,
                      int child_mask,
                      String date,
                      String note,
                      String custom_note,
                      double latitude,
                      double longitude) {
        this.code = code;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.adult_mask = adult_mask;
        this.child_mask = child_mask;
        this.date = date;
        this.note = note;
        this.custom_note = custom_note;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uid);
        dest.writeString(code);
        dest.writeString(name);
        dest.writeString(address);
        dest.writeString(phone);
        dest.writeInt(adult_mask);
        dest.writeInt(child_mask);
        dest.writeString(date);
        dest.writeString(note);
        dest.writeString(custom_note);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MaskEntity> CREATOR = new Creator<MaskEntity>() {
        @Override
        public MaskEntity createFromParcel(Parcel in) {
            return new MaskEntity(in);
        }

        @Override
        public MaskEntity[] newArray(int size) {
            return new MaskEntity[size];
        }
    };

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getAdult_mask() {
        return adult_mask;
    }

    public void setAdult_mask(int adult_mask) {
        this.adult_mask = adult_mask;
    }

    public int getChild_mask() {
        return child_mask;
    }

    public void setChild_mask(int child_mask) {
        this.child_mask = child_mask;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCustom_note() {
        return custom_note;
    }

    public void setCustom_note(String custom_note) {
        this.custom_note = custom_note;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
