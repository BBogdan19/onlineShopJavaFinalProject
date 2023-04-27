package com.sda.OnlineShop.services;

import com.sda.OnlineShop.dto.SelectedProductDto;
import com.sda.OnlineShop.dto.ShoppingCartDto;
import com.sda.OnlineShop.entities.Product;
import com.sda.OnlineShop.entities.SelectedProduct;
import com.sda.OnlineShop.entities.ShoppingCart;

import com.sda.OnlineShop.mapper.ShoppingCardMapper;
import com.sda.OnlineShop.repository.ProductRepository;
import com.sda.OnlineShop.repository.SelectProductRepository;
import com.sda.OnlineShop.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShoppingCartService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired

    private SelectProductRepository selectProductRepository;
    @Autowired
    private ShoppingCardMapper shoppingCardMapper;

    public void addToCart(SelectedProductDto selectedProductDto, String productId, String authenticatedUserEmail) {
        Optional<Product> optionalProduct = productRepository.findById(Integer.valueOf(productId));
        Product product = optionalProduct.get();
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserEmail(authenticatedUserEmail);

        SelectedProduct selectedProduct = buildProduct(selectedProductDto, product, shoppingCart);
        selectProductRepository.save(selectedProduct);
    }

    private SelectedProduct buildProduct(SelectedProductDto selectedProductDto, Product product, ShoppingCart shoppingCart) {
        SelectedProduct selectedProduct = new SelectedProduct();
        selectedProduct.setQuantity(Integer.valueOf(selectedProductDto.getQuantity()));
        selectedProduct.setProduct(product);
        selectedProduct.setShoppingCart(shoppingCart);
        return selectedProduct;
    }

    public ShoppingCartDto getShoppingCartDto (String authenticatedUserEmail) {
        ShoppingCart shoppingCart=shoppingCartRepository.findByUserEmail(authenticatedUserEmail);
        ShoppingCartDto shoppingCartDto= shoppingCardMapper.map(shoppingCart);

        return shoppingCartDto;

   }


    private SelectedProductRepository selectedProductRepository;
    public void addToCart(SelectedProductDto selectedProductDto,String productId, String authenticatedUserEmail){
        Optional<Product> optionalProduct = productRepository.findById(Integer.valueOf(productId));
        Product product = optionalProduct.get();
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserEmail(authenticatedUserEmail);
        SelectedProduct selectedProduct = new SelectedProduct();
        selectedProductDto.setQuantity(String.valueOf(Integer.valueOf(selectedProductDto.getQuantity())));
        selectedProduct.setProduct(product);
        selectedProduct.setShoppingCart(shoppingCart);

        SelectedProduct selectedProduct1 = buildProduct(selectedProduct,product,shoppingCart);
        selectedProductRepository.save(selectedProduct);
    }

    public ShoppingCartDto getShoppingCarDto(String authenticatedUserEmail){
        return null;
        // TODO
    }

}
