/*
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 *
 * Copyright 2012-2020 the original author or authors.
 */
package org.assertj.core.api.recursive.comparison;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.internal.TypeComparators.defaultTypeComparators;
import static org.assertj.core.test.AlwaysEqualComparator.ALWAY_EQUALS;
import static org.assertj.core.test.AlwaysEqualComparator.ALWAY_EQUALS_TUPLE;

import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;

import org.assertj.core.groups.Tuple;
import org.assertj.core.internal.TypeComparators;
import org.assertj.core.util.AbsValueComparator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecursiveComparisonConfiguration_comparatorByType_Test {

  private RecursiveComparisonConfiguration recursiveComparisonConfiguration;

  @BeforeEach
  void setup() {
    recursiveComparisonConfiguration = new RecursiveComparisonConfiguration();
  }

  @Test
  void should_have_default_comparator_by_types() {
    // WHEN
    TypeComparators typeComparators = recursiveComparisonConfiguration.getTypeComparators();
    // THEN
    List<Entry<Class<?>, Comparator<?>>> defaultComparators = defaultTypeComparators().comparatorByTypes().collect(toList());
    assertThat(typeComparators.comparatorByTypes()).containsExactlyElementsOf(defaultComparators);
  }

  @Test
  void should_register_given_comparator_per_types() {
    // GIVEN
    AbsValueComparator<Integer> integerComparator = new AbsValueComparator<>();
    recursiveComparisonConfiguration.registerComparatorForType(integerComparator, Integer.class);
    recursiveComparisonConfiguration.registerComparatorForType(ALWAY_EQUALS_TUPLE, Tuple.class);
    recursiveComparisonConfiguration.registerComparatorForType(ALWAY_EQUALS, Double.class);
    // THEN
    assertThat(recursiveComparisonConfiguration.getComparatorForType(Integer.class)).isSameAs(integerComparator);
    assertThat(recursiveComparisonConfiguration.getComparatorForType(Tuple.class)).isSameAs(ALWAY_EQUALS_TUPLE);
    assertThat(recursiveComparisonConfiguration.getComparatorForType(Double.class)).isSameAs(ALWAY_EQUALS);
  }

}
