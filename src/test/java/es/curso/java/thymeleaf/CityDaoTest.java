

package es.curso.java.thymeleaf;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

import es.curso.java.thymeleaf.dao.CiudadDao;
import es.curso.java.thymeleaf.entity.Ciudad;

@SuppressWarnings("OptionalGetWithoutIsPresent")
public class CityDaoTest {

  private static final String CITY_ID = "sim";
  private CiudadDao cityDao;
  private Ciudad simCity;

  @Before
  public void setup() {
    cityDao = new CiudadDao();
    simCity = new Ciudad(CITY_ID, "Sim City", 2016, 123_456);
    cityDao.add(simCity);
  }

  @Test
  public void should_return_empty_optional_for_unknown_id() {
    Optional<Ciudad> city = cityDao.find("unknown");

    assertThat(city).isNotPresent();
  }

  @Test
  public void should_add_city() {
    Optional<Ciudad> city = cityDao.find(CITY_ID);

    assertThat(city)
        .isPresent()
        .hasValue(simCity);
  }

  @Test
  public void should_update_city() {
    simCity.setFundadaEn(2015);
    cityDao.update(simCity);

    Optional<Ciudad> city = cityDao.find(CITY_ID);

    assertThat(city)
        .isPresent()
        .hasValueSatisfying(s -> assertThat(s.getFundadaEn()).isEqualTo(2015));
  }

  @Test
  public void should_remove_city() {
    cityDao.remove(CITY_ID);

    Optional<Ciudad> city = cityDao.find(CITY_ID);

    assertThat(city).isNotPresent();
  }

  @Test
  public void should_find_all_cities() {
    List<Ciudad> cities = cityDao.getAll();

    assertThat(cities)
        .hasSize(1)
        .containsExactly(simCity);
  }
}