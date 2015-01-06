package org.mybatis.jpetstore.domain;

import com.google.common.base.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Entity(name = "orders")
@TableGenerator(name = "order_seq_gen",
        table = "sequence",
        pkColumnName = "name",
        valueColumnName = "nextid",
        pkColumnValue = "order_seq"
)
@SecondaryTable(name = "orderstatus",
        pkJoinColumns = @PrimaryKeyJoinColumn(name = "orderid", referencedColumnName = "orderid")
)
@NamedQuery(name = "Order.getOrdersByUsername",
        query = "select o from orders o where o.username = :username")
public class Order
        extends BaseDomain
        implements BaseEntity, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "order_seq_gen")
    private Integer orderId;

    @Nonnull
    @Column(name = "userid", nullable = false, length = 80)
    private String username;

    @Nonnull
    @Column(name = "orderdate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date orderDate;

    @Nonnull
    @Column(name = "shipaddr1", nullable = false, length = 80)
    private String shipAddress1;

    @Nullable
    @Column(name = "shipaddr2", nullable = true, length = 80)
    private String shipAddress2;

    @Nonnull
    @Column(name = "shipcity", nullable = false, length = 80)
    private String shipCity;

    @Nonnull
    @Column(name = "shipstate", nullable = false, length = 80)
    private String shipState;

    @Nonnull
    @Column(name = "shipzip", nullable = false, length = 20)
    private String shipZip;

    @Nonnull
    @Column(name = "shipcountry", nullable = false, length = 20)
    private String shipCountry;

    @Nonnull
    @Column(name = "billaddr1", nullable = false, length = 80)
    private String billAddress1;

    @Nullable
    @Column(name = "billaddr2", nullable = true, length = 80)
    private String billAddress2;

    @Nonnull
    @Column(name = "billcity", nullable = false, length = 80)
    private String billCity;

    @Nonnull
    @Column(name = "billstate", nullable = false, length = 80)
    private String billState;

    @Nonnull
    @Column(name = "billzip", nullable = false, length = 20)
    private String billZip;

    @Nonnull
    @Column(name = "billcountry", nullable = false, length = 20)
    private String billCountry;

    @Nonnull
    @Column(name = "courier", nullable = false, length = 80)
    private String courier;

    @Nonnull
    @Column(name = "totalprice", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPrice;

    @Nonnull
    @Column(name = "billtofirstname", nullable = false, length = 80)
    private String billToFirstName;

    @Nonnull
    @Column(name = "billtolastname", nullable = false, length = 80)
    private String billToLastName;

    @Nonnull
    @Column(name = "shiptofirstname", nullable = false, length = 80)
    private String shipToFirstName;

    @Nonnull
    @Column(name = "shiptolastname", nullable = false, length = 80)
    private String shipToLastName;

    @Nonnull
    @Column(name = "creditcard", nullable = false, length = 80)
    private String creditCard;

    @Nonnull
    @Column(name = "exprdate", nullable = false, length = 7)
    private String expiryDate;

    @Nonnull
    @Column(name = "cardtype", nullable = false, length = 80)
    private String cardType;

    @Nonnull
    @Column(name = "locale", nullable = false, length = 80)
    private String locale;

    @Column(table = "orderstatus", nullable = false, length = 25)
    private String status;

    private transient List<LineItem> lineItems = new ArrayList<LineItem>();

    public @Nonnull Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(final @Nonnull Integer orderId) {
        this.orderId = orderId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getShipAddress1() {
        return shipAddress1;
    }

    public void setShipAddress1(String shipAddress1) {
        this.shipAddress1 = shipAddress1;
    }

    public String getShipAddress2() {
        return shipAddress2;
    }

    public void setShipAddress2(String shipAddress2) {
        this.shipAddress2 = shipAddress2;
    }

    public String getShipCity() {
        return shipCity;
    }

    public void setShipCity(String shipCity) {
        this.shipCity = shipCity;
    }

    public String getShipState() {
        return shipState;
    }

    public void setShipState(String shipState) {
        this.shipState = shipState;
    }

    public String getShipZip() {
        return shipZip;
    }

    public void setShipZip(String shipZip) {
        this.shipZip = shipZip;
    }

    public String getShipCountry() {
        return shipCountry;
    }

    public void setShipCountry(String shipCountry) {
        this.shipCountry = shipCountry;
    }

    public String getBillAddress1() {
        return billAddress1;
    }

    public void setBillAddress1(String billAddress1) {
        this.billAddress1 = billAddress1;
    }

    public String getBillAddress2() {
        return billAddress2;
    }

    public void setBillAddress2(String billAddress2) {
        this.billAddress2 = billAddress2;
    }

    public String getBillCity() {
        return billCity;
    }

    public void setBillCity(String billCity) {
        this.billCity = billCity;
    }

    public String getBillState() {
        return billState;
    }

    public void setBillState(String billState) {
        this.billState = billState;
    }

    public String getBillZip() {
        return billZip;
    }

    public void setBillZip(String billZip) {
        this.billZip = billZip;
    }

    public String getBillCountry() {
        return billCountry;
    }

    public void setBillCountry(String billCountry) {
        this.billCountry = billCountry;
    }

    public String getCourier() {
        return courier;
    }

    public void setCourier(String courier) {
        this.courier = courier;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getBillToFirstName() {
        return billToFirstName;
    }

    public void setBillToFirstName(String billToFirstName) {
        this.billToFirstName = billToFirstName;
    }

    public String getBillToLastName() {
        return billToLastName;
    }

    public void setBillToLastName(String billToLastName) {
        this.billToLastName = billToLastName;
    }

    public String getShipToFirstName() {
        return shipToFirstName;
    }

    public void setShipToFirstName(String shipFoFirstName) {
        this.shipToFirstName = shipFoFirstName;
    }

    public String getShipToLastName() {
        return shipToLastName;
    }

    public void setShipToLastName(String shipToLastName) {
        this.shipToLastName = shipToLastName;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void initOrder(Account account, Cart cart) {

        username = account.getUsername();
        orderDate = new Date();

        shipToFirstName = account.getFirstName();
        shipToLastName = account.getLastName();
        shipAddress1 = account.getAddress1();
        shipAddress2 = account.getAddress2();
        shipCity = account.getCity();
        shipState = account.getState();
        shipZip = account.getZip();
        shipCountry = account.getCountry();

        billToFirstName = account.getFirstName();
        billToLastName = account.getLastName();
        billAddress1 = account.getAddress1();
        billAddress2 = account.getAddress2();
        billCity = account.getCity();
        billState = account.getState();
        billZip = account.getZip();
        billCountry = account.getCountry();

        totalPrice = cart.getSubTotal();

        creditCard = "999 9999 9999 9999";
        expiryDate = "12/03";
        cardType = "Visa";
        courier = "UPS";
        locale = "CA";
        status = "P";

        Iterator<CartItem> i = cart.getAllCartItems();
        while (i.hasNext()) {
            CartItem cartItem = (CartItem) i.next();
            addLineItem(cartItem);
        }

    }

    public void addLineItem(CartItem cartItem) {
        LineItem lineItem = new LineItem(lineItems.size() + 1, cartItem);
        addLineItem(lineItem);
    }

    public void addLineItem(LineItem lineItem) {
        lineItems.add(lineItem);
    }

    @Override
    public Long getId() {
        return new Long(orderId);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("orderId", orderId)
                .add("version", version)
                .add("username", username)
                .add("orderDate", orderDate)
                .add("shipAddress1", shipAddress1)
                .add("shipAddress2", shipAddress2)
                .add("shipCity", shipCity)
                .add("shipState", shipState)
                .add("shipZip", shipZip)
                .add("shipCountry", shipCountry)
                .add("billAddress1", billAddress1)
                .add("billAddress2", billAddress2)
                .add("billCity", billCity)
                .add("billState", billState)
                .add("billZip", billZip)
                .add("billCountry", billCountry)
                .add("courier", courier)
                .add("totalPrice", totalPrice)
                .add("billToFirstName", billToFirstName)
                .add("billToLastName", billToLastName)
                .add("shipToFirstName", shipToFirstName)
                .add("shipToLastName", shipToLastName)
                .add("creditCard", creditCard)
                .add("expiryDate", expiryDate)
                .add("cardType", cardType)
                .add("locale", locale)
                .add("status", status)
                .add("lineItems", lineItems)
                .toString();
    }
}
