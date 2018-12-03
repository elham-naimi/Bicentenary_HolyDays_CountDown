package com.elna.holyday.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.util.TypedValue;

import com.elna.holyday.constant.HolyDaysData;
import com.elna.holyday.model.HolyDay;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Calendar;

import static org.threeten.bp.temporal.ChronoUnit.DAYS;
import static org.threeten.bp.temporal.ChronoUnit.MINUTES;


/**
 * Created by elhamnaimi on 1/7/18.
 *
 * doubts : why Calendar.getInstance?
 */

public class Util {

    private static String TAG = Util.class.getSimpleName();

    private static Calendar getCurrentTime() {
        return Calendar.getInstance();
    }


    public static long getDaysLeft(LocalDateTime bicentenaryHolyDay) {
        if(bicentenaryHolyDay == null) return -1;
        LocalDateTime today = LocalDateTime.now();
        long days = DAYS.between(today, bicentenaryHolyDay);
        long minutes = MINUTES.between(today.plusDays(days), bicentenaryHolyDay);
        if (minutes > 1)
            days++;
        if (days < 0) days = 0;
        return days;
    }

    public static Calendar getNextUpdateTime() {
        Calendar nextUpdateTime = Calendar.getInstance();
        nextUpdateTime.set(Calendar.HOUR_OF_DAY, HolyDaysData.NEXT_UPDATE_HOUR);
        nextUpdateTime.set(Calendar.MINUTE, HolyDaysData.NEXT_UPDATE_MINUTE);
        nextUpdateTime.set(Calendar.SECOND, HolyDaysData.NEXT_UPDATE_SECOND);
        if (getCurrentTime().after(nextUpdateTime)) {
            nextUpdateTime.add(Calendar.DATE, 1);
        }

        return nextUpdateTime;

    }

    private static LocalDateTime getBicentenaryHolyDate() {
        return LocalDateTime.of
                (HolyDaysData.BICENTENARY_YEAR, HolyDaysData.BICENTENARY_MONTH, HolyDaysData.BICENTENARY_DAY,
                        HolyDaysData.BICENTENARY_HOUR, HolyDaysData.BICENTENARY_MINUTE, 00);

    }


    public static int getDaysLeftToBicentenary() {

        return (int) Util.getDaysLeft(getBicentenaryHolyDate());

    }


    public static LocalDateTime getNextHolyDayDateTime() {
        return LocalDateTime.now();
    }


    public static int getApproxXToCenterText(String text, Typeface typeface, int fontSize, int widthToFitStringInto) {
        Paint p = new Paint();
        p.setTypeface(typeface);
        p.setTextSize(fontSize);
        float textWidth = p.measureText(text);
        int xOffset = (int) ((widthToFitStringInto - textWidth) / 2f) - (int) (fontSize / 2f);
        return xOffset;
    }

    public static Bitmap buildUpdate(String text, Context context, String fontFile) {
        Bitmap myBitmap = Bitmap.createBitmap(400, 60, Bitmap.Config.ARGB_4444);
        Canvas myCanvas = new Canvas(myBitmap);
        Paint paint = new Paint();
        Typeface mytypeface = Typeface.createFromAsset(context.getAssets(), fontFile);
        paint.setAntiAlias(true);
        paint.setSubpixelText(true);
        paint.setTypeface(mytypeface);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextSize(60);
        paint.setTextAlign(Paint.Align.CENTER);
        myCanvas.drawText(text, 0, 50, paint);
        return myBitmap;
    }

    public static Calendar getLastHolyDayOfTheYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, Calendar.NOVEMBER);
        calendar.set(Calendar.DAY_OF_MONTH, 28);
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 00);
        calendar.set(Calendar.SECOND, 00);
        return calendar;
    }

    public static int getNextYear() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        return calendar.get(Calendar.YEAR);
    }

    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static ArrayList<HolyDay> parseHolyDays(String strHolyDays, int year) {
        ArrayList<HolyDay> holyDaysList = new ArrayList<>();
        if(strHolyDays == null)
           return holyDaysList;
        String[] holyDays = strHolyDays.split("\\#");
        for (String holyday : holyDays
                ) {
            /*  Naw-Rúz$March21201818:00:00 */
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMMddyyyyHHmmss");
            String strDate = holyday.substring(holyday.indexOf("$")+1,holyday.length());
            LocalDateTime date = LocalDateTime.parse(strDate, formatter);
            String desc = holyday.substring(0, holyday.indexOf("$"));
            HolyDay holyDay = new HolyDay(date, desc);
            holyDaysList.add(holyDay);

        }
        return holyDaysList;
    }

