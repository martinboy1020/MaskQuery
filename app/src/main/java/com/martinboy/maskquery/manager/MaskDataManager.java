package com.martinboy.maskquery.manager;

import android.content.Context;

import com.martinboy.http.MackDataBean;
import com.martinboy.managertool.VolleyTool;

import org.json.JSONException;

import java.util.List;

public class MaskDataManager {

    private static MaskDataManager INSTANCE = null;
    private VolleyTool volleyTool;

    private MaskDataManager() {

    }

    public static synchronized MaskDataManager getInstance() {

        if (INSTANCE == null)
            INSTANCE = new MaskDataManager();

        return INSTANCE;

    }

    public void getMaskData(Context context, final MapDataCallBack callBack) {

        if (volleyTool == null)
            volleyTool = new VolleyTool();

        volleyTool.runNetApi(context,
                "https://raw.githubusercontent.com/kiang/pharmacies/master/json/points.json",
                VolleyTool.VolleyToolMethod.METHOD_GET, new VolleyTool.VolleyToolCallBack() {
                    @Override
                    public void callBackStringResponse(String s) {

                        try {
                            MackDataBean mackDataBean = MackDataBean.parse(s);
                            callBack.returnMapData(mackDataBean.getMackDataList());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            callBack.returnMapData(null);
                        }


                    }

                    @Override
                    public void callBackStringResponseError(String s) {
                        callBack.returnMapData(null);
                    }
                });

    }

    public interface MapDataCallBack {

        void returnMapData(List<MackDataBean.MaskData> list);

    }

}
