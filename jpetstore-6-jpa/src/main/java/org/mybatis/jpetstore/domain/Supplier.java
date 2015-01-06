package org.mybatis.jpetstore.domain;

import com.google.common.base.Objects;
import org.mybatis.jpetstore.domain.builder.SupplierBuilder;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Field;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.springframework.util.ReflectionUtils.findField;
import static org.springframework.util.ReflectionUtils.makeAccessible;
import static org.springframework.util.ReflectionUtils.setField;

/**
 * @author Igor Baiborodine
 */
@Entity(name="supplier")
@TableGenerator(name = "supplier_seq_gen",
        table = "sequence",
        pkColumnName = "name",
        valueColumnName = "nextid",
        pkColumnValue = "supplier_seq"
)
public class Supplier
        extends BaseDomain
        implements BaseEntity, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "supplier_seq_gen")
    private Long suppid;

    @Column(nullable = false, length = 80)
    private String name;

    @Column(nullable = false, length = 2)
    private String status;

    @Column(nullable = true, length = 80)
    private String addr1;

    @Column(nullable = true, length = 80)
    private String addr2;

    @Column(nullable = true, length = 80)
    private String city;

    @Column(nullable = true, length = 80)
    private String state;

    @Column(nullable = true, length = 5)
    private String zip;

    @Column(nullable = true, length = 80)
    private String phone;

    protected Supplier() {}

    public Supplier(@Nonnull String name, @Nonnull String status) {
        checkNotNull(name, "Argument[name] must not be null");
        checkNotNull(status, "Argument[status] must not be null");
        setName(name);
        setStatus(status);
    }

    @Nonnull public static Supplier copy(@Nonnull final Supplier supplier) {
        checkNotNull(supplier, "Argument[supplier] must not be null");
        Supplier copy = new SupplierBuilder(supplier.getName(), supplier.getStatus())
                .addr1(supplier.getAddr1())
                .addr2(supplier.getAddr2())
                .city(supplier.getCity())
                .state(supplier.getState())
                .zip(supplier.getZip())
                .phone(supplier.getPhone())
                .build();
        if (supplier.getVersion() != null) {
            Field versionField = findField(Supplier.class, "version");
            makeAccessible(versionField);
            setField(versionField, copy, supplier.getId());
        }
        if (supplier.getId() != null) {
            Field idField = findField(Supplier.class, "suppid");
            makeAccessible(idField);
            setField(idField, copy, supplier.getId());
        }
        return copy;
    }

    @Override
    public Long getId() {
        return suppid;
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
                .add("suppid", suppid)
                .add("version", version)
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
