package org.mybatis.jpetstore.domain.builder;

import org.mybatis.jpetstore.domain.Category;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Igor Baiborodine
 */
public class CategoryBuilder
        implements Builder<Category> {

    @Nonnull
    private String categoryId;
    @Nullable
    private String name;
    @Nullable
    private String description;

    public CategoryBuilder(@Nonnull String categoryId) {

        checkNotNull(categoryId, "Argument[categoryId] must not be null");
        this.categoryId = categoryId;
    }

    public CategoryBuilder name(@Nullable final String name) {

        this.name = name;
        return this;
    }

    public CategoryBuilder description(@Nullable final String description) {

        this.description = description;
        return this;
    }

    @Nonnull
    public Category build() {

        Category category = new Category(categoryId);
        category.setName(name);
        category.setDescription(description);
        return category;
    }
}
