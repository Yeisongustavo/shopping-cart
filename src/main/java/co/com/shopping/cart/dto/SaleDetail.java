package co.com.shopping.cart.dto;

import com.google.gson.Gson;

public class SaleDetail {

	private int saleCode;
	private int productId;
	private int quantity = 0;
	private double discount = 0;
	private double unitPrice;
	private ProductDto product;

	public SaleDetail() {
		super();
	}

	public SaleDetail(int saleCode, int productId, int quantity, double discount) {
		this.saleCode = saleCode;
		this.productId = productId;
		this.quantity = quantity;
		this.discount = discount;
	}

	public int getSaleCode() {
		return saleCode;
	}

	public void setSaleCode(int saleCode) {
		this.saleCode = saleCode;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public ProductDto getProduct() {
		return product;
	}

	public void setProduct(ProductDto product) {
		this.product = product;
	}

	public void addQuantity(int quantity) {
		this.quantity += quantity;
	}

	public void addDiscount(double discount) {
		this.discount += discount;
	}

	public double getSubtotal() {
		double price = product.getPrice();
		return quantity * unitPrice - discount;
	}

	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
