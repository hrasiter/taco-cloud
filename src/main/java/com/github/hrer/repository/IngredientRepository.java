package com.github.hrer.repository;

import com.github.hrer.entity.Ingredient;

public interface IngredientRepository {
	
	Iterable<Ingredient> findAll();
	Ingredient findOne(String id);
	Ingredient save(Ingredient ingredient);
}
