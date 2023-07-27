package com.example.modernjava.DSL.onjava.builder;

import com.example.modernjava.DSL.onjava.Stock;
import com.example.modernjava.DSL.onjava.Trade;
import com.example.modernjava.DSL.onjava.TradeBuilderWithStock;
import com.example.modernjava.DSL.onjava.builder.MethodChainingOrderBuilder;

public class StockBuilder {


  private final MethodChainingOrderBuilder buildr;

  private final Trade trade;

  private final Stock stock = new Stock();


  public StockBuilder(MethodChainingOrderBuilder buildr, Trade trade, String symbol) {
    this.buildr = buildr;
    this.trade = trade;
    stock.setSymbol(symbol);
  }

  public TradeBuilderWithStock on(String market) {
    stock.setMarket(market);
    trade.setStock(stock);
    return new TradeBuilderWithStock(buildr, trade);
  }




}
