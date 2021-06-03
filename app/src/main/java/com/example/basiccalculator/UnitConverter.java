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
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
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

import static com.example.basiccalculator.BaseConverter.convert;

public class UnitConverter extends AppCompatActivity {
  String []length={"LightYears","Mile","Kilometre","metre","Yard","Foot","decimetre","inches","centimetre","milimetre","micromtre","nanometre","Angstrom","picometre","femtometre"};
   public static String convertEandAddOratBegi(double value)
  { String Ans="";
    String val=""+value;
     if(!val.equals(""))
     {   if(val.indexOf('E')!=-1)
     {  String f=val.substring(0,val.indexOf('E'));
         if(f.indexOf('.')!=-1)
         { String g=val.substring(f.indexOf('.')+1,val.indexOf('E'));
            if(g.length()>4) {
                g = g.substring(0, 4);
                f=""+Double.parseDouble(f.substring(0,f.indexOf('.')+1)+g);

            }
         }
         Ans=" or "+f+"x10^("+val.substring(val.indexOf('E')+1)+") ";}
     }
      return Ans;
  }
  public static double toMetre(int from,double value)
    { double inMetre=0;
       if(from==0)
        inMetre=value*9460730472580800.0;
       else if(from==1)
           inMetre=value*1609.344;
       else if(from==2)
             inMetre=value*Math.pow(10,3);
       else if(from==3)
           inMetre=value*Math.pow(10,0);
       else if(from==4)
        inMetre=value*0.9144;
         else if(from==5)
             inMetre=value/3.2808399;
         else if(from==6)
             inMetre=value*Math.pow(10,-1);
         else if(from==7)
             inMetre=value/39.37;
         else if(from==8)
             inMetre=value*Math.pow(10,-2);
         else if(from==9)
             inMetre=value*Math.pow(10,-3);
         else if(from==10)
             inMetre=value*Math.pow(10,-6);
         else if(from==11)
             inMetre=value*Math.pow(10,-9);
         else if(from==12)
             inMetre=value*Math.pow(10,-10);
         else if(from==13)
             inMetre=value*Math.pow(10,-12);
         else if(from==14)
             inMetre=value*Math.pow(10,-15);
    return inMetre;
    }
    public static double frommMetre(int to,double value)
    {  double inSomeLength=0;
        if(to==0)
            inSomeLength=value/9460730472580800.0;
        else if(to==1)
            inSomeLength=value/1609.344;
        else if(to==2)
            inSomeLength=value*Math.pow(10,-3);
        else if(to==3)
            inSomeLength=value*Math.pow(10,0);
        else if(to==4)
        inSomeLength=value/0.9144;
        else if(to==5)
            inSomeLength=value*3.2808399;
        else if(to==6)
            inSomeLength=value*Math.pow(10,1);
        else if(to==7)
            inSomeLength=value*39.37;
        else if(to==8)
            inSomeLength=value*Math.pow(10,2);
        else if(to==9)
            inSomeLength=value*Math.pow(10,3);
        else if(to==10)
            inSomeLength=value*Math.pow(10,6);
        else if(to==11)
            inSomeLength=value*Math.pow(10,9);
        else if(to==12)
            inSomeLength=value*Math.pow(10,10);
        else if(to==13)
            inSomeLength=value*Math.pow(10,12);
        else if(to==14)
            inSomeLength=value*Math.pow(10,15);
     return inSomeLength;
    }
    public static double convertL(double value,int from,int to)
    { double result=value;
    if(from!=to) {
        result=toMetre(from,value);
        result=frommMetre(to,result);
    }
        return result;
    }
    String []area={"Sq. Mile","Sq. Kilometre","Hectare","Acre","Bigha","Sq. metre","Sq. Yard","Sq. Foot","Sq. inches","Sq. centimetre","Sq. milimetre"};

