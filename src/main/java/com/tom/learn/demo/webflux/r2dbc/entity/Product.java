package com.tom.learn.demo.webflux.r2dbc.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Table("product")
public class Product {
	@Id
	private Long id;
	private String description;
	private Boolean completed;
}