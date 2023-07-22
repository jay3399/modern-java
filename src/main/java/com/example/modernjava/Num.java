package com.example.modernjava;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Num {



  public List<Integer> filterNum(FilterNum filterNum , List<Integer> num) {
    List<Integer> nums = new ArrayList<>();

    for (Integer a : num) {
      if (filterNum.check(a)) {
        nums.add(a);
      }
    }
    return nums;
  }


}
