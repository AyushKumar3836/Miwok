package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView numbers=(TextView) findViewById(R.id.numbers);
        TextView family=(TextView) findViewById(R.id.family);
        TextView phrases=(TextView) findViewById(R.id.phrases);
        TextView colors=(TextView) findViewById(R.id.colors);
        numbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,NumbersActivity.class);
                startActivity(i);
            }
        });
        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,FamilyActivity.class);
                startActivity(i);
            }
        });
        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,PhrasesActivity.class);
                startActivity(i);
            }
        });
        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,ColorsActivity.class);
                startActivity(i);
            }
        });

    }



  /*public void openNumbersList(View view)
   {
       Intent i=new Intent(this,NumbersActivity.class);
       startActivity(i);
   }*/



}






