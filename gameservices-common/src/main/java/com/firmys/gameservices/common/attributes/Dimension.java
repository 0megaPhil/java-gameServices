package com.firmys.gameservices.common.attributes;

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
public record Dimension<L extends Quantity<L>>(String name, Double value, String description)
    implements Attribute<String, Double> {

  public static <L1 extends Quantity<L1>> Dimension<L1> of(Unit<L1> key, Number value) {
    return Dimension.<L1>builder().name(key.getName()).value(value.doubleValue()).build();
  }

  public static <L1 extends Quantity<L1>> Dimension<L1> of(String unitName, Number value) {
    return Dimension.<L1>builder().name(unitName).value(value.doubleValue()).build();
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

  public Unit<L> unit() {
    return findUnit(name);
  }

  public Dimension<L> translate(String unitName) {
    Unit<L> newUnit = findUnit(unitName);
    Unit<L> originalUnit = findUnit(name);
    Number newValue = originalUnit.getConverterTo(newUnit).convert(value);
    return Dimension.of(newUnit, newValue);
  }

  public Dimension<L> translate(Unit<L> unit) {
    Unit<L> originalUnit = findUnit(name);
    Number newValue = originalUnit.getConverterTo(unit).convert(value);
    return Dimension.of(unit, newValue);
  }

  public Number valueAs(Unit<L> unit) {
    Unit<L> originalUnit = findUnit(name);
    return originalUnit.getConverterTo(unit).convert(value);
  }

  public Number valueAs(String unitName) {
    Unit<L> originalUnit = findUnit(name);
    return originalUnit.getConverterTo(findUnit(unitName)).convert(value);
  }
}
