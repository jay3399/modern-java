package com.example.modernjava.DSL.onjava;

import com.example.modernjava.DSL.onjava.builder.MethodChainingOrderBuilder;

public class TradeBuilderWithStock {

  private final MethodChainingOrderBuilder builder;

  public final Trade trade;


  public TradeBuilderWithStock(MethodChainingOrderBuilder builder, Trade trade) {
    this.builder = builder;
    this.trade = trade;
  }


  public MethodChainingOrderBuilder at(double price
  ) {
    trade.setPrice(price);
    return builder.addTrade(trade);
  }

}
