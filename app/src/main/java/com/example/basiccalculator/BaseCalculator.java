package com.example.basiccalculator;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Arrays;

import static com.example.basiccalculator.BaseConverter.convert;
import static com.example.basiccalculator.MainActivity.calculate;
import static com.example.basiccalculator.MainActivity.incorrect;

public class BaseCalculator extends AppCompatActivity{
    String spin[]={"all","2","3","4","5","6","7","8","9","10","12","13","14","15","16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31","32","33","34","35","36"};
    static boolean err=false;
    public static String addFinishingBrackets(String x)
    {String ans=x;
    int l=x.length(),s=0;
    for(int i=0;i<l;i++)
    {
        char ch=x.charAt(i);
        if(ch=='(')
            s++;
        else if(ch==')')
            s--;
    }
    for(int i=1;i<=s;i++)
        ans+=')';

    return ans;
    }
    public static String removeDecPointAndTrailingZerosIfReq(String x)
    {String y=x;
     if(x.indexOf('.')!=-1)
     {
         int l=x.length();
         int point=x.indexOf('.');
         if(point+2==l&&x.charAt(point+1)=='0')
             y=x.substring(0,point);
         else
         {  for(int i=l-1;i>point;i--)
             if(x.charAt(i)=='0')
                 y=x.substring(0,i);
             else
                 break;

         }
     }
     return y;
    }
public static String[] storeValuesofAnybase(String x)
{  int len=x.length();
    String []val=new String[len];
    Arrays.fill(val,"");
    int count=0;int flag=1;
    for(int i=0;i<len;i++)
    {
        char ch=x.charAt(i);
        if(ch!='+'&&ch!='-'&&ch!='/'&&ch!='*'&&ch!='('&&ch!=')')
        { val[count]+=ch;flag=0;}
        else if(flag==0)
        {count++;flag=1;}
    }
    return val;
}

public static String[] convertValuesToSomeBase(String []val,int ibase,int fbase)
{      String va[]=new String[val.length];
            for(int i=0;i<val.length;i++)
            { va[i]=convert(val[i],ibase,fbase);
            if(va[i].equals("")&&(!val[i].equals("")))
            {err=true;break;}}
            return  va;
}

