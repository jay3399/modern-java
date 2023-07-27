package com.example.modernjava.DSL.onjava;

import java.util.ArrayList;
import java.util.List;

public class Order {


  private String customer;

  private List<Trade> trades = new ArrayList<>();


  public String getCustomer() {
    return customer;
  }

  public void setCustomer(String customer) {
    this.customer = customer;
  }

  public List<Trade> getTrades() {
    return trades;
  }

  public void addTrades(Trade trade) {
    trades.add(trade);

  }
}
