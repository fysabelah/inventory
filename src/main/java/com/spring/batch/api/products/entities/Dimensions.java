package com.spring.batch.api.products.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Dimensions {

    private BigDecimal width;

    private BigDecimal length;

    private BigDecimal height;
}
