package com.example.task2.service;

import com.example.task2.dto.CategoryResponseDTO;
import com.example.task2.model.Category;
import com.example.task2.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public List<CategoryResponseDTO> getAllCategories() {
        List<Category> categories= categoryRepository.findAll();
        Map<Long, List<Category>> categoryMap = categories.stream()
                .filter(category -> category.getParentId()!=0)
                .collect(Collectors.groupingBy(Category::getParentId));

        return categories.stream()
                .filter(category -> category.getParentId()==0)
                .map(category -> mapToDto(category, categoryMap))
                .collect(Collectors.toList());
    }

    private CategoryResponseDTO mapToDto(Category category, Map<Long, List<Category>> categoryMap){
        List<Category> subClasses = categoryMap.get(category.getId());
        List<CategoryResponseDTO> subClassDTOs = new ArrayList<>();

        if(subClasses!=null){
            subClassDTOs = subClasses.stream()
                    .map(subClass -> mapToDto(subClass, categoryMap))
                    .collect(Collectors.toList());
        }
        return new CategoryResponseDTO(category.getName(), subClassDTOs);
    }
}