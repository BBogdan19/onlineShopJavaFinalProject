package com.sda.OnlineShop.validators;

import com.sda.OnlineShop.dto.ProductDto;
import com.sda.OnlineShop.entities.Product;
import com.sda.OnlineShop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class ProductDtoValidator {
    @Autowired
    private ProductRepository productRepository;

    public void validate(ProductDto productDto, BindingResult bindingResult, MultipartFile productImage) {
        Optional<Product> optionalProduct =  productRepository.findByName(productDto.getName()) ;
        if (optionalProduct.isPresent()) {
            FieldError fieldError = new FieldError("productDto", "name", " The product is already in use!");
            bindingResult.addError(fieldError);
        }
        if (productDto.getName().length() == 0) {
            FieldError fieldError = new FieldError("productDto", "name", " The name is empty!");
            bindingResult.addError(fieldError);
        } else if (productDto.getPrice().length() < 2) {
            FieldError fieldError = new FieldError("productDto", "price", " The price is empty!");
            bindingResult.addError(fieldError);
        } else if (productDto.getQuantity().length() == 0) {
            FieldError fieldError = new FieldError("productDto", "quantity", " The quantity is empty!");
            bindingResult.addError(fieldError);
        } else if (productDto.getDescription().length() < 6) {
            FieldError fieldError = new FieldError("productDto", "description", " The description is too short!");
            bindingResult.addError(fieldError);
        }else if (productImage.isEmpty()){
            FieldError fieldError = new FieldError("productDto", "image", " The Image is empty!");
            bindingResult.addError(fieldError);
        }

        try{
            Integer validatorNumber= Integer.valueOf(productDto.getPrice());
        }catch (NumberFormatException e){
            FieldError fieldError = new FieldError("productDto", "price", " The price have letters!");
            bindingResult.addError(fieldError);
        }

        try{
            Integer validatorNumber= Integer.valueOf(productDto.getQuantity());
        }catch (NumberFormatException e){
            FieldError fieldError = new FieldError("productDto", "quantity", " The quantity have letters!");
            bindingResult.addError(fieldError);
        }


    }
}
