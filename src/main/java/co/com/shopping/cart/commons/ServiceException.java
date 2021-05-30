package co.com.shopping.cart.commons;

import java.io.Serializable;

import com.google.gson.Gson;

public class ServiceException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super();
	}

	private ApiError apiError;

	public ServiceException(String text, Exception e) {
		super(text, e);
	}

	public ServiceException(String text) {
		super(text);
	}

	public ServiceException(ApiError apiError) {
		super(apiError.getMessageUser());
		this.apiError = apiError;
	}

	public ServiceException(ApiError apiError, String message) {
		super(message);
		this.apiError = apiError;
	}

	public ApiError getApiError() {
		return apiError;
	}

	public void setApiError(ApiError apiError) {
		this.apiError = apiError;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}