package br.com.sales.exceptions;

public class OrderNotFoundException extends RuntimeException{
    public OrderNotFoundException(){
        super("Order not found");
    }
}
