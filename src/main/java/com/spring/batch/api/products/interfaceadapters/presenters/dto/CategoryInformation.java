package com.spring.batch.api.products.interfaceadapters.presenters.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import lombok.Getter;
import lombok.Setter;

@JsonSubTypes({
        @JsonSubTypes.Type(value = BookDto.class, name = "BOOKS"),
        @JsonSubTypes.Type(value = ElectronicDto.class, name = "ELECTRONICS"),
        @JsonSubTypes.Type(value = ClothesDto.class, name = "CLOTHES"),
        @JsonSubTypes.Type(value = ShoeDto.class, name = "SHOES")
})
@Getter
@Setter
public abstract class CategoryInformation {

}
