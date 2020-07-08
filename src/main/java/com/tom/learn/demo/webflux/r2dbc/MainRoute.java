package com.tom.learn.demo.webflux.r2dbc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import lombok.RequiredArgsConstructor;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RequestPredicates.path;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;


/**
 * ProductRoute
 */
@Configuration
@RequiredArgsConstructor
public class MainRoute {

    private final ProductHandler productHandler;

    @Bean
    public RouterFunction<ServerResponse> mainRouter() {
        // return Rout
        return RouterFunctions.nest(path("/api"),
            RouterFunctions.route()
                .add(this.productRoute(this.productHandler))
                .build()
        );
    }


    private RouterFunction<ServerResponse> productRoute(ProductHandler productHandler) {
        return RouterFunctions
                .nest(path("/product"),
                    RouterFunctions.route(GET("/{id}"), productHandler::getDetail)
                                    .andRoute(GET("/"), productHandler::getAllProduct)
                                    .andRoute(POST("/").and(contentType(APPLICATION_JSON)), productHandler::saveProduct)
                                    .andRoute(PUT("/").and(contentType(APPLICATION_JSON)), productHandler::updateProduct)
                                    .andRoute(DELETE("/{id}"), productHandler::deleteProduct));
    }
}