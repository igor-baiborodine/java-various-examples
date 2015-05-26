package org.mybatis.jpetstore.domain;

import org.junit.Test;
import org.mybatis.jpetstore.persistence.helper.TestBuilderFactory;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Igor Baiborodine
 */
public class ProductTest {

  @Test
  public void getNameKeywords_shouldTokenizeProductName() {

    Product product = TestBuilderFactory.createProductBuilderWithAllFields().build();

    product.setName("One");
    List<String> nameKeywords = product.getNameKeywords();
    assertThat(nameKeywords.size(), equalTo(1));
    assertThat(nameKeywords.get(0), equalTo(product.getName().toLowerCase()));

    product.setName("One Two");
    nameKeywords = product.getNameKeywords();
    assertThat(nameKeywords.size(), equalTo(2));
    assertTrue(String.format("Product name[%s] does not contain following keyword[%s]",
        product.getName(), nameKeywords.get(0)), product.getName().toLowerCase().contains(nameKeywords.get(0)));
    assertTrue(String.format("Product name[%s] does not contain following keyword[%s]",
        product.getName(), nameKeywords.get(1)), product.getName().toLowerCase().contains(nameKeywords.get(1)));

  }
}
