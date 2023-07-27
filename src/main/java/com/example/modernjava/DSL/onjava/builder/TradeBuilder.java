package com.example.modernjava.DSL.onjava.builder;

import com.example.modernjava.DSL.onjava.Trade;

public class TradeBuilder {

  private final MethodChainingOrderBuilder builder;
  public final Trade trade = new Trade();


  public TradeBuilder(MethodChainingOrderBuilder builder, Trade.Type type, int quantity) {

    this.builder = builder;

    trade.setType(type);

    trade.setQuantity(quantity);
  }

  public StockBuilder stock(String symbol) {
    return new StockBuilder(builder, trade, symbol);
  }


}