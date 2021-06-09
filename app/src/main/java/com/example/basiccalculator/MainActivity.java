package com.example.basiccalculator;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    static boolean incorrect;

    public static class NodeI{
        double val;
        NodeI nexti;

    }
    public static class NodeC{
        char c;
        NodeC nextc;

    }  public static double fact(int j)
    {double f=1;
        for(int i=2;i<=j;i++)
            f*=i;
        return f;
    }
    public static double factUptoSome(int factOf,int factUpto)
    {double f=1;
        for(int i=factOf;i>factUpto;i--)
            f*=i;
        return f;

    }
    public static boolean check(double x)
    { int j=(int)x;
        double k=x-j;
        return k == 0.0;

    }
    public static String correctingFormatE(String x)
    { String ans=x;
       if(x.indexOf('E')!=-1)
          if(x.charAt(x.indexOf('E')+1)=='-')
           ans=x.substring(0,x.indexOf('E')+1)+"("+x.substring(x.indexOf('E')+1)+")";
        return ans;
    }
    public static void perAndCom(NodeI f,NodeC g)
    {while(g!=null)
    { if(g.c=='P'||g.c=='C')
        removeAndInsert(f,g);
        if((g.c!='P'&&g.c!='C')&&g!=null)
        { g=g.nextc;
            f=f.nexti;
        }
    }

    }
    public static void operation(NodeI x,NodeI y,NodeC z)
    {
        char ch=z.c;
        double m=x.val;
        double n=y.val;
        switch(ch)
        {
            case '+':x.val=m+n;
                break;

            case '-':x.val=m-n;
                break;

            case '*':x.val=m*n;
                break;

            case '/':x.val=m/n;
                break;

            case '^':x.val=Math.pow(m,n);
                break;
            case 'E':x.val=m*Math.pow(10,n);
                break;
            case 'P': boolean M=check(m);
                boolean N=check(n);
                if((M==true&&N==true)&&m>=n)
                    x.val=factUptoSome((int)m,(int)(m-n));
                else
                {incorrect=true;
                    x.val=0.0;}
                break;
            case 'C':boolean O=check(m);
                boolean P=check(n);
//                double x1=fact((int)m);
//                double x2=(fact((int)n)*fact((int)(m-n)));
                if((O==true&&P==true)&&m>=n) {
                    int upto=(int)Math.max(n,m-n);
                    int deno=(int)Math.min(n,m-n);
                    double num=factUptoSome((int)m,upto);
                    x.val =num/deno;
                }
                else
                {x.val=0.0;
                    incorrect=true;
                }
                break;
        }
    }

    public static void removeAndInsert(NodeI a,NodeC b)
    {   operation(a,a.nexti,b);
        a.nexti=a.nexti.nexti;
        if(b.nextc==null)
            b=null;
        else
        {b.c=b.nextc.c;
            b.nextc=b.nextc.nextc;
        }
    }
    public static void iterate(NodeI f,NodeC g,char h)
    { while(g!=null)
    { if(g.c==h)
        removeAndInsert(f,g);
        if(g.c!=h&&g!=null)
        { g=g.nextc;
            f=f.nexti;
        }
    }

    }public static void replaceOperator(NodeC g,char h,char i)
    {  while(g!=null) {
        if (g.c == h)
            g.c = i;
        g=g.nextc;
         }
    }

    public static double ansOf(String x)
    {
        double ans=0.0;
        int start=x.indexOf('(');
        if(start==-1)
            incorrect=true;
        if(!incorrect)
        {
            int end=x.lastIndexOf(')');
            double value=calculate(x.substring(start+1,end));
            String m=x.substring(0,start).trim();
            double convert=180/Math.PI;
            double e1=Math.exp(value);
            double e2=Math.exp((-1)*value);
            if(m.equalsIgnoreCase("sin"))
            {   if(!check(value/90))  //not perfectly divides
                ans=Math.sin(value/convert);
               else
            {   double newValue=(int)value%360;
                 ans=newValue==0?0:newValue==90?1:newValue==180?0:-1;
            }
            }
            else if(m.equalsIgnoreCase("sinh"))
                ans=(e1-e2)/2.0;
            else if(m.equalsIgnoreCase("sin-1"))
                ans=Math.asin(value)*convert;

            else if(m.equalsIgnoreCase("cos"))
            {    if(!check(value/90))
                ans=Math.cos(value/convert);
            else
            {   double newValue=(int)value%360;
                ans=newValue==0?1:newValue==90?0:newValue==180?-1:0;
            }
            }
            else if(m.equalsIgnoreCase("cosh"))
                ans=(e1+e2)/2.0;
            else if(m.equalsIgnoreCase("cos-1"))
                ans=Math.acos(value)*convert;

            else if(m.equalsIgnoreCase("tan"))
            {    if(!check(value/90))
                ans=Math.tan(value/convert);
            else
            {   double newValue=(int)value%360;
                ans=newValue==0?0:newValue==90?1.0/0:newValue==180?0:-1.0/0;
            }
            }
            else if(m.equalsIgnoreCase("tanh"))
                ans=(e1-e2)/(e1+e2);
            else if(m.equalsIgnoreCase("tan-1"))
                ans=Math.atan(value)*convert;

            else if(m.equalsIgnoreCase("sec"))
                ans=1.0/ansOf("Cos("+value+")");
            else if(m.equalsIgnoreCase("sech"))
                ans=1.0/ansOf("Cosh("+value+")");
            else if(m.equalsIgnoreCase("sec-1"))
                ans=ansOf("Cos-1("+(1.0/value)+")");

            else if(m.equalsIgnoreCase("cot"))
                ans=1.0/ansOf("Tan("+value+")");
            else if(m.equalsIgnoreCase("coth"))
                ans=1.0/ansOf("Tanh("+value+")");
            else if(m.equalsIgnoreCase("cot-1"))
                ans=ansOf("Tan-1("+(1.0/value)+")");

            else if(m.equalsIgnoreCase("Cosec")||m.equalsIgnoreCase("Csc"))
                ans=1.0/ansOf("Sin("+value+")");
            else if(m.equalsIgnoreCase("cosech")||m.equalsIgnoreCase("csch"))
                ans=1.0/ansOf("Sinh("+value+")");
            else if(m.equalsIgnoreCase("cosec-1")||m.equalsIgnoreCase("csc-1"))
                ans=ansOf("Sin-1("+(1.0/value)+")");

            else if(m.equalsIgnoreCase("sinh-1"))
                ans=Math.log(value+Math.sqrt(value*value+1));
            else if(m.equalsIgnoreCase("cosh-1"))
                ans=Math.log(value+Math.sqrt(value*value-1));
            else if(m.equalsIgnoreCase("tanh-1"))
                ans=Math.log((1+value)/(1-value))/2.0;
            else if(m.equalsIgnoreCase("coth-1"))
                ans=Math.log((value+1)/(value-1))/2.0;
            else if(m.equalsIgnoreCase("cosech-1")||m.equalsIgnoreCase("csch-1"))
                ans=Math.log((1/value)+Math.sqrt((1/(value*value))+1));
            else if(m.equalsIgnoreCase("sech-1"))
                ans=Math.log((1/value)+Math.sqrt((1/(value*value))-1));

            else if(m.equalsIgnoreCase("ln"))
                ans=Math.log(value);
            else if(m.equalsIgnoreCase("log"))
                ans=Math.log10(value);
            else
                incorrect=true;
        }

        return ans;
    }
    public static double calculate(String S)
    {  if(S.equals(""))
        incorrect=true;
        if(!incorrect)
        {
            if(S.charAt(0)=='+')
            S=S.substring(1);
            String Stemp="";
            int len=S.length();
            int flag=0;
            for(int xx=0;xx<len;xx++)
            { char cc=S.charAt(xx);
                if(cc!=' ')
                    Stemp+=cc;
                if(cc=='(')
                    flag++;
                else if(cc==')') {
                    flag--;
                    if(xx+1<len)
                    { char ccc=S.charAt(xx+1);
                        if(!(ccc==')'||ccc=='+'||ccc=='-'||ccc=='*'||ccc=='/'||ccc=='^'||ccc=='E'||ccc=='P'||ccc=='C'||ccc=='×'||ccc=='÷'))
                            incorrect=true;
                    }
                }
                if(flag<0)
                {incorrect=true;
                }
            }
            if(incorrect==false)
            {
                for(int xx=1;xx<=flag;xx++)
                    Stemp+=')';
                S=Stemp;
                len=S.length();
                flag=0;int negativeInt=0;
                NodeI num=new NodeI();
                NodeC op=new NodeC();
                NodeI i=num;NodeC j=op;
                int k=0;
                for( k=0;k<len;k++)
                { char chh=S.charAt(k);
                    if(!((chh>='0'&&chh<='9')||chh=='.'))//enter when not a number
                    {
                        if(k==0&&chh=='-')
                        {flag=1;negativeInt=1;}

                        else
                        {
                            if((chh!='-'&&chh!='+'&&chh!='/'&&chh!='*'&&chh!='^'&&chh!='P'&&chh!='C'&&chh!='E'&&chh!='÷'&&chh!='×')||(chh=='C'&&k+1<len&&(((S.charAt(k+1)=='s'||S.charAt(k+1)=='S')&&k+2<len&&((S.charAt(k+2)!='i'&&S.charAt(k+2)!='I')&&(S.charAt(k+2)!='e'&&S.charAt(k+2)!='E')))||S.charAt(k+1)=='o'||S.charAt(k+1)=='O')))
                            //enter when not a operator,ie,e or (...) or trigonometric functions
                            {    if(chh=='e'&&k-1>=0)
                                    if((S.charAt(k-1)>='0'&&S.charAt(k-1)>='9')||S.charAt(k-1)>=')'||S.charAt(k-1)>='.')
                                     incorrect=true;
                                if(chh=='e')
                                { i.val=Math.exp(1);
                                    k=k+1;
                                }
                                else{
                                    String fn=S.substring(k);
                                    int s=0,ss=0,ff=0;
                                    for(ss=0;ss<fn.length();ss++)
                                    {  if(s!=0)
                                        ff=1;
                                        if(ff==1&&s==0)
                                            break;
                                        if(fn.charAt(ss)=='(')
                                            s++;
                                        else if(fn.charAt(ss)==')')
                                            s--;
                                    }
                                    if(ff==0)
                                        incorrect=true;
                                    else
                                        fn=fn.substring(0,ss);
                                    if(flag==k)
                                    {if(fn.charAt(0)=='(')
                                        i.val=calculate(fn.substring(1,fn.length()-1));
                                    else
                                        i.val=ansOf(fn);
                                    }
                                    else incorrect=true;

                                    k=k+fn.length();
                                }}
                            else if(flag<k)//enter when operator is found
                            {    boolean z=false;
                                int dot=0;
                                String m=S.substring(flag,k);
                                for(int ii=0;ii<m.length();ii++)
                                    if(m.charAt(ii)=='.')
                                        dot++;
                                if(dot<=1)
                                    z=true;
                                if(m.length()==1&&dot==1)
                                    z=false;
                                if(z)
                                    i.val=Double.parseDouble(m);
                                else
                                {
                                    incorrect=true;
                                    i.val=0.0;
                                }
                            }
                            //Negative integer handle
                            else if(flag==k)
                            {  if(chh=='-'&&negativeInt==0)
                               {char prevChh=S.charAt(k-1);
                               if(prevChh=='*'||prevChh=='/'||prevChh=='^'||prevChh=='E'||prevChh=='P'||prevChh=='C'||prevChh=='×'||prevChh=='÷')
                                { negativeInt=1;
                                    flag=k+1;
                                 continue;
                                }else

                               incorrect=true;

                               }
                               else
                                   incorrect=true;
                            }
                            else
                                incorrect=true;
                            if(negativeInt==1)      //changes done:replacing flag with negInt
                            {i.val=(-1)*i.val;negativeInt=0;}
                            if(k!=len)
                            {   char x=S.charAt(k);
                                if(!(x=='+'||x=='-'||x=='*'||x=='/'||x=='^'||x=='E'||x=='P'||x=='C'||x=='×'||x=='÷'))// error condition for eValue for eg."e23"
                                    incorrect=true;
                                j.c=S.charAt(k);
                                NodeI zzz=new NodeI();
                                i.nexti=zzz;
                                i=i.nexti;
                                NodeC zzzz=new NodeC();
                                j.nextc=zzzz;
                                j=j.nextc;
                                flag=k+1;
                            }
                        }

                    }
                }
                j=null;
                if(k!=len+1)
                    if(flag<len)
                    {   boolean z=false;
                        int dot=0;
                        String m=S.substring(flag,len);
                        for(int ii=0;ii<m.length();ii++)
                            if(m.charAt(ii)=='.')
                                dot++;
                        if(dot<=1)
                            z=true;
                        if(m.length()==1&&dot==1)
                            z=false;
                        if(z)
                            i.val=Double.parseDouble(m);
                        else  {
                            incorrect=true;
                            i.val=0.0;
                        }
                        if(negativeInt==1)
                            i.val=(-1)*i.val;
                    }
                    else incorrect=true;

                i.nexti=null;
                replaceOperator(op,'÷','/');
                replaceOperator(op,'×','*');
                iterate(num,op,'E');
                iterate(num,op,'^');
                perAndCom(num,op);
                iterate(num,op,'/');
                iterate(num,op,'*');
                iterate(num,op,'-');
                iterate(num,op,'+');
                return num.val;
            }}
        return 0.0;
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
           Uri photoURI=FileProvider.getUriForFile(this,BuildConfig.APPLICATION_ID+".provider",tempFile);
           intent.putExtra(Intent.EXTRA_STREAM,photoURI);
           intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            try{
                startActivity(Intent.createChooser(intent,"Share Basic Calculator app via"));}
            catch (Exception e ) {
                Toast.makeText(MainActivity.this, "Sorry,app sharing is not supported", Toast.LENGTH_SHORT).show();
            }   } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_layout_main);

        MultiAutoCompleteTextView num=findViewById(R.id.num);
        Button sub=findViewById(R.id.submitbasecal);
        TextView tv=findViewById(R.id.tv);
        TextView liveAns=findViewById(R.id.liveans);
        Button Ures=findViewById(R.id.Ures);
        Button clear=findViewById(R.id.clearbasecal);
        Button fraction=findViewById(R.id.fraction);
        NavigationView nav_view=findViewById(R.id.nav_view);
        DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);
        ImageButton toggle=findViewById(R.id.toggle);
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        Dialog dialog=new Dialog(this);
        TextView iButton=findViewById(R.id.ibutton);
        dialog.setContentView(R.layout.popup);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        iButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
            }
        });
