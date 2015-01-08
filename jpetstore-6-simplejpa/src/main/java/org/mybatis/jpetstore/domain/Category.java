package org.mybatis.jpetstore.domain;

import com.google.common.base.Objects;
import org.mybatis.jpetstore.domain.builder.CategoryBuilder;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

@Entity(name = "Category")
@NamedQuery(name = "Category.getCategoryList",
        query = "select c from Category c")
public class Category implements Serializable {

    private String categoryId;
    private String name;
    private String description;

    protected Category() {}

    public Category(String categoryId) {
        checkNotNull(categoryId, "Argument[categoryId] must not be null");
        this.categoryId = categoryId.trim();
    }

    public static Category copy(@Nonnull final Category category) {
        checkNotNull(category, "Argument[category] must not be null");
        return new CategoryBuilder(category.getCategoryId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    @Id
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(@Nonnull final String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (! (o instanceof Category)) {
            return false;
        }
        Category that = (Category) o;
        return Objects.equal(this.getCategoryId(), that.getCategoryId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getCategoryId());
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("categoryId", categoryId)
                .add("name", name)
                .add("description", description)
                .toString();
    }
}
