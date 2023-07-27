package com.example.modernjava.Strategy;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ProductFactory {

  public static Product createProduct(String name) {
    switch (name) {

      case "loan" : return new Loan();
      case "stock" : return new Stock();
      case "bond" : return new Bond();
      default:
        throw new RuntimeException("no such product" + name);

    }
  }

  public static Product createProductWithLambdas(String name) {
    Supplier<Product> p = map.get(name);
    if(p!=null) return p.get();
    throw new IllegalArgumentException("no such product" + name);

  }

  final static Map<String, Supplier<Product>> map = new HashMap<>();

  static {
    map.put("loan", Loan::new);
    map.put("stock", Stock::new);
    map.put("bond", Bond::new);
  }

}
