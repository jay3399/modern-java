package com.example.modernjava.DSL.onjava.builder;

import com.example.modernjava.DSL.onjava.Trade;
import java.util.function.Consumer;

public class TradeBuilderV2 {

  public Trade trade = new Trade();

  public void quantity(int quantity) {
    trade.setQuantity(quantity);
  }

  public void price(double price) {
    trade.setPrice(price);
  }

  public void stock(Consumer<StockBuilderV2> consumer) {
    StockBuilderV2 builder = new StockBuilderV2();
    consumer.accept(builder);
    trade.setStock(builder.stock);

  }

}
