package co.com.shopping.cart.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import co.com.shopping.cart.commons.ServiceException;
import co.com.shopping.cart.dto.ProductDto;
import co.com.shopping.cart.dto.SaleDetail;
import co.com.shopping.cart.dto.SearchProductRequest;
import co.com.shopping.cart.dto.ShoppingCart;

public interface ShoppingCartService {

	public ProductDto saveProduct(ProductDto product) throws ServiceException;

	public List<ProductDto> uploadProduct(MultipartFile file) throws ServiceException;

	public ShoppingCart initShoppingCart(SaleDetail saleDetail) throws ServiceException;

	public List<ProductDto> searchProductRequest(SearchProductRequest searchProductRequest) throws ServiceException;

	public List<ProductDto> findAll() throws ServiceException;

}
