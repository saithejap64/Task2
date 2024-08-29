package com.example.task2.dto;

import com.example.task2.model.Category;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDTO {
    private String name;
    private List<CategoryResponseDTO> subClasses;

}
