package com.example.basiccalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;

public class Investment extends AppCompatActivity {

    String item[]={"Principal","Time","Rate","Amount"};
    String sipItem[]={"Principal","Time","Amount"};
    String sipandciitem[]={"Principal","Amount"};
    public String puttingComma(String money)
    { String res="";
     String paise=money.substring(money.indexOf('.'));
       money=money.substring(0,money.indexOf('.'));
       StringBuilder str=new StringBuilder();
       str.append(money);
       str.reverse();
       money=str.toString();
       int flag=3,len=money.length();
       for(int i=0;i<len;i++)
       {if(i==flag)
       {res+=',';flag+=2;
       }
       res+=money.charAt(i);
       }
       str.delete(0,len);
       str.append(res);
       str.reverse();
       res=str.toString();
        return res+paise;
    }
    public double correcting(String value)
    {
        if(value.indexOf('.')!=-1)
        {
            value=value+'0';
        }
        return Double.parseDouble(value);
    }
    public String toTwoDigits(String value)
    {   String ans=value;
    if(ans.indexOf('.')!=-1)
    {ans=ans+"0";
    ans=ans.substring(0,ans.indexOf('.')+3);}
    else { ans+=".00";
    }
        return ans;
    }

    public double toMonths(double year,double month)
    {
        double n=year*12+month;
        return n;
    }
    public double toYears(double year,double month)
    {
        double T=year+month/12;
        return T;
    }
    public String toYearMonthFromYear(double T)
    {
        int year=(int)T;
        int month=(int)Math.round((T-year)*12);
        String time="";
        if(month==12) {
            year += 1;
            month = 0;
        }
        if(year!=0)
            time=year+" Years ";
        if(month!=0)
            time=time+month+" Months";
        return time;
    }
    public String toYearMonthFrommonth(double n)
    {  int year=(int)(n/12);
      int month=(int)(n%12);
      String time="";
        if(month==12) {
            year += 1;
            month = 0;
        }
        if(year!=0)
            time=year+" Years ";
        if(month!=0)
            time=time+month+" Months";
        return time;


    }
    public double toMonthlyRate(double R)
    {  double rate=(R/100)/12;
        return rate;

    }
    public double totalInvestment(double P,double T)
    {
        double TI=P*T;
        return TI;
    }
    public double SI(double P,double T,double R)
    {
        double I=P*T*R/100;
        return I;
    }
    public double amountSI(double P,double T,double R)
    {
        double amount=SI(P,T,R)+P;
        return amount;
    }
    public double timeSI(double P,double R,double amount)
    {
        double T=100*(amount-P)/(P*R);
        return T;
    }
    public double rateSI(double P,double T,double amount)
    {
        double R=100*(amount-P)/(P*T);
        return R;
    }
    public double principalSI(double T,double R,double amount)
    {
        double P=amount/(1+T*R/100);
        return P;
    }
    public double CI(double P,double T,double R)
    {
        double I=P*Math.pow((1+R/100),T)-P;
        return I;
    }
    public double amountCI(double P,double T,double R)
    {
        double amount=CI(P,T,R)+P;
        return amount;
    }
    public double timeCI(double P,double R,double amount)
    {
        double T=Math.log(amount/P)/Math.log(1+R/100);
        return T;
    }
    public double rateCI(double P,double T,double amount)
    {
        double R=100*(Math.pow(amount/P,1/T)-1);
        return R;
    }
    public double principalCI(double T,double R,double amount)
    {
        double P=amount/Math.pow((1+R/100),T);
        return P;
    }

