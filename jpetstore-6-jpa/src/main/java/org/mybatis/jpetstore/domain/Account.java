package org.mybatis.jpetstore.domain;

import com.google.common.base.Objects;
import net.sourceforge.stripes.validation.Validate;
import org.mybatis.jpetstore.domain.builder.AccountBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.*;
import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

@Entity(name="account")
@SecondaryTables({
    @SecondaryTable(name = "signon",
            pkJoinColumns = @PrimaryKeyJoinColumn(name = "username", referencedColumnName = "userid")),
    @SecondaryTable(name = "profile",
            pkJoinColumns = @PrimaryKeyJoinColumn(name = "userid", referencedColumnName = "userid"))
})
@NamedQueries({
    @NamedQuery(name = "Account.getAccountByUsername",
            query = "select a from account a where a.userId = :username"),
    @NamedQuery(name = "Account.getAccountByUsernameAndPassword",
            query = "select a from account a where a.userId = :username and a.password = :password")
})
public class Account
        extends BaseDomain
        implements Serializable {

    @Id @Column(name = "userid", unique = true, nullable = false, length = 80, insertable = true)
    private String userId;

    @Column(nullable = false, length = 80)
    private String email;

    @Column(name = "firstname", nullable = false, length = 80)
    private String firstName;

    @Column(name = "lastname", nullable = false, length = 80)
    private String lastName;

    @Column(length = 2)
    private String status;

    @Column(name = "addr1", nullable = false, length = 80)
    private String address1;

    @Column(name = "addr2", length = 40)
    private String address2;

    @Column(nullable = false, length = 80)
    private String city;

    @Column(nullable = false, length = 80)
    private String state;

    @Column(nullable = false, length = 20)
    private String zip;

    @Column(nullable = false, length = 20)
    private String country;

    @Column(nullable = false, length = 80)
    private String phone;

    // signon table
    @Column(table = "signon", nullable = false, length = 25)
    private String password;

    // profile table
    @Column(table = "profile", name = "langpref", nullable = false, length = 80)
    private String languagePreference;

    @Column(table = "profile", name = "favcategory", nullable = true, length = 30)
    private String favouriteCategoryId;

    @Column(table = "profile", name = "mylistopt", nullable = false)
    private boolean listOption;

    @Column(table = "profile", name = "banneropt", nullable = false)
    private boolean bannerOption;

    // bannerdata table
    @Nullable
    @ManyToOne(optional = true, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "favcategory", referencedColumnName = "favcategory")
    private BannerData bannerData;

    public Account() {}

    public Account(String username, String password, String firstName, String lastName) {
        checkNotNull(username, "Argument[username] must not be null");
//        this.username = username;
        this.userId = username;
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
    }

    @Nonnull
    public static Account copy(@Nonnull final Account account) {
        checkNotNull(account, "Argument[account] must not be null");
        return new AccountBuilder(account.getUsername(), account.getPassword(),
                                  account.firstName, account.getLastName())
                .email(account.getEmail())
                .status(account.getStatus())
                .address1(account.getAddress1())
                .address2(account.getAddress2())
                .city(account.getCity())
                .state(account.getState())
                .zip(account.getZip())
                .country(account.getCountry())
                .phone(account.getPhone())
                .languagePreference(account.getLanguagePreference())
                .listOption(account.isListOption())
                .bannerOption(account.isBannerOption())
                .bannerData(account.getBannerData())
                .build();
    }

    public String getUserId() {
        return userId;
    }
    // no setter for userId

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    @Validate(required = true, on = {"newAccount", "editAccount"})
    public void setFirstName(String firstName) {
        checkNotNull(firstName, "Argument[firstName] must not be null");
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Validate(required = true, on = {"newAccount", "editAccount"})
    public void setLastName(String lastName) {
        checkNotNull(lastName, "Argument[lastName] must not be null");
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

    public String getUsername() {
        return userId;
    }

    public void setUsername(String username) {
        this.userId = username;
    }

    @Nonnull
    public String getPassword() {
        return password;
    }

    public void setPassword(@Nonnull final String password) {
        checkNotNull(password, "Argument[password] must not be null");
        this.password = password;
    }

    public String getLanguagePreference() {
        return languagePreference;
    }

    public void setLanguagePreference(final String languagePreference) {
        this.languagePreference = languagePreference;
    }

    @Nullable
    public String getFavouriteCategoryId() {
        return (bannerData != null) ? bannerData.getFavouriteCategoryId() : null;
    }

    public boolean isListOption() {
        return listOption;
    }

    public void setListOption(final boolean listOption) {
        this.listOption = listOption;
    }

    public boolean isBannerOption() {
        return bannerOption;
    }

    public void setBannerOption(final boolean bannerOption) {
        this.bannerOption = bannerOption;
    }

    @Nullable
    public BannerData getBannerData() {
        return (bannerData != null) ? new BannerData(
                bannerData.getFavouriteCategoryId(), bannerData.getBannerName()) : null;
    }

    public void setBannerData(@Nullable final BannerData bannerData) {
        if (bannerData != null) {
            this.bannerData = new BannerData(
                    bannerData.getFavouriteCategoryId(), bannerData.getBannerName());
            this.favouriteCategoryId = this.bannerData.getFavouriteCategoryId();
        }
    }

    @Nullable
    public String getBannerName() {
        return (bannerData != null) ? bannerData.getBannerName() : null;
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
        return Objects.equal(this.userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(userId);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("username", userId)
                .add("version", version)
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
}
