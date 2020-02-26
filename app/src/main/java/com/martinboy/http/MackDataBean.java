package com.martinboy.http;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MackDataBean {

    private List<MaskData> mackDataList;

    public List<MaskData> getMackDataList() {
        return mackDataList;
    }

    private void setMackDataList(List<MaskData> mackDataList) {
        this.mackDataList = mackDataList;
    }

    private MackDataBean() {

    }

    public static MackDataBean parse(String jsonString)
            throws JSONException {

        String FEATURES = "features";
        String PROPERTIES = "properties";
        String GEOMETRY = "geometry";
        String ID = "id";
        String NAME = "name";
        String PHONE = "phone";
        String ADDRESS = "address";
        String MASK_ADULT = "mask_adult";
        String MASK_CHILD = "mask_child";
        String UPDATED = "updated";
        String NOTE = "note";
        String CUSTOM_NOTE = "custom_note";
        String COORDINATES = "coordinates";

        MackDataBean bean = new MackDataBean();

        JSONObject jRoot;

        try {
            jRoot = new JSONObject(jsonString);
        } catch (Exception e) {
            jRoot = null;
        }

        if (jRoot != null) {

            if (!jRoot.isNull(FEATURES)) {

                JSONArray featuresArray = jRoot.getJSONArray(FEATURES);

                List<MaskData> mapDataList = new ArrayList<>();

                for (int i = 0; i < featuresArray.length(); i++) {

                    MaskData maskData = new MaskData();

                    JSONObject featuresObject = featuresArray.getJSONObject(i);

                    if (!featuresObject.isNull(PROPERTIES)) {

                        JSONObject propertiesObject = featuresObject.getJSONObject(PROPERTIES);

                        if (!propertiesObject.isNull(ID)) {
                            maskData.setId(propertiesObject.getString(ID));
                        }

                        if (!propertiesObject.isNull(NAME)) {
                            maskData.setName(propertiesObject.getString(NAME));
                        }

                        if (!propertiesObject.isNull(PHONE)) {
                            maskData.setPhone(propertiesObject.getString(PHONE));
                        }

                        if (!propertiesObject.isNull(ADDRESS)) {
                            maskData.setAddress(propertiesObject.getString(ADDRESS));
                        }

                        if (!propertiesObject.isNull(MASK_ADULT)) {
                            maskData.setMaskAdult(propertiesObject.getInt(MASK_ADULT));
                        }

                        if (!propertiesObject.isNull(MASK_CHILD)) {
                            maskData.setChildAdult(propertiesObject.getInt(MASK_CHILD));
                        }

                        if (!propertiesObject.isNull(UPDATED)) {
                            maskData.setUpdate(propertiesObject.getString(UPDATED));
                        }

                        if (!propertiesObject.isNull(NOTE)) {
                            maskData.setNote(propertiesObject.getString(NOTE));
                        }

                        if (!propertiesObject.isNull(CUSTOM_NOTE)) {
                            maskData.setCustom_note(propertiesObject.getString(CUSTOM_NOTE));
                        }

                    }

                    if (!featuresObject.isNull(GEOMETRY)) {

                        JSONObject geometryObject = featuresObject.getJSONObject(GEOMETRY);

                        if (!geometryObject.isNull(COORDINATES)) {
                            JSONArray coordinatesArray = geometryObject.getJSONArray(COORDINATES);
                            maskData.setLatitude(coordinatesArray.getDouble(0));
                            maskData.setLongitude(coordinatesArray.getDouble(1));
                        }

                    }

                    mapDataList.add(maskData);

                }

                bean.setMackDataList(mapDataList);

            }

        }

        return bean;
    }

    public static class MaskData {

        private String id;
        private String name;
        private String phone;
        private String address;
        private int maskAdult;
        private int childAdult;
        private String update;
        private String note;
        private String custom_note;
        private double latitude;
        private double longitude;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public int getMaskAdult() {
            return maskAdult;
        }

        public void setMaskAdult(int maskAdult) {
            this.maskAdult = maskAdult;
        }

        public int getChildAdult() {
            return childAdult;
        }

        public void setChildAdult(int childAdult) {
            this.childAdult = childAdult;
        }

        public String getUpdate() {
            return update;
        }

        public void setUpdate(String update) {
            this.update = update;
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

}
