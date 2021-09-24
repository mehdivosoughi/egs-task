package com.example.egstask.controller;

import com.example.egstask.model.dto.request.ProductReq;
import com.example.egstask.model.dto.response.ProductRes;
import com.example.egstask.model.dto.response.Response;
import com.example.egstask.model.entity.Category;
import com.example.egstask.model.entity.Product;
import com.example.egstask.model.service.CategoryService;
import com.example.egstask.model.service.ProductService;
import com.example.egstask.model.specification.SearchCriteria;
import com.example.egstask.model.specification.product.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService,
                             CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @PostMapping("/categories/{category_id}/products")
    public ResponseEntity<Response<ProductRes>> createProduct(HttpServletRequest request,
                                                              @PathVariable(name = "category_id") long categoryId,
                                                              @Valid @RequestBody ProductReq productReq) {
        Category category = categoryService.findById(categoryId);
        productReq.setCategory(category);
        ProductRes product = new ProductRes(productService.save(new Product(productReq)));
        Response<ProductRes> response =
                new Response<ProductRes>("OK", request.getRequestURI()).setMessage(product);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/products/{product_id}")
    public ResponseEntity<Response<ProductRes>> getProduct(HttpServletRequest request,
                                                           @PathVariable(name = "product_id") Long productId) {
        Product product = productService.findById(productId);
        Response<ProductRes> response =
                new Response<ProductRes>("OK", request.getRequestURI()).setMessage(new ProductRes(product));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/products/{product_id}")
    public ResponseEntity<Response<ProductRes>> updateProduct(HttpServletRequest request,
                                                              @PathVariable(name = "product_id") Long productId,
                                                              @Valid @RequestBody ProductReq productReq) {
        ProductRes product = new ProductRes(productService.update(productId, new Product(productReq)));
        Response<ProductRes> response =
                new Response<ProductRes>("OK", request.getRequestURI()).setMessage(product);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/products/{product_id}")
    public ResponseEntity<Response> deleteProduct(HttpServletRequest request,
                                                  @PathVariable(name = "product_id") Long productId) {
        productService.delete(productId);
        Response response = new Response<>("OK", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //TODO I had not time but a generic specification builder
    // would combine all 'GET' controller methods with criteria into one
    @GetMapping(value = "/products/byName", params = {"name", "page", "size"})
    public ResponseEntity<Response<Page<ProductRes>>> getProductByName(HttpServletRequest request,
                                                                       @RequestParam("name") String name,
                                                                       @RequestParam("page") int page,
                                                                       @RequestParam("size") int size) {
        ProductSpecification spec = new ProductSpecification(new SearchCriteria("name", ":", name));
        return getResponse(request, page, size, spec);
    }

    @GetMapping(value = "/products/byPriceRange", params = {"min", "max", "page", "size"})
    public ResponseEntity<Response<Page<ProductRes>>> getProductByPriceRange(HttpServletRequest request,
                                                                             @RequestParam("min") double min,
                                                                             @RequestParam("max") double max,
                                                                             @RequestParam("page") int page,
                                                                             @RequestParam("size") int size) {
        ProductSpecification minSpec = new ProductSpecification(
                new SearchCriteria("price", ">", min)
        );
        ProductSpecification maxSpec = new ProductSpecification(
                new SearchCriteria("price", "<", max)
        );
        Specification<Product> spec = Specification.where(minSpec).and(maxSpec);
        return getResponse(request, page, size, spec);
    }

    @GetMapping(value = "/products/byRate", params = {"rate", "page", "size"})
    public ResponseEntity<Response<Page<ProductRes>>> getProductByRate(HttpServletRequest request,
                                                                       @RequestParam("rate") double rate,
                                                                       @RequestParam("page") int page,
                                                                       @RequestParam("size") int size) {
        ProductSpecification spec = new ProductSpecification(new SearchCriteria("rate", ">", rate));
        return getResponse(request, page, size, spec);
    }

    private ResponseEntity<Response<Page<ProductRes>>> getResponse(HttpServletRequest request,
                                                                   @RequestParam("page") int page,
                                                                   @RequestParam("size") int size,
                                                                   Specification<Product> spec) {
        Page<ProductRes> products = productService.findBySpec(spec, page, size).map(ProductRes::new);
        Response<Page<ProductRes>> response =
                new Response<Page<ProductRes>>("OK", request.getRequestURI()).setMessage(products);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
