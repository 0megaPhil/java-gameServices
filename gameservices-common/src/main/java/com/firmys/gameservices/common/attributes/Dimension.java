package com.firmys.gameservices.common.attributes;

import static com.firmys.gameservices.common.JsonUtils.JSON;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firmys.gameservices.common.data.Attribute;
import java.util.Objects;
import java.util.stream.Stream;
import javax.measure.Quantity;
import javax.measure.Unit;
import lombok.Builder;
import lombok.With;
import si.uom.NonSI;
import si.uom.SI;
import systems.uom.common.Imperial;
import systems.uom.common.USCustomary;

@With
@Builder(toBuilder = true)
public record Dimension(String name, String unitType, Double value, String description)
    implements Attribute<String, Double> {

  public static <L1 extends Quantity<L1>> Dimension of(
      String name, Unit<L1> unitType, Number value) {
    return Dimension.builder()
        .name(name)
        .unitType(unitType.getName())
        .value(value.doubleValue())
        .build();
  }

  public static Dimension of(String name, String unitName, Number value) {
    return Dimension.builder().name(name).unitType(unitName).value(value.doubleValue()).build();
  }

  @SuppressWarnings("unchecked")
  private static <L1 extends Quantity<L1>> Unit<L1> findUnit(String unitName) {
    Stream<Unit<?>> unitStream =
        Stream.concat(
            Stream.concat(
                Imperial.getInstance().getUnits().stream(), SI.getInstance().getUnits().stream()),
            Stream.concat(
                NonSI.getInstance().getUnits().stream(),
                USCustomary.getInstance().getUnits().stream()));

    return (Unit<L1>)
        unitStream
            .filter(un -> un.getName() != null)
            .filter(un -> Objects.equals(un.getName().toLowerCase(), unitName.toLowerCase()))
            .findFirst()
            .orElse(null);
  }

  @JsonIgnore
  public <L1 extends Quantity<L1>> Unit<L1> asUnit() {
    return findUnit(unitType);
  }

  public <L extends Quantity<L>> Dimension translate(String unitName) {
    Unit<L> newUnit = findUnit(unitName);
    Unit<L> originalUnit = findUnit(name);
    Number newValue = originalUnit.getConverterTo(newUnit).convert(value);
    return Dimension.of(name, newUnit, newValue);
  }

  public <L extends Quantity<L>> Dimension translate(Unit<L> unit) {
    Unit<L> originalUnit = findUnit(name);
    Number newValue = originalUnit.getConverterTo(unit).convert(value);
    return Dimension.of(name, unit, newValue);
  }

  @SuppressWarnings("unchecked")
  public <L extends Quantity<L>> Number valueAs(Unit<L> unit) {
    return ((Unit<L>) findUnit(unitType)).getConverterTo(unit).convert(value);
  }

  public <L extends Quantity<L>> Number valueAs(String unitName) {
    return findUnit(unitType).getConverterTo(findUnit(unitName)).convert(value);
  }

  @Override
  public String toString() {
    return JSON.toJson(this);
  }
}
