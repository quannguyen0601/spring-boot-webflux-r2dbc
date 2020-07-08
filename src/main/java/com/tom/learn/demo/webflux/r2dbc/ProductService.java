package com.tom.learn.demo.webflux.r2dbc;

import com.tom.learn.demo.webflux.r2dbc.entity.Product;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    Flux<Product> getAllProduct(){
        return productRepository.findAll();
    }

    Mono<Product> saveProduct(Product product) {
        return productRepository.save(product);
    }

    Mono<Product> getProduct(Long productId) {
        return productRepository.findByCustomQueryId(productId);
    }

    // Update an existing task record
    @Transactional
    public Mono<Product> updateProduct(final Product product) {
        return this.productRepository.findById(product.getId())
                .flatMap(t -> {
                    t.setDescription(product.getDescription());
                    t.setCompleted(product.getCompleted());
                    return this.productRepository.save(t);
                });
    }

    // Delete the task record by specified id
    @Transactional
    public Mono<Void> deleteProduct(final Long id){
        return this.productRepository.findById(id)
                .flatMap(this.productRepository::delete);
    }
}