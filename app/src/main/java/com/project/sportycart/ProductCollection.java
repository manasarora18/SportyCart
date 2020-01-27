package com.project.sportycart;

import com.project.sportycart.entity.Product;
import java.util.ArrayList;
import java.util.List;

public class ProductCollection {
    private static int counter;
    private static List<Product> productList=new ArrayList<>();
    public static void add(Product product)
    {
        productList.add(product);
        counter++;
    }
    public static List<Product> get()
    {
        return productList;
    }
}