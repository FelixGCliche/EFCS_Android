package com.v41.efcs.service;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.v41.efcs.model.entity.PreferencesData;
import com.v41.efcs.service.database.PreferencesTable;

public class PreferencesRepository implements Repository<PreferencesData> {
  private SQLiteDatabase database;

  public PreferencesRepository(SQLiteDatabase database) {
    this.database = database;
  }

  /**
   * Insertion de préférences dans la BD
   * @param obj préférences à insérer
   * @return Vrai si insertion réussite, sinon faux
   */
  @Override
  public boolean insert(PreferencesData obj) {
    Cursor cursor = null;
    try {
      database.beginTransaction();
      database.execSQL(PreferencesTable.INSERT_SQL, obj.getTransactionableData());
      database.setTransactionSuccessful();
      return true;
    } catch (Exception e) {
      return false;
    } finally {
      database.endTransaction();
    }
  }

  /**
   * Trouve une préférence dans la BD
   * @param id ID de la préférence à trouver
   * @return Préférence cherchée, null si introuvable
   */
  @Override
  public PreferencesData find(long id) {
    Cursor cursor = null;
    try {
      database.beginTransaction();
      cursor = database.rawQuery(PreferencesTable.SELECT_ONE_OF_SQL, new String[]{String.valueOf(id)});
      cursor.moveToNext();
      double[] zoneLimits = new double[]{
              Double.parseDouble(cursor.getString(4)),
              Double.parseDouble(cursor.getString(5)),
              Double.parseDouble(cursor.getString(6)),
              Double.parseDouble(cursor.getString(7))
      };

      return new PreferencesData(
              cursor.getInt(0),
              Integer.parseInt(cursor.getString(1)),
              Double.parseDouble(cursor.getString(2)),
              Double.parseDouble(cursor.getString(3)),
              zoneLimits);
    } catch (Exception e) {
      return null;
    } finally {
      if (cursor != null)
        cursor.close();

      database.endTransaction();
    }
  }

  /**
   * Retourne la dernière préférence insérée dans la BD
   * @return Dernière préférence insérée, sinon null si BD vide
   */
  @Override
  public PreferencesData findLast() {
    Cursor cursor = null;
    try {
      database.beginTransaction();
      cursor = database.rawQuery(PreferencesTable.SELECT_LAST_OF_SQL, new String[]{});
      cursor.moveToNext();

      double[] zoneLimits = new double[]{
              Double.parseDouble(cursor.getString(4)),
              Double.parseDouble(cursor.getString(5)),
              Double.parseDouble(cursor.getString(6)),
              Double.parseDouble(cursor.getString(7))
      };

      return new PreferencesData(
              cursor.getInt(0),
              Integer.parseInt(cursor.getString(1)),
              Double.parseDouble(cursor.getString(2)),
              Double.parseDouble(cursor.getString(3)),
              zoneLimits);
    } catch (Exception e) {
      return null;
    } finally {
      if (cursor != null)
        cursor.close();

      database.endTransaction();
    }
  }

  /**
   * Met à jour une préférence de la BD
   * @param obj Nouvelle préférence
   * @return Vrai si mise à jour réussie, sinon faux
   */
  @Override
  public boolean save(PreferencesData obj) {
    Cursor cursor = null;
    try {
      database.beginTransaction();
      database.execSQL(PreferencesTable.UPDATE_SQL, obj.getUpdateData());
      database.setTransactionSuccessful();
      return true;
    } catch (Exception e) {
      return false;
    } finally {
      database.endTransaction();
    }
  }

  /**
   * Supprime une préférence de la BD
   * @param obj Préférence à supprimer
   * @return Vrai si suppression réussie, sinon faux
   */
  @Override
  public boolean delete(PreferencesData obj) {
    Cursor cursor = null;
    try {
      database.beginTransaction();
      database.execSQL(PreferencesTable.DELETE_SQL, new String[]{String.valueOf(obj.getId())});
      database.setTransactionSuccessful();
      return true;
    } catch (Exception e) {
      return false;
    } finally {
      database.endTransaction();
    }
  }

  /**
   * Supprime une préférence de la BD
   * @param id id de la préférence à supprimer
   * @return Vrai si suppression réussie, sinon faux
   */
  @Override
  public boolean delete(long id) {
    try {
      database.beginTransaction();
      database.execSQL(PreferencesTable.DELETE_SQL, new String[]{String.valueOf(id)});
      database.setTransactionSuccessful();
      return true;
    } catch (Exception e) {
      return false;
    } finally {
      database.endTransaction();
    }
  }
}
