package com.example.egstask.model.service;

import com.example.egstask.exception.NotFoundException;
import com.example.egstask.model.entity.Product;
import com.example.egstask.model.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.example.egstask.common.ErrorMessages.NOT_FOUND;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public Product findById(Long id) {
        Optional<Product> optional = productRepository.findById(id);
        if (optional.isPresent())
            return optional.get();
        throw new NotFoundException(NOT_FOUND);
    }

    public Product update(Long id, Product productReq) {
        Product product = findById(id);
        product.setName(productReq.getName());
        return productRepository.save(product);
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
    }

    public Page<Product> findBySpec(Specification<Product> spec, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return productRepository.findAll(spec, pageable);
    }

//    public Page<Product> findByName(String name, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        ProductSpecification spec = new ProductSpecification(
//                new SearchCriteria("name", ":", name)
//        );
//        return productRepository.findAll(spec, pageable);
//    }
//
//    public Page<Product> findByPriceRange(double min, double max, int page, int size) {
//        Pageable pageable = PageRequest.of(page, size);
//        ProductSpecification minSpec = new ProductSpecification(
//                new SearchCriteria("price", ">", min)
//        );
//        ProductSpecification maxSpec = new ProductSpecification(
//                new SearchCriteria("price", "<", max)
//        );
//        return productRepository.findAll(Specification.where(minSpec).and(maxSpec), pageable);
//    }

}
