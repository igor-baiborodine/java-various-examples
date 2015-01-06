package org.mybatis.jpetstore.persistence.helper;

import org.mybatis.jpetstore.domain.*;
import org.mybatis.jpetstore.domain.builder.*;

import javax.annotation.Nonnull;
import java.math.BigDecimal;

/**
 * @author Igor Baiborodine
 */
public class TestBuilderFactory {

    public static Builder<Account> createAccountBuilderWithAllFieldsWithBannerData() {
        return new AccountBuilder("brodriguez", "beer", "Bender", "Rodriguez")
                .email("bender.rodriguez@planet-express.earth")
                .status("OK")
                .address1("101 Robot Arms Ave.")
                .address2("#901")
                .city("New New York City")
                .state("NY")
                .zip("01234")
                .country("Earth")
                .phone("0123456789")
                .languagePreference("Martian")
                .listOption(true)
                .bannerOption(true)
                .bannerData(new BannerData("DOGS", "<image src=\"../images/banner_dogs.gif\">"));
    }

    public static Builder<Account> createAccountBuilderWithAllFieldsWithoutBannerData() {
        return new AccountBuilder("pfry", "slurm", "Philip", "Fry")
                .email("philip.j.fry@planet-express.earth")
                .status("OK")
                .address1("555 Planet Express St.")
                .address2("#1")
                .city("New New York City")
                .state("NY")
                .zip("01234")
                .country("Earth")
                .phone("9876543210")
                .languagePreference("Earthian")
                .listOption(true)
                .bannerOption(false);
    }

    public static Builder<Category> createCategoryBuilderWithAllFields() {
        return new CategoryBuilder("FISH")
                .name("Fish")
                .description("<image src='../images/fish_icon.gif'><font size='5' color='blue'>Fish</font>");
    }

    public static Builder<Product> createProductBuilderWithAllFields() {
        return new ProductBuilder("K9-BD-01", createCategoryBuilderWithAllFields().build())
                .name("Bulldog")
                .description("<image src='../images/dog2.gif'>Friendly dog from England");
    }

    public static ProductBuilder createProductBuilderWithAllFields(@Nonnull final Category category) {
        return new ProductBuilder("FI-SW-01", category)
                .name("Angelfish")
                .description("<image src='../images/fish1.gif'>Salt Water fish from Australia");
    }

    public static SupplierBuilder createSupplierBuilderWithAllFields() {
        return new SupplierBuilder("XYZ Pets", "AC")
                .addr1("600 Avon Way")
                .addr2("suite 1001")
                .city("Los Angeles")
                .state("CA")
                .zip("94024")
                .phone("212-947-0797");
    }

    public static ItemBuilder createItemBuilderWithAllFields() {
        return createItemBuilderWithAllFields(createProductBuilderWithAllFields().build());
    }

    public static ItemBuilder createItemBuilderWithAllFields(@Nonnull final Product product) {
        return new ItemBuilder("EST-1", product)
                .supplier(createSupplierBuilderWithAllFields().build())
                .status("P")
                .listPrice(new BigDecimal("16.50"))
                .unitCost(new BigDecimal("10.00"))
                .quantity(100)
                .attribute1("attribute1 value")
                .attribute2("attribute2 value")
                .attribute3("attribute3 value")
                .attribute4("attribute4 value")
                .attribute5("attribute5 value");
    }

    public static LineItemBuilder createLineItemBuilderWithAllFields() {
        return createLineItemBuilderWithAllFields(createItemBuilderWithAllFields().build());
    }

    public static LineItemBuilder createLineItemBuilderWithAllFields(@Nonnull final Item item) {
        CartItem cartItem = new CartItem();
        cartItem.setItem(item);
        cartItem.setQuantity(10);
        return new LineItemBuilder(new Integer("1"), cartItem).orderId(1);
    }

    public static Order createOrderWithAllFields() {
        return createOrderWithAllFields(
                createAccountBuilderWithAllFieldsWithBannerData().build(),
                createItemBuilderWithAllFields().build());
    }

    public static Order createOrderWithAllFields(@Nonnull Account account, @Nonnull Item item) {
        Cart cart = new Cart();
        boolean isInStock = true;
        cart.addItem(item, isInStock);

        Order order = new Order();
        order.initOrder(account, cart);
        return order;
    }
}
