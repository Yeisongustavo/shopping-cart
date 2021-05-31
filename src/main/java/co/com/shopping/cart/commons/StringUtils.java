package co.com.shopping.cart.commons;

public class StringUtils {

	public static Boolean isEmpty(String cadena) {
		return cadena == null || cadena.trim().equals("");
	}

}
