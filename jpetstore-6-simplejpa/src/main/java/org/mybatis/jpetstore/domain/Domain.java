package org.mybatis.jpetstore.domain;

/**
 * @author Igor Baiborodine
 */
public enum Domain {
    ACCOUNT("Account"),
    LINEITEM("LineItem"),
    ITEM("Item"),
    PRODUCT("Product"),
    CATEGORY("Category"),
    SUPPLIER("Supplier"),
    ORDER("Order"),
    SEQUENCE("Sequence");

    private String name;

    Domain(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
