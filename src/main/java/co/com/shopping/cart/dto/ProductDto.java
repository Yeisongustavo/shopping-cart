package co.com.shopping.cart.dto;

import com.google.gson.Gson;

public class ProductDto {

	private Integer id;
	private String name;
	private String brand;
	private Integer price;
	private Integer stock;
	private String status;
	private Integer discount;
	private String uploadDetail;

	public ProductDto() {
		super();
	}

	public ProductDto(String name, String brand, Integer price, Integer stock, String status, Integer discount) {
		super();
		this.name = name;
		this.brand = brand;
		this.price = price;
		this.stock = stock;
		this.status = status;
		this.discount = discount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getDiscount() {
		return discount;
	}

	public void setDiscount(Integer discount) {
		this.discount = discount;
	}

	public String getUploadDetail() {
		return uploadDetail;
	}

	public void setUploadDetail(String uploadDetail) {
		this.uploadDetail = uploadDetail;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}