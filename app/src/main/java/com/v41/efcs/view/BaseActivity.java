package com.v41.efcs.view;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.v41.efcs.R;
import com.v41.efcs.model.entity.PreferencesData;
import com.v41.efcs.model.entity.SensorData;
import com.v41.efcs.service.PreferencesRepository;
import com.v41.efcs.service.Repository;
import com.v41.efcs.service.database.ApplicationDatabaseHelper;

public class BaseActivity extends AppCompatActivity {

  //Le nom de la base de données
  public static final String DATABASE_FILE_NAME = "SensorsDB";
  //Le helper qui permet la connexion à la base de données
  private ApplicationDatabaseHelper applicationDatabaseHelper;
  //La base de données
  private SQLiteDatabase database;

  /**
   * Retourne le nombre d'entrées d'un senseur
   * @param sensorData Senseur à vérifier
   * @return Nombre d'entrées du senseur
   */
  protected int getSensorDataCount(SensorData sensorData) {
    return sensorData.getValues().length;
  }

  /**
   * Méthode qui obtient une base de données en écriture via le ApplicationDatabaseHelper
   * @return un SQLiteDatabase qui représente l'instance de la base de données, la même instance si déjà active
   */
  protected SQLiteDatabase getDatabase() {
    if (applicationDatabaseHelper == null) {
      applicationDatabaseHelper = new ApplicationDatabaseHelper(this, DATABASE_FILE_NAME);
      database = applicationDatabaseHelper.getWritableDatabase();
    }
    return database;
  }

  /**
   * Retourne un extra de type Parcelable associé le nom en paramètre
   * @param name nom de l'extra
   * @return Parcelable associé au nom s'il existe, sinon null
   */
  protected Parcelable getIntentParcelable(String name) {
    if (getIntent().hasExtra(name))
      return getIntent().getParcelableExtra(name);
    else
      return null;

  }

  /**
   * Transforme un tableau de Double en tableau de double
   * @param doubles Tableau de doubles
   * @return Tableau de doubles
   */
  protected double[] toPrimitiveArray(Double[] doubles) {
    double[] primitives = new double[doubles.length];
    for (int i = 0; i < doubles.length; i++) {
      primitives[i] = ((double) doubles[i]);
    }
    return primitives;
  }

  /**
   * Méthode qui gère le onResume de l'activité en redonnant un nouvelle connexion à la base de données
   */
  @Override
  protected void onResume() {
    super.onResume();
    applicationDatabaseHelper = new ApplicationDatabaseHelper(this, DATABASE_FILE_NAME);
    database = applicationDatabaseHelper.getWritableDatabase();
  }

  /**
   * Méthode qui gère le onPause de l'activité en fermant la connexion de base de données si active
   */
  @Override
  protected void onPause() {
    super.onPause();

    if (database != null) {
      database.close();
      database = null;
    }
    applicationDatabaseHelper = null;

  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case R.id.preferences:
        Intent intent = new Intent(this, PreferencesActivity.class);
        startActivity(intent);
        return(true);
    }
    return(super.onOptionsItemSelected(item));
  }
}
