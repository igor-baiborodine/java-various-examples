package org.mybatis.jpetstore.domain.builder;

import org.mybatis.jpetstore.domain.Supplier;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Igor Baiborodine
 */
public class SupplierBuilder
        implements Builder<Supplier> {

    // required
    private String supplierId;
    private String name;
    private String status;

    // optional
    private String addr1;
    private String addr2;
    private String city;
    private String state;
    private String zip;
    private String phone;

    public SupplierBuilder(@Nonnull String supplierId, @Nonnull String name, @Nonnull String status) {
        // non-null validation is done in Supplier(supplierId, name, status) constructor
        this.supplierId = supplierId;
        this.name = name;
        this.status = status;
    }

    @Nonnull public SupplierBuilder addr1(@Nullable final String addr1) {
        this.addr1 = addr1;
        return this;
    }

    @Nonnull public SupplierBuilder addr2(@Nullable final String addr2) {
        this.addr2 = addr2;
        return this;
    }

    @Nonnull public SupplierBuilder city(@Nullable final String city) {
        this.city = city;
        return this;
    }

    @Nonnull public SupplierBuilder state(@Nullable final String state) {
        this.state = state;
        return this;
    }

    @Nonnull public SupplierBuilder zip(@Nullable final String zip) {
        this.zip = zip;
        return this;
    }

    @Nonnull public SupplierBuilder phone(@Nullable final String phone) {
        this.phone = phone;
        return this;
    }

    @Override
    @Nonnull public Supplier build() {
        Supplier supplier = new Supplier(supplierId, name, status);

        supplier.setAddr1(addr1);
        supplier.setAddr2(addr2);
        supplier.setCity(city);
        supplier.setZip(zip);
        supplier.setState(state);
        supplier.setPhone(phone);

        return supplier;
    }
}
