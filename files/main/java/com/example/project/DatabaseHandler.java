package com.example.project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION=1;
	public static final String DATABASE_NAME="game_manager";
	public static final String TABLE_GAME="game";
	public static final String COLUMN_ID="id";
	public static final String COLUMN_CITY="city";
	public static final String COLUMN_DATE="date";
	public static final String COLUMN_GROUPA="groupA";
	public static final String COLUMN_GROUPB="groupB";
	

	public static final String CREATE_TABLE_GAME="CREATE TABLE "+TABLE_GAME+" ("
		        +COLUMN_ID+" INTEGER PRIMARY KEY autoincrement, "
		        +COLUMN_CITY+" TEXT, "
				+COLUMN_DATE+" TEXT, "
				+COLUMN_GROUPA+" TEXT, "
		        +COLUMN_GROUPB+" TEXT)";

	public static final String DELETE_GAMES =
	        "DROP TABLE IF EXISTS " + TABLE_GAME;
	SQLiteDatabase db;
	
	public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
		db=this.getWritableDatabase();
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_GAME);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		// TODO Auto-generated method stub
		db.execSQL(DELETE_GAMES);
		onCreate(db);
	}
	
	
	public long InsertGame(Game game)
	{
		ContentValues values=new ContentValues();
		values.put(COLUMN_CITY, game.getCity());
		values.put(COLUMN_DATE, game.getDate());
		values.put(COLUMN_GROUPA, game.getGroupA());
		values.put(COLUMN_GROUPB, game.getGroupB());
		long id=db.insert(TABLE_GAME, null, values);
		return id;
	}
	
	public int UpdateGame(int id, Game game)
	{
		ContentValues values=new ContentValues();
		values.put(COLUMN_CITY, game.getCity());
		values.put(COLUMN_DATE, game.getDate());
		values.put(COLUMN_GROUPA, game.getGroupA());
		values.put(COLUMN_GROUPB, game.getGroupB());
		int count=db.update(TABLE_GAME, values, COLUMN_ID+" = ?", new String[]{String.valueOf(id)});
		return count;
	}

	public int deleteGame(int id)
	{
		int count=db.delete(TABLE_GAME, COLUMN_ID+" = ?", new String[]{String.valueOf(id)});
		return count;
	}
	
	public ArrayList<Game> SelectGameByDate(String date)
	{
		String[] projection={COLUMN_ID,COLUMN_CITY,COLUMN_DATE,COLUMN_GROUPA,COLUMN_GROUPB};
		Cursor cursor = db.query(
			    TABLE_GAME,
			    projection,
				COLUMN_DATE+ "=?",
				new String[]{date},
			    null,
			    null,
			    null
			    );
		ArrayList<Game> gameList =new ArrayList<Game>();
		if(cursor.moveToFirst())
		{
			do{
				gameList.add(cursorToGame(cursor));
			}while(cursor.moveToNext());
		}
		return gameList;
	}
	public ArrayList<Game> SelectGameByGroup(String group)
	{
		String[] projection={COLUMN_ID,COLUMN_CITY,COLUMN_DATE,COLUMN_GROUPA,COLUMN_GROUPB};
		Cursor cursor = db.query(
				TABLE_GAME,
				projection,
				COLUMN_GROUPA+ "=?",
				new String[]{group},
				null,
				null,
				null
		);
		ArrayList<Game> gameList =new ArrayList<Game>();
		if(cursor.moveToFirst())
		{
			do{
				gameList.add(cursorToGame(cursor));
			}while(cursor.moveToNext());
		}
		cursor = db.query(
				TABLE_GAME,
				projection,
				COLUMN_GROUPB+ "=?",
				new String[]{group},
				null,
				null,
				null
		);
		if(cursor.moveToFirst())
		{
			do{
				gameList.add(cursorToGame(cursor));
			}while(cursor.moveToNext());
		}
		return gameList;
	}
	
	public ArrayList<Game> SelectAllGames()
	{
		String[] projection={COLUMN_ID,COLUMN_CITY,COLUMN_DATE,COLUMN_GROUPA,COLUMN_GROUPB};
		Cursor cursor = db.query(
			    TABLE_GAME,
			    projection,
			    null,
			    null,
			    null,
			    null,
			    null
			    );
		ArrayList<Game> gameList =new ArrayList<Game>();
		if(cursor.moveToFirst())
		{
			do{
				gameList.add(cursorToGame(cursor));
			}while(cursor.moveToNext());
		}
		return gameList;
	}

	private Game cursorToGame(Cursor cursor) {
		Game game =new Game();
		game.setId((int)cursor.getLong( cursor.getColumnIndexOrThrow(COLUMN_ID)));
		game.setCity(cursor.getString( cursor.getColumnIndexOrThrow(COLUMN_CITY)));
		game.setDate(cursor.getString( cursor.getColumnIndexOrThrow(COLUMN_DATE)));
		game.setGroupA(cursor.getString( cursor.getColumnIndexOrThrow(COLUMN_GROUPA)));
		game.setGroupB(cursor.getString( cursor.getColumnIndexOrThrow(COLUMN_GROUPB)));
		return game;
	}
}
