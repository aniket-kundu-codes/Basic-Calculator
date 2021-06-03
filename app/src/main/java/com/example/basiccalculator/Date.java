package com.example.basiccalculator;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Date extends AppCompatActivity {

// functions when days are given

    public int noOfLeapYears(int y)//return no of leap years present Starting from year 1
    {//y is given year
        int v=y-1;
        int noOfLeapYear=(v/400)*97;
        v=v%400;
        noOfLeapYear+=(v/100)*24;
        v=v%100;
        noOfLeapYear+=v/4;
        return noOfLeapYear;
    }
    public int noOfDaysInAYear(int dd,int mm,int yy)
    {  int days=0;
        int m=daysInFeb(yy);
        int max=31;
        for(int k=1;k<mm;k++)
        {days+=k==2?m:(int)Math.abs(max);
            if(k!=7)
                max=1-max;
        }
        days+=dd;
        return days;
    }
    public static int daysInFeb(int yy)
    {int m=28;
        if(yy%4==0)
            m=29;
        if(yy%100==0)
            m=28;
        if(yy%400==0)
            m=29;
        return m;

    }
    public String day_month_cal(int no_of_days,int yy)
    {    int days=0;
        int m=daysInFeb(yy);
        int max=31;int k=0;
        for( k=1;k<=12;k++)
        {   if((days+(k==2?m:(int)Math.abs(max)))<no_of_days)
            days+=k==2?m:(int)Math.abs(max);
        else
            break;
            if(k!=7)
                max=1-max;
        }
        no_of_days-=days;
        return no_of_days+"/"+k;
    }
    public int newDateDays(int dd,int mm,int yy,int days)
    {
        int T1=yy*365+noOfLeapYears(yy);
        T1+=noOfDaysInAYear(dd,mm,yy);
        int T2=T1+days;
        return T2;
    }
    public int roughYear(int yy,int T2)
    {
        int roughY=yy;
        while((roughY*365+noOfLeapYears(roughY))<T2)
            roughY++;
        roughY--;
        return roughY;
    }
    public String CalDate(int dd,int mm,int yy,int days)
    {
        int T2=newDateDays(dd,mm,yy,days);
        int roughY=roughYear(yy,T2);
        String resultDate="/"+roughY;
        T2-=roughY*365+noOfLeapYears(roughY);
        resultDate=day_month_cal(T2,roughY)+resultDate;
        return resultDate;

    }
    public String weekdays(int a)
    {   String ans="";
        if(a==0)
            ans="Sunday";
        else if(a==1)
            ans="Monday";
        else if(a==2)
            ans="Tuesday";
        else if(a==3)
            ans="Wednesday";
        else if(a==4)
            ans="Thursday";
        else if(a==5)
            ans="Friday";
        else if(a==6)
            ans="Saturday";
        return ans;

    }
    public String Week(int dd,int mm,int yy,int days)
    {
        int T2=newDateDays(dd,mm,yy,days);
        int roughY=roughYear(yy,T2);
        int a=roughY;
        a=(a+noOfLeapYears(a))%7;
        T2-=roughY*365+noOfLeapYears(roughY);
        a+=T2-1;
        a%=7;
        return weekdays(a);

    }
    public int CalDay(int dd,int mm,int yy,int ddd,int mmm,int yyy)
    {
        int T1=yy*365+noOfLeapYears(yy);
        T1+=noOfDaysInAYear(dd,mm,yy);
        int T2=yyy*365+noOfLeapYears(yyy);
        T2+=noOfDaysInAYear(ddd,mmm,yyy);
        return (int)Math.abs(T2-T1);

    }
    public static boolean isvalidDate(int dd,int mm,int yy)
    {
        if((mm>0&&mm<13)&&dd>0)
        { int m=daysInFeb(yy);

            int max=31;int k=0;
            for( k=1;k<=12;k++)
            {
                if(k==mm)
                {
                    if(dd<=(k==2?m:(int)Math.abs(max)))
                        return true;
                    else
                        return false;
                }
                if(k!=7)
                    max=1-max;
            }
        }
        return false;

    }
    public int roughYearBack(int yy,int T2)
    {
        int roughYB=yy;
        while((roughYB*365+noOfLeapYears(roughYB))>=T2)
            roughYB--;
        return roughYB;

    }

    public String BCalDate(int dd,int mm,int yy,int Bdays)
    {int T1=yy*365+noOfLeapYears(yy);
        T1+=noOfDaysInAYear(dd,mm,yy);
        int T2=T1-Bdays;
        int roughYB=roughYearBack(yy,T2);
        String resultDate="/"+roughYB;
        T2-=roughYB*365+noOfLeapYears(roughYB);
        resultDate=day_month_cal(T2,roughYB)+resultDate;
        return resultDate;

    }
    public int newBDateDays(int dd,int mm,int yy,int Bdays)
    {
        int T1=yy*365+noOfLeapYears(yy);
        T1+=noOfDaysInAYear(dd,mm,yy);
        int T2=T1-Bdays;
        return T2;

    }
    public String BweekDay(int dd,int mm,int yy,int Bdays)
    { int T2=newBDateDays(dd,mm,yy,Bdays);
        int roughYB=roughYearBack(yy,T2);
        int a=roughYB;
        a=(a+noOfLeapYears(a))%7;
        T2-=roughYB*365+noOfLeapYears(roughYB);
        a+=T2-1;
        a%=7;
        return weekdays(a);


    }
    public static boolean isValidFormat(String date)
    {
        int c=0;
        for(int i=0;i<date.length()-1;i++)
        {char ch1=date.charAt(i);
            char ch2=date.charAt(i+1);
            if(ch1>='0'&&ch1<='9'&&ch2=='/')
                c++;}
        if(c==2)
            return true;
        return false;
    }

    public void divide_(String s,int nd[],char ch)
    {  nd[2]=Integer.parseInt(s.substring(0,s.indexOf(ch)));
        nd[1]=Integer.parseInt(s.substring(s.indexOf(ch)+1,s.lastIndexOf(ch)));
        nd[0]=Integer.parseInt(s.substring(s.lastIndexOf(ch)+1));
    }

    public void divide(String s,int nd[],char ch)
    {  nd[0]=Integer.parseInt(s.substring(0,s.indexOf(ch)));
        nd[1]=Integer.parseInt(s.substring(s.indexOf(ch)+1,s.lastIndexOf(ch)));
        nd[2]=Integer.parseInt(s.substring(s.lastIndexOf(ch)+1));
    }
    public boolean isValidAge(int dd,int mm,int yy,int ddd,int mmm,int yyy)
    {
        if(yyy>=yy)
        { if(yyy==yy)
        { if(mmm>=mm)
        { if(mmm==mm)
        {  if(ddd>=dd)
        {
            return true;
        }
            return false;
        }
            return true;

        }
            return false;
        }
            return true;
        }
        return false;
    }
    public int noOfDaysInMonthOfGivenYear(int mm,int yy)
    {  if(mm<1)
    {
        mm=12;
        yy-=1;
    }

        int max=31;int m=daysInFeb(yy);
        int daysInMonth=0;
        for(int k=1;k<=mm;k++)
        { daysInMonth=(k==2?m:(int)Math.abs(max));
            if(k!=7)
                max=1-max;
        }
        return daysInMonth;

    }
    public String CalAge(int dd,int mm,int yy,int ddd,int mmm,int yyy)
    {  int year=0,month=0,day=0;
        if(isValidAge(dd,mm,yy,ddd,mmm,yyy))
        {

            if(yyy>yy)
            {
                if(mmm>=mm)
                {  if(mmm>mm)
                {  year=yyy-yy;
                    if(ddd>=dd)
                    {    month=mmm-mm;
                        day=ddd-dd;
                    }
                    else
                    { month=mmm-mm-1;
                        day=ddd+noOfDaysInMonthOfGivenYear(mmm-1,yyy)-dd;
                    }
                }
                else
                {    if(ddd>=dd)
                { year=yyy-yy;
                    month=mmm-mm;
                    day=ddd-dd;
                }
                else
                {   year=yyy-yy-1;
                    month=mmm-1+(12-mm);
                    day=ddd+noOfDaysInMonthOfGivenYear(mmm-1,yyy)-dd;
                }

                }

                }
                else
                {
                    year=yyy-yy-1;
                    if(ddd>=dd)
                    { month=mmm+(12-mm);
                        day=ddd-dd;

                    }
                    else
                    {
                        month=mmm-1+(12-mm);
                        day=ddd+noOfDaysInMonthOfGivenYear(mmm-1,yyy)-dd;
                    }
                }

            }
            else
            {
                year=0;
                if(mmm>mm)
                { if(ddd>=dd)
                { month=mmm-mm;
                    day=ddd-dd;
                }
                else
                {
                    month=mmm-mm-1;
                    day=ddd+noOfDaysInMonthOfGivenYear(mmm-1,yyy)-dd;
                }

                }
                else if(mmm==mm)
                {
                    month=0;
                    day=ddd-dd;
                }

            }

            return (year==0?"":year+(year==1?" year ":" years "))+(month==0?"":month+(month==1?" month ":" months "))+day+(day<2?" day":" days");
        }
        return "Enter a Valid DOB";

    }
    TextView.OnEditorActionListener editorActionListener=new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            switch (actionId){
                case EditorInfo.IME_ACTION_UNSPECIFIED:  EditText SecondDate=findViewById(R.id.SecondDate);
                SecondDate.setText(SecondDate.getText().toString()+"/");
                SecondDate.setSelection(SecondDate.getText().toString().length());
                break;
            }
            return false;
        }
    };
    TextView.OnEditorActionListener editorListener=new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                switch (actionId){
                    case EditorInfo.IME_ACTION_UNSPECIFIED:
                        EditText FirstDate=findViewById(R.id.FirstDate);
                   FirstDate.setText(FirstDate.getText().toString()+"/");
                   FirstDate.setSelection(FirstDate.getText().toString().length());
                  break;

            }
            return false;
        }
    };
    public void shareApp()
    {
        ApplicationInfo app=getApplicationContext().getApplicationInfo();
        String filepath=app.sourceDir;
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("application/vnd.android.package-archive");
        File oeiginalApk=new File(filepath);
        try{
            File tempFile=new File(this.getExternalCacheDir()+"/ExtractedApk");
            if(!tempFile.isDirectory())
                if(!tempFile.mkdirs())
                    return;
            tempFile=new File(tempFile.getPath()+"/"+getString(app.labelRes).replace(" ","").toLowerCase()+".apk");
            if(!tempFile.exists())
                if(!tempFile.createNewFile())
                    return;

            InputStream in=new FileInputStream(oeiginalApk);
            OutputStream out=new FileOutputStream(tempFile);
            byte[] buf=new byte[1024];
            int len;
            while((len=in.read(buf))>0)
                out.write(buf,0,len);
            in.close();
            out.close();
            Uri photoURI= FileProvider.getUriForFile(this,BuildConfig.APPLICATION_ID+".provider",tempFile);
            intent.putExtra(Intent.EXTRA_STREAM,photoURI);
            try{
                startActivity(Intent.createChooser(intent,"Share Basic Calculator app via"));}
            catch (Exception e ) {
                Toast.makeText(Date.this, "Sorry,app sharing is not supported", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_layout_date);

        Button submit=findViewById(R.id.submitbasecal);
        EditText FirstDate=findViewById(R.id.FirstDate);
        EditText SecondDate=findViewById(R.id.SecondDate);
        EditText Day=findViewById(R.id.Day);
        TextView Ans_view=findViewById(R.id.Ans_view);
        ImageButton toggle=findViewById(R.id.toggle);
        DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);
        NavigationView navigationView=findViewById(R.id.nav_view);
        RadioButton radioagecal=findViewById(R.id.radioagecal);
        RadioButton radiodatecal=findViewById(R.id.radiodatecal);
        RadioButton radioAfter=findViewById(R.id.radioAfter);
        RadioButton radioBefore=findViewById(R.id.radioBefore);
        TextView firstdatesuggestion=findViewById(R.id.FirstDateSuggestion);


        FirstDate.setOnEditorActionListener(editorListener);
        SecondDate.setOnEditorActionListener(editorActionListener);
        final boolean[] agecal = {false};
        final boolean[] datecal = {false};
        final boolean[] before = {false};
        final boolean[] after = {false};
        SecondDate.setEnabled(false);
        Day.setEnabled(false);
        radioAfter.setEnabled(false);
        radioBefore.setEnabled(false);
        FirstDate.setEnabled(false);

        radioagecal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agecal[0]=true;
                datecal[0]=false;
                SecondDate.setEnabled(false);
                Day.setEnabled(false);
                radioAfter.setEnabled(false);
                radioBefore.setEnabled(false);
                firstdatesuggestion.setText("Enter Your DOB:");
                FirstDate.setEnabled(true);

            }
        });
     radiodatecal.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             agecal[0]=false;
             datecal[0]=true;
             SecondDate.setEnabled(true);
             Day.setEnabled(true);
             radioAfter.setEnabled(true);
             radioBefore.setEnabled(true);
             firstdatesuggestion.setText("Enter Initial Date:");
             FirstDate.setEnabled(true);
         }
     });
     radioAfter.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             after[0]=true;
             before[0]=false;
         }
     });
     radioBefore.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             after[0]=false;
             before[0]=true;
         }
     });


        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });
        //Dark  Mode
        TextView appImage=findViewById(R.id.appImage);
        appImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(AppCompatDelegate.getDefaultNightMode()==AppCompatDelegate.MODE_NIGHT_YES)
                {AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                } else
                {AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
         navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.nav_main)
                {
                    startActivity(new Intent(Date.this,MainActivity.class));
                    finish();

                }
                else if(item.getItemId()==R.id.nav_basecaculator)
                {startActivity(new Intent(Date.this,BaseCalculator.class));
                    finish();
                }
                else if(item.getItemId()==R.id.nav_investment)
                {startActivity(new Intent(Date.this,Investment.class));
                    finish();
                }else if(item.getItemId()==R.id.nav_unitconverter)
                {
                    startActivity(new Intent(Date.this,UnitConverter.class));
                    finish();
                }
                else if(item.getItemId()==R.id.nav_about)
                    startActivity(new Intent(Date.this,About.class));
                else if(item.getItemId()==R.id.nav_share)
                    shareApp();
                 drawerLayout.closeDrawer(GravityCompat.START);

                 return true;
             }
         });
        FirstDate.addTextChangedListener(new TextWatcher() { String y="";
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                     y=FirstDate.getText().toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               String x=FirstDate.getText().toString();
               if(!x.equals(y)) {
                 if(datecal[0])  firstdatesuggestion.setText("Enter Initial Date:");
                    else if(agecal[0]) firstdatesuggestion.setText("Enter Your DOB");
               }
               if(!x.equals(""))
               if(x.charAt(x.length()-1)=='/')
               {  int c=0;
                  for(int i=0;i<x.length();i++)
                   if(x.charAt(i)=='/')
                      c++;
                   if(c==3)
                   {
                       FirstDate.setText(x.substring(0,x.length()-1));
                       FirstDate.setSelection(x.length()-1);
                   }

               }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        SecondDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String x=SecondDate.getText().toString();
                if(!x.equals("")&&x.length()>0){
                    if(!Day.getText().toString().equals(""))Day.setText("");
                if(x.charAt(x.length()-1)=='/')
                {  int c=0;
                    for(int i=0;i<x.length();i++)
                        if(x.charAt(i)=='/')
                            c++;
                    if(c==3)
                    {
                        SecondDate.setText(x.substring(0,x.length()-1));
                        SecondDate.setSelection(x.length()-1);
                    }

                }}

            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Day.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String x=Day.getText().toString();
                if(!x.equals("")&&x.length()>0)
                    if(!SecondDate.getText().toString().equals(""))SecondDate.setText("");

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String FD=FirstDate.getText().toString();
                int Fd[] = new int[3];
                boolean Vf_fd=true;
                if(!FD.equals("")) {
                    Vf_fd=isValidFormat(FD);
                    if(Vf_fd)
                        divide(FD,Fd,'/');
                }
                else
                {
                    FD=java.time.LocalDate.now().toString();
                  divide_(FD,Fd,'-');
                  FirstDate.setText(Fd[0]+"/"+Fd[1]+"/"+Fd[2]);
                  firstdatesuggestion.setText("Today's Date:");
                }
                if(datecal[0]){
                String SD=SecondDate.getText().toString();
                if(!SD.equals(""))
                { int Sd[]=new int[3];
                   boolean Vf_sd=isValidFormat(SD);
                   if(Vf_sd)
                       divide(SD,Sd,'/');
                   if(Vf_fd&&Vf_sd) {
                       if (isvalidDate(Fd[0], Fd[1], Fd[2])&&isvalidDate(Sd[0],Sd[1],Sd[2]))
                       { int No_of_days=CalDay(Fd[0], Fd[1], Fd[2],Sd[0],Sd[1],Sd[2]);
                          Ans_view.setText("Days In Between is "+No_of_days);
                       }
                       else
                           Ans_view.setText("Enter a Valid Date");

                   }
                   else
                       Ans_view.setText("Enter Dates in Valid Format");


                }
                else if(!Day.getText().toString().equals(""))
                {
                    if(Vf_fd)
                    {
                        int days=Integer.parseInt(Day.getText().toString());
                        String Final_date = "",week_day="";
                        if(after[0]) {
                             Final_date = CalDate(Fd[0], Fd[1], Fd[2], days);
                             week_day = Week(Fd[0], Fd[1], Fd[2], days);
                        }
                        else if(before[0])
                        { Final_date=BCalDate(Fd[0], Fd[1], Fd[2], days);
                            week_day = BweekDay(Fd[0], Fd[1], Fd[2], days);
                        }
                        if(after[0]||before[0])
                        Ans_view.setText(Final_date + " fall on " + week_day);
                        else
                            Ans_view.setText("Select After or Before");
                    }
                    else
                        Ans_view.setText("Enter First Date in Valid Format");


                }
                else if(!Vf_fd)
                    Ans_view.setText("Enter First Date in Valid Format");
                else
                    Ans_view.setText("Enter Unfilled Spaces");

            }
            else if(agecal[0])
                { if(Vf_fd)
                {  if(isvalidDate(Fd[0],Fd[1],Fd[2])){
                    String current_date=java.time.LocalDate.now().toString();
                   int Cd[]=new int[3];
                    divide_(current_date,Cd,'-');
                    String age=CalAge(Fd[0],Fd[1],Fd[2],Cd[0],Cd[1],Cd[2]);
                     Ans_view.setText(age);}
                     else
                    Ans_view.setText("Enter a Valid DOB");

                }
                else
                    Ans_view.setText("Enter DOB in Valid Format");

                }
            else
                    Ans_view.setText("Select Age or Date");


            }
        });




    }
}