package com.example.basiccalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class About extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ImageView insta=findViewById(R.id.insta);
        AppBarLayout appBarLayout = ( AppBarLayout)findViewById(R.id.appbar);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
                    // Collapsed
                    insta.setVisibility(View.GONE);

                }//else if (verticalOffset == 0) {
                    // Expanded
                //}
                else {insta.setVisibility(View.VISIBLE);
                    // Somewhere in between
                }
            }
        });
        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String url="https://www.instagram.com/aniket_kundu_/";
               Uri uri = Uri.parse(url);
               Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                   try{
                        startActivity(intent);
                    }
                    catch (Exception e){
                Toast.makeText(About.this, "Sorry,some error occured", Toast.LENGTH_SHORT).show();
            }}
        });

    }
}