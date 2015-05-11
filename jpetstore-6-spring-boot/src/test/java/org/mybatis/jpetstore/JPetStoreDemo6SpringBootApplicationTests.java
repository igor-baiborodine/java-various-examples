package org.mybatis.jpetstore;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.jpetstore.persistence.ProductMapper;
import org.mybatis.jpetstore.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.springframework.test.util.ReflectionTestUtils.getField;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = JPetStoreDemo6SpringBootApplication.class)
@WebAppConfiguration
public class JPetStoreDemo6SpringBootApplicationTests {

	@Autowired
	private ProductMapper productMapper;
  @Autowired
  private CatalogService catalogService;

	@Test
	public void testLoadContext() {
		assertThat(productMapper, notNullValue());
    assertThat(catalogService, notNullValue());
    assertThat(getField(catalogService, "productMapper"), notNullValue());
  }

}
