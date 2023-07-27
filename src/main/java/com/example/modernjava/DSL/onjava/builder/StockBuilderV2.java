package com.example.modernjava.DSL.onjava.builder;

import com.example.modernjava.DSL.onjava.Stock;

public class StockBuilderV2 {

  public Stock stock = new Stock();

  public void symbol(String symbol) {
    stock.setSymbol(symbol);
  }

  public void market(String market) {
    stock.setMarket(market);
  }

}
