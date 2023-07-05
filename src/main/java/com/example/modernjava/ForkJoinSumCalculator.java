package com.example.modernjava;

import java.util.concurrent.RecursiveTask;

public class ForkJoinSumCalculator extends RecursiveTask<Long> {

  private final long[] numbers;

  private final int start;
  private final int end;

  private static final long THRESHOLD = 10_000;


  // 메인테스크의 서브태스크를 재귀적으로 만들때 사용할 비공개 생성자
  public ForkJoinSumCalculator(long[] numbers) {
    this(numbers, 0, numbers.length);
  }
  private ForkJoinSumCalculator(long[] numbers, int start, int end) {
    this.numbers = numbers;
    this.start = start;
    this.end = end;
  }

  @Override
  protected Long compute() {

    int length = end - start;

    // 기준값과 같거나 작으면 순차적으로 결과를 계산
    if (length < THRESHOLD) {
      return computeSequentially();
    }

    ForkJoinSumCalculator leftTask = new ForkJoinSumCalculator(numbers, start, start + length / 2); // 첫번째 절반을 더하도록 서브태스크 생성

    leftTask.fork();   // 다른스레드로 새로 생성한 태스크를 비동기로 실행

    ForkJoinSumCalculator rightTask = new ForkJoinSumCalculator(numbers, start + length / 2, end); // 나머지 서브태스크 생성

    Long compute = rightTask.compute(); // 두번쨰서브태스크를 동기실행 , 추가 분할이 일어날수 있음 .

    Long join = leftTask.join(); // 첫번쨰 태스크 결과읽거나 , 결과 없으면 대기

    return join + compute;


  }

  // 더이상 분할 할수없을떄 서브태스크의 결과를 계산하는 단순알고리즘
  private long computeSequentially() {
    long sum = 0;

    for (int i = start; i < end; i++) {
      sum += numbers[i];
    }
    return sum;

  }
}
