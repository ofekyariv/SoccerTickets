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

public class SearchGroupActivity extends AppCompatActivity {
    EditText etGroup;
    Button btnSearch;
    RecyclerView recyclerView;
    DatabaseHandler databaseHandler;
    ArrayList<Game> gameArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_group);

        etGroup = (EditText) findViewById(R.id.etGroup);

        btnSearch = (Button) findViewById(R.id.btnSearch);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseHandler = new DatabaseHandler(getApplicationContext());

        btnSearch.setOnClickListener(view -> {
            String group = etGroup.getText().toString();
            setGameAdapter(group);
        });
    }

    private void setGameAdapter(String group) {
        gameArrayList = databaseHandler.SelectGameByGroup(group);
        GameSearchAdapter adapter = new GameSearchAdapter(gameArrayList, SearchGroupActivity.this);
        recyclerView.setAdapter(adapter);
    }
}