    public static double toMetre2(int from,double value)
    { double inMetre2=0;
        if(from==0)
            inMetre2=value*2589988.110336;
        else if(from==1)
            inMetre2=value*1000000;
        else if(from==2)
            inMetre2=value*10000;
        else if(from==3)
            inMetre2=value*4046.86;
        else if(from==4)
            inMetre2=value*1337.8;
        else if(from==5)
            inMetre2=value;
        else if(from==6)
            inMetre2=value*0.83612736;
        else if(from==7)
            inMetre2=value*0.09290304;
        else if(from==8)
            inMetre2=value*0.00064516;
        else if(from==9)
            inMetre2=value*0.0001;
        else if(from==10)
            inMetre2=value*0.000001;
        return inMetre2;

    }
    public static double fromMetre2(int to,double value)
    { double inSomeArea=0;
      if(to==0)
          inSomeArea=value/2589988.110336;
      else if(to==1)
          inSomeArea=value/1000000;
      else if(to==2)
          inSomeArea=value/10000;
      else if(to==3)
          inSomeArea=value/4046.86;
      else if(to==4)
          inSomeArea=value/1337.8;
      else if(to==5)
          inSomeArea=value;
      else if(to==6)
          inSomeArea=value/0.83612736;
      else if(to==7)
          inSomeArea=value/0.09290304;
      else if(to==8)
          inSomeArea=value/0.00064516;
      else if(to==9)
          inSomeArea=value/0.0001;
      else if(to==10)
          inSomeArea=value/0.000001;

      return inSomeArea;

    }
    public static double convertA(double value,int from,int to)
    { double result=value;
     if(from!=to)
      { result=toMetre2(from,value);
        result=fromMetre2(to,result);
      }
       return result;
    }
    String volume[]={"Cu. metre","Cu. Yard","Cu. Foot","Gallon","Litre(or dm^3)","Quart","Cup","Cu. inches","tablespoon","teaspoon","Mililitre(or cc)","Cu. milimetre"};
   public static double toLitre(int from,double value)
   { double inLitre=0;
    if(from==0)
        inLitre=value*1000;
    else if(from==1)
        inLitre=value*764.5549;
    else if(from==2)
        inLitre=value*28.316846592;
    else if(from==3)
        inLitre=value*3.785411784;
    else if(from==4)
        inLitre=value;
    else if(from==5)
        inLitre=value*0.946352946;
    else if(from==6)
        inLitre=value*0.236588;
    else if(from==7)
        inLitre=value*0.016387064;
    else if(from==8)
        inLitre=value*0.01478676478125;
    else if(from==9)
        inLitre=value*0.00492892;
    else if(from==10)
        inLitre=value*0.001;
    else if(from==11)
        inLitre=value*0.000001;
                return inLitre;

   }
   public static double fromLitre(int to,double value)
   { double inSomeVolume=0;
       if(to==0)
           inSomeVolume=value/1000;
       else if(to==1)
           inSomeVolume=value/764.5549;
       else if(to==2)
           inSomeVolume=value/28.316846592;
       else if(to==3)
           inSomeVolume=value/3.785411784;
       else if(to==4)
           inSomeVolume=value;
       else if(to==5)
           inSomeVolume=value/0.946352946;
       else if(to==6)
           inSomeVolume=value/0.236588;
       else if(to==7)
           inSomeVolume=value/0.016387064;
       else if(to==8)
           inSomeVolume=value/0.01478676478125;
       else if(to==9)
           inSomeVolume=value/0.00492892;
       else if(to==10)
           inSomeVolume=value/0.001;
       else if(to==11)
           inSomeVolume=value/0.000001;
       return inSomeVolume;
   }
    public static double convertV(double value,int from,int to)
    { double result=value;
     if(from!=to)
     {
         result=toLitre(from,value);
         result=fromLitre(to,result);
     }
     return result;
    }
String pressure[]={"Std. Atmos.","Bar","Kgf/cm^2","Decibar","Torr(or mmHg)","Pascal(Pa or N/m^2)","mTorr","dyne/cm^2"};
   public static double toTorr(int from,double value)
   { double inTorr=0;
       if(from==0)
           inTorr=value*760;
       else if(from==1)
           inTorr=value*750.0616828226103;
       else if(from==2)
           inTorr=value*735.5592401852354;
       else if(from==3)
           inTorr=value*75.00616828226103;
       else if(from==4)
           inTorr=value;
       else if(from==5)
           inTorr=value*(750.0616828226103/100000);
       else if(from==6)
           inTorr=value*0.001;
       else if(from==7)
           inTorr=value*(750.0616828226103/1000000);
   return inTorr;
   }
   public static double fromTorr(int to,double value)
   { double inSomePressure=0;
       if(to==0)
           inSomePressure=value/760;
       else if(to==1)
           inSomePressure=value/750.0616828226103;
       else if(to==2)
           inSomePressure=value/735.5592401852354;
       else if(to==3)
           inSomePressure=value/75.00616828226103;
       else if(to==4)
           inSomePressure=value;
       else if(to==5)
           inSomePressure=value/(750.0616828226103/100000);
       else if(to==6)
           inSomePressure=value/0.001;
       else if(to==7)
           inSomePressure=value/(750.0616828226103/1000000);
   return inSomePressure;
   }
   public static double convertP(double value,int from,int to)
   { double result=value;
       if(from!=to)
       {
           result=toTorr(from,value);
           result=fromTorr(to,result);
       }
       return result;
   }
   String density[]={"Kg/mL or kg/cm^3","Kg/L or Kg/dm^3","Kg/m^3","g/mL or g/cm^3","g/L or g/dm^3","g/m^3"};//kg/L=g/mL and kg/m^3=g/L
  public static double toGrampml(int from,double value)
  { double inGpml=0;
     if(from==0)
         inGpml=value*1000;
     else if(from==1)
         inGpml=value;
     else if(from==2)
         inGpml=value*0.001;
     else if(from==3)
         inGpml=value;
     else if(from==4)
         inGpml=value*0.001;
     else if(from==5)
         inGpml=value*0.000001;
  return inGpml;
  }public static double fromGrampml(int to,double value)
    { double inSomeDensity=0;
        if(to==0)
            inSomeDensity=value/1000;
        else if(to==1)
            inSomeDensity=value;
        else if(to==2)
            inSomeDensity=value/0.001;
        else if(to==3)
            inSomeDensity=value;
        else if(to==4)
            inSomeDensity=value/0.001;
        else if(to==5)
            inSomeDensity=value/0.000001;
        return inSomeDensity;
    }
   public static double convertD(double value,int from,int to)
   { double result=value;
       if(from!=to)
       {
           result=toGrampml(from,value);
           result=fromGrampml(to,result);
       }
       return result;
   }
   String speed[]={"Metre/second","Knot","Miles/second","foot/hour","Kilometre/hour"};
  public static double toMilesps(int from,double value)
  { double inMilesps=0;
    if(from==0)
        inMilesps=value*2.23693629205440;
    else if(from==1)
        inMilesps=value*1.150779447029349;
    else if(from==2)
        inMilesps=value;
    else if(from==3)
        inMilesps=value*0.68181818181818;
    else if(from==4)
        inMilesps=value*(2.23693629205440/3.6);
      return inMilesps;
  }
  public static double fromMilesps(int to,double value)
  { double inSomeSpeed=0;
      if(to==0)
          inSomeSpeed=value/2.23693629205440;
      else if(to==1)
          inSomeSpeed=value/1.150779447029349;
      else if(to==2)
          inSomeSpeed=value;
      else if(to==3)
          inSomeSpeed=value/0.68181818181818;
      else if(to==4)
          inSomeSpeed=value/(2.23693629205440/3.6);
      return inSomeSpeed;
  }
  public static double convertS(double value,int from,int to)
  { double result=value;
      if(from!=to)
      {
          result=toMilesps(from,value);
          result=fromMilesps(to,result);
      }
      return result;
  }
  String temp[]={"Celsius","Kelvin","Fahrenheit"};
  public static double convertTemp(double value,int from,int to)
  {double result=value;
      if(from!=to)
      { if(from==0&&to==1)
        result=value+273;
        else if(from==0&&to==2)
            result=(value*9/5)+32;
        else if(from==1&&to==0)
            result=value-273;
      else if(from==1&&to==2)
          result=((value-273)*9/5)+32;
      else if(from==2&&to==0)
          result=(value-32)*5/9;
      else if(from==2&&to==1)
          result=(value-32)*5/9+273;
      }
      return result;
  }
  String weight[]={"Tonne","Kilogram","Pound","Ounce","gram","miligram","microgram"};
    public static double toGram(int from,double value)
    { double inGram=0;
    if(from==0)
        inGram=value*1000000;
    else if(from==1)
        inGram=value*1000;
    else if(from==2)
        inGram=value*453.59237;
    else if(from==3)
        inGram=value*28.349523125;
    else if(from==4)
        inGram=value;
    else if(from==5)
        inGram=value*0.001;
    else if(from==6)
        inGram=value*0.000001;
    return inGram;
    }
    public static double fromGram(int to,double value)
    { double inSomeWeight=0;
        if(to==0)
            inSomeWeight=value/1000000;
        else if(to==1)
            inSomeWeight=value/1000;
        else if(to==2)
            inSomeWeight=value/453.59237;
        else if(to==3)
            inSomeWeight=value/28.349523125;
        else if(to==4)
            inSomeWeight=value;
        else if(to==5)
            inSomeWeight=value/0.001;
        else if(to==6)
            inSomeWeight=value/0.000001;
        return inSomeWeight;
    }
    public static double convertW(double value,int from,int to)
    { double result=value;
        if(from!=to)
        {
            result=toGram(from,value);
            result=fromGram(to,result);
        }
        return result;
    }
    String time[]={"Days","Hours","Minutes","Seconds"};
    public static double convertTime(double value,int from,int to)
    {double result=value;
        if(from!=to)
        { if(from==0&&to==1)
            result=value*24;
        else if(from==0&&to==2)
            result=value*24*60;
        else if(from==0&&to==3)
            result=value*24*60*60;
        else if(from==1&&to==0)
            result=value/24;
        else if(from==1&&to==2)
            result=value*60;
        else if(from==1&&to==3)
            result=value*60*60;
        else if(from==2&&to==0)
            result=value/(24*60);
        else if(from==2&&to==1)
            result=value/60;
        else if(from==2&&to==3)
            result=value*60;
        else if(from==3&&to==0)
            result=value/(24*60*60);
        else if(from==3&&to==1)
            result=value/(60*60);
        else if(from==3&&to==2)
            result=value/60;

        }
        return result;
    }
    String base[]={"Hexadecimal","Decimal","Octal","Binary"};
    public static String convertB(String value,int from,int to)
    { String minus="";
    if(value.charAt(0)=='-')
    {minus="-";value=value.substring(1);}

        String result=value;
    if(from!=to)
        result=convert(value,from,to);
        return minus+result;
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
                Toast.makeText(UnitConverter.this, "Sorry,app sharing is not supported", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_layout_unitconverter);

        ScrollView sv=findViewById(R.id.scrollViewunit);
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
                    startActivity(new Intent(UnitConverter.this,MainActivity.class));
                    finish();
                }
                else if(item.getItemId()==R.id.nav_basecaculator)
                {
                    startActivity(new Intent(UnitConverter.this,BaseCalculator.class));
                    finish();
                }
                else if(item.getItemId()==R.id.nav_date)
                {
                    startActivity(new Intent(UnitConverter.this,Date.class));
                    finish();
                }
                else if(item.getItemId()==R.id.nav_investment)
                {
                    startActivity(new Intent(UnitConverter.this,Investment.class));
                    finish();
                }
                else if(item.getItemId()==R.id.nav_about)
                 startActivity(new Intent(UnitConverter.this,About.class));
                else if(item.getItemId()==R.id.nav_share)
                    shareApp();
                drawerLayout.closeDrawer(GravityCompat.START);


