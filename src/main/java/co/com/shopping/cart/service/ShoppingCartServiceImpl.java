package co.com.shopping.cart.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import co.com.shopping.cart.commons.ApiError;
import co.com.shopping.cart.commons.ServiceException;
import co.com.shopping.cart.constant.ConstantsEnum;
import co.com.shopping.cart.dto.ProductDto;
import co.com.shopping.cart.dto.SaleDetail;
import co.com.shopping.cart.dto.SearchProductRequest;
import co.com.shopping.cart.dto.ShoppingCart;
import co.com.shopping.cart.helper.CSVHelper;
import co.com.shopping.cart.model.Product;
import co.com.shopping.cart.repository.ProductRepository;

@Service(value = "ShoppingCartService")
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private ProductRepository repository;

	@Override
	public ShoppingCart initShoppingCart(SaleDetail saleDetail) throws ServiceException {
		return new ShoppingCart(saleDetail);
	}

	public ShoppingCart deleteShoppingCart(ShoppingCart shoppingCart) throws ServiceException {
		shoppingCart.deleteCart();
		return shoppingCart;
	}

	@Override
	public ProductDto saveProduct(ProductDto product) throws ServiceException {
		validateRequest(product);
		return convertToDto(repository.save(convertToEntity(product)));
	}

	private void validateRequest(ProductDto product) throws ServiceException {
		if (product == null)
			throw new ServiceException(new ApiError(ConstantsEnum.PROJECT_NAME.getValue(),
					ConstantsEnum.PROJECT_NAME.getValue(), ConstantsEnum.PROJECT_NAME.getCode()));
	}

	private ProductDto convertToDto(Product entity) {
		return modelMapper.map(entity, ProductDto.class);
	}

	private Product convertToEntity(ProductDto dto) {
		return modelMapper.map(dto, Product.class);
	}

	@Override
	public List<ProductDto> uploadProduct(MultipartFile file) throws ServiceException {
		repository.deleteAll();
		List<ProductDto> products = CSVHelper.csvToProducts(file);
		products.forEach(p -> validate(p));
		repository.saveAll(products.stream().filter(p -> p.getUploadDetail() != null).collect(Collectors.toList())
				.stream().map(this::convertToEntity).collect(Collectors.toList()));
		products.forEach(p -> p.setUploadDetail(p.getUploadDetail() == null ? "CARGADO" : p.getUploadDetail()));
		return products;
	}

	private void validate(ProductDto productDto) {
		try {
			for (Field f : productDto.getClass().getDeclaredFields()) {
				f.setAccessible(true);
				if (!("id".equals(f.getName()) || "uploadDetail".equals(f.getName()))
						&& (f.get(productDto) == null || f.get(productDto).toString().isEmpty()))
					productDto.setUploadDetail("PRODUCTO NO CARGADO");
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<ProductDto> searchProductRequest(SearchProductRequest searchProductRequest) throws ServiceException {
		List<Product> products = repository.findByName(searchProductRequest.getName(),
				PageRequest.of(searchProductRequest.getPage(), searchProductRequest.getPageSize(), null));
		return products.stream().map(this::convertToDto).collect(Collectors.toList());
	}
	
	@Override
	public List<ProductDto> findAll() throws ServiceException {
		return repository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
	}

}
