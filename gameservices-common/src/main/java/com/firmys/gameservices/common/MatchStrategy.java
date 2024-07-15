package com.firmys.gameservices.common;

public enum MatchStrategy {
  ANY("any"),
  ALL("all"),
  EXACT("exact"),
  PARTIAL("partial");

  private final String strategyString;

  MatchStrategy(String strategyString) {
    this.strategyString = strategyString;
  }

  public static MatchStrategy get(String strategyString) {
    if (strategyString == null) {
      return null;
    }
    return MatchStrategy.valueOf(strategyString.toUpperCase());
  }

  public String getStrategyString() {
    return strategyString;
  }
}
