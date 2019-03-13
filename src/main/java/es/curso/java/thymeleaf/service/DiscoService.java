package es.curso.java.thymeleaf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.curso.java.thymeleaf.dao.DiscoDao;
import es.curso.java.thymeleaf.entity.Disco;

@Service
public class DiscoService {

	@Autowired
	DiscoDao discoDao;

	public Optional<Disco> find(String id) {
		return discoDao.find(id);
	}

	public void add(Disco disco) {
		discoDao.add(disco);
	}

	public void update(Disco disco) {
		discoDao.update(disco);
	}

	public void remove(String id) {
		discoDao.remove(id);
	}

	public List<Disco> getAll() {
		return discoDao.getAll();
	}

}
