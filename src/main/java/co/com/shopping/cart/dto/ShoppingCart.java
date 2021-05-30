package co.com.shopping.cart.dto;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

	public static final int NOT_FOUND = -1;

	private List<SaleDetail> saleDetails;

	public ShoppingCart() {
		saleDetails = new ArrayList<>();
	}
	
	public ShoppingCart(SaleDetail saleDetail) {
		this.saleDetails = new ArrayList<>();
		addItem(saleDetail);
	}

	public void addItem(SaleDetail saleDetail) {
		int productId = saleDetail.getProduct().getId();
		if (this.productFound(productId)) {
			this.addQuantity(productId, saleDetail.getQuantity());
			this.addDiscount(productId, saleDetail.getDiscount());
		} else {
			saleDetail.setProductId(productId);
			saleDetails.add(saleDetail);
		}
	}

	public List<SaleDetail> getSaleDetails() {
		return saleDetails;
	}

	public void deleteCart() {
		saleDetails.clear();
	}

	public void deleteItem(int productId) {
		int index = getIndex(productId);
		if (index != NOT_FOUND) {
			saleDetails.remove(index);
		}
	}

	public boolean productFound(int productId) {
		return getIndex(productId) != NOT_FOUND;
	}

	public void addQuantity(int productId, int quantity) {
		SaleDetail saleDetail = getSaleDetail(productId);
		if (saleDetail != null)
			saleDetail.addQuantity(quantity);
	}

	public void addDiscount(int productId, double discount) {
		SaleDetail saleDetail = getSaleDetail(productId);
		if (saleDetail != null)
			saleDetail.addDiscount(discount);
	}

	public double getTotalCarrito() {
		double total = 0;
		for (SaleDetail saleDetail : saleDetails) {
			total += saleDetail.getSubtotal();
		}
		return total;
	}

	public SaleDetail getSaleDetail(int productId) {
		SaleDetail saleDetail = null;
		int index = getIndex(productId);
		if (index != NOT_FOUND) {
			saleDetail = saleDetails.get(index);
		}
		return saleDetail;
	}

	private int getIndex(int productId) {
		SaleDetail saleDetail = new SaleDetail();
		saleDetail.setProductId(productId);
		return saleDetails.indexOf(saleDetail);
	}

}