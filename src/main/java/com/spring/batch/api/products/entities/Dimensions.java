package com.spring.batch.api.products.entities;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dimensions {

    private BigDecimal width;

    private BigDecimal length;

    private BigDecimal height;
}
