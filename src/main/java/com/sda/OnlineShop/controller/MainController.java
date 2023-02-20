package com.sda.OnlineShop.controller;

import com.sda.OnlineShop.dto.ProductDto;

import com.sda.OnlineShop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private ProductService productService;

    @GetMapping("/addProduct")
    public String addProductGet(Model model) {
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);

        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String addProductPost(@ModelAttribute ProductDto productDto,
                                 @RequestParam("productImage") MultipartFile productImage) {
        productService.addProduct(productDto, productImage);
        System.out.println(productDto);

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
      Optional <ProductDto> optionalProductDto = productService.getOptionalProductDtoById(productId);
      if(optionalProductDto.isEmpty()){
          return "error";
      }
        model.addAttribute("productDto",optionalProductDto.get());
        return "viewProduct";
    }


}