    public double amountSip(double P,double n,double r)//monthly investment P,no of Months n,annual rate rate
    {
        double amount=P*((Math.pow(1+r,n)-1)/r)*(r+1);
        return amount;
    }
    public double timeSip(double P,double r,double amount)
    {
        double n=Math.log(1+amount*r/(P*(1+r)))/Math.log(1+r);
        return  n;
    }
    public double principalSip(double n,double r,double amount)
    {
        double P=amount/(((Math.pow(1+r,n)-1)/r)*(r+1));
        return P;
    }
    public double amountcisip(double P,double n1,double T2,double R)
    {
        double iamount=amountSip(P,n1,toMonthlyRate(R));
        T2=T2-toYears(0,n1);
        double famount=amountCI(iamount,T2,R);
        return famount;

    }
    public double principalcisip(double n1,double T2,double R,double famount)
    {    T2=T2-toYears(0,n1);
        double iamount=principalCI(T2,R,famount);
        double P=principalSip(n1,toMonthlyRate(R),iamount);
        return P;
    }
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
                Toast.makeText(Investment.this, "Sorry,app sharing is not supported", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_layout_investment);
        RadioGroup R1=findViewById(R.id.R1);
        RadioGroup R2=findViewById(R.id.R2);
        RadioButton si=findViewById(R.id.SI);
        RadioButton ci=findViewById(R.id.CI);
        RadioButton sip=findViewById(R.id.SIP);
        RadioButton cisip=findViewById(R.id.CIandSIP);
        TextView principal=findViewById(R.id.principal);
        EditText principalV=findViewById(R.id.principalV);
        TableRow principalTable=findViewById(R.id.principalTable);
        TextView totalTime=findViewById(R.id.TotalTime);
        TableRow totalTimeTable=findViewById(R.id.TotalTimeTable);
        TextView years=findViewById(R.id.Years);
        EditText yearsV=findViewById(R.id.YearsV);
        TextView months=findViewById(R.id.Months);
        EditText monthsV=findViewById(R.id.MonthV);
        TextView sipCF=findViewById(R.id.SipCF);
        TextView sipYears=findViewById(R.id.SipYears);
        EditText sipYearsV=findViewById(R.id.SipYearsV);
        TextView sipMonths=findViewById(R.id.SipMonths);
        EditText sipMonthsV=findViewById(R.id.SipMonthsV);
        TextView rate=findViewById(R.id.Rate);
        EditText rateV=findViewById(R.id.RateV);
        TableRow rateTable=findViewById(R.id.rateTable);
        TextView amount=findViewById(R.id.Amount);
        EditText amountV=findViewById(R.id.AmountV);
        TableRow amountTable=findViewById(R.id.amountTable);
        TextView ans=findViewById(R.id.Ans);
        Button submit=findViewById(R.id.Submit);
        TableRow sipCFTable=findViewById(R.id.sipCFtable);
        Spinner spinner=findViewById(R.id.spinner);
        TextView want=findViewById(R.id.want);
        R1.clearCheck();
        R2.clearCheck();

        sipCF.setVisibility(View.GONE);
        sipCFTable.setVisibility(View.GONE);

        investmentViewModel ivm= ViewModelProviders.of(this).get(investmentViewModel.class);
        ans.setText(ivm.getText());

