package org.mybatis.jpetstore.domain;

import com.google.common.base.Objects;
import net.sourceforge.stripes.validation.Validate;

import javax.persistence.*;
import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Igor Baiborodine
 */
@Entity(name = "Account")
// Named queries not supported by SimpleJPA
@NamedQueries({
        @NamedQuery(name = "Account.getAccountByUsername",
                query = "select a from Account a where a.username = :username"),
        @NamedQuery(name = "Account.getAccountByUsernameAndPassword",
                query = "select a from Account a where a.username = :username and a.password = :password")
})
public class Account implements Serializable {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String status;

    // address
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String phone;

    // profile
    private String favouriteCategoryId;
    private String languagePreference;
    private boolean listOption;
    private boolean bannerOption;
    private String bannerName;

    public Account() {}

    public Account(String username, String password, String firstName, String lastName) {

        setUsername(username);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
    }

    @Override
    public boolean equals(final Object o) {

        if (o == this) {
            return true;
        }
        if (! (o instanceof Account)) {
            return false;
        }
        Account that = (Account) o;
        return Objects.equal(this.username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(username);
    }

    @Override
    public String toString() {

        return Objects.toStringHelper(this)
                .add("username", username)
                .add("email", email)
                .add("firstName", firstName)
                .add("lastName", lastName)
                .add("status", status)
                .add("address1", address1)
                .add("address2", address2)
                .add("city", city)
                .add("state", state)
                .add("zip", zip)
                .add("country", country)
                .add("phone", phone)
                .add("languagePreference", languagePreference)
                .add("listOption", listOption)
                .add("bannerOption", bannerOption)
                .add("favouriteCategoryId", getFavouriteCategoryId())
                .add("bannerName", getBannerName())
                .toString();
    }

    @Id
    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {

        checkNotNull(username, "Argument[username] must not be null");
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    @Validate(required=true, on={"newAccount", "editAccount"})
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Validate(required=true, on={"newAccount", "editAccount"})
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFavouriteCategoryId() {
        return favouriteCategoryId;
    }

    public void setFavouriteCategoryId(String favouriteCategoryId) {
        this.favouriteCategoryId = favouriteCategoryId;
    }

    public String getLanguagePreference() {
        return languagePreference;
    }

    public void setLanguagePreference(String languagePreference) {
        this.languagePreference = languagePreference;
    }


    public boolean isListOption() {
        return listOption;
    }

    public void setListOption(final boolean listOption) {
        this.listOption = listOption;
    }

    public String getListOptionString() {
        return String.valueOf(listOption);
    }

    public void setListOptionString(String listOptionString) {
        this.listOption = new Boolean(listOptionString);
    }

    public boolean isBannerOption() {
        return bannerOption;
    }

    public void setBannerOption(final boolean bannerOption) {
        this.bannerOption = bannerOption;
    }

    public String getBannerOptionString() {
        return String.valueOf(bannerOption);
    }

    public void setBannerOptionString(final String bannerOptionString) {
        this.bannerOption = new Boolean(bannerOptionString);
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }
}