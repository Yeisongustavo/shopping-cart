package co.comVirgin.Persona;

public class PersonaNotFoundException extends RuntimeException {

	public PersonaNotFoundException(Long id) {
		super("couldNotfindUsuario"+ id);
	}

}
