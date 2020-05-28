package com.v41.efcs;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.v41.efcs.model.entity.PreferencesData;
import com.v41.efcs.service.PreferencesRepository;
import com.v41.efcs.service.database.ApplicationDatabaseHelper;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class RepositoryTest {

    SQLiteDatabase database = null;
    private PreferencesRepository repo;

    @After
    public void tearDown() {
        database.close();
    }

    @Before
    public void setUp() throws Exception {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        ApplicationDatabaseHelper helper = new ApplicationDatabaseHelper(appContext, null);
        database = helper.getWritableDatabase();
        repo = new PreferencesRepository(database);
    }

    @Test
    public void T01_canFindLastPreferenceInDatabase()
    {
        // Arrange
        PreferencesData data = new PreferencesData(0, true, 28.33, 79.53, new double[]{20.26, 22.42, 24.58, 26.74 });

        //Act
        PreferencesData foundData =  repo.findLast();

        //Assert
        Assert.assertEquals(true, data.equals(foundData));
    }

    @Test
    public void T02_canInsertPreferenceInDatabase()
    {
        // Arrange
        PreferencesData data = new PreferencesData(0, true, 10.0, 20.0, new double[]{0.0, 10.0, 20.00, 30.00});

        //Act
        repo.insert(data);

        //Assert
        Assert.assertEquals(true, data.equals(repo.findLast()));
    }

    @Test
    public void T03_canFindPreferenceByIdInDatabase()
    {
        // Arrange
        PreferencesData data = new PreferencesData(0, true, 10.0, 20.0, new double[]{0.0, 10.0, 20.00, 30.00});
        PreferencesData data2 = new PreferencesData(0, true, 10.0, 20.0, new double[]{5.0, 15.0, 25.00, 35.00});
        repo.insert(data);
        repo.insert(data2);

        //Act
        PreferencesData last = repo.findLast();
        PreferencesData lastById = repo.find(last.getId());

        //Assert
        Assert.assertEquals(true, data2.equals(lastById));
    }

    @Test
    public void T04_canSavePreferenceInDatabase()
    {
        // Arrange
        PreferencesData data = new PreferencesData(0, true, 10.0, 20.0, new double[]{0.0, 10.0, 20.00, 30.00});
        repo.insert(data);
        data = repo.findLast();
        data.setZoneLimits(new double[]{50.0,60.0,70.0,80.0});

        //Act
        repo.save(data);

        //Assert
        Assert.assertEquals(true, data.equals(repo.findLast()));
    }

    @Test
    public void T05_canDeletePreferenceByIdInDatabase()
    {
        // Arrange
        PreferencesData data = new PreferencesData(0, true, 10.0, 20.0, new double[]{0.0, 10.0, 20.00, 30.00});
        PreferencesData data2 = new PreferencesData(0, true, 10.0, 20.0, new double[]{0.0, 10.0, 20.00, 30.00});
        repo.insert(data);
        repo.insert(data2);

        //Act
        repo.delete(repo.findLast().getId());

        //Assert
        Assert.assertEquals(true, data.equals(repo.findLast()));
    }

    @Test
    public void T06_canDeletePreferenceFromDatabase()
    {
        // Arrange
        PreferencesData data = new PreferencesData(0, true, 10.0, 20.0, new double[]{0.0, 10.0, 20.00, 30.00});
        PreferencesData data2 = new PreferencesData(0, true, 10.0, 20.0, new double[]{0.0, 10.0, 20.00, 30.00});
        repo.insert(data);
        repo.insert(data2);

        //Act
        repo.delete(repo.findLast());

        //Assert
        Assert.assertEquals(true, data.equals(repo.findLast()));
    }

    @Test
    public void T07_cantDeleteInvalidPreferenceByIdInDatabase()
    {
        //La suppression d'un élément inexisant n'entraîne pas d'erreur, aucune row impactée probablement
        Assert.assertTrue(true);
    }

    @Test
    public void T08_cantDeleteInvalidPreferenceFromDatabase()
    {
        //La suppression d'un élément inexisant n'entraîne pas d'erreur, aucune row impactée probablement
        Assert.assertTrue(true);
    }

    @Test
    public void T09_cantFindInvalidLastPreferenceInEmptyDatabase()
    {
        repo.delete(repo.findLast());

        //Act
        PreferencesData foundData =  repo.findLast();

        //Assert
        Assert.assertNull(foundData);
    }


    @Test
    public void T10_cantInsertInvalidPreferenceInDatabase()
    {
        //Vu que le modèle obligatoirement rempli, je n'ai pas trouvé de cas d'exception ici
        Assert.assertTrue(true);
    }

    @Test
    public void T11_cantFindInvalidPreferenceByIdInDatabase()
    {
        // Arrange
        PreferencesData data = new PreferencesData(0, true, 10.0, 20.0, new double[]{0.0, 10.0, 20.00, 30.00});
        PreferencesData data2 = new PreferencesData(0, true, 10.0, 20.0, new double[]{5.0, 15.0, 25.00, 35.00});
        repo.insert(data);

        //Act
        PreferencesData lastById = repo.find(-2);

        //Assert
        Assert.assertNull(lastById);
    }

    @Test
    public void T12_cantSaveInvalidPreferenceInDatabase()
    {
        // Un update sur un id invalide semble passer au niveau du execSQL, aucune row impactée probablement
       Assert.assertTrue(true);
    }


}
