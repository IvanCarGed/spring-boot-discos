package es.curso.java.thymeleaf;

import static es.curso.java.thymeleaf.controller.HomeController.VIEW_INDEX;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import es.curso.java.thymeleaf.controller.HomeController;

public class HomeControllerTest {

  private HomeController controller;

  @Before
  public void setup() {
    controller = new HomeController();
  }

  @Test
  public void should_show_index() {
    String home = controller.getHome();

    assertThat(home).isEqualTo(VIEW_INDEX);
  }
}