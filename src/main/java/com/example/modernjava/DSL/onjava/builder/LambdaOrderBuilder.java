package com.example.modernjava.DSL.onjava.builder;

import com.example.modernjava.DSL.onjava.Order;
import com.example.modernjava.DSL.onjava.Trade.Type;
import java.util.function.Consumer;

public class LambdaOrderBuilder {

  private Order order = new Order();


  public static Order order(Consumer<LambdaOrderBuilder> consumer) {

    LambdaOrderBuilder builder = new LambdaOrderBuilder();

    consumer.accept(builder);

    return builder.order;

  }

  public void forCustomer(String customer
  ) {
    order.setCustomer(customer);
  }

  public void buy(Consumer<TradeBuilderV2> consumer) {
    trade(consumer, Type.BUY);

  }

  public void sell(Consumer<TradeBuilderV2> consumer) {
    trade(consumer, Type.SELL);

  }

  private void trade(Consumer<TradeBuilderV2> consumer, Type type) {
    TradeBuilderV2 builder = new TradeBuilderV2();
    builder.trade.setType(type);
    consumer.accept(builder);
    order.addTrades(builder.trade);
  }

}
