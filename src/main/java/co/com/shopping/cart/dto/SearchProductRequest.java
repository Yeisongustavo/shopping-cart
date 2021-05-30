package co.com.shopping.cart.dto;

import com.google.gson.Gson;

public class SearchProductRequest {

	private Integer page;
	private Integer pageSize;
	private String name;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}

}
