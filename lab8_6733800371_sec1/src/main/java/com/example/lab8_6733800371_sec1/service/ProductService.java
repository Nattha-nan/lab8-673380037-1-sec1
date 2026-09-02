package com.example.lab8_6733800371_sec1.service;

import com.example.lab8_6733800371_sec1.model.Product;
import com.example.lab8_6733800371_sec1.model.Review;
import com.example.lab8_6733800371_sec1.repository.ProductRepository;
import com.example.lab8_6733800371_sec1.strategy.DiscountContext;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final DiscountContext discountContext;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
        this.discountContext = new DiscountContext();
    }

    public List<Product> getAllProducts() {
        List<Product> products = productRepository.findAll();
        products.forEach(this::applyDiscount);
        return products;
    }

    public Product getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));
        applyDiscount(product);
        return product;
    }

    public Product saveProduct(Product product) {
        linkChildEntities(product);
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, Product product) {
        product.setId(id);
        linkChildEntities(product);
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    // ผูก FK ฝั่ง child กลับมาที่ parent ก่อน save (JPA ไม่ทำให้อัตโนมัติ)
    private void linkChildEntities(Product product) {
        if (product.getDetail() != null) {
            product.getDetail().setProduct(product);
        }
        if (product.getReviews() != null) {
            for (Review review : product.getReviews()) {
                if (review.getReviewer() == null || review.getReviewer().isBlank()) continue;
                review.setProduct(product);
                if (review.getReviewDate() == null) {
                    review.setReviewDate(LocalDate.now());
                }
            }
        }
    }

    private void applyDiscount(Product product) {
        double discounted = discountContext.calculate(product.getDiscountType(), product.getPrice());
        product.setDiscountedPrice(discounted);
    }
}