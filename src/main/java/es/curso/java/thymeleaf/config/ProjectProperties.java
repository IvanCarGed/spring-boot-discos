
package es.curso.java.thymeleaf.config;

import javax.validation.constraints.NotEmpty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "discos")
public class ProjectProperties {

	@NotEmpty
	private String discosFile;

	public String getDiscosFile() {
		return discosFile;
	}

	public void setDiscosFile(String discosFile) {
		this.discosFile = discosFile;
	}

}