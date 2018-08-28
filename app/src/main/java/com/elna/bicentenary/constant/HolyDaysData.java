package com.elna.bicentenary.constant;


import java.util.HashMap;

public class HolyDaysData {
    public static final int BICENTENARY_MINUTE = 00;
    public static final int NEXT_UPDATE_MINUTE = 00;

    public static final int BICENTENARY_HOUR = 18;
    public static final int NEXT_UPDATE_HOUR = 18;

    public static final int BICENTENARY_DAY = 28;
    public static final int BICENTENARY_MONTH = 10;

    public static final int BICENTENARY_YEAR = 2019;
     public static final int NEXT_UPDATE_SECOND = 0;

    public static HashMap<Integer,String> holyDays = new HashMap<Integer, String>();




    public  static void init(){
        holyDays.put(2018, "0321Naw-Rúz$0421First Day of Ridván$0429Ninth Day of Ridván$0502Twelth Day of Ridván$0524Declaration of The Báb$0529Ascension of Bahá’u’lláh&3 AM$"+
                "0710Commemoration of the Martyrdom of The Báb&12 PM$1109Birth Anniversary of The Báb$1110Birth Anniversary of Bahá’u’lláh$1126Day of the Covenant$1128Ascension of ‘Abdu’l‑Bahá&1 AM$");
        holyDays.put(2019,
                "0321Naw-Rúz$0421First Day of Ridván$0429Ninth Day of Ridván$0502Twelth Day of Ridván$0524Declaration of The Báb$0529Ascension of Bahá’u’lláh&3 AM$"+
                        "0710Commemoration of the Martyrdom of the Báb&12 PM$1029Bicentenary of the birth of The Báb$1030Birth Anniversary of Bahá’u’lláh$1126Day of the Covenant$1128Ascension of ‘Abdu’l‑Bahá&1 AM$");
        holyDays.put(2020,
                "0220Naw-Rúz$0320First Day of Ridván$0328Ninth Day of Ridván$0401Twelth Day of Ridván$0423Declaration of The Báb$0428Ascension of Bahá’u’lláh&3 AM$"+
                        "0609Commemoration of the Martyrdom of the Báb&12 PM$0918Birth Anniversary of The Báb$0919Birth Anniversary of Bahá’u’lláh$1025Day of the Covenant$1127Ascension of ‘Abdu’l‑Bahá&1 AM$");
        holyDays.put(2021,
                "0320Naw-Rúz$0420First Day of Ridván$0428Ninth Day of Ridván$0501Twelth Day of Ridván$0523Declaration of The Báb$0528Ascension of Bahá’u’lláh&3 AM$"+
                        "0709Commemoration of the Martyrdom of the Báb&12 PM$1106Birth Anniversary of The Báb$1107Birth Anniversary of Bahá’u’lláh$1125Day of the Covenant$1127Ascension of ‘Abdu’l‑Bahá&1 AM$");
        holyDays.put(2022,"0321Naw-Rúz$0421First Day of Ridván$0429Ninth Day of Ridván$0502Twelth Day of Ridván$0524Declaration of the Báb$0529Ascension of Bahá’u’lláh&3 AM$"+
                "0710Commemoration of the Martyrdom of the Báb&12 PM$1026Birth Anniversary of the Báb$1027Birth Anniversary of Bahá’u’lláh$1126Day of the Covenant$1128Ascension of ‘Abdu’l‑Bahá&1 AM$");


    }

}
