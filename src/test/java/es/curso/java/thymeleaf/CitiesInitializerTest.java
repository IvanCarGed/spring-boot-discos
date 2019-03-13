

package es.curso.java.thymeleaf;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import es.curso.java.thymeleaf.Application;
import es.curso.java.thymeleaf.dao.CiudadDao;
import es.curso.java.thymeleaf.entity.Ciudad;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class CitiesInitializerTest {

  @Resource
  private CiudadDao cityDao;

  @Test
  public void should_have_inserted_cities() {
    List<Ciudad> cities = cityDao.getAll();

    assertThat(cities).isNotEmpty();
  }
}