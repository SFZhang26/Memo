package com.example.sfzhang.memo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

/**
 * Created by sf Zhang on 2016/12/21.
 */
public class OneShotAlarm extends BroadcastReceiver {

    private int alarmId;
    int BIG_NUM_FOR_ALARM=100;

    @Override
    public void onReceive(Context context, Intent intent) {
        //showMemo(context);

        alarmId=intent.getIntExtra("alarmId",0);

        Toast.makeText(context,"Time UP!",Toast.LENGTH_LONG).show();

        Vibrator vb =(Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(300);

        showNotice(context);
    }

    //show notice and it can be clicked
    private void showNotice(Context context) {
        int num=alarmId-BIG_NUM_FOR_ALARM;
        Log.d("MainActivity","alarmNoticeId "+num);

        //********************BUG SOLVED***********************
        //got a bug here:
        //after clicking the notice, we cannot get to the correct memo
        //while we always go to the second memo initialized
        //****************************************************
        Intent intent=new Intent(context,Edit.class);

        Memo record= getMemoWithId(num);
        deleteTheAlarm(num);//or num

        transportInformationToEdit(intent,record);

        PendingIntent pi=PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);//PendingIntent.FLAG_UPDATE_CURRENT is very important which caused a bug and troubles me for a long time

        NotificationManager manager=(NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Notification notification=new NotificationCompat.Builder(context)
                .setContentTitle(record.getTextDate()+" "+record.getTextTime())
                .setContentText(record.getMainText())
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.icon))
                .setContentIntent(pi)
                .setAutoCancel(true)
                //.setStyle(new NotificationCompat.BigTextStyle().bigText(record.getMainText()))
                .setLights(Color.GREEN,1000,1000)
                .build();
        manager.notify(num,notification);
    }

    private void deleteTheAlarm(int num) {
        ContentValues temp = new ContentValues();
        temp.put("alarm", "");
        String where = String.valueOf(num);
        DataSupport.updateAll(Memo.class, temp, "id = ?", where);
    }
    private void transportInformationToEdit(Intent it, Memo record) {
        it.putExtra("num",record.getNum());
        it.putExtra("tag",record.getTag());
        it.putExtra("textDate",record.getTextDate());
        it.putExtra("textTime",record.getTextTime());
        record.setAlarm("");
        it.putExtra("alarm","");
        it.putExtra("mainText",record.getMainText());
    }

    private Memo getMemoWithId(int num) {
        String whereArgs = String.valueOf(num);
        Memo record= DataSupport.where("id = ?", whereArgs).findFirst(Memo.class);
        return record;
    }
}
