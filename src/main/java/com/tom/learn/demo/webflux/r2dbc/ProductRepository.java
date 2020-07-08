package com.tom.learn.demo.webflux.r2dbc;

import com.tom.learn.demo.webflux.r2dbc.entity.Product;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import reactor.core.publisher.Mono;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {

    @Query("SELECT * FROM Product WHERE id = $1")
    Mono<Product> findByCustomQueryId(Long productId);
}