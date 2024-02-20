package com.example.tacocloudspringinaction.repositories;

import com.example.tacocloudspringinaction.models.Ingredient;

public interface IngredientRepository {
    Iterable<Ingredient> findAll();
    Ingredient findOne(String id);
    Ingredient save(Ingredient ingredient);
}
