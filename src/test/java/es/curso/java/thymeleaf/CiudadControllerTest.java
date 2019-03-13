

package es.curso.java.thymeleaf;

import static es.curso.java.thymeleaf.controller.CiudadController.FRAGMENT_FORM;
import static es.curso.java.thymeleaf.controller.CiudadController.MODEL_ATTRIBUTE_CITIES;
import static es.curso.java.thymeleaf.controller.CiudadController.MODEL_ATTRIBUTE_CITY;
import static es.curso.java.thymeleaf.controller.CiudadController.SECTION_CITIES;
import static es.curso.java.thymeleaf.controller.CiudadController.VIEW_CITIES;
import static es.curso.java.thymeleaf.controller.CiudadController.VIEW_CITY_DELETE;
import static es.curso.java.thymeleaf.controller.CiudadController.VIEW_CITY_FORM;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.view.RedirectView;

import es.curso.java.thymeleaf.controller.CiudadController;
import es.curso.java.thymeleaf.dao.CiudadDao;
import es.curso.java.thymeleaf.entity.Ciudad;

@RunWith(MockitoJUnitRunner.class)
public class CiudadControllerTest {

  private static final String CIUDAD_ID = "sim";
  @Mock
  private CiudadDao ciudadDao;
  @InjectMocks
  private CiudadController controller;

  private ModelMap modelMap;
  private Ciudad ciudad;

  @Before
  public void setup() {
    modelMap = new ModelMap();
    ciudad = new Ciudad(CIUDAD_ID, "Sim Ciudad", 2016, 123_456);
  }

  @Test
  public void should_show_overview() {
    List<Ciudad> ciudadList = new ArrayList<>();
    when(ciudadDao.getAll()).thenReturn(ciudadList);

    String view = controller.overview(modelMap);

    assertThat(view).isEqualTo(VIEW_CITIES);
    assertThat(modelMap.get(MODEL_ATTRIBUTE_CITIES)).isEqualTo(ciudadList);
  }

  @Test
  public void should_show_ciudad_form_page() {
    mockFindCiudad();

    String view = controller.showUpdateCityPage(CIUDAD_ID, modelMap);

    assertThat(view).isEqualTo(VIEW_CITY_FORM);
    assertThat(modelMap.get(MODEL_ATTRIBUTE_CITY)).isEqualTo(ciudad);
  }

  @Test
  public void should_show_ciudad_form_fragment() {
    mockFindCiudad();

    String view = controller.showUpdateCityForm(CIUDAD_ID, modelMap);

    assertThat(view).isEqualTo(VIEW_CITY_FORM + FRAGMENT_FORM);
    assertThat(modelMap.get(MODEL_ATTRIBUTE_CITY)).isEqualTo(ciudad);
  }

  @Test
  public void should_update_ciudad() {
    RedirectView view = controller.updateCity(CIUDAD_ID, ciudad);

    assertThat(view.isRedirectView()).isTrue();
    assertThat(view.getUrl()).isEqualTo("");

    verify(ciudadDao).update(ciudad);
  }

  @Test
  public void should_update_ciudad_name() {
    mockFindCiudad();

    controller.partialUpdateCity(CIUDAD_ID, "name", "Almere");

    assertThat(ciudad.getNombre()).isEqualTo("Almere");

    verify(ciudadDao).update(ciudad);
  }

  @Test
  public void should_update_ciudad_population() {
    mockFindCiudad();

    controller.partialUpdateCity(CIUDAD_ID, "population", "987654");

    assertThat(ciudad.getPoblacion()).isEqualTo(987654);

    verify(ciudadDao).update(ciudad);
  }

  @Test
  public void should_update_ciudad_founded_in() {
    mockFindCiudad();

    controller.partialUpdateCity(CIUDAD_ID, "foundedIn", "2000");

    assertThat(ciudad.getFundadaEn()).isEqualTo(2000);

    verify(ciudadDao).update(ciudad);
  }

  @Test
  public void should_not_update_ciudad_if_parameter_is_unknown() {
    mockFindCiudad();

    controller.partialUpdateCity(CIUDAD_ID, "unsupported", "My value");

    verify(ciudadDao, never()).update(ciudad);
  }

  @Test
  public void should_show_delete_ciudad_page() {
    mockFindCiudad();

    String view = controller.showDeleteCityPage(CIUDAD_ID, modelMap);

    assertThat(view).isEqualTo(VIEW_CITY_DELETE);
    assertThat(modelMap.get(MODEL_ATTRIBUTE_CITY)).isEqualTo(ciudad);
  }

  @Test
  public void should_show_delete_ciudad_form_fragment() {
    mockFindCiudad();

    String view = controller.showDeleteCityForm(CIUDAD_ID, modelMap);

    assertThat(view).isEqualTo(VIEW_CITY_DELETE + FRAGMENT_FORM);
    assertThat(modelMap.get(MODEL_ATTRIBUTE_CITY)).isEqualTo(ciudad);
  }

  @Test
  public void should_delete_ciudad() {
    RedirectView view = controller.deleteCity(CIUDAD_ID);

    assertThat(view.isRedirectView()).isTrue();
    assertThat(view.getUrl()).isEqualTo("/ciudades");

    verify(ciudadDao).remove(CIUDAD_ID);
  }

  @Test
  public void should_set_section_to_modelMap() {
    String section = controller.section();

    assertThat(section).isEqualTo(SECTION_CITIES);
  }

  private void mockFindCiudad() {
    when(ciudadDao.find(CIUDAD_ID)).thenReturn(Optional.of(ciudad));
  }
}