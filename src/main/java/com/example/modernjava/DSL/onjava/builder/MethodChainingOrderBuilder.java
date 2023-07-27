package com.example.modernjava.DSL.onjava.builder;

import com.example.modernjava.DSL.onjava.Order;
import com.example.modernjava.DSL.onjava.Trade;
import com.example.modernjava.DSL.onjava.Trade.Type;

public class MethodChainingOrderBuilder {


  public final Order order = new Order();


  private MethodChainingOrderBuilder(String customer) {
    order.setCustomer(customer);
  }

  public static MethodChainingOrderBuilder forCustomer(String customer) {
    return new MethodChainingOrderBuilder(customer);
  }

  public TradeBuilder buy(int quantity) {
    return new TradeBuilder(this, Type.BUY, quantity);
  }

  public TradeBuilder sell(int quantity) {
    return new TradeBuilder(this, Type.SELL, quantity);
  }


  public MethodChainingOrderBuilder addTrade(Trade trade) {
    order.addTrades(trade);
    return this;
  }

  public Order end() {
    return order;
  }




}
