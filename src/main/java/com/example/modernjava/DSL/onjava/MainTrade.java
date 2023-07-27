package com.example.modernjava.DSL.onjava;

import static com.example.modernjava.DSL.onjava.builder.LambdaOrderBuilder.*;
import static com.example.modernjava.DSL.onjava.builder.NestedFunctionOrderBuilder.*;

import com.example.modernjava.DSL.onjava.Trade.Type;
import com.example.modernjava.DSL.onjava.builder.MethodChainingOrderBuilder;

public class MainTrade {

  public static void main(String[] args) {

    Order order = new Order();

    order.setCustomer("BigBank");

    Stock stock1 = new Stock();

    stock1.setSymbol("IBM");
    stock1.setMarket("NYSE");

    Trade trade1 = new Trade();

    trade1.setType(Type.BUY);
    trade1.setQuantity(100);
    trade1.setPrice(125);
    trade1.setStock(stock1);

    order.addTrades(trade1);

    Stock stock2 = new Stock();

    stock2.setSymbol("GOOGLE");
    stock2.setMarket("NASDAQ");

    Trade trade2 = new Trade();

    trade2.setType(Type.BUY);
    trade2.setQuantity(10);
    trade2.setPrice(3000);
    trade2.setStock(stock2);

    order.addTrades(trade2);

    Order builder = MethodChainingOrderBuilder.forCustomer("Jay")
        .buy(80)
        .stock("IBM")
        .on("NYSE")
        .at(125)
        .sell(50)
        .stock("GOOGLE")
        .on("NASDAQ")
        .at(375)
        .end();

    System.out.println("builder = " + builder.getCustomer());

    Order order1 = order("BinkBank", buy(80, stock("IBM", on("NYSE")), at(300)),
        sell(50, stock("GOOGLE", on("NASDAQ")), at(375)));

    System.out.println("order1 = " + order1.getCustomer());




    order( o -> {
      o.forCustomer("Serra");
      o.buy(
          t -> {
            t.quantity(80);
            t.price(125);
            t.stock(
                s -> {
                  s.symbol("IMB");
                  s.market("NYSE");
                }
            );
          }
      );
      o.sell(t -> {
        t.quantity(50);
        t.price(375.00);
        t.stock(
            s->{
              s.symbol("GOOGLE");
              s.market("NASDAQ");

            }
        );
      });
    });




  }

}
