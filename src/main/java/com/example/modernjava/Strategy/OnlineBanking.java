package com.example.modernjava.Strategy;


import java.util.function.Consumer;

/**
 * 템플릿 메서드
 *
 * 각 지점은 , 해당 클래스를 상속받아 makeCustomerHappy 메서드가 원하는 동작을 수행하도록 구현
 */

public abstract class OnlineBanking {

  public void processCustomer(int id) {
    //Customer c = repository.get(id)
    //makeCustomerHappy(c)
  }

  /**
   * 람다 표현식을 사용해서 , 클래스 상속없이 사용가능
   */


//  public void processCustomer(int id , Consumer<Customer> makeCustomerHappy) {
    //Customer c = repository.get(id)
    //makeCustomerHappy.accept(c)
//  }

//  new OnlineBanking().processCustomer(1337 , (Consumer c) -> System.out.println("Hello" + c.getName()))


//  abstract void makeCustomerHappy(Customer c);




}
