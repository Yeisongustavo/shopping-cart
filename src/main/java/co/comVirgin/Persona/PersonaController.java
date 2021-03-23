package co.comVirgin.Persona;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController 
public class PersonaController {
	private final PersonaRepository repository; 
	  PersonaController(PersonaRepository repository) {
	    this.repository = repository;
	  }
	  
	  @GetMapping("/Persona")
	  List<Persona> All (){
		return repository.findAll();
		  }
	  
	  @PostMapping("/Persona")
	  Persona newPersona(@RequestBody Persona newPersona) {
	    return repository.save(newPersona);
	  }
	  @GetMapping("/Persona/{id}") 
	  Persona one(@PathVariable Long id) {
	    
	    return repository.findById(id)
	      .orElseThrow(() -> new PersonaNotFoundException(id));
	  }
	  @PutMapping("/Persona/{id}")
	  Persona replacePersona(@RequestBody Persona newPersona, @PathVariable Long id) {
	    
	    return repository.findById(id)
	      .map(Persona -> {
	    	  Persona.setName(newPersona.getName());
	    	  Persona.setLast_name(newPersona.getLast_name());
	    	  Persona.setDoc_type(newPersona.getDoc_type());
	    	  Persona.setCedula(newPersona.getCedula());
	    	  Persona.setBirthday(newPersona.getBirthday());
	    	  Persona.setAddress(newPersona.getAddress());
	    	  Persona.setPhone(newPersona.getPhone());
	    	  Persona.phone = newPersona.getPhone();
	        return repository.save(Persona);
	      })
	      .orElseGet(() -> {
	        newPersona.setId(id);
	        return repository.save(newPersona);
	      });
	  }
	  @DeleteMapping("/Persona/{id}")
	  void deletePersona(@PathVariable Long id) {
		    repository.deleteById(id);
		  }
}
