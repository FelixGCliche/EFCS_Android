package com.v41.efcs;

import org.junit.Before;
import org.junit.Test;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.v41.efcs.controller.TemperatureActivityController;
import com.v41.efcs.model.TemperatureModel;
import com.v41.efcs.model.mock.TemperatureActivityModelMock;
import com.v41.efcs.view.mock.TemperatureActivityMock;
import com.v41.efcs.view.TemperatureView;

import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class TemperatureActivityControllerTest {

  TemperatureModel model;
  TemperatureView view;

  @Before
  public void initialize() {
    view = new TemperatureActivityMock();
    model = new TemperatureActivityModelMock(0, 10);
  }


  //region Tests Increase/Decrease Buttons
  @Test
  public void ShouldOnHighLimitIncreaseIncreaseHighLimit() {
    TemperatureActivityController controller = new TemperatureActivityController(view, model);

    controller.onHighLimitIncrease(1);

    assertTrue(model.getHighLimit() == 11);
  }
  @Test
  public void ShouldOnHighLimitDecreaseDecreaseHighLimit() {
    TemperatureActivityController controller = new TemperatureActivityController(view, model);

    controller.onHighLimitDecrease(1);

    assertTrue(model.getHighLimit() == 9);
  }
  @Test
  public void ShouldOnLowLimitIncreaseIncreaseLowLimit() {
    TemperatureActivityController controller = new TemperatureActivityController(view, model);

    controller.onLowLimitIncrease(1);

    assertTrue(model.getLowLimit() == 1);
  }
  @Test
  public void ShouldOnLowLimitDecreaseDecreaseLowLimit() {
    TemperatureActivityController controller = new TemperatureActivityController(view, model);

    controller.onLowLimitDecrease(1);

    assertTrue(model.getLowLimit() == -1);
  }
  //endregion Buttons Butto

  //region Tests setLimitsEnabled Buttons
  public void ShouldIncreaseLowLimitAboveHighLimitDisableLimits() {
    TemperatureActivityController controller = new TemperatureActivityController(view, model);

    controller.onLowLimitIncrease(11);

    assertTrue(view.isLimitsEnabled());
  }
  public void ShouldDecreaseHighLimitBelowLowLimitDisableLimits() {
    TemperatureActivityController controller = new TemperatureActivityController(view, model);

    controller.onHighLimitDecrease(11);

    assertTrue(view.isLimitsEnabled());
  }
  //endregion Buttons

  //region Tests SetLimit EditText
  @Test
  public void ShouldOnSetHighLimitChangeHighLimit() {
    TemperatureActivityController controller = new TemperatureActivityController(view, model);

    controller.onSetHighLimit(20);

    assertTrue(model.getHighLimit() == 20);
  }
  @Test
  public void ShouldSOnSetLowLimitChangeLowLimit() {
    TemperatureActivityController controller = new TemperatureActivityController(view, model);

    controller.onSetLowLimit(-10);

    assertTrue(model.getLowLimit() == -10);
  }
  @Test
  public void ShouldOnSetHighLimitBelowLowLimitKeepHighLimit() {
    TemperatureActivityController controller = new TemperatureActivityController(view, model);

    controller.onSetHighLimit(-1);

    assertTrue(model.getHighLimit() == 10);
  }
  @Test
  public void ShouldSOnSetLowLimitAboveHighLimitKeepLowLimit() {
    TemperatureActivityController controller = new TemperatureActivityController(view, model);

    controller.onSetLowLimit(20);

    assertTrue(model.getLowLimit() == 0);
  }
  //endregion

}
