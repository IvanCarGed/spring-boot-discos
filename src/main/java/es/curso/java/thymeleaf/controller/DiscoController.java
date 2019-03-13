package es.curso.java.thymeleaf.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.function.Supplier;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.view.RedirectView;

import es.curso.java.thymeleaf.dao.DiscoDao;
import es.curso.java.thymeleaf.entity.Disco;

@Controller
@RequestMapping("discos")
public class DiscoController {

	  public static final String VIEW_DISCS = "pages/discos";
	  public static final String VIEW_DISC_FORM = "pages/discos-form";
	  public static final String VIEW_DISC_DELETE = "pages/disco-delete";
	  public static final String MODEL_ATTRIBUTE_DISCS = "discos";
	  public static final String MODEL_ATTRIBUTE_DISC = "disco";
	  public static final String FRAGMENT_FORM = " :: form";
	  public static final String SECTION_DISCS = "discos";
	  private static final Logger LOG = LoggerFactory.getLogger(DiscoController.class);
	  private static final String ID = "id";
	  private static final String PATH_ID = "/{id}";
	  private static final String X_REQUESTED_WITH_XML_HTTP_REQUEST = "X-Requested-With=XMLHttpRequest";
	  
	  @Resource
	  private DiscoDao discoDao;
	  
	  @RequestMapping
	  public String overview(ModelMap modelMap) {
		  modelMap.addAttribute(MODEL_ATTRIBUTE_DISCS, discoDao.getAll());
		  return VIEW_DISCS;
	  }

	  @GetMapping(value = PATH_ID)
	  public String showUpdateDiscPage(@PathVariable(ID) String id,
	                                   ModelMap modelMap) {
	    Disco disco = discoDao.find(id).orElseThrow(notFoundException());
	    modelMap.addAttribute(MODEL_ATTRIBUTE_DISC, disco);
	    return VIEW_DISC_FORM;
	  }

	  @GetMapping(value = PATH_ID, headers = { X_REQUESTED_WITH_XML_HTTP_REQUEST })
	  public String showDiscCityForm(@PathVariable(ID) String id,
	                                   ModelMap modelMap) {
	    LOG.info("Requesting disco {} via XHR", id);

	    // Let Thymeleaf only return the th:fragment="form" within the view
	    return showUpdateDiscPage(id, modelMap) + FRAGMENT_FORM;
	  }

	  @PostMapping(value = PATH_ID)
	  public RedirectView updateDisc(@PathVariable(ID) String id,
	                                 @ModelAttribute("disco") Disco disco) {
	    LOG.info("Updating disco {}", id);

	    discoDao.update(disco);
	    return new RedirectView("");
	  }

	  @PostMapping(headers = { X_REQUESTED_WITH_XML_HTTP_REQUEST }, params = { "pk" })
	  @ResponseStatus(code = NO_CONTENT)
	  public void partialUpdateDisc(@RequestParam("pk") String id,
	                                @RequestParam("name") String parameterTitulo,
	                                @RequestParam("value") String value) {
	    Disco disco = discoDao.find(id).orElseThrow(notFoundException());
	    if ("titulo".equalsIgnoreCase(parameterTitulo)) {
	    	disco.setTitulo(value);
	    } else if ("lanzamiento".equalsIgnoreCase(parameterTitulo)) {
	    	disco.setLanzamiento(Integer.parseInt(value));
	    } else if ("ventas".equalsIgnoreCase(parameterTitulo)) {
	    	disco.setVentas(Integer.parseInt(value));
	    } else {
	      LOG.warn("Invalid request for updating a disc. Parameter titulo '{}', value '{}'", parameterTitulo, value);
	      return;
	    }
	    discoDao.update(disco);
	  }

	  @GetMapping(value = PATH_ID + "/delete")
	  public String showDeleteDiscPage(@PathVariable(ID) String id,
	                                   ModelMap modelMap) {
	    Disco disco = discoDao.find(id).orElseThrow(notFoundException());
	    modelMap.addAttribute(MODEL_ATTRIBUTE_DISC, disco);

	    return VIEW_DISC_DELETE;
	  }

	  @GetMapping(value = PATH_ID + "/delete", headers = { X_REQUESTED_WITH_XML_HTTP_REQUEST })
	  public String showDeleteDiscForm(@PathVariable(ID) String id,
	                                   ModelMap modelMap) {
	    LOG.info("Requesting delete disc form {} via XHR", id);

	    return showDeleteDiscPage(id, modelMap) + FRAGMENT_FORM;
	  }

	  @PostMapping(value = PATH_ID + "/delete")
	  public RedirectView deleteDisc(@PathVariable(ID) String id) {
	    LOG.info("Deleting disc {}", id);

	    discoDao.remove(id);
	    return new RedirectView("/discos");
	  }

	  @ModelAttribute("section")
	  public String section() {
	    return SECTION_DISCS;
	  }

	  private Supplier<HttpClientErrorException> notFoundException() {
	    return () -> new HttpClientErrorException(HttpStatus.NOT_FOUND);
	  }
}
