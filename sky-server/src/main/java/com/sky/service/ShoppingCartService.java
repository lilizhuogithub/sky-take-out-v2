package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    /**
     * 添加购物车
     * @param shoppingCartDTO
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);


    List<ShoppingCart> showShoppingCart();
<<<<<<< HEAD

    void cleanShoppingCart();
=======
>>>>>>> 853085de920e01958818a6476fb0a4f1fa7d5779
}