    public static String convertExpToSomeBase(String original,int ibase,int fbase)
{  String modified="";
         if(ibase!=fbase)
         { String val[]=storeValuesofAnybase(original);
         String va[]=convertValuesToSomeBase(val,ibase,fbase);
         if(err==false){
         int  count=0;
         for(int i=0;i<original.length();i++)
         { char ch=original.charAt(i);
           if(ch=='+'||ch=='-'||ch=='/'||ch=='*'||ch=='('||ch==')')
           modified+=ch;
            else
           {    modified+=removeDecPointAndTrailingZerosIfReq(va[count]);
                i=i+val[count].length()-1;
                count++;
           }
         }}}
         else modified=original;
         return modified;
}
public  static String calculateBase(String S,int base)
{    String decExp=convertExpToSomeBase(S,base,10);
    String finalAns="Error";
    if(err==false){
     double ansInDec=calculate(decExp);
     if(!(String.valueOf(ansInDec).equals("Infinity")||String.valueOf(ansInDec).equals("NaN")))
     {String x=BigDecimal.valueOf(ansInDec).toPlainString();int minus=0;
     if(x.charAt(0)=='-')
     {x=x.substring(1);
     minus=1;}
     finalAns=convert(x,10,base);
     if(minus==1)
     finalAns="-"+finalAns;}}
     return finalAns;

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
                Toast.makeText(BaseCalculator.this, "Sorry,app sharing is not supported", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_layout_basecalculator);
        Button __0= findViewById(R.id.__0);
        Button __1= findViewById(R.id.__1);
        Button __2= findViewById(R.id.__2);
        Button _3= findViewById(R.id.__3);
        Button _4= findViewById(R.id.__4);
        Button _5= findViewById(R.id.__5);
        Button _6= findViewById(R.id.__6);
        Button _7= findViewById(R.id.__7);
        Button _8= findViewById(R.id.__8);
        Button _9= findViewById(R.id.__9);
        Button a= findViewById(R.id.aa);
        Button b= findViewById(R.id.bb);
        Button c= findViewById(R.id.cc);
        Button d= findViewById(R.id.dd);
        Button e= findViewById(R.id.ee);
        Button f= findViewById(R.id.ff);
        Button g= findViewById(R.id.gg);
        Button h= findViewById(R.id.hh);
        Button i= findViewById(R.id.ii);
        Button j= findViewById(R.id.jj);
        Button k= findViewById(R.id.kk);
        Button l= findViewById(R.id.ll);
        Button m= findViewById(R.id.mm);
        Button n= findViewById(R.id.nn);
        Button o= findViewById(R.id.oo);
        Button p= findViewById(R.id.pp);
        Button q= findViewById(R.id.qq);
        Button r= findViewById(R.id.rr);
        Button s= findViewById(R.id.ss);
        Button t= findViewById(R.id.tt);
        Button u= findViewById(R.id.uu);
        Button v= findViewById(R.id.vv);
        Button w= findViewById(R.id.ww);
        Button x= findViewById(R.id.xx);
        Button y= findViewById(R.id.yy);
        Button z= findViewById(R.id.zz);
        EditText Number_value_basecal=findViewById(R.id.number_value_basecal);
        Button submitbasecal=findViewById(R.id.submitbasecal);

        Button plusbasecal=findViewById(R.id.plusbasecal);
        Button minusbasecal=findViewById(R.id.minusbasecal);
        Button multiplybasecal=findViewById(R.id.multiplybasecal);
        Button dividebasecal=findViewById(R.id.dividebasecal);
        Button bracket=findViewById(R.id.bracket);
        Button dott=findViewById(R.id.dott);
        Button clearbasecal=findViewById(R.id.clearbasecal);
        Button delbasecal=findViewById(R.id.delbasecal);
        RadioButton sixteen=findViewById(R.id.sixteen);
        RadioButton ten=findViewById(R.id.ten);
        RadioButton eight=findViewById(R.id.eight);
        RadioButton two=findViewById(R.id.two);
        ScrollView scrollViewBaseCal=findViewById(R.id.scrollViewBaseCal);
        LinearLayout linearLayout=findViewById(R.id.linearlayout);

        Spinner spinner=findViewById(R.id.spinner2);
        final int[] initialBase = {0};


        ArrayAdapter arrayAdapter=new ArrayAdapter(BaseCalculator.this,android.R.layout.simple_spinner_item,spin);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        RadioGroup bases=findViewById(R.id.bases);

        NavigationView nav_view=findViewById(R.id.nav_view);
        DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);
        ImageButton toggle=findViewById(R.id.toggle);

