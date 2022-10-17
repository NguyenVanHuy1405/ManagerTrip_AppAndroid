package com.example.final_code;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ExpensesActivity extends AppCompatActivity {
    FloatingActionButton add_expense_button;
    RecyclerView recyclerView;
    ArrayList<String> Id_expense, Type, Amount, Time, Trip_id;
    String trip_id;
    ExpensesAdapter expensesAdapter;
    Database myData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);
        recyclerView = findViewById(R.id.recyclerviewExpense);
        trip_id = getIntent().getStringExtra("tripid");
        add_expense_button = findViewById(R.id.addExpenseButton);
        add_expense_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ExpensesActivity.this, AddExpenseActivity.class);
                intent.putExtra("get_id", trip_id);
                startActivity(intent);
            }
        });
        Id_expense = new ArrayList<>();
        Type = new ArrayList<>();
        Amount = new ArrayList<>();
        Time = new ArrayList<>();
        Trip_id = new ArrayList<>();

        //get all data expense
//        getData();
        expensesAdapter = new ExpensesAdapter(ExpensesActivity.this, Id_expense, Type, Amount, Time, Trip_id);
        recyclerView.setAdapter(expensesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ExpensesActivity.this));
    }
    void getData(){
        Cursor cursor = myData.expenseData();
        if(cursor.getCount() == 0){
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }else{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                Id_expense.add(cursor.getString(0));
                Type.add(cursor.getString(1));
                Amount.add(cursor.getString(2));
                Time.add(cursor.getString(3));
                Trip_id.add(cursor.getString(4));
                cursor.moveToNext();
            }
        }
    }


}