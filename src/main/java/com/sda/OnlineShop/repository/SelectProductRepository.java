package com.sda.OnlineShop.repository;

import com.sda.OnlineShop.entities.SelectedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SelectProductRepository extends JpaRepository<SelectedProduct,Integer> {
}
