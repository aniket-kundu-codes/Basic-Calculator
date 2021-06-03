package com.example.basiccalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import java.math.*;

public class BaseConverter extends AppCompatActivity{

    static boolean incorrect;
    public static boolean check(String number, int ibase)
    { boolean f=false;
        int dot=0;int l=number.length();
        for(int i=0;i<l;i++)
        { char ch=number.charAt(i);
            if(i==0&&ch=='.')
            {f=true;break;
            }
            if(ch=='.')
                dot++;
            if(dot==2)
            {f=true;
                break;
            }
            if(!(ch==46))
            { if(ch>='0'&&ch<='9')
                if(!((ch-48)<ibase))
                {f=true;break;}
                if(ch>='A'&&ch<='Z')
                    if(!((ch-55)<ibase))
                    {f=true;break;}
                if(!((ch>='0'&&ch<='9')||(ch>='A'&&ch<='Z')))
                {f=true;break;}
            }
        }
        return f;

    }
    public static String covTodecF(String number, int ibase)
    {int dec=10;
        double dec_ans=0;String front="";
        front=number;
        if(number.indexOf('.')!=-1)
            front=number.substring(0,number.indexOf('.'));
        int j=0;
        for(int i=front.length()-1;i>=0;i--)
        {  char ch=front.charAt(i);
            if(ch>='0'&&ch<='9')
                dec_ans+=(ch-48)*Math.pow(ibase,j++);
            else
                dec_ans+=(ch-55)*Math.pow(ibase,j++);
        }
        String x=BigDecimal.valueOf(dec_ans).toPlainString()+".0";
        return x;
    }
    public static double covTodecL(String afterPointNumber, int ibase)
    { double res=0;
        for(int i=0;i<afterPointNumber.length();i++)
        { char ch=afterPointNumber.charAt(i);
            if(ch>='0'&&ch<='9')
                res+=(ch-48)*Math.pow(ibase,-(i+1));
            else
                res+=(ch-55)*Math.pow(ibase,-(i+1));
        }
        return res;
    }

    public static String convToBaseF(String fnumber, int fbase)
    {  String F="";long fnum=0;
        try{ fnum=Long.parseLong(fnumber.substring(0,fnumber.indexOf('.')));}
        catch (Exception e){incorrect=true;}
        if(incorrect==false)
        {if(fnum==0)
            F="0";
        while(fnum!=0)
        {  long rem=fnum%fbase;
            if(rem<10)
                F=rem+F;
            else
                F=(char)(rem+55)+F;
            fnum/=fbase;
        }}

        return F;
    }
    public static String convToBaseL(double afterPointNumber, int fbase)
    {   String F="";
        if(afterPointNumber==0.0)
            return F;
        for(int i=1;i<=17;i++)
        {  afterPointNumber*=fbase;
            int add=(int)afterPointNumber;
            if(add<10)
                F=F+add;
            else
                F=F+(char)(add+55);
            afterPointNumber=afterPointNumber-add;
            if(afterPointNumber==0.0)
                break;
        }
        return "."+F;

    }


