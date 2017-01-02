package com.example.sfzhang.memo;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Edit extends Activity
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, View.OnLongClickListener, RadioGroup.OnCheckedChangeListener{

    //int[] color={R.color.yellow, R.color.blue,R.color.green,R.color.red,R.color.white};

    LinearLayout myLayout;

    TextView date_text;
    TextView time_text;
    ImageButton alarm_button;
    EditText edt;
    TextView av;
    RadioGroup tagRadio;
    RadioButton rdButton;
    String pic="";
    String audio="";

    int tag;
    String textDate;
    String textTime;
    String mainText;

    //alarm clock
    int num=0; //for requestcode
    int BIG_NUM_FOR_ALARM=100;
    String alarm="";
    int alarm_hour=0;
    int alarm_minute=0;
    int alarm_year=0;
    int alarm_month=0;
    int alarm_day=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_edit);

        Intent it=getIntent();
        getInformationFromMain(it);

        myLayout = (LinearLayout) findViewById(R.id.whole);
        //myLayout.setBackgroundColor(color[tag]);
        //myLayout.setBackgroundResource(R.drawable.edit_bg_yellow);
        myLayout.setBackgroundResource(R.drawable.edit_bg_yellow);

        date_text=(TextView) findViewById(R.id.dateText);
        time_text=(TextView) findViewById(R.id.timeText);
        alarm_button=(ImageButton) findViewById((R.id.alarmButton));
        //alarm_button.setBackgroundResource(R.mipmap.ic_launcher);
        edt=(EditText) findViewById(R.id.editText);
        //edt.setBackgroundColor(color[tag]);
        av=(TextView) findViewById(R.id.alarmView);

        date_text.setText(textDate);
        time_text.setText(textTime);
        edt.setText(mainText);

        av.setOnLongClickListener(this);
        if(alarm.length()>1) av.setText("Alert at "+alarm+"!");
        else av.setVisibility(View.GONE);

        tagRadio=(RadioGroup) findViewById(R.id.tagRadio);
        tagRadio.setOnCheckedChangeListener(this);

        setRadioButtonCheckedAccordingToTag(tag);
        rdButton.setChecked(true);
    }

    private void setRadioButtonCheckedAccordingToTag(int tag) {
        switch (tag) {
            case 0:
                rdButton=(RadioButton) findViewById(R.id.yellow);
                break;
            case 1:
                rdButton=(RadioButton) findViewById(R.id.blue);
                break;
            case 2:
                rdButton=(RadioButton) findViewById(R.id.green);
                break;
            case 3:
                rdButton=(RadioButton) findViewById(R.id.red);
                break;
            case 4:
                rdButton=(RadioButton) findViewById(R.id.white);
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (tagRadio.getCheckedRadioButtonId()) {
            case R.id.yellow:
                tag=0;
                //edt.setBackgroundColor(color[tag]);
                myLayout.setBackgroundResource(R.drawable.edit_bg_yellow);
                break;
            case R.id.blue:
                tag=1;
                //edt.setBackgroundColor(color[tag]);
                myLayout.setBackgroundResource(R.drawable.edit_bg_blue);
                break;
            case R.id.green:
                tag=2;
                //edt.setBackgroundColor(color[tag]);
                myLayout.setBackgroundResource(R.drawable.edit_bg_green);
                break;
            case R.id.red:
                tag=3;
                //edt.setBackgroundColor(color[tag]);
                myLayout.setBackgroundResource(R.drawable.edit_bg_red);
                break;
            case R.id.white:
                tag=4;
                //edt.setBackgroundColor(color[tag]);
                myLayout.setBackgroundResource(R.drawable.edit_bg_white);
                break;
            default:
                break;
        }
    }

    //*********************************set alarm clock***********************************
    public void setAlarm(View v) {
        if(alarm.length()<=1) {
            //if no alarm clock has been set up before
            //show the current time
            Calendar c=Calendar.getInstance();
            alarm_hour=c.get(Calendar.HOUR_OF_DAY);
            alarm_minute=c.get(Calendar.MINUTE);

            alarm_year=c.get(Calendar.YEAR);
            alarm_month=c.get(Calendar.MONTH)+1;
            alarm_day=c.get(Calendar.DAY_OF_MONTH);
        }
        else {
            //show the alarm clock time which has been set up before
            int i=0, k=0;
            while(i<alarm.length()&&alarm.charAt(i)!='/') i++;
            alarm_year=Integer.parseInt(alarm.substring(k,i));
            k=i+1;i++;
            while(i<alarm.length()&&alarm.charAt(i)!='/') i++;
            alarm_month=Integer.parseInt(alarm.substring(k,i));
            k=i+1;i++;
            while(i<alarm.length()&&alarm.charAt(i)!=' ') i++;
            alarm_day=Integer.parseInt(alarm.substring(k,i));
            k=i+1;i++;
            while(i<alarm.length()&&alarm.charAt(i)!=':') i++;
            alarm_hour=Integer.parseInt(alarm.substring(k,i));
            k=i+1;i++;
            alarm_minute=Integer.parseInt(alarm.substring(k));
        }

        new TimePickerDialog(this,this,alarm_hour,alarm_minute,true).show();
        new DatePickerDialog(this,this,alarm_year,alarm_month-1,alarm_day).show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        alarm_year=year;
        alarm_month=monthOfYear+1;
        alarm_day=dayOfMonth;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        alarm_hour=hourOfDay;
        alarm_minute=minute;

        alarm=alarm_year+"/"+alarm_month+"/"+alarm_day+" "+alarm_hour+":"+alarm_minute;
        av.setText("Alert at "+alarm+"!");
        av.setVisibility(View.VISIBLE);
        Toast.makeText(this,"Alarm will be on at "+alarm+" !",Toast.LENGTH_LONG).show();
    }

    //******************************************************************************************



    //press the save button
    public void onSave(View v) {
        returnResult();
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        //if the Back Button is pressed
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            returnResult();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // after pressing back or save, return the current state
    private void returnResult() {
        Intent it=new Intent();

        it.putExtra("tag",tag);
        //no need for date and time
        it.putExtra("alarm",alarm);
        it.putExtra("mainText",edt.getText().toString());

        setResult(RESULT_OK,it);
    }

    //read Intent information from main_activity
    private void getInformationFromMain(Intent it) {
        num=it.getIntExtra("num",0);

        tag=it.getIntExtra("tag",0);
        textDate=it.getStringExtra("textDate");
        textTime=it.getStringExtra("textTime");

        alarm=it.getStringExtra("alarm");
        mainText=it.getStringExtra("mainText");
    }

    @Override
    public boolean onLongClick(View v) {
        if(v.getId()==R.id.alarmView||v.getId()==R.id.alarmButton) {
            //delete the alarm information
            alarm="";
            //hide textView
            av.setVisibility(View.GONE);
        }
        return true;
    }


}
