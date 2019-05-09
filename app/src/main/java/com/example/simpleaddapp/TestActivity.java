package com.example.simpleaddapp;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.Random;

public class TestActivity extends MainActivity {

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
        setContentView(R.layout.activity_test);

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
}
