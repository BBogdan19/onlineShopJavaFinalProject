package com.sda.OnlineShop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
@GetMapping("/addProduct")
    public String addProductGet(){
    //teoretic aici executam "bussiness logic"
    //dupa intoarcem un nume de pagina
        return "addProduct";
    }
    @PostMapping("/addProduct")
    public String addProductPost(){
        System.out.println("S-a apelat add product!");
        return "addProduct";
    }
}
