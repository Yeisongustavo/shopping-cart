package co.com.shopping.cart.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import co.com.shopping.cart.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	public List<Product> findByName(String name, PageRequest pageRequest);

}