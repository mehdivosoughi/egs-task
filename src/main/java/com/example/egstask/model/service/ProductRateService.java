package com.example.egstask.model.service;

import com.example.egstask.exception.BadRequestException;
import com.example.egstask.model.dto.request.RateReq;
import com.example.egstask.model.entity.Product;
import com.example.egstask.model.entity.Rating;
import com.example.egstask.model.repository.ProductRepository;
import com.example.egstask.model.repository.RateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.example.egstask.common.ErrorMessages.BAD_REQUEST;

@Service
public class ProductRateService {

    private final RateRepository rateRepository;
    private final ProductRepository productRepository;

    public ProductRateService(RateRepository rateRepository, ProductRepository productRepository) {
        this.rateRepository = rateRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void saveRateAndProduct(Product product, RateReq rateReq) {
        Rating rating = new Rating(rateReq);
        Optional<Rating> optional = rateRepository.findById(rating.getId());
        if (optional.isPresent()) {
            throw new BadRequestException(BAD_REQUEST);
        }

        rateRepository.save(rating);
        product.setRate(calculateProductRate(product.getRate(), product.getRateCount(), rateReq.getRate()));
        product.setRateCount(product.getRateCount() + 1);
        productRepository.save(product);
    }

    private double calculateProductRate(double productRate, int productRateCount, int newRate) {
        return (productRate * productRateCount + newRate) / (productRateCount + 1);
    }

}
