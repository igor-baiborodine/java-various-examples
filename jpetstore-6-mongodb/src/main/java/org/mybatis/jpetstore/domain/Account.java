/*
 *    Copyright 2010-2013 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.mybatis.jpetstore.domain;

import com.google.common.base.Objects;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import net.sourceforge.stripes.validation.Validate;
import org.mybatis.jpetstore.domain.builder.AccountBuilder;

import javax.annotation.Nonnull;
import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Igor Baiborodine
 */
public class Account
        extends AbstractBaseDomain<String>
        implements Serializable {

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String status;

    // address sub-document
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String country;
    private String phone;

    // profile sub-document, example:
    // {
    //    profile : {
    //                 fav_category : "CATS",
    //                 lang_preference : "english",
    //                 mylist_opt : 1,
    //                 banner_opt : 1
    //                 banner_name : '<image src="../images/banner_fish.gif">'
    //              }
    // }
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

    public static Account fromDBObject(@Nonnull final DBObject accountObj) {

        checkNotNull(accountObj, "Argument[accountObj] must not be null");

        AccountBuilder builder =
                new AccountBuilder((String) accountObj.get("username"),
                                   (String) accountObj.get("password"),
                                   (String) accountObj.get("firstname"),
                                   (String) accountObj.get("lastname"))
                .email((String) accountObj.get("email"))
                .status((String) accountObj.get("status"));

        // address sub-document
        DBObject addressObj = (DBObject) accountObj.get("address");
        builder.address1((String) addressObj.get("address1"))
                .address2((String) addressObj.get("address2"))
                .city((String) addressObj.get("city"))
                .state((String) addressObj.get("state"))
                .zip((String) addressObj.get("zip"))
                .country((String) addressObj.get("country"))
                .phone((String) addressObj.get("phone"));

        // profile sub-document
        DBObject profileObj = (DBObject) accountObj.get("profile");
        builder.languagePreference((String) profileObj.get("lang_pref"))
                .favouriteCategoryId((String) profileObj.get("fav_category"))
                .listOption(new Boolean((String) profileObj.get("list_opt")))
                .bannerOption(new Boolean((String) profileObj.get("banner_opt")))
                .bannerName((String) profileObj.get("banner_name"));

        return builder.build();
    }

    @Override
    public String getId() {

        return username;
    }

    @Override
    public DBObject toDBObject() {

        BasicDBObject accountObj = new BasicDBObject();
        appendTo(accountObj, "_id", getUsername());
        appendTo(accountObj, "username", getUsername());
        appendTo(accountObj, "password", getPassword());
        appendTo(accountObj, "firstname", getFirstName());
        appendTo(accountObj, "lastname", getLastName());
        appendTo(accountObj, "email", getEmail());
        appendTo(accountObj, "status", getStatus());

        // address sub-document
        BasicDBObject addressObj = new BasicDBObject();
        appendTo(addressObj, "address1", getAddress1());
        appendTo(addressObj, "address2", getAddress2());
        appendTo(addressObj, "city", getCity());
        appendTo(addressObj, "state", getState());
        appendTo(addressObj, "zip", getZip());
        appendTo(addressObj, "country", getCountry());
        appendTo(addressObj, "phone", getPhone());

        appendTo(accountObj, "address", addressObj);

        // profile sub-document
        BasicDBObject profileObj = new BasicDBObject();
        appendTo(profileObj, "fav_category", getFavouriteCategoryId());
        appendTo(profileObj, "lang_pref", getLanguagePreference() );
        appendTo(profileObj, "list_opt", String.valueOf(isListOption()));
        appendTo(profileObj, "banner_opt", String.valueOf(isBannerOption()));
        appendTo(profileObj, "banner_name", getBannerName());

        appendTo(accountObj, "profile", profileObj);

        return accountObj;
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

    public void setListOption(boolean listOption) {
        this.listOption = listOption;
    }

    public boolean isBannerOption() {
        return bannerOption;
    }

    public void setBannerOption(boolean bannerOption) {
        this.bannerOption = bannerOption;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(String bannerName) {
        this.bannerName = bannerName;
    }

}
