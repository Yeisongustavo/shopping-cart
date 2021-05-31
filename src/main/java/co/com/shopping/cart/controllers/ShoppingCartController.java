package co.com.shopping.cart.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.com.shopping.cart.commons.ApiError;
import co.com.shopping.cart.commons.GeneralResponse;
import co.com.shopping.cart.commons.ServiceException;
import co.com.shopping.cart.constant.ConstantsEnum;
import co.com.shopping.cart.dto.ProductDto;
import co.com.shopping.cart.dto.SaleDetail;
import co.com.shopping.cart.dto.SearchProductRequest;
import co.com.shopping.cart.dto.ShoppingCart;
import co.com.shopping.cart.service.ShoppingCartService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

	private static final Logger logger = LoggerFactory.getLogger(ConstantsEnum.PROJECT_NAME.getValue());

	@Autowired
	private ShoppingCartService service;

	@PostMapping("/searchProductRequest")
	public ResponseEntity<GeneralResponse<Page<ProductDto>>> searchProductRequest(
			@RequestBody SearchProductRequest searchProductRequest) {
		logger.info(" Init searchProductRequest");
		HttpStatus status = HttpStatus.OK;
		GeneralResponse<Page<ProductDto>> response = new GeneralResponse<>();
		try {
			response.setData(service.searchProductRequest(searchProductRequest));
			response.setSuccess(true);
		} catch (ServiceException e) {
			logger.info(e.getMessage(), e);
			response.setApiError(e.getApiError());
			response.setSuccess(false);
			status = HttpStatus.BAD_REQUEST;
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			response.setApiError(new ApiError(e.getMessage(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.name()));
			response.setSuccess(false);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("End searchProductRequest");
		return new ResponseEntity<GeneralResponse<Page<ProductDto>>>(response, status);
	}

	@GetMapping("/findAll")
	public ResponseEntity<GeneralResponse<List<ProductDto>>> findAll() {
		logger.info(" Init findAll");
		HttpStatus status = HttpStatus.OK;
		GeneralResponse<List<ProductDto>> response = new GeneralResponse<>();
		try {
			response.setData(service.findAll());
			response.setSuccess(true);
		} catch (ServiceException e) {
			logger.info(e.getMessage(), e);
			response.setApiError(e.getApiError());
			response.setSuccess(false);
			status = HttpStatus.BAD_REQUEST;
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			response.setApiError(new ApiError(e.getMessage(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.name()));
			response.setSuccess(false);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("End findAll");
		return new ResponseEntity<GeneralResponse<List<ProductDto>>>(response, status);
	}

	@PostMapping("/init-shopping-cart")
	public ResponseEntity<GeneralResponse<ShoppingCart>> initShoppingCart(@RequestBody SaleDetail saleDetail) {
		logger.info(" Init initShoppingCart");
		HttpStatus status = HttpStatus.OK;
		GeneralResponse<ShoppingCart> response = new GeneralResponse<>();
		try {
			response.setData(service.initShoppingCart(saleDetail));
			response.setSuccess(true);
		} catch (ServiceException e) {
			logger.info(e.getMessage(), e);
			response.setApiError(e.getApiError());
			response.setSuccess(false);
			status = HttpStatus.BAD_REQUEST;
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			response.setApiError(new ApiError(e.getMessage(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.name()));
			response.setSuccess(false);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("End initShoppingCart");
		return new ResponseEntity<GeneralResponse<ShoppingCart>>(response, status);
	}

	@PostMapping("/delete-shopping-cart")
	public ResponseEntity<GeneralResponse<ShoppingCart>> deleteShoppingCart(@RequestBody ShoppingCart shoppingCart) {
		logger.info(" Init initShoppingCart");
		HttpStatus status = HttpStatus.OK;
		GeneralResponse<ShoppingCart> response = new GeneralResponse<>();
		try {
			response.setData(service.deleteShoppingCart(shoppingCart));
			response.setSuccess(true);
		} catch (ServiceException e) {
			logger.info(e.getMessage(), e);
			response.setApiError(e.getApiError());
			response.setSuccess(false);
			status = HttpStatus.BAD_REQUEST;
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			response.setApiError(new ApiError(e.getMessage(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.name()));
			response.setSuccess(false);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("End initShoppingCart");
		return new ResponseEntity<GeneralResponse<ShoppingCart>>(response, status);
	}

	@PostMapping("/upload-products")
	public ResponseEntity<GeneralResponse<List<ProductDto>>> uploadProduct(@RequestParam("file") MultipartFile file) {
		logger.info(" Init saveProduct");
		HttpStatus status = HttpStatus.OK;
		GeneralResponse<List<ProductDto>> response = new GeneralResponse<>();
		try {
			response.setData(service.uploadProduct(file));
			response.setSuccess(true);
		} catch (ServiceException e) {
			logger.info(e.getMessage(), e);
			response.setApiError(e.getApiError());
			response.setSuccess(false);
			status = HttpStatus.BAD_REQUEST;
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			response.setApiError(new ApiError(e.getMessage(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.name()));
			response.setSuccess(false);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("End saveProduct");
		return new ResponseEntity<GeneralResponse<List<ProductDto>>>(response, status);
	}

	@PostMapping("/product")
	public ResponseEntity<GeneralResponse<ProductDto>> saveProduct(@RequestBody ProductDto product) {
		logger.info(" Init saveProduct");
		HttpStatus status = HttpStatus.OK;
		GeneralResponse<ProductDto> response = new GeneralResponse<>();
		try {
			response.setData(service.saveProduct(product));
			response.setSuccess(true);
		} catch (ServiceException e) {
			logger.info(e.getMessage(), e);
			response.setApiError(e.getApiError());
			response.setSuccess(false);
			status = HttpStatus.BAD_REQUEST;
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			response.setApiError(new ApiError(e.getMessage(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.name()));
			response.setSuccess(false);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("End saveProduct");
		return new ResponseEntity<GeneralResponse<ProductDto>>(response, status);
	}

	@GetMapping(value = "/test")
	public ResponseEntity<GeneralResponse<String>> test() {
		logger.info(" Init test");
		HttpStatus status = HttpStatus.OK;
		GeneralResponse<String> response = new GeneralResponse<>();
		try {
			response.setData("Hello from ShoppingCart!");
			response.setSuccess(true);
		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			response.setApiError(new ApiError(e.getMessage(), e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.name()));
			response.setSuccess(false);
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("End test");
		return new ResponseEntity<GeneralResponse<String>>(response, status);
	}
}
