package org.mybatis.jpetstore.domain;

import com.google.common.base.Objects;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Igor Baiborodine
 */
@Entity(name="Supplier")
public class Supplier implements Serializable {

    private String supplierId;
    private String name;
    private String status;
    private String addr1;
    private String addr2;
    private String city;
    private String state;
    private String zip;
    private String phone;

    protected Supplier() {}

    public Supplier(final String supplierId, final String name, final String status) {

        checkNotNull(supplierId, "Argument[supplierId] must not be null");
        checkNotNull(name, "Argument[name] must not be null");
        checkNotNull(status, "Argument[status] must not be null");
        setSupplierId(supplierId);
        setName(name);
        setStatus(status);
    }

    @Id
    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(final String supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(final String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(final String addr2) {
        this.addr2 = addr2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(final String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(final String zip) {
        this.zip = zip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(final String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("supplierId", supplierId)
                .add("name", name)
                .add("status", status)
                .add("addr1", addr1)
                .add("addr2", addr2)
                .add("city", city)
                .add("state", state)
                .add("zip", zip)
                .add("phone", phone)
                .toString();
    }
}
