package com.sda.OnlineShop.services;

import com.sda.OnlineShop.dto.SelectedProductDto;
import com.sda.OnlineShop.dto.ShoppingCartDto;
import com.sda.OnlineShop.entities.Product;
import com.sda.OnlineShop.entities.SelectedProduct;
import com.sda.OnlineShop.entities.ShoppingCart;
import com.sda.OnlineShop.repository.ProductRepository;
import com.sda.OnlineShop.repository.SelectedProductRepository;
import com.sda.OnlineShop.repository.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShoppingCartService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
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
