package es.curso.java.thymeleaf.dao;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import es.curso.java.thymeleaf.entity.Disco;

@Repository
public class DiscoDao {
	
	private Set<Disco> discos = new HashSet<>();
	
	public Optional<Disco> find(String id) {
	    return discos
	        .stream()
	        .filter(d -> d.getId().equals(id))
	        .findFirst();
	  }

	public void add(Disco disco) {
		discos.add(disco);
	  }

	  public void update(Disco disco) {
	    remove(disco.getId());
	    add(disco);
	  }


	  public void remove(String id) {
	    if (StringUtils.isNotBlank(id)) {
	    	discos.removeIf(d -> d.getId().equals(id));
	    }
	  }
	  public List<Disco> getAll() {
		    List<Disco> discoList = new ArrayList<>(discos);
		    discoList.sort(Comparator.comparing(Disco::getTitulo));
		    return discoList;
		  }
}
