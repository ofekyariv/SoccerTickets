package com.example.project;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etCity, etGroupA, etGroupB;
    DatePicker datePicker;
    Button btnSave, btnDelete, btnCancel, btnGroupSearch, btnDateSearch;
    RecyclerView recyclerView;
    DatabaseHandler databaseHandler;
    ArrayList<Game> gameArrayList;
    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCity = (EditText) findViewById(R.id.etCity);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        etGroupA = (EditText) findViewById(R.id.etGroupA);
        etGroupB = (EditText) findViewById(R.id.etGroupB);

        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnGroupSearch = (Button) findViewById(R.id.btnGroupSearch);
        btnDateSearch = (Button) findViewById(R.id.btnDateSearch);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseHandler = new DatabaseHandler(getApplicationContext());
        setGameAdapter();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String city = etCity.getText().toString();
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int day = datePicker.getDayOfMonth();
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
                String date = format.format(calendar.getTime());
                String groupA = etGroupA.getText().toString();
                String groupB = etGroupB.getText().toString();
                String command = btnSave.getText().toString();
                if (command.equals("Save")) {
                    game = new Game(city, date, groupA, groupB);
                    insertGame(game);
                } else if (game != null) {
                    game.setCity(city);
                    game.setDate(date);
                    game.setGroupA(groupA);
                    game.setGroupB(groupB);
                    updateGame(game);
                }
            }
        });

        btnDelete.setOnClickListener(view -> {
            if (game != null) {
                deleteGame(game);
            }
        });

        btnCancel.setOnClickListener(view -> resetAllViews());
        btnGroupSearch.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SearchGroupActivity.class);
            startActivity(intent);
        });
        btnDateSearch.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, SearchDateActivity.class);
            startActivity(intent);
        });
    }

    private void deleteGame(Game game) {
        int count = databaseHandler.deleteGame(game.getId());
        if (count > 0) {
            Toast.makeText(MainActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
            resetAllViews();
            setGameAdapter();
        }
    }

    private void updateGame(Game game) {
        int count = databaseHandler.UpdateGame(game.getId(), game);
        if (count > 0) {
            Toast.makeText(MainActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
            resetAllViews();
            setGameAdapter();
        }
    }

    private void setGameAdapter() {
        gameArrayList = databaseHandler.SelectAllGames();
        GameAdapter adapter = new GameAdapter(gameArrayList, MainActivity.this);
        recyclerView.setAdapter(adapter);
    }

    private void insertGame(Game game) {
        long id = databaseHandler.InsertGame(game);
        if (id > 0) {
            Toast.makeText(MainActivity.this, "Saved Successfully", Toast.LENGTH_SHORT).show();
            resetAllViews();
            setGameAdapter();
        }

    }

    private void resetAllViews() {
        etCity.setText("");
        etGroupA.setText("");
        etGroupB.setText("");
        etCity.requestFocus();
        btnSave.setText("Save");
        btnDelete.setVisibility(View.GONE);
        game = null;
    }

    public void setGameData(Game game) {
        this.game = game;
        etCity.setText(game.getCity());
        String date = game.getDate();
        if(date!=null){
            int year = Integer.parseInt(date.substring(6,10));
            int month = Integer.parseInt(date.substring(0,2))-1;
            int day = Integer.parseInt(date.substring(3,5));
            datePicker.updateDate(year,month,day);
        }
        etGroupA.setText(game.getGroupA());
        etGroupB.setText(game.getGroupB());
        btnSave.setText("Update");
        btnDelete.setVisibility(View.VISIBLE);
    }
}
