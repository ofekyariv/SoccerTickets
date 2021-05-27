package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class SearchDateActivity extends AppCompatActivity {
    DatePicker datePicker;
    Button btnSearch;
    RecyclerView recyclerView;
    DatabaseHandler databaseHandler;
    ArrayList<Game> gameArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_date);

        datePicker = (DatePicker) findViewById(R.id.datePicker);

        btnSearch = (Button) findViewById(R.id.btnSearch);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseHandler = new DatabaseHandler(getApplicationContext());

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
                String date = format.format(calendar.getTime());
                setGameAdapter(date);
                //search
            }
        });
    }

    private void setGameAdapter(String date) {
        gameArrayList = databaseHandler.SelectGameByDate(date);
        GameSearchAdapter adapter = new GameSearchAdapter(gameArrayList, SearchDateActivity.this);
        recyclerView.setAdapter(adapter);
    }
}