//        LayoutInflater inflater;
//        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View popup=inflater.inflate(R.layout.popup,null);
        TextView cancel=dialog.findViewById(R.id.Cancelpopup);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
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


       num.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {
               String x=num.getText().toString().trim();
               if(!(x.equals("")))
               {
                   incorrect=false;
                   double answer=calculate(x);
                   if(!incorrect&&!(""+answer).equals("NaN"))
                   {  if(check(answer))
                       liveAns.setText(""+(int)answer);
                   else
                       liveAns.setText(""+answer);
                   }else liveAns.setText("");
               }
               else liveAns.setText("");
           }

           @Override
           public void afterTextChanged(Editable s) {

           }
       });

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId()==R.id.nav_basecaculator)
                {
                    startActivity(new Intent(MainActivity.this,BaseCalculator.class));
                   finish();
                }
                else if(item.getItemId()==R.id.nav_date)
                {
                    startActivity(new Intent(MainActivity.this,Date.class));
                    finish();
                }
                else if(item.getItemId()==R.id.nav_investment)
                {
                    startActivity(new Intent(MainActivity.this,Investment.class));
                    finish();
                }
                else if(item.getItemId()==R.id.nav_unitconverter)
                {
                    startActivity(new Intent(MainActivity.this,UnitConverter.class));
                    finish();
                }
                else if(item.getItemId()==R.id.nav_about)
                    startActivity(new Intent(MainActivity.this,About.class));
                else if(item.getItemId()==R.id.nav_share)
                {
                    shareApp();

//                ApplicationInfo applicationInfo= getApplicationContext().getApplicationInfo();
//                String filepath=applicationInfo.sourceDir;
//                Intent intent=new Intent(Intent.ACTION_SEND);
//                // intent.setType(MimeTypeMap.getFileExtensionFromUrl(filepath.substring(filepath.lastIndexOf('.')+1)));
////                    intent.setType("application/vnd.android.package-archive");
////
////                    Log.d("11", "onNavigationItemSelected: "+intent.resolveActivity(getPackageManager()));
//              //  intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(filepath)));
           //      intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(MainActivity.this,BuildConfig.APPLICATION_ID+".provider",new File(filepath)));
//
//               // intent.setType("application/vnd.android.package-archive");
//                intent.setType("*/*");
//                Log.d("file", "onNavigationItemSelected: "+filepath);
//                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                Log.d("11", "onNavigationItemSelected: "+intent);
//                if(intent.resolveActivity(getPackageManager())!=null)
//                   startActivity(Intent.createChooser(intent,""));
//                else
//                    Toast.makeText(MainActivity.this,"Sorry No App to Share this file",Toast.LENGTH_LONG);


//                    android.content.pm.ApplicationInfo app =
//                            getApplicationContext().getApplicationInfo();
//                    String filePath = app.sourceDir;
//                    Intent intent = new Intent(Intent.ACTION_SEND);
//                    intent.setType("*/*");
//                    java.io.File originalApk = new java.io.File(filePath);
//                    try {
//                        java.io.File tempFile = new java.io.File(getExternalCacheDir() + "/ExtractedApk");
//                        if (!tempFile.isDirectory())
//                            if (!tempFile.mkdirs())
//                                ;
//                        tempFile = new java.io.File(tempFile.getPath() + "/" +
//                                "export.apk");
//                        if (!tempFile.exists())
//                        {
//                            try{
//                                if (!tempFile.createNewFile()) {
//                                     }
//                            }
//                            catch (java.io.IOException e){}
//                        }
//                        java.io.InputStream in = new java.io.FileInputStream (originalApk);
//                        java.io.OutputStream out = new java.io.FileOutputStream(tempFile);
//                        byte[] buf = new byte[1024];
//                        int len;
//                        while ((len = in.read(buf)) > 0) {
//                            out.write(buf, 0, len);
//                        }
//                        in.close();
//                        out.close();
//                        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(tempFile));
//                        startActivity(Intent.createChooser(intent, "Share app via"));
//                    }
//                    catch (java.io.IOException e)
//                    {
//                    }



//                try {
//                    PackageManager pm =getApplicationContext().getPackageManager();
//                    ApplicationInfo ai = pm.getApplicationInfo(getApplicationContext().getPackageName(), 0);//context.getPackageName() is used for send my app's apk, you can give package name which you want to share
//                    File srcFile = new File(ai.sourceDir);
//                    Intent share = new Intent();
//                    share.setAction(Intent.ACTION_SEND);
//                    share.setType("application/vnd.android.package-archive");
//                    share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(srcFile));
//                    share.setPackage("com.android.bluetooth");
//                    getApplicationContext().startActivity(share);
//                }catch (Exception e){}
                }

                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });


        //final int[] a = {0};
        final String[] sss = {""};

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                liveAns.setText("");
                //a[0]=0;
                String S=num.getText().toString().trim();
                if(!(S.equals("")))
                {
                incorrect=false;
                double answer=calculate(S);
                if(!(incorrect==true))
                {  if(check(answer))
                    tv.setText("="+(int)answer);
                   else
                    tv.setText("="+answer);
                }
                else
                    tv.setText("Error,Please ReEnter");

                }}
        });
        Ures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(tv.getText().toString().equals("")))
                 sss[0] =tv.getText().toString();
                if(!(sss[0].equals("")|| sss[0].equals("Error,Please ReEnter")|| sss[0].equals("=Infinity")|| sss[0].equals("=NaN")))
                {
                    if(sss[0].charAt(0)=='=')
                    sss[0]=sss[0].substring(1);
                sss[0]=correctingFormatE(sss[0]);
                    incorrect=false;
                  double xx=calculate(sss[0]);
                  if(check(xx))
                 sss[0]=""+(int)xx;
                  else
                      sss[0]=""+xx;
                    sss[0]=correctingFormatE(sss[0]);
//                 if(a[0]==0){num.setText(sss[0]);
//                    a[0] =1;}
//                else
                    String numval=num.getText().toString();
                    num.setText(numval+sss[0]);
                num.setSelection(numval.length()+sss[0].length());}
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                num.setText("");
                liveAns.setText("");
                sss[0]=tv.getText().toString();
                tv.setText("");
            }
        });
        fraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String div=tv.getText().toString();
                if(!(div.equals("")|| div.equals("Error,Please ReEnter")|| div.equals("=Infinity")|| div.equals("=NaN")))
                {  int f=0;
                    if(div.charAt(1)=='-')
                    { div=div.substring(2);f=1;}
                  else
                      div=div.substring(1);
                  boolean existenceOfEOrSlash=false;
                  for(int i=0;i<div.length();i++)
                  if(div.charAt(i)=='E'||div.charAt(i)=='/'){
                  existenceOfEOrSlash=true;break;}
                  if(existenceOfEOrSlash==false)
                  { double Value=Double.parseDouble(div);
                double a=0,b=0;
                    for(b=1;b<=1000000;b++)
                    {  a=b*Value;
                        if(check(a))
                            break;
                    }
                    if(b!=1000001)
                    {if(f==1)
                    tv.setText("=-"+(int)a+"/"+(int)b);
                    else tv.setText("="+(int)a+"/"+(int)b);}}

            }}
        });

    }
}