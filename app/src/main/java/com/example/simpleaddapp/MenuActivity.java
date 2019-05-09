package com.example.simpleaddapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MenuActivity extends AppCompatActivity {
    static char MathOp = '+';
    static int maxRand = 50;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.commonmenus,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent = new Intent(this, MainActivity.class);



        if(id==R.id.mnuAdd) {
            MathOp = '+';
            maxRand = 50;
        }
        else if(id==R.id.mnuSub) {
            MathOp = '-';
            maxRand = 50;
        }
        else if(id==R.id.mnuMul) {
            MathOp = 'x';
            maxRand = 10;
        }
        else if(id==R.id.mnuDiv) {
            MathOp = '/';
            maxRand = 10;
        }
        Log.d("math", Character.toString(MathOp));
        MainActivity.newExercise();
        MainActivity.operator.setText(Character.toString(MathOp));


        return super.onOptionsItemSelected(item);
    }
}
