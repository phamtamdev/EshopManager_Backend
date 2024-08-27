package com.tambao.EshopManeger_Backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
public class FilterDto {
    private List<VariantFilter> variantFilters;
    private List<BrandFilter> brandFilters;
    private PriceRange priceRange;
    private Integer categoryId;
    private Integer brandId;
    private String sortType;

    @Getter
    @AllArgsConstructor
    public static class VariantFilter{
        private int variantId;
        private String variantName;
        private List<String> values;
    }

    @Getter
    @AllArgsConstructor
    public static class BrandFilter{
        private int brandId;
        private String brandName;
    }

    @Getter
    @AllArgsConstructor
    public static class PriceRange{
        private int min;
        private int max;
    }
}
