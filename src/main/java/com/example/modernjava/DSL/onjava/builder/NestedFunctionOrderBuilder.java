package com.example.modernjava.DSL.onjava.builder;

import com.example.modernjava.DSL.onjava.Order;
import com.example.modernjava.DSL.onjava.Stock;
import com.example.modernjava.DSL.onjava.Trade;
import com.example.modernjava.DSL.onjava.Trade.Type;
import java.util.stream.Stream;
import org.springframework.boot.web.servlet.filter.OrderedRequestContextFilter;

public class NestedFunctionOrderBuilder {


  public static Order order(String customer, Trade... trades) {

    Order order = new Order();

    order.setCustomer(customer);

    Stream.of(trades).forEach(order::addTrades);

    return order;
  }


  public static Trade buy(int quantity, Stock stock, double price) {
    return buildTrade(quantity, stock, price, Type.BUY);
  }

  public static Trade sell(int quantity, Stock stock, double price) {

    return buildTrade(quantity, stock, price, Type.SELL);
  }


  private static Trade buildTrade(int quantity, Stock stock, double price, Trade.Type buy) {

    Trade trade = new Trade();

    trade.setQuantity(quantity);
    trade.setStock(stock);
    trade.setPrice(price);
    trade.setType(buy);
    return trade;
  }

  public static double at(double price) {
    return price;
  }

  public static Stock stock(String symbol , String market
  ) {
    Stock stock = new Stock();
    stock.setSymbol(symbol);
    stock.setMarket(market);
    return stock;
  }

  public static String on(String market) {
    return market;
  }

}
