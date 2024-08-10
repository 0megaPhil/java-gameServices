package com.firmys;

import java.util.*;

public class Main {

  public static void main(String[] args) {
    String[] countries = {"USA", "Canada", "Mexico"};
    Long[] populations = {39L, 35L, 29L};

    function(countries, populations);
  }

  public static void function(String[] countries, Long[] populations) {
    List<List<Double>> populationList = new ArrayList<>();
    for (int i = 0; i < countries.length; i++) {
      if (i == 0) {
        populationList.add(List.of(0D, populations[i].doubleValue()));
      } else {
        populationList.add(
            List.of(
                populationList.get(i - 1).get(1),
                populations[i].doubleValue() + populationList.get(i - 1).get(1)));
      }
    }
    Double max =
        populationList.stream()
            .map(list -> list.get(1))
            .max(Comparator.comparing(Double::doubleValue))
            .get();
    System.out.println("PopList: " + populationList);
    Random rand = new Random();
    var randomRange = rand.nextDouble(0, max);
    System.out.println("Random Range: " + randomRange);

    for (int i = 0; i < populationList.size(); i++) {
      if (randomRange >= populationList.get(i).get(0)
          && randomRange < populationList.get(i).get(1)) {
        System.out.println("Chose Country: " + countries[i]);
      }
    }
  }
}
