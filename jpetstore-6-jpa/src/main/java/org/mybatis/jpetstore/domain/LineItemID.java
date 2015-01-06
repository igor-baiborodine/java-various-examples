package org.mybatis.jpetstore.domain;

import com.google.common.base.Objects;

import javax.annotation.Nonnull;
import java.io.Serializable;

/**
 * @author Igor Baiborodine
 */
public class LineItemID
        implements Serializable {
    @Nonnull
    private Integer orderId;
    @Nonnull
    private Integer lineNumber;

    protected LineItemID() {}

    public LineItemID(@Nonnull final Integer orderId, @Nonnull final Integer lineNumber) {
        this.orderId = orderId;
        this.lineNumber = lineNumber;
    }

    @Nonnull
    public Integer getOrderId() {
        return orderId;
    }

    @Nonnull
    public Integer getLineNumber() {
        return lineNumber;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof LineItemID)) return false;

        final LineItemID that = (LineItemID) o;
        return Objects.equal(this.orderId, that.orderId)
                && Objects.equal(this.orderId, that.orderId);
    }

    @Override
    public int hashCode() {
        int result = orderId != null ? Objects.hashCode(orderId) : 0;
        result = result + (lineNumber != null ? Objects.hashCode(lineNumber) : 0);
        return result;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("orderId", orderId)
                .add("lineNumber", lineNumber)
                .toString();
    }
}
