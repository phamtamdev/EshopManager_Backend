package com.tambao.EshopManeger_Backend.service.Impl;

import com.tambao.EshopManeger_Backend.dto.VariantDto;
import com.tambao.EshopManeger_Backend.entity.Category;
import com.tambao.EshopManeger_Backend.entity.Variant;
import com.tambao.EshopManeger_Backend.exception.ResourceNotFoundException;
import com.tambao.EshopManeger_Backend.mapper.VariantMapper;
import com.tambao.EshopManeger_Backend.repository.CategoryRepository;
import com.tambao.EshopManeger_Backend.repository.VariantRepository;
import com.tambao.EshopManeger_Backend.service.IVariantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VariantService implements IVariantService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private VariantRepository variantRepository;

    @Override
    public List<VariantDto> getVariantsByCategoryId(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        List<Variant> variants = variantRepository.findByCategoryId(category.getId());
        return variants.stream()
                .map(VariantMapper::mapToVariantDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<VariantDto> getVariantsWithPageAndSearchAndSorting(int page, int size, String keyword, String field, String sortOrder) {
        Pageable pageable = createPageable(page, size, field, sortOrder);
        Page<Variant> variants = variantRepository.findByNameContainingIgnoreCase(keyword, pageable);
        return variants.map(VariantMapper::mapToVariantDto);
    }

    @Override
    public VariantDto getVariantById(int id) {
        Variant variant = variantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Variant not found"));
        return VariantMapper.mapToVariantDto(variant);
    }

    @Override
    public List<VariantDto> getAllVariants() {
        return List.of();
    }

    @Override
    public VariantDto saveVariant(VariantDto variantDto) {
        return VariantMapper.mapToVariantDto(variantRepository.save(VariantMapper.mapToVariant(variantDto)));
    }

    @Override
    public VariantDto updateVariant(Integer variantId, VariantDto variantDto) {
        Variant variant = variantRepository.findById(variantId).orElseThrow(() -> new ResourceNotFoundException("Variant not found"));
        Category category = categoryRepository.findById(variantDto.getCategoryId()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        variant.setName(variantDto.getName());
        variant.setCategory(category);
        Variant updatedVariant = variantRepository.save(variant);
        return VariantMapper.mapToVariantDto(updatedVariant);
    }

    @Override
    public void deleteVariant(int id) {
        Variant variant = variantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Variant not found"));
        variantRepository.delete(variant);
    }

    private Pageable createPageable(int page, int size, String field, String sortOrder) {
        Sort.Direction direction = Sort.Direction.ASC;
        if ("desc".equalsIgnoreCase(sortOrder)) {
            direction = Sort.Direction.DESC;
        }
        return PageRequest.of(page, size, Sort.by(direction, field));
    }
}
