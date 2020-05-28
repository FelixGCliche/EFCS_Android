package com.v41.efcs.service.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ApplicationDatabaseHelper extends SQLiteOpenHelper {

  //Méthode utile
  public static final String SELECT_LAST_ID_SQL = "" +
          "SELECT last_insert_rowid()";

  private static final int DATABASE_VERSION = 1;

  public ApplicationDatabaseHelper(Context context, String fileName) {
    super(context, fileName, null, DATABASE_VERSION);
  }

  @Override
  public void onCreate(SQLiteDatabase db) {
    db.execSQL(PreferencesTable.CREATE_TABLE_SQL);
    db.execSQL(PreferencesTable.INSERT_SQL, new String[]
            {
                    "1",
                    Double.toString(28.33),
                    Double.toString(79.53),
                    Double.toString(20.26),
                    Double.toString(22.42),
                    Double.toString(24.58),
                    Double.toString(26.74)
            });
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    db.execSQL(PreferencesTable.DROP_TABLE_SQL);
    onCreate(db);
  }
}