        NavigationView nav_view=findViewById(R.id.nav_view);
        DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);
        ImageButton toggle=findViewById(R.id.toggle);
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
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.nav_main)
                {
                    startActivity(new Intent(Investment.this,MainActivity.class));
                    finish();
                }
                else if(item.getItemId()==R.id.nav_date)
                {startActivity(new Intent(Investment.this,Date.class));
                    finish();
                }
                else if(item.getItemId()==R.id.nav_basecaculator)
                {startActivity(new Intent(Investment.this,BaseCalculator.class));
                    finish();
                }
                else if(item.getItemId()==R.id.nav_unitconverter)
                {
                    startActivity(new Intent(Investment.this,UnitConverter.class));
                    finish();
                }
                else if(item.getItemId()==R.id.nav_about)
                    startActivity(new Intent(Investment.this,About.class));
                else if(item.getItemId()==R.id.nav_share)
                    shareApp();
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
       if(ivm.isSi()==true)
       { ArrayAdapter adapter=new ArrayAdapter(Investment.this,android.R.layout.simple_spinner_item,item);
           adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
           spinner.setAdapter(adapter);
           R2.clearCheck();
           sipCF.setVisibility(View.GONE);
           sipCFTable.setVisibility(View.GONE);
           totalTime.setVisibility(View.VISIBLE);
           totalTimeTable.setVisibility(View.VISIBLE);

       }
       si.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ivm.setSi(true);
               ArrayAdapter adapter=new ArrayAdapter(Investment.this,android.R.layout.simple_spinner_item,item);
               adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
               spinner.setAdapter(adapter);
              R2.clearCheck();
               sipCF.setVisibility(View.GONE);
               sipCFTable.setVisibility(View.GONE);
               totalTime.setVisibility(View.VISIBLE);
               totalTimeTable.setVisibility(View.VISIBLE);

           }
       });
       if(ivm.isSip()==true)
       {
           ArrayAdapter adapter=new ArrayAdapter(Investment.this,android.R.layout.simple_spinner_item,sipItem);
           adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
           spinner.setAdapter(adapter);

           R2.clearCheck();
           sipCF.setVisibility(View.VISIBLE);
           sipCFTable.setVisibility(View.VISIBLE);
           totalTime.setVisibility(View.GONE);
           totalTimeTable.setVisibility(View.GONE);
       }
        sip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivm.setSip(true);
                ArrayAdapter adapter=new ArrayAdapter(Investment.this,android.R.layout.simple_spinner_item,sipItem);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                R2.clearCheck();
                    sipCF.setVisibility(View.VISIBLE);
                    sipCFTable.setVisibility(View.VISIBLE);
                totalTime.setVisibility(View.GONE);
                totalTimeTable.setVisibility(View.GONE);
            }
        });
       if (ivm.isCi()==true)
       {
           ArrayAdapter adapter=new ArrayAdapter(Investment.this,android.R.layout.simple_spinner_item,item);
           adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
           spinner.setAdapter(adapter);
           R1.clearCheck();
           sipCF.setVisibility(View.GONE);
           sipCFTable.setVisibility(View.GONE);
           totalTime.setVisibility(View.VISIBLE);
           totalTimeTable.setVisibility(View.VISIBLE);
       }
        ci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivm.setCi(true);
                ArrayAdapter adapter=new ArrayAdapter(Investment.this,android.R.layout.simple_spinner_item,item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                R1.clearCheck();
                    sipCF.setVisibility(View.GONE);
                    sipCFTable.setVisibility(View.GONE);
                totalTime.setVisibility(View.VISIBLE);
                totalTimeTable.setVisibility(View.VISIBLE);


            }
        });
       if(ivm.isCisip()==true)
       { ArrayAdapter adapter=new ArrayAdapter(Investment.this,android.R.layout.simple_spinner_item,sipandciitem);
           adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
           spinner.setAdapter(adapter);
           R1.clearCheck();
           sipCF.setVisibility(View.VISIBLE);
           sipCFTable.setVisibility(View.VISIBLE);
           totalTime.setVisibility(View.VISIBLE);
           totalTimeTable.setVisibility(View.VISIBLE);


       }
        cisip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivm.setCisip(true);
                ArrayAdapter adapter=new ArrayAdapter(Investment.this,android.R.layout.simple_spinner_item,sipandciitem);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                R1.clearCheck();
                    sipCF.setVisibility(View.VISIBLE);
                    sipCFTable.setVisibility(View.VISIBLE);
                totalTime.setVisibility(View.VISIBLE);
                totalTimeTable.setVisibility(View.VISIBLE);

            }
        });
        monthsV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                  String value=monthsV.getText().toString();
                  if(!value.equals(""))
                  {
                      double val=Double.parseDouble(value);
                      if(val>=12)
                      {  val=Double.parseDouble(value.substring(0,value.length()-1));
                         if(val<12)
                         {monthsV.setText(value.substring(0,value.length()-1));
                        monthsV.setSelection(value.length()-1);}
                        else
                         {monthsV.setText("");
                             monthsV.setSelection(0);}

                      }

                  }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        sipMonthsV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String value=sipMonthsV.getText().toString();
                if(!value.equals(""))
                {
                    double val=Double.parseDouble(value);
                    if(val>=12)
                    {
                        val=Double.parseDouble(value.substring(0,value.length()-1));
                        if(val<12){
                            sipMonthsV.setText(value.substring(0,value.length()-1));
                        sipMonthsV.setSelection(value.length()-1);}
                        else {
                            sipMonthsV.setText("");
                            sipMonthsV.setSelection(0);}
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

   spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
       @Override
       public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
           String selected=parent.getSelectedItem().toString();
           if(selected.equalsIgnoreCase("Principal"))
           {   principalV.setEnabled(false);
               yearsV.setEnabled(true);
               monthsV.setEnabled(true);
               sipYearsV.setEnabled(true);
               sipMonthsV.setEnabled(true);
               rateV.setEnabled(true);
               amountV.setEnabled(true);
           }
           else if(selected.equalsIgnoreCase("Time"))
           {principalV.setEnabled(true);
               yearsV.setEnabled(false);
               monthsV.setEnabled(false);
               sipYearsV.setEnabled(false);
               sipMonthsV.setEnabled(false);
               rateV.setEnabled(true);
               amountV.setEnabled(true);

           }
           else if(selected.equalsIgnoreCase("Rate"))
           {principalV.setEnabled(true);
               yearsV.setEnabled(true);
               monthsV.setEnabled(true);
               sipYearsV.setEnabled(true);
               sipMonthsV.setEnabled(true);
               rateV.setEnabled(false);
               amountV.setEnabled(true);

           }
           else if(selected.equalsIgnoreCase("Amount"))
           {principalV.setEnabled(true);
               yearsV.setEnabled(true);
               monthsV.setEnabled(true);
               sipYearsV.setEnabled(true);
               sipMonthsV.setEnabled(true);
               rateV.setEnabled(true);
               amountV.setEnabled(false);

           }
       }

       @Override
       public void onNothingSelected(AdapterView<?> parent) {

       }
   });
   principalV.addTextChangedListener(new TextWatcher() {
       @Override
       public void beforeTextChanged(CharSequence s, int start, int count, int after) {

       }

       @Override
       public void onTextChanged(CharSequence s, int start, int before, int count) {
          String pv=principalV.getText().toString();
          if((!pv.equals(""))&&pv.length()==1&&pv.charAt(0)=='.')
          {principalV.setText(""); principalV.setSelection(0);}
          else if((!pv.equals(""))&&pv.indexOf('.')!=-1)
          { if(pv.length()>(pv.indexOf('.')+3))
          {principalV.setText(pv.substring(0,pv.indexOf('.')+3));
          principalV.setSelection(pv.indexOf('.')+3);}
       }}

       @Override
       public void afterTextChanged(Editable s) {

       }
   });
   rateV.addTextChangedListener(new TextWatcher() {
       @Override
       public void beforeTextChanged(CharSequence s, int start, int count, int after) {

       }

       @Override
       public void onTextChanged(CharSequence s, int start, int before, int count) {
           String pv=rateV.getText().toString();
           if((!pv.equals(""))&&pv.length()==1&&pv.charAt(0)=='.')
           {   pv="";
               rateV.setText(pv);rateV.setSelection(0); }
           else if((!pv.equals(""))&&pv.indexOf('.')!=-1)
           { if(pv.length()>(pv.indexOf('.')+3))
           {rateV.setText(pv.substring(0,pv.indexOf('.')+3));
               rateV.setSelection(pv.indexOf('.')+3);}
           }
           if(!pv.equals(""))
           {
               double value=Double.parseDouble(pv);
               if(value>100)
               {  double ans=value/10;
                if(ans<=100)
                {rateV.setText(""+ans);
                   rateV.setSelection((""+ans).length());}
                else {rateV.setText("");
                    rateV.setSelection(0);}

               }

           }
       }

       @Override
       public void afterTextChanged(Editable s) {

       }
   });
   amountV.addTextChangedListener(new TextWatcher() {
       @Override
       public void beforeTextChanged(CharSequence s, int start, int count, int after) {

       }

       @Override
       public void onTextChanged(CharSequence s, int start, int before, int count) {
           String pv=amountV.getText().toString();
           if((!pv.equals(""))&&pv.length()==1&&pv.charAt(0)=='.')
           {amountV.setText(""); amountV.setSelection(0);}
           else if((!pv.equals(""))&&pv.indexOf('.')!=-1)
           { if(pv.length()>(pv.indexOf('.')+3))
           {amountV.setText(pv.substring(0,pv.indexOf('.')+3));
               amountV.setSelection(pv.indexOf('.')+3);}
           }
       }

       @Override
       public void afterTextChanged(Editable s) {

       }
   });
   submit.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           int cc1 = 0, cc2 = 0, cc3 = 0, cc4 = 0;
           int pp = 0, tt = 0, rr = 0, aa = 0;
           if (si.isChecked())
               cc1 = 1;
           else if (ci.isChecked())
               cc2 = 1;
           else if (sip.isChecked())
               cc3 = 1;
           else if (cisip.isChecked())
               cc4 = 1;

           if (cc1 != 0 || cc2 != 0 || cc3 != 0 || cc4 != 0) {
                String spinnerItem="Principal";
                try{spinnerItem = spinner.getSelectedItem().toString();} catch (Exception e) {
                }
               String principalVV=principalV.getText().toString();
               String yearsVV=yearsV.getText().toString();
               String monthsVV=monthsV.getText().toString();
               String sipYearsVV=sipYearsV.getText().toString();
               String sipMonthsVV=sipMonthsV.getText().toString();
               String rateVV=rateV.getText().toString();
               String amountVV=amountV.getText().toString();
               if(spinnerItem.equalsIgnoreCase("Principal"))
               {    if(cc1==1) {
                   if (!yearsVV.equals("") && !monthsVV.equals("") && !rateVV.equals("") && !amountVV.equals(""))
                   {  double T=toYears(Double.parseDouble(yearsVV),Double.parseDouble(monthsVV));
                      double R=correcting(rateVV);
                      double A=correcting(amountVV);
                      String res=toTwoDigits(BigDecimal.valueOf(principalSI(T,R,A)).toPlainString());
                      if(res.charAt(0)!='N'&&res.charAt(0)!='I')
                      { ivm.setText("Principal Value: Rs."+puttingComma(res));
                      ans.setText(ivm.getText());}
                      else
                      { ivm.setText("Enter with Proper Value");
                          ans.setText(ivm.getText());}
                    }
                   else
                   {ivm.setText("Enter Unfilled Spaces");
                       ans.setText(ivm.getText());}

                  }
                    else if(cc2==1)
                       {     if (!yearsVV.equals("") && !monthsVV.equals("") && !rateVV.equals("") && !amountVV.equals(""))
                       {  double T=toYears(Double.parseDouble(yearsVV),Double.parseDouble(monthsVV));
                           double R=correcting(rateVV);
                           double A=correcting(amountVV);
                           String res=toTwoDigits(BigDecimal.valueOf(principalCI(T,R,A)).toPlainString());
                           if(res.charAt(0)!='N'&&res.charAt(0)!='I')
                           { ivm.setText("Principal Value: Rs."+puttingComma(res));
                               ans.setText(ivm.getText());}
                           else
                           { ivm.setText("Enter with Proper Value");
                               ans.setText(ivm.getText());}
                       }
                       else
                       {ivm.setText("Enter Unfilled Spaces");
                           ans.setText(ivm.getText());}

                       }
                    else if(cc3==1)
                            {  if (!sipYearsVV.equals("") && !sipMonthsVV.equals("") && !rateVV.equals("") && !amountVV.equals(""))
                            {  double n=toMonths(Double.parseDouble(sipYearsVV),Double.parseDouble(sipMonthsVV));
                                double r=toMonthlyRate(correcting(rateVV));
                                double A=correcting(amountVV);
                                String res=toTwoDigits(BigDecimal.valueOf(principalSip(n,r,A)).toPlainString());
                                if(res.charAt(0)!='N'&&res.charAt(0)!='I')
                                { ivm.setText("Monthly Installment: Rs."+puttingComma(res));
                                    ans.setText(ivm.getText());}
                                else
                                { ivm.setText("Enter with Proper Value");
                                    ans.setText(ivm.getText());}
                            }
                            else
                            {ivm.setText("Enter Unfilled Spaces");
                                ans.setText(ivm.getText());}

                            }
                    else if(cc4==1)
                         {   if (!yearsVV.equals("") && !monthsVV.equals("")&&!sipYearsVV.equals("") && !sipMonthsVV.equals("") && !rateVV.equals("") && !amountVV.equals(""))
                         {  double n=toMonths(Double.parseDouble(sipYearsVV),Double.parseDouble(sipMonthsVV));
                             double T=toYears(correcting(yearsVV),correcting(monthsVV));
                             double R=correcting(rateVV);
                             double A=correcting(amountVV);
                             String res=toTwoDigits(BigDecimal.valueOf(principalcisip(n,T,R,A)).toPlainString());
                             if(res.charAt(0)!='N'&&res.charAt(0)!='I')
                             { ivm.setText("Monthly Installment: Rs."+puttingComma(res));
                                 ans.setText(ivm.getText());}
                             else
                             { ivm.setText("Enter with Proper Value");
                                 ans.setText(ivm.getText());}
                         }
                         else
                         {ivm.setText("Enter Unfilled Spaces");
                             ans.setText(ivm.getText());}


                         }


               }
                else  if(spinnerItem.equalsIgnoreCase("Time"))
               {    if(cc1==1) {
                   if (!principalVV.equals("")&& !rateVV.equals("") && !amountVV.equals(""))
                   {   double P=correcting(principalVV);
                       double R=correcting(rateVV);
                       double A=correcting(amountVV);
                       String res=""+timeSI(P,R,A);
                       if(res.charAt(0)!='N'&&res.charAt(0)!='I') {
                           res=toYearMonthFromYear(timeSI(P,R,A));
                           ivm.setText("Total Time: "+res);
                           ans.setText(ivm.getText());
                       }
                       else
                       { ivm.setText("Enter with Proper Value");
                           ans.setText(ivm.getText());}
                   }
                   else
                   {ivm.setText("Enter Unfilled Spaces");
                       ans.setText(ivm.getText());}


               }
               else if(cc2==1)
               {      if (!principalVV.equals("")&& !rateVV.equals("") && !amountVV.equals(""))
               {   double P=correcting(principalVV);
                   double R=correcting(rateVV);
                   double A=correcting(amountVV);
                   String res=""+timeCI(P,R,A);
                   if(res.charAt(0)!='N'&&res.charAt(0)!='I') {
                       res=toYearMonthFromYear(timeCI(P,R,A));
                       ivm.setText("Total Time: "+res);
                       ans.setText(ivm.getText());
                   }
                   else
                   { ivm.setText("Enter with Proper Value");
                       ans.setText(ivm.getText());}
               }
               else
               {ivm.setText("Enter Unfilled Spaces");
                   ans.setText(ivm.getText());}


               }
               else if(cc3==1)
               {   if (!principalVV.equals("")&& !rateVV.equals("") && !amountVV.equals(""))
               {   double P=correcting(principalVV);
                   double r=toMonthlyRate(correcting(rateVV));
                   double A=correcting(amountVV);
                   String res=""+timeSip(P,r,A);
                   if(res.charAt(0)!='N'&&res.charAt(0)!='I'){
                        res=toYearMonthFrommonth(timeSip(P,r,A));
                       ivm.setText("Total Time: "+res);
                       ans.setText(ivm.getText());//months
                        }
                   else
                   { ivm.setText("Enter with Proper Value");
                       ans.setText(ivm.getText());}
               }
               else
               {ivm.setText("Enter Unfilled Spaces");
                   ans.setText(ivm.getText());}


               }
               }
               else  if(spinnerItem.equalsIgnoreCase("Rate"))
               {    if(cc1==1) {
                   if (!principalVV.equals("")&& !yearsVV.equals("") && !monthsVV.equals("")  && !amountVV.equals(""))
                   {   double P=correcting(principalVV);
                       double T=toYears(Double.parseDouble(yearsVV),Double.parseDouble(monthsVV));
                       double A=correcting(amountVV);
                       String res=toTwoDigits(BigDecimal.valueOf(rateSI(P,T,A)).toPlainString());
                       if(res.charAt(0)!='N'&&res.charAt(0)!='I')
                       { ivm.setText("Annual Rate : "+res+"%");
                           ans.setText(ivm.getText());}
                       else
                       { ivm.setText("Enter with Proper Value");
                           ans.setText(ivm.getText());}
                   }
                   else
                   {ivm.setText("Enter Unfilled Spaces");
                       ans.setText(ivm.getText());}

               }
               else if(cc2==1)
               {      if ((!principalVV.equals("")&& !yearsVV.equals("") && !monthsVV.equals("")  && !amountVV.equals("")))
                   {   double P=correcting(principalVV);
                       double T=toYears(Double.parseDouble(yearsVV),Double.parseDouble(monthsVV));
                       double A=correcting(amountVV);
                       String res=toTwoDigits(BigDecimal.valueOf(rateCI(P,T,A)).toPlainString());
                       if(res.charAt(0)!='N'&&res.charAt(0)!='I')
                       {ivm.setText("Annual Rate : "+res+"%");
                           ans.setText(ivm.getText());}
                       else
                       { ivm.setText("Enter with Proper Value");
                           ans.setText(ivm.getText());}
                   }
               else
               {ivm.setText("Enter Unfilled Spaces");
                   ans.setText(ivm.getText());}


               } }
               else  if(spinnerItem.equalsIgnoreCase("Amount"))
               {    if(cc1==1) {
                   if (!principalVV.equals("")&& !yearsVV.equals("") && !monthsVV.equals("") &&!rateVV.equals(""))
                   {   double P=correcting(principalVV);
                       double T=toYears(Double.parseDouble(yearsVV),Double.parseDouble(monthsVV));
                       double R=correcting(rateVV);
                       String res=toTwoDigits(BigDecimal.valueOf(amountSI(P,T,R)).toPlainString());
                       Log.d("Actual", "onClick: "+amountSI(P,T,R));
                       if(res.charAt(0)!='N'&&res.charAt(0)!='I')
                       { ivm.setText("Total Amount: Rs"+puttingComma(res));
                           ans.setText(ivm.getText());}
                       else
                       { ivm.setText("Enter with Proper Value");
                           ans.setText(ivm.getText());}
                   }
                   else
                   {ivm.setText("Enter Unfilled Spaces");
                       ans.setText(ivm.getText());}

               }
               else if(cc2==1)
               {      if (!principalVV.equals("")&& !yearsVV.equals("") && !monthsVV.equals("") &&!rateVV.equals(""))
               {   double P=correcting(principalVV);
                   double T=toYears(Double.parseDouble(yearsVV),Double.parseDouble(monthsVV));
                   double R=correcting(rateVV);
                   String res=toTwoDigits(BigDecimal.valueOf(amountCI(P,T,R)).toPlainString());
                   if(res.charAt(0)!='N'&&res.charAt(0)!='I')
                   { ivm.setText("Total Amount: Rs"+puttingComma(res));
                       ans.setText(ivm.getText());}
                   else
                   { ivm.setText("Enter with Proper Value");
                       ans.setText(ivm.getText());}
               }
               else
               {ivm.setText("Enter Unfilled Spaces");
                   ans.setText(ivm.getText());}


               }
               else if(cc3==1)
               {   if (!principalVV.equals("")&& !sipYearsVV.equals("") && !sipMonthsVV.equals("") &&!rateVV.equals(""))
               {   double P=correcting(principalVV);
                   double n=toMonths(Double.parseDouble(sipYearsVV),Double.parseDouble(sipMonthsVV));
                   double r=toMonthlyRate(correcting(rateVV));
                   String res=toTwoDigits(BigDecimal.valueOf(amountSip(P,n,r)).toPlainString());
                   if(res.charAt(0)!='N'&&res.charAt(0)!='I')
                   { ivm.setText("Total Amount: Rs"+puttingComma(res));
                       ans.setText(ivm.getText());}
                   else
                   { ivm.setText("Enter with Proper Value");
                       ans.setText(ivm.getText());}
               }
               else
               {ivm.setText("Enter Unfilled Spaces");
                   ans.setText(ivm.getText());}
               }
               else if(cc4==1)
               {   if (!principalVV.equals("")&& !yearsVV.equals("") && !monthsVV.equals("")&&!sipYearsVV.equals("") && !sipMonthsVV.equals("")  &&!rateVV.equals(""))
               {   double P=correcting(principalVV);
                   double n=toMonths(Double.parseDouble(sipYearsVV),Double.parseDouble(sipMonthsVV));
                   double T=toYears(correcting(yearsVV),correcting(monthsVV));
                   double R=correcting(rateVV);
                   String res=toTwoDigits(BigDecimal.valueOf(amountcisip(P,n,T,R)).toPlainString());
                   if(res.charAt(0)!='N'&&res.charAt(0)!='I')
                   { ivm.setText("Total Amount: Rs"+puttingComma(res));
                       ans.setText(ivm.getText());}
                   else
                   { ivm.setText("Enter with Proper Value");
                       ans.setText(ivm.getText());}
               }
               else
               {ivm.setText("Enter Unfilled Spaces");
                   ans.setText(ivm.getText());}

               }
               }



           }
            else
                ans.setText("Select The Type of Investment");

       }
   });

    }
}