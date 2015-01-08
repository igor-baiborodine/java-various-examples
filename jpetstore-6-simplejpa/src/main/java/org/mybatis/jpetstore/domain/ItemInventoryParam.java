package org.mybatis.jpetstore.domain;

/**
 * @author Igor Baiborodine
 */
public enum ItemInventoryParam {
    ITEM_ID("itemId"), INCREMENT("increment");

    private ItemInventoryParam(String name) {
        this.name = name;
    }

    private String name;
    @Override
    public String toString() {
        return name;
    }
}
