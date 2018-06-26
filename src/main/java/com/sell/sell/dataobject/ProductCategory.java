package com.sell.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
@DynamicUpdate // only update the updated field but not the whole object.
@Data // from lmbok which will add getter , setter and toString automatically when compile to class file.
public class ProductCategory {
    /** category id*/
    @Id
    @GeneratedValue
    private Integer categoryId;
    /** category name*/
    private String categoryName;
    /** category type*/
    private Integer categoryType;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
