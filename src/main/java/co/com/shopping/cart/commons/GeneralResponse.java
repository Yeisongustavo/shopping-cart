package co.com.shopping.cart.commons;

import java.io.Serializable;

import com.google.gson.Gson;

public class GeneralResponse<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private T data;
	private boolean success;
	private ApiError apiError;

	public GeneralResponse() {
		super();
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
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
