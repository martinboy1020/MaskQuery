package com.martinboy.maskquery.manager;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadCvsManager {

    private static ReadCvsManager INSTANCE = null;
    private CvsHandler handler;

    private ReadCvsManager() {

    }

    public synchronized static ReadCvsManager getInstance() {

        if (INSTANCE == null)
            INSTANCE = new ReadCvsManager();

        return INSTANCE;

    }

    public void readCSV(final String path, CvsCallBack cvsCallBack) {

        if (handler == null) {
            handler = new CvsHandler(cvsCallBack);
        } else {
            handler.callBack = cvsCallBack;
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                List<MaskData> list = new ArrayList<MaskData>();
                File file = new File(path);
                if (!file.exists()) {
                    file.mkdirs();
                }
                FileInputStream fileInputStream;
                Scanner scanner;
                try {
                    fileInputStream = new FileInputStream(file);
                    scanner = new Scanner(fileInputStream, "UTF-8");
                    scanner.nextLine();//讀下一行,把表頭越過。不註釋的話第一行數據就越過去了
                    while (scanner.hasNextLine()) {
                        String sourceString = scanner.nextLine();
                        Log.e("source-->", sourceString);
                        String[] rowData = sourceString.split(",");
//                        Pattern pattern = Pattern.compile("[^,]*,");
//                        Matcher matcher = pattern.matcher(sourceString);
//                        String[] lines = new String[7];
//                        int i = 0;
//                        while (matcher.find()) {
//                            String find = matcher.group().replace(",", "");
//                            lines[i] = find.trim();
//                            i++;
//                        }
                        MaskData bean = new MaskData(rowData[0].trim(), rowData[1].trim(), rowData[2].trim(), rowData[3].trim(), rowData[4].trim(), rowData[5].trim(), rowData[6].trim());
                        list.add(bean);
                    }

                    Message message = new Message();
                    message.what = 0;
                    message.obj = list;
                    handler.sendMessage(message);

                } catch (NumberFormatException e) {
                    Log.e("tag1", "NumberFormatException");
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    Log.e("tag1", "文件不存在");
                    e.printStackTrace();
                }

            }
        });

        thread.start();

    }

    private static class CvsHandler extends Handler {

        private CvsCallBack callBack;

        CvsHandler(CvsCallBack callBack) {
            this.callBack = callBack;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (callBack != null) {
                List<MaskData> list = (List<MaskData>) msg.obj;
                callBack.returnData(list);
            }

        }
    }

    public interface CvsCallBack {

        void returnData(List<MaskData> data);

    }

    public class MaskData {

        private String code;
        private String name;
        private String address;
        private String phone;
        private String adultMask;
        private String childMask;
        private String date;

        public MaskData(String code, String name, String address, String phone, String adultMask, String childMask, String date) {
            this.code = code;
            this.name = name;
            this.address = address;
            this.phone = phone;
            this.adultMask = adultMask;
            this.childMask = childMask;
            this.date = date;
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

        public String getAdultMask() {
            return adultMask;
        }

        public void setAdultMask(String adultMask) {
            this.adultMask = adultMask;
        }

        public String getChildMask() {
            return childMask;
        }

        public void setChildMask(String childMask) {
            this.childMask = childMask;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

}