                return true;
            }
        });
        EditText numberL=findViewById(R.id.Numberl);
        Button showl=findViewById(R.id.showl);
        showl.setVisibility(View.GONE);
        LinearLayout lll=findViewById(R.id.lll);
        lll.setVisibility(View.GONE);
      TextView lylans=findViewById(R.id.lightYearslengthans);
      TextView milans=findViewById(R.id.mileslengthans);
        TextView klans=findViewById(R.id.Kilolengthans);
      TextView mlans=findViewById(R.id.metrelans);
      TextView ylans=findViewById(R.id.yardlans);
      TextView footlans=findViewById(R.id.footlans);
      TextView dlans=findViewById(R.id.decilans);
      TextView ilans=findViewById(R.id.inchlans);
      TextView clans=findViewById(R.id.centilans);
      TextView mmlans=findViewById(R.id.mililans);
      TextView meulans=findViewById(R.id.microlans);
      TextView nlans=findViewById(R.id.nanolans);
      TextView anglans=findViewById(R.id.angstromlans);
      TextView plans=findViewById(R.id.picolans);
      TextView fmlans=findViewById(R.id.femtolans);
        Spinner spinnerl=findViewById(R.id.spinnerl);
        int[] spinnerlpos = {0};
        ArrayAdapter arrayAdapterl=new ArrayAdapter(UnitConverter.this, android.R.layout.simple_spinner_item,length);
        arrayAdapterl.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerl.setAdapter(arrayAdapterl);
        spinnerl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerlpos[0] =position;
                numberL.setText(numberL.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        numberL.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                 String x=numberL.getText().toString();
                 if(x.equals("")||x.equals("."))
                 {lll.setVisibility(View.GONE);showl.setVisibility(View.GONE);}
                 else
                 { lll.setVisibility(View.VISIBLE);
                   showl.setVisibility(View.VISIBLE);
                   showl.setBackgroundColor(Color.RED);
                   showl.setText("Hide");
                   showl.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                           if(showl.getText().toString().equals("Hide"))
                           { showl.setText("Show");
                               showl.setBackgroundColor(Color.GREEN);
                             lll.setVisibility(View.GONE);
                           }
                           else
                           {showl.setText("Hide");
                               showl.setBackgroundColor(Color.RED);
                            lll.setVisibility(View.VISIBLE);
                           }
                       }
                   });
                  if(showl.getText().toString().equals("Hide"))
                  {double value=Double.parseDouble(x);
                      lylans.setText(BigDecimal.valueOf(convertL(value,spinnerlpos[0],0)).toPlainString()+convertEandAddOratBegi(convertL(value,spinnerlpos[0],0)));
                      milans.setText(BigDecimal.valueOf(convertL(value,spinnerlpos[0],1)).toPlainString()+convertEandAddOratBegi(convertL(value,spinnerlpos[0],1)));
                      klans.setText(BigDecimal.valueOf(convertL(value,spinnerlpos[0],2)).toPlainString()+convertEandAddOratBegi(convertL(value,spinnerlpos[0],2)));
                     mlans.setText(BigDecimal.valueOf(convertL(value,spinnerlpos[0],3)).toPlainString()+convertEandAddOratBegi(convertL(value,spinnerlpos[0],3)));
                      ylans.setText(BigDecimal.valueOf(convertL(value,spinnerlpos[0],4)).toPlainString()+convertEandAddOratBegi(convertL(value,spinnerlpos[0],4)));
                      footlans.setText(BigDecimal.valueOf(convertL(value,spinnerlpos[0],5)).toPlainString()+convertEandAddOratBegi(convertL(value,spinnerlpos[0],5)));
                     dlans.setText(BigDecimal.valueOf(convertL(value,spinnerlpos[0],6)).toPlainString()+convertEandAddOratBegi(convertL(value,spinnerlpos[0],6)));
                     ilans.setText(BigDecimal.valueOf(convertL(value,spinnerlpos[0],7)).toPlainString()+convertEandAddOratBegi(convertL(value,spinnerlpos[0],7)));
                     clans.setText(BigDecimal.valueOf(convertL(value,spinnerlpos[0],8)).toPlainString()+convertEandAddOratBegi(convertL(value,spinnerlpos[0],8)));
                     mmlans.setText(BigDecimal.valueOf(convertL(value,spinnerlpos[0],9)).toPlainString()+convertEandAddOratBegi(convertL(value,spinnerlpos[0],9)));
                     meulans.setText(BigDecimal.valueOf(convertL(value,spinnerlpos[0],10)).toPlainString()+convertEandAddOratBegi(convertL(value,spinnerlpos[0],10)));
                     nlans.setText(BigDecimal.valueOf(convertL(value,spinnerlpos[0],11)).toPlainString()+convertEandAddOratBegi(convertL(value,spinnerlpos[0],11)));
                     anglans.setText(BigDecimal.valueOf(convertL(value,spinnerlpos[0],12)).toPlainString()+convertEandAddOratBegi(convertL(value,spinnerlpos[0],12)));
                     plans.setText(BigDecimal.valueOf(convertL(value,spinnerlpos[0],13)).toPlainString()+convertEandAddOratBegi(convertL(value,spinnerlpos[0],13)));
                     fmlans.setText(BigDecimal.valueOf(convertL(value,spinnerlpos[0],14)).toPlainString()+convertEandAddOratBegi(convertL(value,spinnerlpos[0],14)));
                 }}
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
      EditText numberA=findViewById(R.id.Numbera);
      Button showa=findViewById(R.id.showa);
      showa.setVisibility(View.GONE);
      LinearLayout lla=findViewById(R.id.lla);
      lla.setVisibility(View.GONE);
      TextView mileAans=findViewById(R.id.mileAans);
      TextView kiloAans=findViewById(R.id.kiloAans);
      TextView hecAans=findViewById(R.id.hectareAans);
      TextView acreAans=findViewById(R.id.acreAans);
      TextView bighaAans=findViewById(R.id.bighaAans);
      TextView mAans=findViewById(R.id.metreAans);
      TextView yAans=findViewById(R.id.yardAans);
      TextView fAans=findViewById(R.id.footAans);
      TextView iAans=findViewById(R.id.inchesAans);
      TextView cAans=findViewById(R.id.centiAans);
      TextView miliAans=findViewById(R.id.miliAans);
      Spinner spinnera=findViewById(R.id.spinnera);
      int[] spinnerapos = {0};
      ArrayAdapter arrayAdaptera=new ArrayAdapter(UnitConverter.this, android.R.layout.simple_spinner_item,area);
      arrayAdaptera.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
      spinnera.setAdapter(arrayAdaptera);
      spinnera.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              spinnerapos[0]=position;
              numberA.setText(numberA.getText().toString());
          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });
      numberA.addTextChangedListener(new TextWatcher() {
          @Override
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {

          }

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
              String x=numberA.getText().toString();
              if(x.equals("")||x.equals("."))
              {lla.setVisibility(View.GONE);showa.setVisibility(View.GONE);}
              else
              { lla.setVisibility(View.VISIBLE);
                  showa.setVisibility(View.VISIBLE);
                  showa.setBackgroundColor(Color.RED);
                  showa.setText("Hide");
                  showa.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {
                          if(showa.getText().toString().equals("Hide"))
                          { showa.setText("Show");
                              showa.setBackgroundColor(Color.GREEN);
                              lla.setVisibility(View.GONE);
                          }
                          else
                          {showa.setText("Hide");
                              showa.setBackgroundColor(Color.RED);
                              lla.setVisibility(View.VISIBLE);
                          }
                      }
                  });
                  if(showa.getText().toString().equals("Hide"))
                  {double value=Double.parseDouble(x);
                      mileAans.setText(BigDecimal.valueOf(convertA(value,spinnerapos[0],0)).toPlainString()+convertEandAddOratBegi(convertA(value,spinnerapos[0],0)));
                      kiloAans.setText(BigDecimal.valueOf(convertA(value,spinnerapos[0],1)).toPlainString()+convertEandAddOratBegi(convertA(value,spinnerapos[0],1)));
                      hecAans.setText(BigDecimal.valueOf(convertA(value,spinnerapos[0],2)).toPlainString()+convertEandAddOratBegi(convertA(value,spinnerapos[0],2)));
                      acreAans.setText(BigDecimal.valueOf(convertA(value,spinnerapos[0],3)).toPlainString()+convertEandAddOratBegi(convertA(value,spinnerapos[0],3)));
                      bighaAans.setText(BigDecimal.valueOf(convertA(value,spinnerapos[0],4)).toPlainString()+convertEandAddOratBegi(convertA(value,spinnerapos[0],4)));
                       mAans.setText(BigDecimal.valueOf(convertA(value,spinnerapos[0],5)).toPlainString()+convertEandAddOratBegi(convertA(value,spinnerapos[0],5)));
                      yAans.setText(BigDecimal.valueOf(convertA(value,spinnerapos[0],6)).toPlainString()+convertEandAddOratBegi(convertA(value,spinnerapos[0],6)));
                       fAans.setText(BigDecimal.valueOf(convertA(value,spinnerapos[0],7)).toPlainString()+convertEandAddOratBegi(convertA(value,spinnerapos[0],7)));
                       iAans.setText(BigDecimal.valueOf(convertA(value,spinnerapos[0],8)).toPlainString()+convertEandAddOratBegi(convertA(value,spinnerapos[0],8)));
                       cAans.setText(BigDecimal.valueOf(convertA(value,spinnerapos[0],9)).toPlainString()+convertEandAddOratBegi(convertA(value,spinnerapos[0],9)));
                       miliAans.setText(BigDecimal.valueOf(convertA(value,spinnerapos[0],10)).toPlainString()+convertEandAddOratBegi(convertA(value,spinnerapos[0],10)));

                  }}
          }

          @Override
          public void afterTextChanged(Editable s) {

          }
      });

        EditText numberV=findViewById(R.id.Numberv);
        Button showv=findViewById(R.id.showv);
        showv.setVisibility(View.GONE);
        LinearLayout llv=findViewById(R.id.llv);
        llv.setVisibility(View.GONE);
        TextView mVans=findViewById(R.id.metrevans);
        TextView yVans=findViewById(R.id.yardvans);
        TextView fVans=findViewById(R.id.footvans);
        TextView gVans=findViewById(R.id.gallonvans);
        TextView lVans=findViewById(R.id.litreVans);
        TextView qVans=findViewById(R.id.quartvans);
        TextView cVans=findViewById(R.id.cupvans);
        TextView iVans=findViewById(R.id.inchesvans);
        TextView tableVans=findViewById(R.id.tablespoonvans);
        TextView teaVans=findViewById(R.id.teaspoonvans);
        TextView mililitreVans=findViewById(R.id.mililitrevans);
        TextView mmVans=findViewById(R.id.milivans);

        Spinner spinnerv=findViewById(R.id.spinnerv);
        int[] spinnervpos = {0};
        ArrayAdapter arrayAdapterv=new ArrayAdapter(UnitConverter.this, android.R.layout.simple_spinner_item,volume);
        arrayAdapterv.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerv.setAdapter(arrayAdapterv);
        spinnerv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnervpos[0]=position;
                numberV.setText(numberV.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        numberV.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String x=numberV.getText().toString();
                if(x.equals("")||x.equals("."))
                {llv.setVisibility(View.GONE);showv.setVisibility(View.GONE);}
                else
                { llv.setVisibility(View.VISIBLE);
                    showv.setVisibility(View.VISIBLE);
                    showv.setBackgroundColor(Color.RED);
                    showv.setText("Hide");
                    showv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(showv.getText().toString().equals("Hide"))
                            { showv.setText("Show");
                                showv.setBackgroundColor(Color.GREEN);
                                llv.setVisibility(View.GONE);
                            }
                            else
                            {showv.setText("Hide");
                                showv.setBackgroundColor(Color.RED);
                                llv.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    if(showv.getText().toString().equals("Hide"))
                    {double value=Double.parseDouble(x);
                        mVans.setText(BigDecimal.valueOf(convertV(value,spinnervpos[0],0)).toPlainString()+convertEandAddOratBegi(convertV(value,spinnervpos[0],0)));
                        yVans.setText(BigDecimal.valueOf(convertV(value,spinnervpos[0],1)).toPlainString()+convertEandAddOratBegi(convertV(value,spinnervpos[0],1)));
                        fVans.setText(BigDecimal.valueOf(convertV(value,spinnervpos[0],2)).toPlainString()+convertEandAddOratBegi(convertV(value,spinnervpos[0],2)));
                        gVans.setText(BigDecimal.valueOf(convertV(value,spinnervpos[0],3)).toPlainString()+convertEandAddOratBegi(convertV(value,spinnervpos[0],3)));
                        lVans.setText(BigDecimal.valueOf(convertV(value,spinnervpos[0],4)).toPlainString()+convertEandAddOratBegi(convertV(value,spinnervpos[0],4)));
                        qVans.setText(BigDecimal.valueOf(convertV(value,spinnervpos[0],5)).toPlainString()+convertEandAddOratBegi(convertV(value,spinnervpos[0],5)));
                        cVans.setText(BigDecimal.valueOf(convertV(value,spinnervpos[0],6)).toPlainString()+convertEandAddOratBegi(convertV(value,spinnervpos[0],6)));
                        iVans.setText(BigDecimal.valueOf(convertV(value,spinnervpos[0],7)).toPlainString()+convertEandAddOratBegi(convertV(value,spinnervpos[0],7)));
                        tableVans.setText(BigDecimal.valueOf(convertV(value,spinnervpos[0],8)).toPlainString()+convertEandAddOratBegi(convertV(value,spinnervpos[0],8)));
                        teaVans.setText(BigDecimal.valueOf(convertV(value,spinnervpos[0],9)).toPlainString()+convertEandAddOratBegi(convertV(value,spinnervpos[0],9)));
                        mililitreVans.setText(BigDecimal.valueOf(convertV(value,spinnervpos[0],10)).toPlainString()+convertEandAddOratBegi(convertV(value,spinnervpos[0],10)));
                        mmVans.setText(BigDecimal.valueOf(convertV(value,spinnervpos[0],11)).toPlainString()+convertEandAddOratBegi(convertV(value,spinnervpos[0],11)));
                    }}
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        EditText numberP=findViewById(R.id.Numberp);
        Button showp=findViewById(R.id.showp);
        showp.setVisibility(View.GONE);
        LinearLayout llp=findViewById(R.id.llp);
        llp.setVisibility(View.GONE);
        TextView atmPans=findViewById(R.id.atmpans);
        TextView barPans=findViewById(R.id.barpans);
        TextView kgPans=findViewById(R.id.kgpans);
        TextView deciPans=findViewById(R.id.decipans);
        TextView torrPans=findViewById(R.id.torrpans);
        TextView paPans=findViewById(R.id.papans);
        TextView miliPans=findViewById(R.id.milipans);
        TextView dynePans=findViewById(R.id.dynepans);


        Spinner spinnerp=findViewById(R.id.spinnerp);
        int[] spinnerppos = {0};
        ArrayAdapter arrayAdapterp=new ArrayAdapter(UnitConverter.this, android.R.layout.simple_spinner_item,pressure);
        arrayAdapterp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerp.setAdapter(arrayAdapterp);
        spinnerp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerppos[0]=position;
                numberP.setText(numberP.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        numberP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String x=numberP.getText().toString();
                if(x.equals("")||x.equals("."))
                {llp.setVisibility(View.GONE);showp.setVisibility(View.GONE);}
                else
                { llp.setVisibility(View.VISIBLE);
                    showp.setVisibility(View.VISIBLE);
                    showp.setBackgroundColor(Color.RED);
                    showp.setText("Hide");
                    showp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(showp.getText().toString().equals("Hide"))
                            { showp.setText("Show");
                                showp.setBackgroundColor(Color.GREEN);
                                llp.setVisibility(View.GONE);
                            }
                            else
                            {showp.setText("Hide");
                                showp.setBackgroundColor(Color.RED);
                                llp.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    if(showp.getText().toString().equals("Hide"))
                    {double value=Double.parseDouble(x);
                        atmPans.setText(BigDecimal.valueOf(convertP(value,spinnerppos[0],0)).toPlainString()+convertEandAddOratBegi(convertP(value,spinnerppos[0],0)));
                        barPans.setText(BigDecimal.valueOf(convertP(value,spinnerppos[0],1)).toPlainString()+convertEandAddOratBegi(convertP(value,spinnerppos[0],1)));
                        kgPans.setText(BigDecimal.valueOf(convertP(value,spinnerppos[0],2)).toPlainString()+convertEandAddOratBegi(convertP(value,spinnerppos[0],2)));
                        deciPans.setText(BigDecimal.valueOf(convertP(value,spinnerppos[0],3)).toPlainString()+convertEandAddOratBegi(convertP(value,spinnerppos[0],3)));
                        torrPans.setText(BigDecimal.valueOf(convertP(value,spinnerppos[0],4)).toPlainString()+convertEandAddOratBegi(convertP(value,spinnerppos[0],4)));
                        paPans.setText(BigDecimal.valueOf(convertP(value,spinnerppos[0],5)).toPlainString()+convertEandAddOratBegi(convertP(value,spinnerppos[0],5)));
                        miliPans.setText(BigDecimal.valueOf(convertP(value,spinnerppos[0],6)).toPlainString()+convertEandAddOratBegi(convertP(value,spinnerppos[0],6)));
                        dynePans.setText(BigDecimal.valueOf(convertP(value,spinnerppos[0],7)).toPlainString()+convertEandAddOratBegi(convertP(value,spinnerppos[0],7)));
                        }}
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        EditText numberD=findViewById(R.id.Numberd);
        Button showd=findViewById(R.id.showd);
        showd.setVisibility(View.GONE);
        LinearLayout lld=findViewById(R.id.lld);
        lld.setVisibility(View.GONE);
        TextView kgmlDans=findViewById(R.id.kgmldans);
        TextView kgldDans=findViewById(R.id.kgldans);
        TextView kgmDans=findViewById(R.id.kgmdans);
        TextView gmlDans=findViewById(R.id.gmldans);
        TextView glDans=findViewById(R.id.gldans);
        TextView gmDans=findViewById(R.id.gmdans);

        Spinner spinnerd=findViewById(R.id.spinnerd);
        int[] spinnerdpos = {0};
        ArrayAdapter arrayAdapterd=new ArrayAdapter(UnitConverter.this, android.R.layout.simple_spinner_item,density);
        arrayAdapterd.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerd.setAdapter(arrayAdapterd);
        spinnerd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerdpos[0]=position;
                numberD.setText(numberD.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        numberD.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String x=numberD.getText().toString();
                if(x.equals("")||x.equals("."))
                {lld.setVisibility(View.GONE);showd.setVisibility(View.GONE);}
                else
                { lld.setVisibility(View.VISIBLE);
                    showd.setVisibility(View.VISIBLE);
                    showd.setBackgroundColor(Color.RED);
                    showd.setText("Hide");
                    showd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(showd.getText().toString().equals("Hide"))
                            { showd.setText("Show");
                                showd.setBackgroundColor(Color.GREEN);
                                lld.setVisibility(View.GONE);
                            }
                            else
                            {showd.setText("Hide");
                                showd.setBackgroundColor(Color.RED);
                                lld.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    if(showd.getText().toString().equals("Hide"))
                    {double value=Double.parseDouble(x);
                        kgmlDans.setText(BigDecimal.valueOf(convertD(value,spinnerdpos[0],0)).toPlainString()+convertEandAddOratBegi(convertD(value,spinnerdpos[0],0)));
                        kgldDans.setText(BigDecimal.valueOf(convertD(value,spinnerdpos[0],1)).toPlainString()+convertEandAddOratBegi(convertD(value,spinnerdpos[0],1)));
                        kgmDans.setText(BigDecimal.valueOf(convertD(value,spinnerdpos[0],2)).toPlainString()+convertEandAddOratBegi(convertD(value,spinnerdpos[0],2)));
                        gmlDans.setText(BigDecimal.valueOf(convertD(value,spinnerdpos[0],3)).toPlainString()+convertEandAddOratBegi(convertD(value,spinnerdpos[0],3)));
                        glDans.setText(BigDecimal.valueOf(convertD(value,spinnerdpos[0],4)).toPlainString()+convertEandAddOratBegi(convertD(value,spinnerdpos[0],4)));
                        gmDans.setText(BigDecimal.valueOf(convertD(value,spinnerdpos[0],5)).toPlainString()+convertEandAddOratBegi(convertD(value,spinnerdpos[0],5)));
                       }}
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        EditText numberS=findViewById(R.id.Numbers);
        Button shows=findViewById(R.id.shows);
        shows.setVisibility(View.GONE);
        LinearLayout lls=findViewById(R.id.lls);
        lls.setVisibility(View.GONE);
        TextView mpssans=findViewById(R.id.mpssans);
        TextView ksans=findViewById(R.id.ksans);
        TextView mipsans=findViewById(R.id.mipssans);
        TextView fphsans=findViewById(R.id.fphsans);
        TextView kphsans=findViewById(R.id.kphsans);

        Spinner spinners=findViewById(R.id.spinners);
        int[] spinnerspos = {0};
        ArrayAdapter arrayAdapters=new ArrayAdapter(UnitConverter.this, android.R.layout.simple_spinner_item,speed);
        arrayAdapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinners.setAdapter(arrayAdapters);
        spinners.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerspos[0]=position;
                numberS.setText(numberS.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        numberS.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String x=numberS.getText().toString();
                if(x.equals("")||x.equals("."))
                {lls.setVisibility(View.GONE);shows.setVisibility(View.GONE);}
                else
                { lls.setVisibility(View.VISIBLE);
                    shows.setVisibility(View.VISIBLE);
                    shows.setBackgroundColor(Color.RED);
                    shows.setText("Hide");
                    shows.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(shows.getText().toString().equals("Hide"))
                            { shows.setText("Show");
                                shows.setBackgroundColor(Color.GREEN);
                                lls.setVisibility(View.GONE);
                            }
                            else
                            {shows.setText("Hide");
                                shows.setBackgroundColor(Color.RED);
                                lls.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    if(shows.getText().toString().equals("Hide"))
                    {double value=Double.parseDouble(x);
                       mpssans.setText(BigDecimal.valueOf(convertS(value,spinnerspos[0],0)).toPlainString()+convertEandAddOratBegi(convertS(value,spinnerspos[0],0)));
                       ksans.setText(BigDecimal.valueOf(convertS(value,spinnerspos[0],1)).toPlainString()+convertEandAddOratBegi(convertS(value,spinnerspos[0],1)));
                       mipsans.setText(BigDecimal.valueOf(convertS(value,spinnerspos[0],2)).toPlainString()+convertEandAddOratBegi(convertS(value,spinnerspos[0],2)));
                        fphsans.setText(BigDecimal.valueOf(convertS(value,spinnerspos[0],3)).toPlainString()+convertEandAddOratBegi(convertS(value,spinnerspos[0],3)));
                        kphsans.setText(BigDecimal.valueOf(convertS(value,spinnerspos[0],4)).toPlainString()+convertEandAddOratBegi(convertS(value,spinnerspos[0],4)));
                    }}
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        EditText numberTemp=findViewById(R.id.Numbertemp);
        Button showtemp=findViewById(R.id.showtemp);
        showtemp.setVisibility(View.GONE);
        LinearLayout lltemp=findViewById(R.id.lltemp);
        lltemp.setVisibility(View.GONE);
        TextView ctempans=findViewById(R.id.ctempans);
        TextView ktempans=findViewById(R.id.ktempans);
        TextView ftempans=findViewById(R.id.ftempans);

        Spinner spinnertemp=findViewById(R.id.spinnertemp);
        int[] spinnertemppos = {0};
        ArrayAdapter arrayAdaptertemp=new ArrayAdapter(UnitConverter.this, android.R.layout.simple_spinner_item,temp);
        arrayAdaptertemp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnertemp.setAdapter(arrayAdaptertemp);
        spinnertemp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnertemppos[0]=position;
                numberTemp.setText(numberTemp.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        numberTemp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String x=numberTemp.getText().toString();
                if(x.equals("")||x.equals(".")||x.equals('-'))
                {lltemp.setVisibility(View.GONE);showtemp.setVisibility(View.GONE);}
                else
                {boolean trying=true;
                    try{ double v=Double.parseDouble(x);}
                catch (Exception e){
                    trying=false;
                }
                   if(trying){
                    lltemp.setVisibility(View.VISIBLE);
                    showtemp.setVisibility(View.VISIBLE);
                    showtemp.setBackgroundColor(Color.RED);
                    showtemp.setText("Hide");
                    showtemp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(showtemp.getText().toString().equals("Hide"))
                            { showtemp.setText("Show");
                                showtemp.setBackgroundColor(Color.GREEN);
                                lltemp.setVisibility(View.GONE);
                            }
                            else
                            {showtemp.setText("Hide");
                                showtemp.setBackgroundColor(Color.RED);
                                lltemp.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    if(showtemp.getText().toString().equals("Hide"))
                    {
                        double value=Double.parseDouble(x);
                        ctempans.setText(BigDecimal.valueOf(convertTemp(value,spinnertemppos[0],0)).toPlainString()+convertEandAddOratBegi(convertTemp(value,spinnertemppos[0],0)));
                        ktempans.setText(BigDecimal.valueOf(convertTemp(value,spinnertemppos[0],1)).toPlainString()+convertEandAddOratBegi(convertTemp(value,spinnertemppos[0],1)));
                        ftempans.setText(BigDecimal.valueOf(convertTemp(value,spinnertemppos[0],2)).toPlainString()+convertEandAddOratBegi(convertTemp(value,spinnertemppos[0],2)));
                    }}}
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        EditText numberw=findViewById(R.id.Numberw);
        Button showw=findViewById(R.id.showw);
        showw.setVisibility(View.GONE);
        LinearLayout llw=findViewById(R.id.llw);
        llw.setVisibility(View.GONE);
        TextView twans=findViewById(R.id.twans);
        TextView kwans=findViewById(R.id.kwans);
        TextView pwans=findViewById(R.id.pwans);
        TextView owans=findViewById(R.id.owans);
        TextView gwans=findViewById(R.id.gwans);
        TextView miliwans=findViewById(R.id.miliwans);
        TextView microwans=findViewById(R.id.microwans);

        Spinner spinnerw=findViewById(R.id.spinnerw);
        int[] spinnerwpos = {0};
        ArrayAdapter arrayAdapterw=new ArrayAdapter(UnitConverter.this, android.R.layout.simple_spinner_item,weight);
        arrayAdapterw.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerw.setAdapter(arrayAdapterw);
        spinnerw.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerwpos[0]=position;
                numberw.setText(numberw.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        numberw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String x=numberw.getText().toString();
                if(x.equals("")||x.equals("."))
                {llw.setVisibility(View.GONE);showw.setVisibility(View.GONE);}
                else
                { llw.setVisibility(View.VISIBLE);
                    showw.setVisibility(View.VISIBLE);
                    showw.setBackgroundColor(Color.RED);
                    showw.setText("Hide");
                    showw.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(showw.getText().toString().equals("Hide"))
                            { showw.setText("Show");
                                showw.setBackgroundColor(Color.GREEN);
                                llw.setVisibility(View.GONE);
                            }
                            else
                            {showw.setText("Hide");
                                showw.setBackgroundColor(Color.RED);
                                llw.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    if(showw.getText().toString().equals("Hide"))
                    {double value=Double.parseDouble(x);
                         twans.setText(BigDecimal.valueOf(convertW(value,spinnerwpos[0],0)).toPlainString()+convertEandAddOratBegi(convertW(value,spinnerwpos[0],0)));
                         kwans.setText(BigDecimal.valueOf(convertW(value,spinnerwpos[0],1)).toPlainString()+convertEandAddOratBegi(convertW(value,spinnerwpos[0],1)));
                         pwans.setText(BigDecimal.valueOf(convertW(value,spinnerwpos[0],2)).toPlainString()+convertEandAddOratBegi(convertW(value,spinnerwpos[0],2)));
                         owans.setText(BigDecimal.valueOf(convertW(value,spinnerwpos[0],3)).toPlainString()+convertEandAddOratBegi(convertW(value,spinnerwpos[0],3)));
                         gwans.setText(BigDecimal.valueOf(convertW(value,spinnerwpos[0],4)).toPlainString()+convertEandAddOratBegi(convertW(value,spinnerwpos[0],4)));
                         miliwans.setText(BigDecimal.valueOf(convertW(value,spinnerwpos[0],5)).toPlainString()+convertEandAddOratBegi(convertW(value,spinnerwpos[0],5)));
                         microwans.setText(BigDecimal.valueOf(convertW(value,spinnerwpos[0],6)).toPlainString()+convertEandAddOratBegi(convertW(value,spinnerwpos[0],6)));


                    }}
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        EditText numberTime=findViewById(R.id.Numbertime);
        Button showtime=findViewById(R.id.showtime);
        showtime.setVisibility(View.GONE);
        LinearLayout lltime=findViewById(R.id.lltime);
        lltime.setVisibility(View.GONE);
        TextView dtimeans=findViewById(R.id.dtimeans);
        TextView htimeans=findViewById(R.id.htimeans);
        TextView mtimeans=findViewById(R.id.mtimeans);
        TextView stimeans=findViewById(R.id.stimeans);


        Spinner spinnertime=findViewById(R.id.spinnertime);
        int[] spinnertimepos = {0};
        ArrayAdapter arrayAdaptertime=new ArrayAdapter(UnitConverter.this, android.R.layout.simple_spinner_item,time);
        arrayAdaptertime.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnertime.setAdapter(arrayAdaptertime);
        spinnertime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnertimepos[0]=position;
                numberTime.setText(numberTime.getText().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        numberTime.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String x=numberTime.getText().toString();
                if(x.equals("")||x.equals("."))
                {lltime.setVisibility(View.GONE);showtime.setVisibility(View.GONE);}
                else
                { lltime.setVisibility(View.VISIBLE);
                    showtime.setVisibility(View.VISIBLE);
                    showtime.setBackgroundColor(Color.RED);
                    showtime.setText("Hide");
                    showtime.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(showtime.getText().toString().equals("Hide"))
                            { showtime.setText("Show");
                                showtime.setBackgroundColor(Color.GREEN);
                                lltime.setVisibility(View.GONE);
                            }
                            else
                            {showtime.setText("Hide");
                                showtime.setBackgroundColor(Color.RED);
                                lltime.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    if(showtime.getText().toString().equals("Hide"))
                    {double value=Double.parseDouble(x);
                        dtimeans.setText(BigDecimal.valueOf(convertTime(value,spinnertimepos[0],0)).toPlainString()+convertEandAddOratBegi(convertTime(value,spinnertimepos[0],0)));
                        htimeans.setText(BigDecimal.valueOf(convertTime(value,spinnertimepos[0],1)).toPlainString()+convertEandAddOratBegi(convertTime(value,spinnertimepos[0],1)));
                        mtimeans.setText(BigDecimal.valueOf(convertTime(value,spinnertimepos[0],2)).toPlainString()+convertEandAddOratBegi(convertTime(value,spinnertimepos[0],2)));
                        stimeans.setText(BigDecimal.valueOf(convertTime(value,spinnertimepos[0],3)).toPlainString()+convertEandAddOratBegi(convertTime(value,spinnertimepos[0],3)));

                    }}
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        EditText numberb=findViewById(R.id.Numberb);
        Button showb=findViewById(R.id.showb);
        showb.setVisibility(View.GONE);
        LinearLayout llb=findViewById(R.id.llb);
        llb.setVisibility(View.GONE);
        TextView hbans=findViewById(R.id.hbaseans);
        TextView dbans=findViewById(R.id.dbaseans);
        TextView obans=findViewById(R.id.obaseans);
        TextView bbans=findViewById(R.id.bbaseans);

        Spinner spinnerb=findViewById(R.id.spinnerb);
        int[] spinnerbpos = {0};
       // numberb.setKeyListener(DigitsKeyListener.getInstance("0123456789ABCDE"));
        ArrayAdapter arrayAdapterb=new ArrayAdapter(UnitConverter.this, android.R.layout.simple_spinner_item,base);
        arrayAdapterb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerb.setAdapter(arrayAdapterb);
        spinnerb.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String x=numberb.getText().toString();
                if(!(x.equals("")||x.equals(".")))
                 if(spinnerbpos[0]!=position)
                 {int from=spinnerbpos[0]==0?16:spinnerbpos[0]==1?10:spinnerbpos[0]==2?8:2;
                  int to=position==0?16:position==1?10:position==2?8:2;
                     x=convertB(x.toUpperCase(),from,to);}
                spinnerbpos[0]=position;
                numberb.setText(x);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        numberb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String x=numberb.getText().toString();
                if(!x.equals(""))
                {
                  int pos=spinnerbpos[0];
                  int f=0;
                    for(int i=0;i<x.length();i++){
                        char ch=x.charAt(i);
                        String y=x.substring(0,i)+x.substring(i+1);
                  if(pos==1)
                  {if(ch>'9')
                  {x=y;break;}}
                  else if(pos==2)
                  {if(ch>'7')
                  { x=y;break;}}
                     else if(pos==3)
                  { if(ch>'1')
                  { x=y;break;
                  }}
                     if(ch=='.')
                     {f++;
                     if(f==2)
                     {  x=y;break;}
                     if(i==0)
                     {x=y;break;}
                     if(i==1&&x.charAt(0)=='-')
                     {x=y;break;}
                     }
                        if(ch=='-'&&i!=0)
                        {x=y;break;}


                    }

                     if(!x.equals(numberb.getText().toString()))
                     {numberb.setText(x);
                     numberb.setSelection(x.length());
                     }
                }

                if(x.equals("")||x.equals(".")||x.equals("-"))
                {llb.setVisibility(View.GONE);showb.setVisibility(View.GONE);}
                else
                { llb.setVisibility(View.VISIBLE);
                    showb.setVisibility(View.VISIBLE);
                    showb.setBackgroundColor(Color.RED);
                    showb.setText("Hide");
                    showb.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(showb.getText().toString().equals("Hide"))
                            { showb.setText("Show");
                                showb.setBackgroundColor(Color.GREEN);
                                llb.setVisibility(View.GONE);
                            }
                            else
                            {showb.setText("Hide");
                                showb.setBackgroundColor(Color.RED);
                                llb.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                    if(showb.getText().toString().equals("Hide"))
                    {    int from=spinnerbpos[0]==0?16:spinnerbpos[0]==1?10:spinnerbpos[0]==2?8:2;
                          hbans.setText(convertB(x.toUpperCase(),from,16));
                          dbans.setText(convertB(x.toUpperCase(),from,10));
                          obans.setText(convertB(x.toUpperCase(),from,8));
                          bbans.setText(convertB(x.toUpperCase(),from,2));
                    }}
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

  }
}