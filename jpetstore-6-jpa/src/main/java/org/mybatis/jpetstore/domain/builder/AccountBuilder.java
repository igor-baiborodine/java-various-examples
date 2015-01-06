package org.mybatis.jpetstore.domain.builder;

import org.mybatis.jpetstore.domain.Account;
import org.mybatis.jpetstore.domain.BannerData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * @author Igor Baiborodine
 */
public class AccountBuilder
        implements Builder<Account> {

    private String email;
    private String firstName;
    private String lastName;
    private String status;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String phone;

    // signon
    private String username;
    private String password;

    // profile
    private String languagePreference;
    private boolean listOption;
    private boolean bannerOption;

    // bannerdata
    private BannerData bannerData;

    public AccountBuilder(final String username, final String password,
                          final String firstName, final String lastName) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    @Nonnull public Account build() {
        Account account = new Account(username, password, firstName, lastName);

        account.setEmail(email);
        account.setStatus(status);
        account.setAddress1(address1);
        account.setAddress2(address2);
        account.setCity(city);
        account.setState(state);
        account.setZip(zip);
        account.setCountry(country);
        account.setPhone(phone);
        // signon

        // profile
        account.setLanguagePreference(languagePreference);
        account.setListOption(listOption);
        account.setBannerOption(bannerOption);
        // bannerdata
        account.setBannerData(bannerData);

        return account;
    }

    @Nonnull public AccountBuilder email(@Nonnull final String email) {
        this.email = email;
        return this;
    }

    @Nonnull public AccountBuilder firstName(@Nonnull final String firstName) {
        this.firstName = firstName;
        return this;
    }

    @Nonnull public AccountBuilder lastName(@Nonnull final String lastName) {
        this.lastName = lastName;
        return this;
    }

    @Nonnull public AccountBuilder status(@Nonnull final String status) {
        this.status = status;
        return this;
    }

    @Nonnull public AccountBuilder address1(@Nonnull final String address1) {
        this.address1 = address1;
        return this;
    }

    @Nonnull public AccountBuilder address2(@Nonnull final String address2) {
        this.address2 = address2;
        return this;
    }

    @Nonnull public AccountBuilder city(@Nonnull final String city) {
        this.city = city;
        return this;
    }

    @Nonnull public AccountBuilder state(@Nonnull final String state) {
        this.state = state;
        return this;
    }

    @Nonnull public AccountBuilder zip(@Nonnull final String zip) {
        this.zip = zip;
        return this;
    }

    @Nonnull public AccountBuilder country(@Nonnull final String country) {
        this.country = country;
        return this;
    }

    @Nonnull public AccountBuilder phone(@Nonnull final String phone) {
        this.phone = phone;
        return this;
    }

    @Nonnull public AccountBuilder password(@Nonnull final String password) {
        this.password = password;
        return this;
    }

    @Nonnull public AccountBuilder languagePreference(@Nonnull final String languagePreference) {
        this.languagePreference = languagePreference;
        return this;
    }

    @Nonnull public AccountBuilder listOption(@Nonnull final boolean listOption) {
        this.listOption = listOption;
        return this;
    }

    @Nonnull public AccountBuilder bannerOption(@Nonnull final boolean bannerOption) {
        this.bannerOption = bannerOption;
        return this;
    }

    @Nonnull public AccountBuilder bannerData(@Nullable final BannerData bannerData) {
        if (bannerData != null) {
            this.bannerData = new BannerData(
                    bannerData.getFavouriteCategoryId(), bannerData.getBannerName());
        }
        return this;
    }
}
