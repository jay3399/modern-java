package com.example.modernjava.Strategy;

public abstract class ProcessingObject<T> {

  public ProcessingObject<T> succesor;

  public void setSuccesor(ProcessingObject<T> succesor) {
    this.succesor = succesor;
  }

  public T handle(T input) {
    T t = handWork(input);
    if (succesor != null) {
      return succesor.handle(t);
    }
    return t;
  }


  abstract protected T handWork(T input);


}
