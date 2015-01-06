package org.mybatis.jpetstore.domain;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.persistence.*;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Igor Baiborodine
 */
@Entity(name = "bannerdata")
public class BannerData implements Serializable {

    @Id
    @Nonnull
    @Column(name = "favcategory", unique = true, nullable = false, length = 30)
    private String favouriteCategoryId;

    @Column(name = "bannername", nullable = true, length = 255)
    private String bannerName;

    public BannerData() {}

    public BannerData(@Nonnull String favouriteCategoryId, @Nullable String bannerName) {
        checkNotNull(favouriteCategoryId, "Argument[favoriteCategoryId] must not be null");

        this.favouriteCategoryId = favouriteCategoryId;
        this.bannerName = bannerName;
    }

    public String getFavouriteCategoryId() {
        return favouriteCategoryId;
    }

    public void setFavouriteCategoryId(@Nullable final String favouriteCategoryId) {
        this.favouriteCategoryId = favouriteCategoryId;
    }

    public String getBannerName() {
        return bannerName;
    }

    public void setBannerName(@Nullable final String bannerName) {
        this.bannerName = bannerName;
    }
}
