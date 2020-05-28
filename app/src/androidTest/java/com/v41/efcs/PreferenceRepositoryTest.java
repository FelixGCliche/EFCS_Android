package com.v41.efcs;

import android.database.sqlite.SQLiteDatabase;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.v41.efcs.model.entity.PreferencesData;
import com.v41.efcs.service.PreferencesRepository;
import com.v41.efcs.service.Repository;
import com.v41.efcs.service.database.ApplicationDatabaseHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class PreferenceRepositoryTest {

  private ApplicationDatabaseHelper applicationDatabaseHelper;
  private SQLiteDatabase database;

  private Repository<PreferencesData> repository;

  @Before
  public void initialize() {
    if (applicationDatabaseHelper == null) {
      applicationDatabaseHelper = new ApplicationDatabaseHelper(InstrumentationRegistry.getInstrumentation().getTargetContext(), null);
      database = applicationDatabaseHelper.getWritableDatabase();
    }

    repository = new PreferencesRepository(database);
  }

  @Test
  public void canInitRepository() {
    assertNotNull(repository);
    assertNotNull(repository.findLast());
  }

  @Test
  public void shouldFindLastReturnDefaultValue() {
    PreferencesData preferencesData = new PreferencesData(1, 1, 28.33, 79.53, new double[]{20.26, 22.42, 24.58, 26.74});
    PreferencesData last = repository.findLast();

    assertTrue(last.getId() == preferencesData.getId());
    assertTrue(last.getKeepPreferences() == preferencesData.getKeepPreferences());
    assertTrue(last.getTempLowLimit() == preferencesData.getTempLowLimit());
    assertTrue(last.getTempHighLimit() == preferencesData.getTempHighLimit());
    assertTrue(last.getHumidityHighLimits()[0] == preferencesData.getHumidityHighLimits()[0]);
    assertTrue(last.getHumidityHighLimits()[1] == preferencesData.getHumidityHighLimits()[1]);
    assertTrue(last.getHumidityHighLimits()[2] == preferencesData.getHumidityHighLimits()[2]);
    assertTrue(last.getHumidityHighLimits()[3] == preferencesData.getHumidityHighLimits()[3]);
  }

  @Test
  public void shouldFindLastReturnLastInsertedValueInFullDB() {
    PreferencesData preferencesData = new PreferencesData(2, 1, 28.33, 79.53, new double[]{20.26, 22.42, 24.58, 26.74});

    repository.insert(preferencesData);
    PreferencesData last = repository.findLast();

    assertTrue(last.getId() == preferencesData.getId());
    assertTrue(last.getKeepPreferences() == preferencesData.getKeepPreferences());
    assertTrue(last.getTempLowLimit() == preferencesData.getTempLowLimit());
    assertTrue(last.getTempHighLimit() == preferencesData.getTempHighLimit());
    assertTrue(last.getHumidityHighLimits()[0] == preferencesData.getHumidityHighLimits()[0]);
    assertTrue(last.getHumidityHighLimits()[1] == preferencesData.getHumidityHighLimits()[1]);
    assertTrue(last.getHumidityHighLimits()[2] == preferencesData.getHumidityHighLimits()[2]);
    assertTrue(last.getHumidityHighLimits()[3] == preferencesData.getHumidityHighLimits()[3]);
  }

  @Test
  public void shouldFindLastReturnNullOnEmptyDB() {
    repository.delete(1);

    assertNull(repository.findLast());
  }

  @Test
  public void shouldFindReturnDefaultValue() {
    PreferencesData preferencesData = new PreferencesData(1, 1, 28.33, 79.53, new double[]{20.26, 22.42, 24.58, 26.74});
    PreferencesData retval = repository.find(1);

    assertTrue(retval.getId() == preferencesData.getId());
    assertTrue(retval.getKeepPreferences() == preferencesData.getKeepPreferences());
    assertTrue(retval.getTempLowLimit() == preferencesData.getTempLowLimit());
    assertTrue(retval.getTempHighLimit() == preferencesData.getTempHighLimit());
    assertTrue(retval.getHumidityHighLimits()[0] == preferencesData.getHumidityHighLimits()[0]);
    assertTrue(retval.getHumidityHighLimits()[1] == preferencesData.getHumidityHighLimits()[1]);
    assertTrue(retval.getHumidityHighLimits()[2] == preferencesData.getHumidityHighLimits()[2]);
    assertTrue(retval.getHumidityHighLimits()[3] == preferencesData.getHumidityHighLimits()[3]);
  }

  @Test
  public void shouldFindReturnLastInsertedValueInFullDB() {
    PreferencesData preferencesData = new PreferencesData(2, 1, 28.33, 79.53, new double[]{20.26, 22.42, 24.58, 26.74});

    repository.insert(preferencesData);
    PreferencesData retval = repository.find(2);

    assertTrue(retval.getId() == preferencesData.getId());
    assertTrue(retval.getKeepPreferences() == preferencesData.getKeepPreferences());
    assertTrue(retval.getTempLowLimit() == preferencesData.getTempLowLimit());
    assertTrue(retval.getTempHighLimit() == preferencesData.getTempHighLimit());
    assertTrue(retval.getHumidityHighLimits()[0] == preferencesData.getHumidityHighLimits()[0]);
    assertTrue(retval.getHumidityHighLimits()[1] == preferencesData.getHumidityHighLimits()[1]);
    assertTrue(retval.getHumidityHighLimits()[2] == preferencesData.getHumidityHighLimits()[2]);
    assertTrue(retval.getHumidityHighLimits()[3] == preferencesData.getHumidityHighLimits()[3]);
  }

  @Test
  public void shouldFindReturnNullOnInexistantData() {
    assertNull(repository.find(2));
  }

  @Test
  public void shouldFindReturnNullOnEmptyDB() {
    repository.delete(1);

    assertNull(repository.find(1));
  }

  @Test
  public void canInsertInEmptyDB() {
    PreferencesData preferencesData = new PreferencesData(2, 1, 28.33, 79.53, new double[]{20.26, 22.42, 24.58, 26.74});

    repository.delete(1);
    repository.insert(preferencesData);
    PreferencesData retval = repository.findLast();

    assertTrue(retval.getId() == preferencesData.getId());
    assertTrue(retval.getKeepPreferences() == preferencesData.getKeepPreferences());
    assertTrue(retval.getTempLowLimit() == preferencesData.getTempLowLimit());
    assertTrue(retval.getTempHighLimit() == preferencesData.getTempHighLimit());
    assertTrue(retval.getHumidityHighLimits()[0] == preferencesData.getHumidityHighLimits()[0]);
    assertTrue(retval.getHumidityHighLimits()[1] == preferencesData.getHumidityHighLimits()[1]);
    assertTrue(retval.getHumidityHighLimits()[2] == preferencesData.getHumidityHighLimits()[2]);
    assertTrue(retval.getHumidityHighLimits()[3] == preferencesData.getHumidityHighLimits()[3]);
  }

  @Test
  public void canInsertMultiple() {
    PreferencesData preferencesData = new PreferencesData(2, 1, 28.33, 79.53, new double[]{20.26, 22.42, 24.58, 26.74});
    boolean isOk;

    isOk = repository.insert(preferencesData);
    assertTrue(isOk && repository.find(2) != null);

    isOk = repository.insert(preferencesData);
    assertTrue(isOk &&repository.find(3) != null);
  }

  @Test
  public void shouldReturnFalseOnInsertNull() {
    assertFalse(repository.insert(null));
  }

  @Test
  public void canDeleteByIDFromDB() {
    assertTrue(repository.delete(1));
    assertNull(repository.find(1));
  }

  @Test
  public void canDeleteByObjectFromDB() {
    PreferencesData preferencesData = new PreferencesData(1, 1, 28.33, 79.53, new double[]{20.26, 22.42, 24.58, 26.74});

    assertTrue(repository.delete(preferencesData));
    assertNull(repository.find(1));
  }

  @Test
  public void canDeleteMultipleFromDB() {
    PreferencesData preferencesData = new PreferencesData(1, 1, 28.33, 79.53, new double[]{20.26, 22.42, 24.58, 26.74});

    repository.insert(preferencesData);
    repository.insert(preferencesData);
    repository.insert(preferencesData);
    repository.insert(preferencesData);

    for (int i = 1; i < 6; i++) {
      assertTrue(repository.delete(i));
      assertNull(repository.find(i));
    }
  }

  @Test
  public void shouldReturnFalseOnDeleteNull() {
    assertFalse(repository.delete(null));
  }

  @Test
  public void canSaveDataFromDB() {
    PreferencesData preferencesData = new PreferencesData(1, 0, 28.33, 79.53, new double[]{20.26, 22.42, 24.58, 26.74});

    assertTrue(repository.save(preferencesData));
    assertTrue(repository.findLast().getKeepPreferences() == 0);
  }

  @Test
  public void shouldReturnFalseOnSaveNull() {
    assertFalse(repository.save(null));
  }
}
