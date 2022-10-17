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
import android.widget.Toast;

import java.util.Calendar;

public class AddExpenseActivity extends AppCompatActivity {
    String trip_id;
    private EditText Amount;
    private EditText Time;
    private RadioButton buttonTravel;
    private RadioButton buttonFood;
    private RadioButton buttonTransport;
    private Button addExpense;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        Amount = findViewById(R.id.amount);
        Time = findViewById(R.id.time);
        buttonFood = findViewById(R.id.food);
        buttonTravel = findViewById(R.id.travel);
        buttonTransport = findViewById(R.id.transport);
        trip_id = getIntent().getStringExtra("get_id");
        addExpense = findViewById(R.id.addExpense);
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        Time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(AddExpenseActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day+"/"+month+"/"+year;
                        Time.setText(date);
                    }
                },year,month,day);
                dialog.show();
            }
        });
        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(error()){
                    String outMount =Amount.getText().toString();
                    String outTime = Time.getText().toString();
                    int button = 0;
                    if(buttonFood.isChecked()){
                        button = 1;
                        showAlertDiaLog("Type: "+"Food"+"\n"+
                                "Amount: "+outMount+"\n"+
                                "Destination:"+" "+outTime+"\n"+
                                "Id_trip"+trip_id
                        );
                        Database myDb = new Database(AddExpenseActivity.this);
                        myDb.addExpense(trip_id,
                                button,
                                Amount.getText().toString().trim(),
                                Time.getText().toString().trim());
                    }
                    else if(buttonTransport.isChecked()){
                        button = 2;
                        showAlertDiaLog("Type: "+"Transport"+"\n"+
                                "Amount: "+outMount+"\n"+
                                "Destination:"+" "+outTime+"\n"
                        );
                        Database myDb = new Database(AddExpenseActivity.this);
                        myDb.addExpense(trip_id,
                                button,
                                Amount.getText().toString().trim(),
                                Time.getText().toString().trim());
                    }
                    else if(buttonTravel.isChecked()){
                        button = 3;
                        showAlertDiaLog("Type: "+"Travel"+"\n"+
                                "Amount: "+outMount+"\n"+
                                "Destination:"+" "+outTime+"\n"
                        );
                        Database myDb = new Database(AddExpenseActivity.this);
                        myDb.addExpense(trip_id,
                                button,
                                Amount.getText().toString().trim(),
                                Time.getText().toString().trim());
                    }

                }
            }
        });
        //getTripId();
    }

    private void showAlertDiaLog(String message){
        AlertDialog dialog = new AlertDialog.Builder(AddExpenseActivity.this).
                setTitle("Expense of trip:")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(AddExpenseActivity.this,"New expenses of trip added successfully", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(AddExpenseActivity.this,ExpensesActivity.class));
                    }
                }).create();
        dialog.show();
    }
//    void getTripId() {
//        if (getIntent().hasExtra("id")) {
//            trip_id = getIntent().getStringExtra("id");
//            Toast.makeText(this,"Id co roi day", Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(this,"No Id Trip", Toast.LENGTH_LONG).show();
//        }
//    }

    private boolean error(){
        if (Amount.getText().toString().length() == 0){
            Toast.makeText(AddExpenseActivity.this,"Please enter amount", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(Time.getText().toString().length() == 0){
            Toast.makeText(AddExpenseActivity.this,"Please enter time of amount", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!buttonFood.isChecked() && !buttonTransport.isChecked() && !buttonTravel.isChecked()){
            Toast.makeText(AddExpenseActivity.this,"Please select type of amount", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}