package com.sda.OnlineShop.controller;

import com.sda.OnlineShop.dto.ProductDto;

import com.sda.OnlineShop.dto.RegistrationDto;
import com.sda.OnlineShop.dto.SelectedProductDto;

import com.sda.OnlineShop.dto.ShoppingCartDto;
import com.sda.OnlineShop.entities.User;
import com.sda.OnlineShop.repository.ShoppingCartRepository;
import com.sda.OnlineShop.services.*;



import com.sda.OnlineShop.validators.ProductDtoValidator;
import com.sda.OnlineShop.validators.RegistrationDtoValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @Autowired

    private ShoppingCartService shoppingCartService;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserDetailsServiceImp userDetailsService;



    

    @GetMapping("/addProduct")
    public String addProductGet(Model model) {
        ProductDto productDto = new ProductDto();
        model.addAttribute("productDto", productDto);
        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String addProductPost(@ModelAttribute ProductDto productDto,
                                 @RequestParam("productImage") MultipartFile productImage)
    {

        productService.addProduct(productDto, productImage);
        System.out.println(productDto);
        return "redirect:/addProduct";
    }

    @GetMapping("/home")
    public String homeGet(Model model) {
        List<ProductDto> productDtos = productService.getAllProductDtos();
        model.addAttribute("productDtos", productDtos);
        return "home";
    }

    @GetMapping("/product/{name}/{productId}")
    public String viewProductGet(Model model,
                                 @PathVariable(value = "productId") String productId,
                                 @PathVariable(value = "name") String name)
    {
        Optional<ProductDto> optionalProductDto = productService.getOptionalProductDtoById(productId);
        if (optionalProductDto.isEmpty()) {
            return "error";
        }
        model.addAttribute("productDto", optionalProductDto.get());

        SelectedProductDto selectedProductDto = new SelectedProductDto();
        model.addAttribute("selectedProductDto", selectedProductDto);

        return "viewProduct";
    }
    @PostMapping("/product/{productId}")
    public String viewProductPost(@ModelAttribute SelectedProductDto selectedProductDto,
                                  @PathVariable(value = "productId")String productId,
                                    Authentication authentication){
        System.out.println(selectedProductDto);
        System.out.println(authentication.getName());
        shoppingCarService.addToCart(selectedProductDto,productId, authentication.getName());
        return "redirect:/product/"+ productId;

    }

    @PostMapping("/product/{name}/{productId}")
    public String viewProductPost(@ModelAttribute SelectedProductDto selectedProductDto,
                                  @PathVariable(value = "productId") String productId,
                                  @PathVariable(value = "name") String name,
                                  Authentication authentication) {
        System.out.println(selectedProductDto);
        System.out.println(authentication.getName());

        shoppingCartService.addToCart(selectedProductDto, productId, authentication.getName());
        return "redirect:/product/" + name + "/" + productId;
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
    public String viewLoginGet() {
        return "login";
    }

    @GetMapping("/checkout")
    public String viewCheckoutGet(Authentication authentication, Model model) {
        ShoppingCartDto shoppingCartDto = shoppingCartService.getShoppingCartDto(authentication.getName());
        model.addAttribute("shoppingCartDto", shoppingCartDto);
        System.out.println(shoppingCartDto);
        return "checkout";
    }


    @PostMapping("/confirmation")
    public String launchOrderPost(Authentication authentication, Model model) {

        orderService.launchOrder(authentication.getName());
        ShoppingCartDto shoppingCartDto = shoppingCartService.getShoppingCartDto(authentication.getName());
        model.addAttribute("shoppingCartDto", shoppingCartDto);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime now = LocalDateTime.now();

        User user = userDetailsService.getCurrentUserConfimation(authentication.getName());
        model.addAttribute("user", user);
        model.addAttribute("dtf", dtf);
        model.addAttribute("now", now);
        return "confirmation";
    }



 



}
