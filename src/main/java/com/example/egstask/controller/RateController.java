package com.example.egstask.controller;

import com.example.egstask.component.Global;
import com.example.egstask.model.dto.request.RateReq;
import com.example.egstask.model.dto.response.Response;
import com.example.egstask.model.entity.EgsUser;
import com.example.egstask.model.entity.Product;
import com.example.egstask.model.service.ProductRateService;
import com.example.egstask.model.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class RateController {

    private final ProductRateService productRateService;
    private final ProductService productService;

    public RateController(ProductRateService productRateService, ProductService productService) {
        this.productRateService = productRateService;
        this.productService = productService;
    }

    @PostMapping("products/{product_id}/rate")
    public ResponseEntity<Response> rateProduct(HttpServletRequest request,
                                                @PathVariable("product_id") Long productId,
                                                @Valid @RequestBody RateReq rateReq) {
        EgsUser user = Global.getUserInfo();
        Product product = productService.findById(productId);
        rateReq.setUserId(user.getId());
        rateReq.setProductId(product.getId());
        productRateService.saveRateAndProduct(product, rateReq);
        Response response = new Response("OK", request.getRequestURI());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
