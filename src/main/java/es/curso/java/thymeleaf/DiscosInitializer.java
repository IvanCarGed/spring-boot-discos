package es.curso.java.thymeleaf;

import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import es.curso.java.thymeleaf.config.ProjectProperties;
import es.curso.java.thymeleaf.dao.DiscoDao;
import es.curso.java.thymeleaf.entity.Disco;

@Configuration
@EnableConfigurationProperties(ProjectProperties.class)
public class DiscosInitializer implements InitializingBean {

	@Resource
	private DiscoDao discoDao;
	@Resource
	private ObjectMapper objectMapper;
	@Resource
	private ProjectProperties projectProperties;

	@Override
	public void afterPropertiesSet() throws Exception {
		org.springframework.core.io.Resource resource = new ClassPathResource(projectProperties.getDiscosFile());

		List<Disco> discos;
		try (InputStream inputStream = resource.getInputStream()) {
			discos = objectMapper.readValue(inputStream, new TypeReference<List<Disco>>() {
			});
		}
		discos.forEach(disco -> discoDao.add(disco));
	}

}
