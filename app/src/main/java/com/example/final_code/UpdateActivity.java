package com.example.final_code;

import androidx.appcompat.app.ActionBar;
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
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.Calendar;

public class UpdateActivity extends AppCompatActivity {
    EditText editName, editDestination, editDate, editDescription;
    RadioButton editButtonYes, editButtonNo;
    ImageView delete;
    Button update;
    Button Expenses;
    String radiobutton ="0";
    String id,name,destination,date,risks,description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        editName = findViewById(R.id.edit_name);
        editDestination = findViewById(R.id.edit_destination);
        editDate = findViewById(R.id.edit_etSelectDate);
        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        editDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(UpdateActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day+"/"+month+"/"+year;
                        editDate.setText(date);
                    }
                },year,month,day);
                dialog.show();
            }
        });
        editDescription = findViewById(R.id.edit_description);
        editButtonYes = findViewById(R.id.edit_buttonYes);
        editButtonNo = findViewById(R.id.edit_buttonNo);
        update = findViewById(R.id.edit);
        delete = findViewById(R.id.btn_delete);
        Expenses = findViewById(R.id.expense);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (error()){
                    name=editName.getText().toString().trim();
                    destination = editDestination.getText().toString().trim();
                    date = editDate.getText().toString().trim();
                    description = editDescription.getText().toString().trim();
                    if(editButtonYes.isChecked()){
                        risks = "1";
                        Database myDb = new Database(UpdateActivity.this);
                        myDb.updateDatabase(id, name, destination, date, risks, description);
                    }
                    else if(editButtonNo.isChecked()){
                        risks ="0";
                        Database myDb = new Database(UpdateActivity.this);
                        myDb.updateDatabase(id, name, destination, date, risks, description);
                    }
                }
            }
        });
        Expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateActivity.this,ExpensesActivity.class);
                intent.putExtra("tripid", id);
                startActivity(intent);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });
        getAndSetIntentDate();
        ActionBar ab = getSupportActionBar();
        ab.setTitle(name);
    }

    void getAndSetIntentDate(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("name")
                && getIntent().hasExtra("destination") && getIntent().hasExtra("date")
                && getIntent().hasExtra("risks") && getIntent().hasExtra("description")){
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("name");
            destination = getIntent().getStringExtra("destination");
            date = getIntent().getStringExtra("date");
            risks = getIntent().getStringExtra("risks");
            description = getIntent().getStringExtra("description");

            //setData

            editName.setText(name);
            editDestination.setText(destination);
            editDate.setText(date);
            editDescription.setText(description);
            if(risks.equals("1")){
                editButtonYes.setChecked(true);
            }
            else if(risks.equals("0")){
                editButtonNo.setChecked(true);
            }

        }
        else{
            Toast.makeText(this,"No data", Toast.LENGTH_LONG).show();
        }
    }
    private boolean error(){
        if (editName.getText().toString().length() == 0){
            Toast.makeText(UpdateActivity.this,"Please enter your name", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(editDestination.getText().toString().length() == 0){
            Toast.makeText(UpdateActivity.this,"Please enter your destination", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(editDate.getText().toString().length() == 0){
            Toast.makeText(UpdateActivity.this,"Please choose date your trip", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(editDescription.getText().toString().length() == 0){
            Toast.makeText(UpdateActivity.this,"Please enter your description", Toast.LENGTH_LONG).show();
            return false;
        }
        else if(!editButtonYes.isChecked() && !editButtonNo.isChecked()){
            Toast.makeText(UpdateActivity.this,"Please select your required risks", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
    void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + name + " ? ");
        builder.setMessage("Are you sure want to delete " + name + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Database myDb = new Database(UpdateActivity.this);
                myDb.deleteOnRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}