package com.tom.learn.demo.webflux.r2dbc;

import com.tom.learn.demo.webflux.r2dbc.entity.Product;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ProductHandler {

    private  final ProductService productService;

    Mono<ServerResponse> getAllProduct(final ServerRequest request){
        return ServerResponse.ok().body(productService.getAllProduct().log(), Product.class);
    }

    Mono<ServerResponse> getDetail(final ServerRequest request){
        Long productId = Long.parseLong(request.pathVariable("id"));
        return productService.getProduct(productId)
                            .flatMap(i -> ServerResponse.ok().bodyValue(i))
                            .switchIfEmpty(ServerResponse.notFound().build());
    }

    Mono<ServerResponse> saveProduct(final ServerRequest request) {
        Mono<Product> product = request.bodyToMono(Product.class);
        return product
                .map(i -> productService.saveProduct(i))
                .flatMap(i -> ServerResponse.ok().body(i, Product.class))
                .doOnError(err  -> ServerResponse.status(500).bodyValue(err.getMessage()));
    }

    // Update an existing task record
    Mono<ServerResponse> updateProduct(final ServerRequest request) {
        Mono<Product> product = request.bodyToMono(Product.class);
        return  product.map(i -> productService.updateProduct(i))
                        .flatMap(i -> ServerResponse.ok().body(i, Product.class));
    }

    // Delete the task record by specified id
    Mono<ServerResponse> deleteProduct(final ServerRequest request){
        Long productId = Long.parseLong(request.pathVariable("id"));
        return this.productService.deleteProduct(productId).log()
                .flatMap(i -> ServerResponse.ok().bodyValue("Delete OK"));
    }
}