       Number_value_basecal.setShowSoftInputOnFocus(false);

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
                    startActivity(new Intent(BaseCalculator.this,MainActivity.class));
                    finish();
                }
                else if(item.getItemId()==R.id.nav_date)
                {startActivity(new Intent(BaseCalculator.this,Date.class));
                    finish();
                }
                else if(item.getItemId()==R.id.nav_investment)
                {startActivity(new Intent(BaseCalculator.this,Investment.class));
                    finish();
                }
                else if(item.getItemId()==R.id.nav_unitconverter)
                {
                    startActivity(new Intent(BaseCalculator.this,UnitConverter.class));
                    finish();
                }
                else if(item.getItemId()==R.id.nav_about)
                    startActivity(new Intent(BaseCalculator.this,About.class));
                else if(item.getItemId()==R.id.nav_share)
                shareApp();
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });
            Number_value_basecal.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                      String text=Number_value_basecal.getText().toString();
                      if(!text.equals(""))
                      {int len=text.length();
                        if(len>=2)
                        {
                            char ch1=text.charAt(len-2);
                            char ch2=text.charAt(len-1);
                            if(ch1==')'&&((ch2>='0'&&ch2<='9')||(ch2>='A'&&ch2<='Z')))
                            {   text=text.substring(0,len-1);
                                Number_value_basecal.setText(text);
                                Number_value_basecal.setSelection(len-1);
                            }
                        }
                      }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        plusbasecal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exp=Number_value_basecal.getText().toString();
                if(!exp.equals(""))
                {
                       char ch=exp.charAt(exp.length()-1);
                    if(ch!='+'&&ch!='-'&&ch!='/'&&ch!='*'&&ch!='.'&&ch!='(')
                       {
                           Number_value_basecal.setText(exp+"+");
                           Number_value_basecal.setSelection(exp.length()+1);
                       }
                }
            }
        });

        minusbasecal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exp=Number_value_basecal.getText().toString();
                if(!exp.equals(""))
                {
                    char ch=exp.charAt(exp.length()-1);
                    if(ch!='+'&&ch!='-'&&ch!='/'&&ch!='*'&&ch!='.')
                    {
                        Number_value_basecal.setText(exp+"-");
                        Number_value_basecal.setSelection(exp.length()+1);
                    }
                }
                else {
                    Number_value_basecal.setText("-");
                    Number_value_basecal.setSelection(1);
                }
            }
        });
        multiplybasecal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exp=Number_value_basecal.getText().toString();
                if(!exp.equals(""))
                {
                    char ch=exp.charAt(exp.length()-1);
                    if(ch!='+'&&ch!='-'&&ch!='/'&&ch!='*'&&ch!='.'&&ch!='(')
                    {
                        Number_value_basecal.setText(exp+"*");
                        Number_value_basecal.setSelection(exp.length()+1);
                    }
                }
            }
        });
        dividebasecal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exp=Number_value_basecal.getText().toString();
                if(!exp.equals(""))
                {
                    char ch=exp.charAt(exp.length()-1);
                    if(ch!='+'&&ch!='-'&&ch!='/'&&ch!='*'&&ch!='.'&&ch!='(')
                    {
                        Number_value_basecal.setText(exp+"/");
                        Number_value_basecal.setSelection(exp.length()+1);
                    }
                }
            }
        });
        bracket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(initialBase[0]!=0){
                String exp=Number_value_basecal.getText().toString();
                if(!exp.equals(""))
                { if(exp.charAt(0)!='.'){
                    int s=0;int len=exp.length();
                    for(int i=0;i<len;i++)
                    {   char cc=exp.charAt(i);
                        if(cc=='(')
                            s++;
                        else if(cc==')')
                            s--;
                    }
                    char ch=exp.charAt(exp.length()-1);
                    if(ch=='+'||ch=='-'||ch=='/'||ch=='*'||ch=='('||ch==')')
                    {
                      if(s==0&&exp.charAt(len-1)==')')
                      {Number_value_basecal.setText(exp+"*(");
                          Number_value_basecal.setSelection(exp.length()+2);

                      }
                      else if(s==0)
                      {Number_value_basecal.setText(exp+"(");
                          Number_value_basecal.setSelection(exp.length()+1);

                      }
                      else if((s>0)&&(ch=='+'||ch=='-'||ch=='/'||ch=='*')){
                          Number_value_basecal.setText(exp+"(");
                          Number_value_basecal.setSelection(exp.length()+1);

                      }
                      else if(ch!='('){
                          Number_value_basecal.setText(exp+")");
                          Number_value_basecal.setSelection(exp.length()+1);

                      }
                      else if(ch=='('){ Number_value_basecal.setText(exp+"(");
                          Number_value_basecal.setSelection(exp.length()+1);

                      }
                    }
                    else if(s>0&&exp.charAt(exp.length()-1)!='.')
                    {Number_value_basecal.setText(exp+")");
                        Number_value_basecal.setSelection(exp.length()+1);
                    }
                    else if(exp.charAt(exp.length()-1)!='.')
                    {   Number_value_basecal.setText(exp+"*(");
                        Number_value_basecal.setSelection(exp.length()+2);
                    }
                }}
                else
                {
                    Number_value_basecal.setText("(");
                    Number_value_basecal.setSelection(1);
                }
            }}
        });
        delbasecal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exp=Number_value_basecal.getText().toString();
                if(!exp.equals(""))
                {
                    exp=exp.substring(0,exp.length()-1);
                    Number_value_basecal.setText(exp);
                    Number_value_basecal.setSelection(exp.length());
                }
            }
        });
        delbasecal.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Number_value_basecal.setText("");
                Number_value_basecal.setSelection(0);
                return true;
            }
        });
        SharedPreferences sharedPreferences=getSharedPreferences("BaseCalculator",MODE_PRIVATE);
       final int id[]={0};
        while(sharedPreferences.contains(""+id[0]))
        {   TextView tv=new TextView(BaseCalculator.this);
            tv.setTextIsSelectable(true);

           if(sharedPreferences.contains(""+(id[0]+1)))
           {tv.setTextColor(ContextCompat.getColor(BaseCalculator.this, R.color.blackinvest));
            tv.setTextSize(16);
           }
           else
           {tv.setTextSize(20);
               tv.setTextColor(getResources().getColor(R.color.lightsky));
           }
           tv.setText(sharedPreferences.getString(""+id[0],""));
           linearLayout.addView(tv);
              id[0]++;
        }
        if(linearLayout.getBottom()!=-1)
        scrollViewBaseCal.post(new Runnable() {
            @Override
            public void run() {
                scrollViewBaseCal.scrollTo(0,linearLayout.getBottom());
            }
        });
        final int[] spId = {linearLayout.getChildCount()};
        clearbasecal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linearLayout.removeAllViews();
                SharedPreferences.Editor edit=sharedPreferences.edit();
                edit.clear();
                edit.apply();
                spId[0]=0;
                Number_value_basecal.setText("");
            }
        });


        submitbasecal.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.Q)
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences=getSharedPreferences("BaseCalculator",MODE_PRIVATE);
                SharedPreferences.Editor edit=sharedPreferences.edit();

                for (int i = 0; i <linearLayout.getChildCount(); i++) {
                    View vv = linearLayout.getChildAt(i);
                    if (vv instanceof TextView) {
                        ((TextView) vv).setTextColor(ContextCompat.getColor(BaseCalculator.this, R.color.blackinvest));
                        ((TextView) vv).setTextSize(16);
                    }
                }
                TextView tv=new TextView(BaseCalculator.this);
                tv.setTextSize(20);
                tv.setTextColor(getResources().getColor(R.color.lightsky));
                tv.setTextIsSelectable(true);

                if(initialBase[0]!=0){
                    {  String expInBase=Number_value_basecal.getText().toString();
                    incorrect=false;err=false;
                    String x=calculateBase(expInBase,initialBase[0]);
                        String finalExp=removeDecPointAndTrailingZerosIfReq(calculateBase(expInBase,initialBase[0]));
                        if(incorrect==false&&!finalExp.equals(""))
                        {  if(!finalExp.equals("Error"))
                        { if(linearLayout.getChildCount()!=0)
                            tv.setText("\n"+addFinishingBrackets(expInBase)+"\n="+finalExp+" base("+initialBase[0]+")");
                            else
                                tv.setText(addFinishingBrackets(expInBase)+"\n="+finalExp+" base("+initialBase[0]+")");}
                        else
                            tv.setText(finalExp);
                                if(!finalExp.equals("Error"))
                                {  if(linearLayout.getChildCount()!=0)
                                    edit.putString("" + spId[0],"\n"+addFinishingBrackets(expInBase)+"\n="+finalExp+" base("+initialBase[0]+")");
                               else edit.putString("" + spId[0],addFinishingBrackets(expInBase)+"\n="+finalExp+" base("+initialBase[0]+")");

                                    edit.apply();
                                spId[0]++;}

                        err=false;}
                        else
                        {  if(linearLayout.getChildCount()!=0)
                            tv.setText("\nError");
                        else tv.setText("Error");
                        incorrect=false;err=false;}

                        linearLayout.addView(tv);
                        scrollViewBaseCal.post(new Runnable() {
                            @Override
                            public void run() {
                                scrollViewBaseCal.scrollTo(0,linearLayout.getBottom());
                            }
                        });
                    }

                }else {
                    if(linearLayout.getChildCount()!=0)
                    tv.setText("\nSelect Base");
                    else
                        tv.setText("Select Base");
                    linearLayout.addView(tv);
                    incorrect=false;err=false;
                    scrollViewBaseCal.post(new Runnable() {
                        @Override
                        public void run() {
                            scrollViewBaseCal.scrollTo(0,linearLayout.getBottom());
                        }
                    });

                }
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String x=parent.getSelectedItem().toString();
                int a=0;
                if(!x.equals("all"))
                    a=Integer.valueOf(x);
                if(a>1)
                {    int ibase=initialBase[0];
                    initialBase[0]=a;
                    bases.clearCheck();
                    String value=Number_value_basecal.getText().toString();
                    if(!value.equals("")) {
                        incorrect=false;err=false;
                        value=convertExpToSomeBase(value, ibase, initialBase[0]);
                        Number_value_basecal.setText(value);
                        Number_value_basecal.setSelection(value.length());  }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sixteen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setSelection(0);
                int ibase=initialBase[0];
                initialBase[0]=16;
                String value=Number_value_basecal.getText().toString();
                if(!value.equals("")) {
                    incorrect=false;err=false;
                    value=convertExpToSomeBase(value, ibase, initialBase[0]);
                    Number_value_basecal.setText(value);
                    Number_value_basecal.setSelection(value.length());

                }
            }
        });
        ten.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setSelection(0);
                int ibase=initialBase[0];
                initialBase[0]=10;
                String value=Number_value_basecal.getText().toString();
                if(!value.equals(""))
                {incorrect=false;err=false;
                    value=convertExpToSomeBase(value, ibase, initialBase[0]);
                    Number_value_basecal.setText(value);
                    Number_value_basecal.setSelection(value.length());

                }
            }
        });
        eight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setSelection(0);
                int ibase=initialBase[0];
                initialBase[0]=8;
                String value=Number_value_basecal.getText().toString();
                if(!value.equals(""))
                {incorrect=false;err=false;
                    value=convertExpToSomeBase(value, ibase, initialBase[0]);
                    Number_value_basecal.setText(value);
                    Number_value_basecal.setSelection(value.length());

                }
            }
        });
        two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setSelection(0);
                int ibase=initialBase[0];
                initialBase[0]=2;
                String value=Number_value_basecal.getText().toString();
                if(!value.equals(""))
                {incorrect=false;err=false;
                    value=convertExpToSomeBase(value, ibase, initialBase[0]);
                    Number_value_basecal.setText(value);
                    Number_value_basecal.setSelection(value.length());

                }
            }
        });
        dott.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x=Number_value_basecal.getText().toString();
                int l=x.length();int dotP=0;int i=0;
                  if(l>0)
                  {  for(i=l-1;i>=0;i--)
                  {
                      char cc=x.charAt(i);
                      if(cc=='.')
                          dotP++;
                      if(cc=='+'||cc=='-'||cc=='*'||cc=='/'||cc=='('||cc==')')
                          break;
                  }
                        if(dotP==0&&i!=l-1) {
                            Number_value_basecal.setText(Number_value_basecal.getText().toString() + dott.getText().toString());
                            Number_value_basecal.setSelection(Number_value_basecal.getText().length());
                        }
                    }}

        });

        __0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {   if (0 < initialBase[0]) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + __0.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());
                }
                }
            }
        });
        __1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {   if (1 < initialBase[0]) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + __1.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());
                }
                }
            }
        });
        __2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {   if (2 < initialBase[0]) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + __2.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());
                }
                }
            }
        });
        _3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {   if (3 < initialBase[0]) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + _3.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());
                }
                }
            }
        });
        _4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {   if (4 < initialBase[0]) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + _4.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());
                }
                }
            }
        });
        _5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {   if (5 < initialBase[0]) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + _5.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());
                }
                }
            }
        });
        _6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {   if (6 < initialBase[0]) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + _6.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());
                }
                }
            }
        });
        _7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {   if (7 < initialBase[0]) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + _7.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());
                }
                }
            }
        });
        _8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {   if (8 < initialBase[0]) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + _8.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());
                }
                }
            }
        });
        _9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {   if (9 < initialBase[0]) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + _9.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());
                }
                }
            }
        });


        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {   if ('A' < (55 + initialBase[0])) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + a.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());
                }
                }
            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                { if ('B' < (55 + initialBase[0])) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + b.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());

                }
                }}
        });
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                { if ('C' < (55 + initialBase[0])) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + c.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());

                }}
            }
        });
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {if ('D' < (55 + initialBase[0])) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + d.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());
                }
                }}
        });
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                { if ('E' < (55 + initialBase[0])) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + e.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());

                }}
            }
        });
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {if ('F' < (55 + initialBase[0])) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + f.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());

                }}
            }
        });
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {if ('G' < (55 + initialBase[0])) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + g.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());

                }}
            }
        });
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    if ('H' < (55 + initialBase[0])) {
                        Number_value_basecal.setText(Number_value_basecal.getText().toString() + h.getText().toString());
                        Number_value_basecal.setSelection(Number_value_basecal.getText().length());

                    }
                } }
        });
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                { if ('I' < (55 + initialBase[0])) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + i.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());

                }}
            }
        });
        j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                { if ('J' < (55 + initialBase[0])) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + j.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());

                }}
            }
        });
        k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                { if ('K' < (55 + initialBase[0])) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + k.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());

                }}
            }
        });
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                { if ('L' < (55 + initialBase[0])) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + l.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());

                }}
            }
        });
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {if ('M' < (55 + initialBase[0])) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + m.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());

                }}
            }
        });
        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                { if ('N' < (55 + initialBase[0])) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + n.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());

                }
                }}});
        o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {if ('O' < (55 + initialBase[0])) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + o.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());

                }}
            }
        });
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {if ('P' < (55 + initialBase[0])) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + p.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());

                }}
            }
        });
        q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {  if ('Q' < (55 + initialBase[0])) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + q.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());

                }}
            }
        });
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {if ('R' < (55 + initialBase[0])) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + r.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());
                }
                }}
        });
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {if ('S' < (55 + initialBase[0])) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + s.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());

                }}
            }
        });
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                { if ('T' < (55 + initialBase[0])) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + t.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());

                }}
            }
        });
        u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                { if ('U' < (55 + initialBase[0])) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + u.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());

                }}
            }
        });
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {if ('V' < (55 + initialBase[0])) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + "V");
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());

                }}
            }
        });
        w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {if ('W' < (55 + initialBase[0])) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + w.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());

                }}
            }
        });
        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                { if ('X' < (55 + initialBase[0])) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + x.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());

                }}
            }
        });
        y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {if ('Y' < (55 + initialBase[0])) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + y.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());

                }}
            }
        });
        z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {if ('Z' < (55 + initialBase[0])) {
                    Number_value_basecal.setText(Number_value_basecal.getText().toString() + z.getText().toString());
                    Number_value_basecal.setSelection(Number_value_basecal.getText().length());

                }}
            }
        });


    }
}