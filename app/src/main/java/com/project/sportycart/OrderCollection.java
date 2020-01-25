package com.project.sportycart;

import com.project.sportycart.entity.OrderTable;

import java.util.ArrayList;
import java.util.List;

public class OrderCollection {

    private static int counter;
    private static List<OrderTable> orderTableList=new ArrayList<>();
    public static void add(OrderTable orderTable)
    {
        orderTableList.add(orderTable);
        counter++;

    }
    public static List<OrderTable> get()
    {
        return orderTableList;
    }


}
