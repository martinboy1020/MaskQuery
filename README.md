# MaskQuery
這是剛好因應新冠肺炎議題所自己做的一個Side Project

裡頭用到的部分有  
口罩資訊資料來源:  
https://raw.githubusercontent.com/kiang/pharmacies/master/json/points.json  
  
本專案使用的網路接口套件為Volley  
資料庫的部分使用的是Android Room  
地圖部分目前是直接傳送到Google地圖App呈現 有時間的話再看看使用Google Map Api  
  
原本資料來源的部分想直接使用健保署的資料  
但因為目前測試Android10在下載CVS檔案時 不知道為何在漫遊狀態下異常的慢  
而且健保署資料目前並無經緯度 無法直接顯示在地圖上 需要透過額外的方式獲取  
故後來將獲取資料的部分改為使用kiang大大提供的口罩資訊  
