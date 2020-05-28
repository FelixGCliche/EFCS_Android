package com.v41.efcs.service.database;

public final class PreferencesTable {

  public static final String CREATE_TABLE_SQL = "" +
          "CREATE TABLE preferences (" +
          "       id  INTEGER PRIMARY KEY AUTOINCREMENT, " +
          "       keep_preferences  TEXT, " +
          "       temp_low_limit  TEXT, " +
          "       temp_high_limit TEXT, " +
          "       humidity_zone_1_limit TEXT, " +
          "       humidity_zone_2_limit TEXT, " +
          "       humidity_zone_3_limit TEXT, " +
          "       humidity_zone_4_limit TEXT " +
          ")";

  public static final String DROP_TABLE_SQL = "" +
          "DROP TABLE preferences";

  public static final String INSERT_SQL = "" +
          "INSERT INTO preferences ( " +
          "        keep_preferences, " +
          "        temp_low_limit, " +
          "        temp_high_limit, " +
          "        humidity_zone_1_limit, " +
          "        humidity_zone_2_limit, " +
          "        humidity_zone_3_limit, " +
          "        humidity_zone_4_limit " +
          ") VALUES ( " +
          "        ?, " +
          "        ?, " +
          "        ?, " +
          "        ?, " +
          "        ?, " +
          "        ?, " +
          "        ? " +
          ")";

  public static final String SELECT_ALL_OF_SQL = "" +
          "SELECT " +
          "        preferences.id, " +
          "        preferences.keep_preferences, " +
          "        preferences.temp_low_limit, " +
          "        preferences.temp_high_limit, " +
          "        preferences.humidity_zone_1_limit, " +
          "        preferences.humidity_zone_2_limit, " +
          "        preferences.humidity_zone_3_limit, " +
          "        preferences.humidity_zone_4_limit " +
          "FROM " +
          "        preferences ";



  public static final String SELECT_ONE_OF_SQL = "" +
          "SELECT " +
          "        preferences.id, " +
          "        preferences.keep_preferences, " +
          "        preferences.temp_low_limit, " +
          "        preferences.temp_high_limit, " +
          "        preferences.humidity_zone_1_limit, " +
          "        preferences.humidity_zone_2_limit, " +
          "        preferences.humidity_zone_3_limit, " +
          "        preferences.humidity_zone_4_limit " +
          "FROM " +
          "        preferences " +
          "WHERE " +
          "        preferences.id = ?";


  public static final String SELECT_LAST_OF_SQL = "" +
          "SELECT " +
          "        MAX(id), " +
          "        preferences.keep_preferences, " +
          "        preferences.temp_low_limit, " +
          "        preferences.temp_high_limit, " +
          "        preferences.humidity_zone_1_limit, " +
          "        preferences.humidity_zone_2_limit, " +
          "        preferences.humidity_zone_3_limit, " +
          "        preferences.humidity_zone_4_limit " +
          "FROM " +
          "preferences ";


  public static final String UPDATE_SQL = "" +
          "UPDATE preferences " +
          "SET " +
          "        keep_preferences = ?, " +
          "        temp_low_limit = ?, " +
          "        temp_high_limit = ?, " +
          "        humidity_zone_1_limit = ?, " +
          "        humidity_zone_2_limit = ?, " +
          "        humidity_zone_3_limit = ?, " +
          "        humidity_zone_4_limit = ? " +
          "WHERE " +
          "        id = ?";

  public static final String DELETE_SQL = "" +
          "DELETE FROM preferences " +
          "WHERE " +
          "        id = ?";

  private PreferencesTable() {  }
}
