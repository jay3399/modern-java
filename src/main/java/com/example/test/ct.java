package com.example.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class ct {

  public static void main(String[] args) {
    String[] input = new String[]{
        "1622025201 REQUEST 10001 192.168.0.1",
        "1622025202 REQUEST 10002 192.168.0.2",
        "1622025203 REQUEST 10003 192.168.0.1",
        "1622025211 RESPONSE 10003",
        "1622025212 RESPONSE 10002",
        "1622025213 RESPONSE 10001",
        "1622025221 REQUEST 10004 192.168.0.2",
        "1622025223 REQUEST 10005 192.168.0.2",
        "1622025230 RESPONSE 10004",
        "1622025231 REQUEST 10006 192.168.0.3",
        "1622025236 RESPONSE 10006"
    };

    Map<String, Integer> ipRequestCount = new HashMap<>();

    for (String log : input) {
      String[] parts = log.split(" ");
      String logType = parts[1];

      if (logType.equals("REQUEST")) {
        String ipAddress = parts[3];
        ipRequestCount.put(ipAddress, ipRequestCount.getOrDefault(ipAddress, 0) + 1);
      }
    }

    for (Map.Entry<String, Integer> entry : ipRequestCount.entrySet()) {
      System.out.println("(" + entry.getKey() + ", " + entry.getValue() + ")");
    }

    Map<String, Long> ipRequestCount2 = Arrays.stream(input)
        .filter(log -> log.split(" ")[1].equals("REQUEST"))
        .map(log -> log.split(" ")[3])
        .collect(Collectors.groupingBy(ip -> ip, Collectors.counting()));

    ipRequestCount2.forEach((ip, count) -> System.out.println("(" + ip + ", " + count + ")"));


    Map<String, Long> requestTimes = new HashMap<>();
    Map<String, Long> responseTimes = new HashMap<>();

    for (String log : input) {
      String[] parts = log.split(" ");
      long timestamp = Long.parseLong(parts[0]);
      String logType = parts[1];
      String requestId = parts[2];

      if (logType.equals("REQUEST")) {
        requestTimes.put(requestId, timestamp);
      } else if (logType.equals("RESPONSE")) {
        responseTimes.put(requestId, timestamp);
      }
    }

    for (Map.Entry<String, Long> entry : requestTimes.entrySet()) {
      String requestId = entry.getKey();
      long requestTime = entry.getValue();
      long responseTime = responseTimes.getOrDefault(requestId, -1L);

      if (responseTime != -1) {
        long elapsedTime = responseTime - requestTime;
        System.out.println("(" + requestId + ", " + requestTime + ", " + elapsedTime + ")");
      } else {
        System.out.println("(" + requestId + ", " + requestTime + ", FAIL)");
      }
    }
  }

  }



