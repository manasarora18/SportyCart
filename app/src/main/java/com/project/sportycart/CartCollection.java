package com.project.sportycart;

import com.project.sportycart.entity.Cart;

import java.util.ArrayList;
import java.util.List;

public class CartCollection {
    private static int counter;
    private static List<Cart> cartList=new ArrayList<>();
    public static void add(Cart c)
    {
        cartList.add(c);
        counter++;

    }
    public static List<Cart> get()
    {
        return cartList;
    }
}