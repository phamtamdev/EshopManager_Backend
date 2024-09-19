package com.tambao.EshopManeger_Backend.service.Impl;

import com.tambao.EshopManeger_Backend.Utils.SlugUtils;
import com.tambao.EshopManeger_Backend.dto.FilterDto;
import com.tambao.EshopManeger_Backend.dto.ProductDto;
import com.tambao.EshopManeger_Backend.entity.Brand;
import com.tambao.EshopManeger_Backend.entity.Category;
import com.tambao.EshopManeger_Backend.entity.Product;
import com.tambao.EshopManeger_Backend.entity.ProductVariant;
import com.tambao.EshopManeger_Backend.exception.ResourceNotFoundException;
import com.tambao.EshopManeger_Backend.mapper.ProductMapper;
import com.tambao.EshopManeger_Backend.repository.BrandRepository;
import com.tambao.EshopManeger_Backend.repository.CategoryRepository;
import com.tambao.EshopManeger_Backend.repository.ProductRepository;
import com.tambao.EshopManeger_Backend.service.IProductService;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(ProductMapper::mapToProductDTO).collect(Collectors.toList());
    }

    @Override
    public Page<ProductDto> getProductsWithPage(int page, int size) {
        Page<Product> products = productRepository.findAll(PageRequest.of(page, size));
        return products.map(ProductMapper::mapToProductDTO);
    }

    @Override
    public Page<ProductDto> getProductsWithPageAndSearch(String keyword, int page, int size) {
        Page<Product> products = productRepository.findByNameContainingIgnoreCase(keyword, PageRequest.of(page, size));
        return products.map(ProductMapper::mapToProductDTO);
    }

    @Override
    public Page<ProductDto> getProductsWithPageAndSorting(String field, int page, int size, String sortOrder) {
        Pageable pageable = createPageable(page, size, field, sortOrder);
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(ProductMapper::mapToProductDTO);
    }

    @Override
    public Page<ProductDto> getProductsWithPageAndSortingAndSearch(String field, String keyword, int page, int size, String sortOrder) {
        Pageable pageable = createPageable(page, size, field, sortOrder);
        Page<Product> products = productRepository.findByNameContainingIgnoreCase(keyword, pageable);
        return products.map(ProductMapper::mapToProductDTO);
    }

    @Override
    public Page<ProductDto> getProductsByCategoryIdWithSearchAndSort(Integer categoryId, int page, int size, String sortField, String sortOrder, String keyword) {
        Pageable pageable = createPageable(page, size, sortField, sortOrder);
        Page<Product> products = productRepository.findByCategoryIdAndNameContaining(categoryId, keyword, pageable);
        return products.map(ProductMapper::mapToProductDTO);
    }

    @Override
    public Page<ProductDto> getProductsByCategoryIdAndBrandIdWithSearchAndSort(Integer categoryId, Integer brandId, int page, int size, String sortField, String sortOrder, String keyword) {
        Pageable pageable = createPageable(page, size, sortField, sortOrder);
        Page<Product> products = productRepository.findByCategoryIdAndBrandIdAndNameContaining(categoryId, brandId, keyword, pageable);
        return products.map(ProductMapper::mapToProductDTO);
    }

    @Override
    public Page<ProductDto> getProductsByFilters(FilterDto filters, int page, int size) {
        Pageable pageable;
        if ("asc".equalsIgnoreCase(filters.getSortType())) {
            pageable = PageRequest.of(page, size, Sort.by("discountedPrice").ascending());
        } else if ("desc".equalsIgnoreCase(filters.getSortType())) {
            pageable = PageRequest.of(page, size, Sort.by("discountedPrice").descending());
        } else {
            pageable = PageRequest.of(page, size);
        }

        Page<Product> products = productRepository.findAll(createSpecification(filters), pageable);
        return products.map(ProductMapper::mapToProductDTO);
    }

    @Override
    public ProductDto getProductById(int id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id= " + id));
        return ProductMapper.mapToProductDTO(product);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found with id= " + productDto.getCategoryId()));
        Product product = ProductMapper.mapToProduct(productDto);
        product.setCategory(category);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.mapToProductDTO(savedProduct);
    }

    @Override
    public ProductDto updateProduct(Integer id, ProductDto productDto) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id= " + id));
        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found with id= " + productDto.getCategoryId()));
        Brand brand = brandRepository.findById(productDto.getBrandId()).orElseThrow(() -> new ResourceNotFoundException("Brand not found with id= " + productDto.getBrandId()));
        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setSlug(SlugUtils.toSlug(productDto.getName()));
        product.setCategory(category);
        product.setBrand(brand);
        Product updatedProduct = productRepository.save(product);
        return ProductMapper.mapToProductDTO(updatedProduct);
    }

    @Override
    public ProductDto getBySlug(String slug) {
        Product product = productRepository.findBySlug(slug);
        if(product == null) {
            throw new ResourceNotFoundException("Product not found with name= " + slug);
        }
        return ProductMapper.mapToProductDTO(product);
    }

    @Override
    public List<Product> getProductsByIds(List<Integer> productIds) {
        return productRepository.findAllByIdIn(productIds);
    }

    @Override
    public void deleteProduct(int id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id= " + id));
        productRepository.delete(product);
    }

    private Pageable createPageable(int page, int size, String field, String sortOrder) {
        Sort.Direction direction = Sort.Direction.ASC;
        if ("desc".equalsIgnoreCase(sortOrder)) {
            direction = Sort.Direction.DESC;
        }
        return PageRequest.of(page, size, Sort.by(direction, field));
    }

    private Specification<Product> createSpecification(FilterDto filterDto) {
        return new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (filterDto.getCategoryId() != null) {
                    Path<Long> categoryIdPath = root.get("category").get("id");
                    predicates.add(criteriaBuilder.equal(categoryIdPath, filterDto.getCategoryId()));
                }

                if (filterDto.getBrandId() != null) {
                    Path<Long> brandIdPath = root.get("brand").get("id");
                    predicates.add(criteriaBuilder.equal(brandIdPath, filterDto.getBrandId()));

                    if (filterDto.getBrandFilters() != null && !filterDto.getBrandFilters().isEmpty()) {
                        List<Integer> brandIds = filterDto.getBrandFilters().stream().map(FilterDto.BrandFilter::getBrandId).collect(Collectors.toList());
                        predicates.add(brandIdPath.in(brandIds));
                    }
                } else {

                    if (filterDto.getBrandFilters() != null && !filterDto.getBrandFilters().isEmpty()) {
                        Path<Long> brandIdPath = root.get("brand").get("id");
                        List<Integer> brandIds = filterDto.getBrandFilters().stream().map(FilterDto.BrandFilter::getBrandId).collect(Collectors.toList());
                        predicates.add(brandIdPath.in(brandIds));
                    }
                }

                if (filterDto.getPriceRange() != null) {
                    FilterDto.PriceRange priceRange = filterDto.getPriceRange();
                    predicates.add(criteriaBuilder.between(root.get("discountedPrice"), priceRange.getMin(), priceRange.getMax()));
                }

                if (filterDto.getVariantFilters() != null && !filterDto.getVariantFilters().isEmpty()) {
                    Join<Product, ProductVariant> productVariantJoin = root.join("productVariants", JoinType.INNER);
                    for (FilterDto.VariantFilter variantFilter : filterDto.getVariantFilters()) {
                        Predicate variantIdPredicate = criteriaBuilder.equal(productVariantJoin.get("variant").get("id"), variantFilter.getVariantId());
                        Predicate variantValuePredicate = productVariantJoin.get("variantValue").in(variantFilter.getValues());
                        predicates.add(criteriaBuilder.and(variantIdPredicate, variantValuePredicate));
                    }
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
