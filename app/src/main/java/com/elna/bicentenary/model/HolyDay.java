package com.elna.bicentenary.model;

import org.threeten.bp.LocalDateTime;

/**
 * Created by elhamnaimi on 1/7/18.
 */

public class HolyDay {


    public String desc;
    public LocalDateTime date;
    public String hour;

    public String getDesc() {
        return desc;
    }

    public  LocalDateTime getDate() {return date;}

    public HolyDay(LocalDateTime date, String desc) {
       this.date =  date;
        this.desc = desc;
    }
}