    public static String convert(String number,int ibase,int fbase)
    {   incorrect=check(number,ibase);
        if(!incorrect)
        { //coverting to decimal

            String firstPart="";
            double lastPart=0;
            firstPart=covTodecF(number,ibase);
            if(number.indexOf('.')!=-1)
            {String last=number.substring(number.indexOf('.')+1);
                if(last.equals(""))
                    last+="0";
                lastPart=covTodecL(last,ibase);}
            //converting to some base
            String Firstpart=convToBaseF(firstPart,fbase);
            if(!Firstpart.equals(""))
            { String Lastpart=convToBaseL(lastPart,fbase);
            String fanswer=Firstpart+Lastpart;
            if(ibase!=fbase)
            return fanswer;
            else
            return number;}
        }
        return "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_layout_baseconverter);

        NavigationView nav_view=findViewById(R.id.nav_view);
        DrawerLayout drawerLayout=findViewById(R.id.drawer_layout);


        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.nav_main)
                {
                    startActivity(new Intent(BaseConverter.this,MainActivity.class));
                    finish();
                }
                else if(item.getItemId()==R.id.nav_date)
                {startActivity(new Intent(BaseConverter.this,Date.class));
                    finish();
                }
                else if(item.getItemId()==R.id.nav_investment)
                {startActivity(new Intent(BaseConverter.this,Investment.class));
                    finish();
                }
                drawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });

        Button _0= findViewById(R.id._0);
        Button _1= findViewById(R.id._1);
        Button _2= findViewById(R.id._2);
        Button _3= findViewById(R.id._3);
        Button _4= findViewById(R.id._4);
        Button _5= findViewById(R.id._5);
        Button _6= findViewById(R.id._6);
        Button _7= findViewById(R.id._7);
        Button _8= findViewById(R.id._8);
        Button _9= findViewById(R.id._9);
        Button dot= findViewById(R.id.dot);

        Button a= findViewById(R.id.a);
        Button b= findViewById(R.id.b);
        Button c= findViewById(R.id.c);
        Button d= findViewById(R.id.d);
        Button e= findViewById(R.id.e);
        Button f= findViewById(R.id.f);
        Button g= findViewById(R.id.g);
        Button h= findViewById(R.id.h);
        Button i= findViewById(R.id.i);
        Button j= findViewById(R.id.j);
        Button k= findViewById(R.id.k);
        Button l= findViewById(R.id.l);
        Button m= findViewById(R.id.m);
        Button n= findViewById(R.id.n);
        Button o= findViewById(R.id.o);
        Button p= findViewById(R.id.p);
        Button q= findViewById(R.id.q);
        Button r= findViewById(R.id.r);
        Button s= findViewById(R.id.s);
        Button t= findViewById(R.id.t);
        Button u= findViewById(R.id.u);
        Button v= findViewById(R.id.v);
        Button w= findViewById(R.id.w);
        Button x= findViewById(R.id.x);
        Button y= findViewById(R.id.y);
        Button z= findViewById(R.id.z);
        Button Submit=findViewById(R.id.Submit);
        Button clear=findViewById(R.id.clearB);
        EditText initial_base=findViewById(R.id.initial_base);
        EditText final_base=findViewById(R.id.final_base);
        TextView answer_view=findViewById(R.id.ans_view);
        EditText Number_value=findViewById(R.id.Number_value);
        ImageButton toggle=findViewById(R.id.toggle);

        Number_value.setShowSoftInputOnFocus(false);

        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });



        _0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(initial_base.getText().toString().equals(""))) {   if (0 < Integer.parseInt(initial_base.getText().toString())) {
                    Number_value.setText(Number_value.getText().toString() + _0.getText().toString());
                    Number_value.setSelection(Number_value.getText().length());
                }
                }
            }
        });
        _1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(initial_base.getText().toString().equals(""))) {   if (1 < Integer.parseInt(initial_base.getText().toString())) {
                    Number_value.setText(Number_value.getText().toString() + _1.getText().toString());
                    Number_value.setSelection(Number_value.getText().length());
                }
                }
            }
        });
        _2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(initial_base.getText().toString().equals(""))) {   if (2 < Integer.parseInt(initial_base.getText().toString())) {
                    Number_value.setText(Number_value.getText().toString() + _2.getText().toString());
                    Number_value.setSelection(Number_value.getText().length());
                }
                }
            }
        });
        _3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(initial_base.getText().toString().equals(""))) {   if (3 < Integer.parseInt(initial_base.getText().toString())) {
                    Number_value.setText(Number_value.getText().toString() + _3.getText().toString());
                    Number_value.setSelection(Number_value.getText().length());
                }
                }
            }
        });
        _4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(initial_base.getText().toString().equals(""))) {   if (4 < Integer.parseInt(initial_base.getText().toString())) {
                    Number_value.setText(Number_value.getText().toString() + _4.getText().toString());
                    Number_value.setSelection(Number_value.getText().length());
                }
                }
            }
        });
        _5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(initial_base.getText().toString().equals(""))) {   if (5 < Integer.parseInt(initial_base.getText().toString())) {
                    Number_value.setText(Number_value.getText().toString() + _5.getText().toString());
                    Number_value.setSelection(Number_value.getText().length());
                }
                }
            }
        });
        _6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(initial_base.getText().toString().equals(""))) {   if (6 < Integer.parseInt(initial_base.getText().toString())) {
                    Number_value.setText(Number_value.getText().toString() + _6.getText().toString());
                    Number_value.setSelection(Number_value.getText().length());
                }
                }
            }
        });
        _7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(initial_base.getText().toString().equals(""))) {   if (7 < Integer.parseInt(initial_base.getText().toString())) {
                    Number_value.setText(Number_value.getText().toString() + _7.getText().toString());
                    Number_value.setSelection(Number_value.getText().length());
                }
                }
            }
        });
        _8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(initial_base.getText().toString().equals(""))) {   if (8 < Integer.parseInt(initial_base.getText().toString())) {
                    Number_value.setText(Number_value.getText().toString() + _8.getText().toString());
                    Number_value.setSelection(Number_value.getText().length());
                }
                }
            }
        });
        _9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(initial_base.getText().toString().equals(""))) {   if (9 < Integer.parseInt(initial_base.getText().toString())) {
                    Number_value.setText(Number_value.getText().toString() + _9.getText().toString());
                    Number_value.setSelection(Number_value.getText().length());
                }
                }
            }
        });
        dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x=Number_value.getText().toString();
                int l=x.length();
                if(l>0){
                    int ff=0;
                    for(int i=0;i<l;i++)
                        if(x.charAt(i)=='.')
                            ff++;
                       if(ff==0){
                    Number_value.setText(Number_value.getText().toString() + dot.getText().toString());
                    Number_value.setSelection(Number_value.getText().length());

                }}}

        });

            a.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(initial_base.getText().toString().equals(""))) {   if ('A' < (55 + Integer.parseInt(initial_base.getText().toString()))) {
                        Number_value.setText(Number_value.getText().toString() + a.getText().toString());
                        Number_value.setSelection(Number_value.getText().length());
                    }
                    }
                }
            });
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(initial_base.getText().toString().equals(""))) { if ('B' < (55 + Integer.parseInt(initial_base.getText().toString()))) {
                        Number_value.setText(Number_value.getText().toString() + b.getText().toString());
                        Number_value.setSelection(Number_value.getText().length());

                    }
                }}
            });
            c.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(initial_base.getText().toString().equals(""))) { if ('C' < (55 + Integer.parseInt(initial_base.getText().toString()))) {
                        Number_value.setText(Number_value.getText().toString() + c.getText().toString());
                        Number_value.setSelection(Number_value.getText().length());

                    }}
                }
            });
            d.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(initial_base.getText().toString().equals(""))) {if ('D' < (55 + Integer.parseInt(initial_base.getText().toString()))) {
                        Number_value.setText(Number_value.getText().toString() + d.getText().toString());
                        Number_value.setSelection(Number_value.getText().length());
                    }
                }}
            });
            e.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(initial_base.getText().toString().equals(""))) { if ('E' < (55 + Integer.parseInt(initial_base.getText().toString()))) {
                        Number_value.setText(Number_value.getText().toString() + e.getText().toString());
                        Number_value.setSelection(Number_value.getText().length());

                    }}
                }
            });
            f.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(initial_base.getText().toString().equals(""))) {if ('F' < (55 + Integer.parseInt(initial_base.getText().toString()))) {
                        Number_value.setText(Number_value.getText().toString() + f.getText().toString());
                        Number_value.setSelection(Number_value.getText().length());

                    }}
                }
            });
            g.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(initial_base.getText().toString().equals(""))) {if ('G' < (55 + Integer.parseInt(initial_base.getText().toString()))) {
                        Number_value.setText(Number_value.getText().toString() + g.getText().toString());
                        Number_value.setSelection(Number_value.getText().length());

                    }}
                }
            });
            h.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(initial_base.getText().toString().equals(""))) {
                        if ('H' < (55 + Integer.parseInt(initial_base.getText().toString()))) {
                            Number_value.setText(Number_value.getText().toString() + h.getText().toString());
                            Number_value.setSelection(Number_value.getText().length());

                        }
                    } }
            });
            i.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(initial_base.getText().toString().equals(""))) { if ('I' < (55 + Integer.parseInt(initial_base.getText().toString()))) {
                        Number_value.setText(Number_value.getText().toString() + i.getText().toString());
                        Number_value.setSelection(Number_value.getText().length());

                    }}
                }
            });
            j.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(initial_base.getText().toString().equals(""))) { if ('J' < (55 + Integer.parseInt(initial_base.getText().toString()))) {
                        Number_value.setText(Number_value.getText().toString() + j.getText().toString());
                        Number_value.setSelection(Number_value.getText().length());

                    }}
                }
            });
            k.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(initial_base.getText().toString().equals(""))) { if ('K' < (55 + Integer.parseInt(initial_base.getText().toString()))) {
                        Number_value.setText(Number_value.getText().toString() + k.getText().toString());
                        Number_value.setSelection(Number_value.getText().length());

                    }}
                }
            });
            l.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(initial_base.getText().toString().equals(""))) { if ('L' < (55 + Integer.parseInt(initial_base.getText().toString()))) {
                        Number_value.setText(Number_value.getText().toString() + l.getText().toString());
                        Number_value.setSelection(Number_value.getText().length());

                    }}
                }
            });
            m.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(initial_base.getText().toString().equals(""))) {if ('M' < (55 + Integer.parseInt(initial_base.getText().toString()))) {
                        Number_value.setText(Number_value.getText().toString() + m.getText().toString());
                        Number_value.setSelection(Number_value.getText().length());

                    }}
                }
            });
            n.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(initial_base.getText().toString().equals(""))) { if ('N' < (55 + Integer.parseInt(initial_base.getText().toString()))) {
                        Number_value.setText(Number_value.getText().toString() + n.getText().toString());
                        Number_value.setSelection(Number_value.getText().length());

                    }
                }}});
            o.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(initial_base.getText().toString().equals(""))) {if ('O' < (55 + Integer.parseInt(initial_base.getText().toString()))) {
                        Number_value.setText(Number_value.getText().toString() + o.getText().toString());
                        Number_value.setSelection(Number_value.getText().length());

                    }}
                }
            });
            p.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(initial_base.getText().toString().equals(""))) {if ('P' < (55 + Integer.parseInt(initial_base.getText().toString()))) {
                        Number_value.setText(Number_value.getText().toString() + p.getText().toString());
                        Number_value.setSelection(Number_value.getText().length());

                    }}
                }
            });
            q.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(initial_base.getText().toString().equals(""))) {  if ('Q' < (55 + Integer.parseInt(initial_base.getText().toString()))) {
                        Number_value.setText(Number_value.getText().toString() + q.getText().toString());
                        Number_value.setSelection(Number_value.getText().length());

                    }}
                }
            });
            r.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(initial_base.getText().toString().equals(""))) {if ('R' < (55 + Integer.parseInt(initial_base.getText().toString()))) {
                        Number_value.setText(Number_value.getText().toString() + r.getText().toString());
                        Number_value.setSelection(Number_value.getText().length());
                    }
                }}
            });
            s.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(initial_base.getText().toString().equals(""))) {if ('S' < (55 + Integer.parseInt(initial_base.getText().toString()))) {
                        Number_value.setText(Number_value.getText().toString() + s.getText().toString());
                        Number_value.setSelection(Number_value.getText().length());

                    }}
                }
            });
            t.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(initial_base.getText().toString().equals(""))) { if ('T' < (55 + Integer.parseInt(initial_base.getText().toString()))) {
                        Number_value.setText(Number_value.getText().toString() + t.getText().toString());
                        Number_value.setSelection(Number_value.getText().length());

                    }}
                }
            });
            u.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(initial_base.getText().toString().equals(""))) { if ('U' < (55 + Integer.parseInt(initial_base.getText().toString()))) {
                        Number_value.setText(Number_value.getText().toString() + u.getText().toString());
                        Number_value.setSelection(Number_value.getText().length());

                    }}
                }
            });
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(initial_base.getText().toString().equals(""))) {if ('V' < (55 + Integer.parseInt(initial_base.getText().toString()))) {
                        Number_value.setText(Number_value.getText().toString() + "V");
                        Number_value.setSelection(Number_value.getText().length());

                    }}
                }
            });
            w.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(initial_base.getText().toString().equals(""))) {if ('W' < (55 + Integer.parseInt(initial_base.getText().toString()))) {
                        Number_value.setText(Number_value.getText().toString() + w.getText().toString());
                        Number_value.setSelection(Number_value.getText().length());

                    }}
                }
            });
            x.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(initial_base.getText().toString().equals(""))) { if ('X' < (55 + Integer.parseInt(initial_base.getText().toString()))) {
                        Number_value.setText(Number_value.getText().toString() + x.getText().toString());
                        Number_value.setSelection(Number_value.getText().length());

                    }}
                }
            });
            y.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(initial_base.getText().toString().equals(""))) {if ('Y' < (55 + Integer.parseInt(initial_base.getText().toString()))) {
                        Number_value.setText(Number_value.getText().toString() + y.getText().toString());
                        Number_value.setSelection(Number_value.getText().length());

                    }}
                }
            });
            z.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!(initial_base.getText().toString().equals(""))) {if ('Z' < (55 + Integer.parseInt(initial_base.getText().toString()))) {
                        Number_value.setText(Number_value.getText().toString() + z.getText().toString());
                        Number_value.setSelection(Number_value.getText().length());

                    }}
                }
            });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String initial=initial_base.getText().toString();
                String finall=final_base.getText().toString();
                boolean not=false;
                if(!(initial.equals("")||finall.equals("")))
                {
                int ibase=Integer.parseInt(initial_base.getText().toString());
                int fbase=Integer.parseInt(final_base.getText().toString());
                if(((ibase>1&&ibase<37)&&(fbase>1&&fbase<37))) {
                    String number = Number_value.getText().toString();
                    String ans = number;
                    if (ibase != fbase)
                        ans = convert(number, ibase, fbase);
                    if (!incorrect) {
                        String last=(ans.substring(1+ans.indexOf('.')));
                        if(last.length()==1&&last.charAt(0)=='0')
                        answer_view.setText(ans.substring(0,ans.indexOf('.')));
                        else
                            answer_view.setText(ans);

                    }
                    else
                        answer_view.setText("ReEnter");
                }
                else
                    answer_view.setText("Please Enter Base in between 2 to 36");
            }
            else
                    answer_view.setText("Please Enter Base");
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer_view.setText("");
                Number_value.setText("");
                initial_base.setText("");
                final_base.setText("");
            }
        });






    }
}