/*

the value 0 if the time represented by the argument is equal to the time represented by this Calendar;
 a value less than 0 if the time of this Calendar is before the time represented by the argument;
 and a value greater than 0 if the time of this Calendar is after the time represented by the argument.
 */

    public static int getUpcomingHolyDayIndex(){
        ArrayList<HolyDay> holyDays = null;
        Calendar currentDate = Util.getCurrentTime();

        Log.i(TAG,"currentDate.compareTo(Util.getLastHolyDayOfTheYear() "+currentDate.compareTo(Util.getLastHolyDayOfTheYear()));
        if (currentDate.compareTo(Util.getLastHolyDayOfTheYear()) < 0) {
            Log.i(TAG,"year"+Util.getCurrentYear());
            holyDays = Util.parseHolyDays(Util.getHolyDaysStringForYear(Util.getCurrentYear()), Util.getCurrentYear());

        } else {
            Log.i(TAG,"year"+Util.getNextYear());

            holyDays = Util.parseHolyDays(Util.getHolyDaysStringForYear(Util.getNextYear()), Util.getNextYear());

        }

        int index = Util.getUpcomingHolyDayIndex(holyDays);
        return index;

    }


    public static String getHolyDaysStringForYear(int year) {
        return  HolyDaysData.holyDays.get(year);
    }



    public static int getUpcomingHolyDayIndex(ArrayList<HolyDay> dateList) {
        LocalDateTime currentDate = LocalDateTime.now();

        int index = 0;
        boolean flag = false;
        for (HolyDay holyDay : dateList
                ) {

            //    LocalDateTime endOfHolyDay =  holyDay.date.plusDays(1);

            if (currentDate.isBefore(holyDay.date)) {
                flag = true;
                break;
            }
            index++;
        }
        return index;

    }



    public static ArrayList<HolyDay> getUpcomingHolyDays(int numberOfHolyDaysToShow) {
        ArrayList<HolyDay > upcomingHolyDays = new ArrayList<>();
        ArrayList<HolyDay > holyDaysList = new ArrayList<>();
        int index = 0;

        // Current year + Next year
        if (Util.getCurrentTime().compareTo(Util.getLastHolyDayOfTheYear()) < 0){
            index = Util.getUpcomingHolyDayIndex();
            ArrayList<HolyDay> currentYearHolyDays = Util.parseHolyDays(Util.getHolyDaysStringForYear(Util.getCurrentYear()),Util.getCurrentYear());
            ArrayList<HolyDay> nextYearHolyDays = Util.parseHolyDays(Util.getHolyDaysStringForYear(Util.getNextYear()), Util.getNextYear());
            holyDaysList.addAll(currentYearHolyDays);
            holyDaysList.addAll(nextYearHolyDays);
        }
        // Reached end of year. Add only Next year
        else{
            ArrayList<HolyDay> nextYearHolyDays = Util.parseHolyDays(Util.getHolyDaysStringForYear(Util.getNextYear()), Util.getNextYear());
            holyDaysList.addAll(nextYearHolyDays);
        }

        // Population to another list. Can be removed.
        for (int i = 0; i < numberOfHolyDaysToShow; i++) {
            try{
                upcomingHolyDays.add(holyDaysList.get(index));
                index++;
            }catch (IndexOutOfBoundsException e){
                upcomingHolyDays.add(new HolyDay(LocalDateTime.now(),"Year currently not supported"));
            }

        }
        return upcomingHolyDays;
    }

    /*

     Current date 2018-08-10T08:54:46.080
08-10 08:54:46.080 7078-7078/? I/TAG: 2018-08-10T08:54:46.075
08-10 08:54:46.081 7078-7078/? I/TAG: 2018-08-10T08:54:46.077
    2018-08-10T08:54:46.078
    2018-08-10T08:54:46.078
    2018-08-10T08:54:46.078
    2018-08-10T08:54:46.079
    2018-08-10T08:54:46.079
    2018-08-10T08:54:46.079
    2018-08-10T08:54:46.080
    2018-08-10T08:54:46.080
    2018-08-10T08:54:46.080
     */
    public static Bitmap drawText(Context context, String text, int fontSizeSP,
                                  boolean bold, String fontFileName,int color) {
        Typeface typeface = Typeface.createFromAsset(context.getAssets(),
                fontFileName);

        int fontSizePX = convertDiptoPix(context, fontSizeSP);

        Paint paint = new Paint();
        //this is a must
        paint.setAntiAlias(true);
        //this is a must if you have tiny text or really thin font like i do
        paint.setSubpixelText(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setTypeface(typeface);
        paint.setTextSize(fontSizePX);
        //  paint.setColor(color);
        paint.setFakeBoldText(bold);
        paint.setColor(context.getResources().getColor(color));

        int padding = (fontSizePX / 10);
        int width = (int) (paint.measureText(text) + padding * 2);
        int height = (int) (fontSizePX + padding * 2);
        //some might wanna use Config.ARGB_8888, but this is way more efficient
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(result);
        int theY = fontSizePX;
        canvas.drawText(text, padding, theY, paint);
        return result;
    }

    private static int convertDiptoPix(Context context, float dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dip, context.getResources().getDisplayMetrics());
    }


    public static String formatCase(String string) {
        return string.substring(0,1)+  string.substring(1).toLowerCase() ;
    }

    public static  String getWhenString(HolyDay holyDay){
        String week = holyDay.date.getDayOfWeek().toString();
        week = Util.formatCase(week);
        String month =  holyDay.date.getMonth().toString().substring(0,3);
        month =Util.formatCase(month);
        return week+", "+month+" "+holyDay.date.getDayOfMonth()+" "+" "+holyDay.date.getYear();
    }


}
