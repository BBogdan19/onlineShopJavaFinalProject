package com.sda.OnlineShop.controller;

import com.sda.OnlineShop.dto.ProductDto;

import com.sda.OnlineShop.dto.RegistrationDto;
import com.sda.OnlineShop.dto.SelectedProductDto;
import com.sda.OnlineShop.services.ProductService;
import com.sda.OnlineShop.services.RegistrationService;
import com.sda.OnlineShop.validators.ProductDtoValidator;
import com.sda.OnlineShop.validators.RegistrationDtoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private ProductService productService;
    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private RegistrationDtoValidator registrationDtoValidator;
    @Autowired
    private ProductDtoValidator productDtoValidator;

    @GetMapping("/addProduct")
    public String addProductGet(Model model) {
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);

        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String addProductPost(@ModelAttribute ProductDto productDto,
                                 @RequestParam("productImage") MultipartFile productImage,
                                 BindingResult bindingResult) {
        productDtoValidator.validate(productDto, bindingResult, productImage);
        if (bindingResult.hasErrors()) {
            return "addProduct";
        }

        productService.addProduct(productDto, productImage);

        return "addProduct";
    }

    @GetMapping("/home")
    public String homeGet(Model model) {
        List<ProductDto> productDto = productService.getAllProductDtos();
        model.addAttribute("productDtos", productDto);
        return "home";
    }

    @GetMapping("/product/{productId}")
    public String viewProductGet(@PathVariable(value = "productId") String productId, Model model) {
        Optional<ProductDto> optionalProductDto = productService.getOptionalProductDtoById(productId);
        if (optionalProductDto.isEmpty()) {
            return "error";
        }
        model.addAttribute("productDto", optionalProductDto.get());

        SelectedProductDto selectedProductDto = new SelectedProductDto();
        model.addAttribute("selectedProductDto", selectedProductDto);

        return "viewProduct";
    }

    @GetMapping("/registration")
    public String viewRegistrationGet(Model model) {
        RegistrationDto registrationDto = new RegistrationDto();
        model.addAttribute("registrationDto", registrationDto);
        return "registration";
    }

    @PostMapping("/registration")
    public String viewRegistrationPost(@ModelAttribute RegistrationDto registrationDto, BindingResult bindingResult) {

        registrationDtoValidator.validate(registrationDto, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }

        registrationService.addRegistration(registrationDto);

        return "registration";
    }

    @GetMapping("/logIn")
    public String viewLogInGet() {
        return "logIn";
    }

}
