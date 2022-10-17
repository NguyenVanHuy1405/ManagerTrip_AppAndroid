package com.example.final_code;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    private EditText etSelectDate;
    private EditText name;
    private EditText destination;
    private RadioButton buttonYes;
    private RadioButton buttonNo;
    private android.widget.RadioGroup RadioGroup;
    private EditText description;
    private Button add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        etSelectDate = findViewById(R.id.etSelectDate);
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        etSelectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day+"/"+month+"/"+year;
                        etSelectDate.setText(date);
                    }
                },year,month,day);
                dialog.show();
            }
        });

        name = findViewById(R.id.name);
        destination = findViewById(R.id.destination);
        etSelectDate = findViewById(R.id.etSelectDate);
        buttonYes = findViewById(R.id.buttonYes);
        buttonNo = findViewById(R.id.buttonNo);
        description =findViewById(R.id.description);
        add = findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (error()) {
                    String outName =name.getText().toString();
                    String outDestination = destination.getText().toString();
                    String outDate = etSelectDate.getText().toString();
                    int button = 0;
                    String outDescription = description.getText().toString();
                    if(buttonYes.isChecked()){
                        showAlertDiaLog("Name:"+" "+outName+"\n"+
                                "Destination:"+" "+outDestination+"\n"+
                                "Date trip:"+" "+outDate+"\n"+
                                "Require Risks Assessment:"+" "+"Yes"+"\n"+
                                "Description:"+" "+outDescription
                        );
                        button = 1;
                        Database myDb = new Database(AddActivity.this);
                        myDb.addTrip(name.getText().toString().trim(),
                                destination.getText().toString().trim(),
                                etSelectDate.getText().toString().trim(),
                                button,
                                description.getText().toString().trim());
                    }
                    else{
                        showAlertDiaLog("Name:"+" "+outName+"\n"+
                                "Destination:"+" "+outDestination+"\n"+
                                "Date trip:"+" "+outDate+"\n"+
                                "Require Risks Assessment:"+" "+"No"+"\n"+
                                "Description:"+" "+outDescription
                        );
                        button = 0;
                        Database myDb = new Database(AddActivity.this);
                        myDb.addTrip(name.getText().toString().trim(),
                                destination.getText().toString().trim(),
                                etSelectDate.getText().toString().trim(),
                                button,
                                description.getText().toString().trim());
                    }
                }
            }
        });
    }
    private void showAlertDiaLog(String message){
        AlertDialog dialog = new AlertDialog.Builder(AddActivity.this).
                setTitle("Trip information:")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(AddActivity.this,"New trip added successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AddActivity.this,MainActivity.class));
                    }
                }).create();
        dialog.show();
    }
    private boolean error(){
        if (name.getText().toString().length() == 0){
            Toast.makeText(AddActivity.this,"Please enter your name", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(destination.getText().toString().length() == 0){
            Toast.makeText(AddActivity.this,"Please enter your destination", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(etSelectDate.getText().toString().length() == 0){
            Toast.makeText(AddActivity.this,"Please choose date your trip", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(description.getText().toString().length() == 0){
            Toast.makeText(AddActivity.this,"Please enter your description", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!buttonYes.isChecked() && !buttonNo.isChecked()){
            Toast.makeText(AddActivity.this,"Please select your required risks", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}