package com.dayang.miki.category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
    private Long categoryId;
    private String categoryName;

    @Setter @Getter
    public class CategoryParent {
        private Long categoryParentId;
        private Long categoryId;
        private String categoryName;
    }

}
