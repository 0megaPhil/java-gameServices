package com.firmys.gameservices.common;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

public class CommonSetTests {

  Faker faker = new Faker();

  @Test
  void commonSet_construct() {
    //    var objects =
    //        IntStream.range(0, 10)
    //            .parallel()
    //            .mapToObj(
    //                i ->
    //                    Characteristic.builder()
    //                        .name(faker.name().fullName())
    //                        .description(faker.witcher().witcher())
    //                        .build())
    //            .collect(Collectors.toSet());
    //
    //    Assertions.assertThat(objects).isNotNull();
    //
    //    String writable = ConversionUtils.writeSet(objects);
    //    Assertions.assertThat(writable).isNotNull();
    //    Assertions.assertThat(writable).contains(Characteristic.class.getName());
    //
    //    Set<Characteristic> parsed = ConversionUtils.readSet(writable);
    //    Assertions.assertThat(parsed).isNotNull();
    //    Assertions.assertThat(parsed.size()).isEqualTo(objects.size());
    //    Assertions.assertThat(parsed).containsAll(objects);
  }
}
