package co.com.shopping.cart.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

	@Override
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
		// FIXME: Validar cuales insertar
		validate(products);

		repository.saveAll(products.stream().filter(p -> p.getUploadDetail() != null).collect(Collectors.toList())
				.stream().map(this::convertToEntity).collect(Collectors.toList()));
		products.forEach(p -> p.setUploadDetail(p.getUploadDetail() == null ? "CARGADO" : p.getUploadDetail()));
		return products;
	}

	private void validate(List<ProductDto> products) throws ServiceException {
		try {
			for (ProductDto productDto : products) {
				for (Field f : productDto.getClass().getDeclaredFields()) {
					f.setAccessible(true);
					if (!("id".equals(f.getName()) || "uploadDetail".equals(f.getName()))
							&& (f.get(productDto) == null || f.get(productDto).toString().isEmpty()))
						productDto.setUploadDetail("PRODUCTO NO CARGADO");
				}
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new ServiceException("Ocurrio un error al validar la información a insertar: " + e.getMessage());
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Page<ProductDto> searchProductRequest(SearchProductRequest searchProductRequest) throws ServiceException {
		validateSearchProductRequest(searchProductRequest);

		List<Product> products = repository.findAll();
		List<Product> listOfPage = listOfPage(products,
				PageRequest.of(searchProductRequest.getPage(), searchProductRequest.getPageSize()));
		return listConvertToPage(listOfPage.stream().map(this::convertToDto).collect(Collectors.toList()),
				products.size(), PageRequest.of(searchProductRequest.getPage(), searchProductRequest.getPageSize()));
	}

	private void validateSearchProductRequest(SearchProductRequest searchProductRequest) throws ServiceException {
		if (searchProductRequest == null)
			throw new ServiceException("El request no puede estar vacio");
	}

	private Page<ProductDto> listConvertToPage(List<ProductDto> list, long total, Pageable pageable) {
		if (list.isEmpty())
			return new PageImpl<ProductDto>(new ArrayList<ProductDto>(), pageable, list.size());
		return new PageImpl<ProductDto>(list, pageable, total);
	}

	private List<Product> listOfPage(List<Product> list, Pageable pageable) {
		int start = (pageable.getPageNumber()) * pageable.getPageSize();
		int end = (start + pageable.getPageSize()) > list.size() ? list.size()
				: (pageable.getPageSize() * (pageable.getPageNumber() + 1));
		if (start > end)
			return new ArrayList<Product>();
		return list.subList(start, end);
	}

	@Override
	public List<ProductDto> findAll() throws ServiceException {
		return repository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
	}

}
