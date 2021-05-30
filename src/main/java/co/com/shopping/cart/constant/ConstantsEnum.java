package co.com.shopping.cart.constant;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ConstantsEnum {

	PROJECT_NAME("PROJECT_NAME", "carrito-compras"), DATA("DATA", "Sesión de datos");

	private String code;
	private String value;

	ConstantsEnum(String code, String value) {
		this.code = code;
		this.value = value;
	}

	public static ConstantsEnum getByCode(String code) {
		for (ConstantsEnum constantsEnum : values()) {
			if (constantsEnum.getCode().equalsIgnoreCase(code))
				return constantsEnum;
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
