package com.example.simpleaddapp;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

public class MainActivity extends MenuActivity {

    static int num1;
    static int num2;
    static int result;
    static String userInput;
    static int resultUserInput;
    static int doneEx;
    static int Exp;

    static boolean activityForeground;

    static TextView num1TextView;
    static TextView num2TextView;
    static TextView operator;
    static TextView experienceView;

    static Button addBtn;

    static String correct = "Goed! Volgende";
    static String incorrect = "Probeer opnieuw";
    static String defaultBtnText = "Controleer";

    static String defaultColor = "#bfe6f2";
    static String correctColor = "#21ff7d";
    static String incorrectColor = "#ff6142";


    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference myRef = database.getReference("Experience");


    static void newExercise() {
        if (MathOp=='+') {
            num1 = new Random().nextInt(maxRand) + 1;
            num2 = new Random().nextInt(maxRand) + 1;
            result = num1 + num2;
        }
        else if (MathOp=='-') {
            num1 = new Random().nextInt(maxRand) + 1;
            num2 = num1 - new Random().nextInt(num1);
            result = num1 - num2;
        }
        else if (MathOp=='x') {
            num1 = new Random().nextInt(maxRand) + 1;
            num2 = new Random().nextInt(maxRand) + 1;
            result = num1 * num2;
        }
        else if (MathOp=='/') {
            num2 = new Random().nextInt(maxRand) + 1;
            num1 = num2 * new Random().nextInt(maxRand);

            result = num1 / num2;
        }
        Log.d("math", Integer.toString(result));


        num1TextView.setText(num1 + "");
        num2TextView.setText(num2 + "");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        operator = (TextView) findViewById(R.id.Operator);
        num1TextView = (TextView) findViewById(R.id.num1TextView);
        num2TextView = (TextView) findViewById(R.id.num2TextView);
        addBtn = (Button) findViewById(R.id.AddBtn);
        addBtn.getBackground().setColorFilter(Color.parseColor(defaultColor), PorterDuff.Mode.DARKEN);
        experienceView = (TextView) findViewById(R.id.ExperienceView);

        Exp = getValue("Exp");
        experienceView.setText(Integer.toString(getValue("Exp")));
        newExercise();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resultEditText = (EditText) findViewById(R.id.resultEditText);
                userInput = resultEditText.getText().toString();

                if(userInput.equals(null) || userInput.equals("")) {
                    addBtn.setText("geen nummer ingevuld");
                    addBtn.getBackground().setColorFilter(Color.parseColor(incorrectColor), PorterDuff.Mode.DARKEN);
                } else {
                    resultUserInput = Integer.parseInt(resultEditText.getText().toString());

                    if (result == resultUserInput && addBtn.getText().toString().contains(correct)) {
                        Exp++;
                        setPrefValue("Exp",Exp);
                        experienceView.setText(Integer.toString(getValue("Exp")));
                        // write to firebase database
                        myRef.setValue(Exp);
                        newExercise();
                        addBtn.setText(defaultBtnText);
                        resultEditText.setText("");
                        addBtn.getBackground().setColorFilter(Color.parseColor(defaultColor), PorterDuff.Mode.DARKEN);
                    } else if (result == resultUserInput) {
                        addBtn.setText(correct);
                        addBtn.getBackground().setColorFilter(Color.parseColor(correctColor), PorterDuff.Mode.DARKEN);
                    } else {
                        addBtn.setText(incorrect);
                        resultEditText.setText("");
                        addBtn.getBackground().setColorFilter(Color.parseColor(incorrectColor), PorterDuff.Mode.DARKEN);
                    }
                }
            }

        });
    }

    public int getValue(String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        return prefs.getInt(key, 0);
    }

    public void setPrefValue(String key,int newValue) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, Exp);
        editor.commit();
    }

}
