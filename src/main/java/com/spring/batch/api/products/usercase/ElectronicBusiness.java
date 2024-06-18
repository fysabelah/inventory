package com.spring.batch.api.products.usercase;

import com.spring.batch.api.products.entities.Electronic;
import com.spring.batch.api.products.entities.Product;
import com.spring.batch.api.products.entities.availability.ProductAvailabilityElectronic;
import com.spring.batch.api.products.utils.enums.ElectronicType;
import com.spring.batch.api.products.utils.enums.ProductCategory;
import com.spring.batch.api.products.utils.exceptions.BusinessException;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ElectronicBusiness extends ProductBusiness {

    protected ElectronicBusiness(Clock clock) {
        super(clock);
    }

    @Override
    public void updateQuantity(Integer quantity, Integer protection, LocalDateTime updatedAt, Product product) throws BusinessException {
        // TODO para os casos que tem lista é necessário passar o sku. Nesse caso talvez sera interessante realizar interface ou lançar throw quando não implementado.
    }

    @Override
    public void updateSpecificInformation(Product toUpdate, Product newInformation) throws BusinessException {

    }

    public void updateToInsert(Electronic electronic) {
        String model = electronic.getModel();
        String brand = electronic.getBrand();
        ElectronicType type = electronic.getType();

        electronic.getAvailability().forEach(availability -> {
            availability.setSku(
                    super.createSku(List.of(model, brand, type.name(), availability.getColor()),
                            ProductCategory.ELECTRONICS)
            );

            availability.setUpdatedAt(LocalDateTime.now(clock));
        });
    }

    public void updateAvailability(Product product, String sku, ProductAvailabilityElectronic availabilityElectronic) {
        /*List<ProductAvailabilityElectronic> items = product.getElectronic().getAvailability()
                .stream()
                .filter(item -> !item.getSku().equals(sku))
                .collect(Collectors.toList());

        availabilityElectronic.setSku(sku);
        availabilityElectronic.setUpdatedAt(LocalDateTime.now(clock));

        items.add(availabilityElectronic);

        product.getElectronic().setAvailability(items);*/
    }

    public void verifyAndUpdate(Product product, String model, String brand, String name, String features) throws BusinessException {
       /* boolean isToUpdate = false;

        if (model != null && !model.isBlank() && !product.getElectronic().getModel().equals(model)) {
            product.getElectronic().setModel(model);
            isToUpdate = true;
        }

        if (brand != null && !brand.isBlank() && !product.getElectronic().getBrand().equals(brand)) {
            isToUpdate = true;
            product.getElectronic().setBrand(brand);
        }

        if (name != null && !name.isBlank() && !product.getElectronic().getName().equals(name)) {
            isToUpdate = true;
            product.getElectronic().setName(name);
        }

        if (features != null && !features.isBlank() && !features.equals(product.getElectronic().getFeatures())) {
            isToUpdate = true;
            product.getElectronic().setFeatures(features);
        }

        if (!isToUpdate) {
            throw new BusinessException("NOTHING_TO_UPDATE");
        }

        setSku(product);*/
    }
}
