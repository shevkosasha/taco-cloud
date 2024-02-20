package com.example.tacocloudspringinaction.web;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.example.tacocloudspringinaction.repositories.IngredientRepository;
import com.example.tacocloudspringinaction.repositories.TacoRepository;
import jakarta.validation.Valid;

import com.example.tacocloudspringinaction.models.Design;
import com.example.tacocloudspringinaction.models.Ingredient;
import com.example.tacocloudspringinaction.models.Ingredient.Type;
import com.example.tacocloudspringinaction.models.Order;
import com.example.tacocloudspringinaction.models.Taco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

//import tacos.Taco;
//import tacos.Ingredient;
//import tacos.Ingredient.Type;
@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;
//    private final IngredientRepository ingredientRepo;
    private TacoRepository designRepo;
    @Autowired
    public DesignTacoController(
            IngredientRepository ingredientRepo,
            TacoRepository designRepo
    ) {
        this.ingredientRepo = ingredientRepo;
        this.designRepo = designRepo;
    }

    @ModelAttribute(name = "order")
    public Order order() {
        return new Order();
    }
    @ModelAttribute(name = "taco")
    public Taco taco() {
        return new Taco();
    }
//    @Autowired
//    public DesignTacoController(IngredientRepository ingredientRepo) {
//        this.ingredientRepo = ingredientRepo;
//    }
    @GetMapping
    public String showDesignForm(Model model) {
//        !---- without db ----
//        List<Ingredient> ingredients = Arrays.asList(
//                new Ingredient("FLTO", "Flour Tortilla", Type.WRAP),
//                new Ingredient("COTO", "Corn Tortilla", Type.WRAP),
//                new Ingredient("GRBF", "Ground Beef", Type.PROTEIN),
//                new Ingredient("CARN", "Carnitas", Type.PROTEIN),
//                new Ingredient("TMTO", "Diced Tomatoes", Type.VEGGIES),
//                new Ingredient("LETC", "Lettuce", Type.VEGGIES),
//                new Ingredient("CHED", "Cheddar", Type.CHEESE),
//                new Ingredient("JACK", "Monterrey Jack", Type.CHEESE),
//                new Ingredient("SLSA", "Salsa", Type.SAUCE),
//                new Ingredient("SRCR", "Sour Cream", Type.SAUCE)
//        );

//        !---- with db ----
        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepo.findAll().forEach(i -> ingredients.add(i));
        Type[] types = Ingredient.Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
//            model.addAttribute(type.toString().toLowerCase(), ingredients);

        }
        return "design";
    }

    @PostMapping
    public String processDesign(@Valid Taco design, Errors errors, Order order) {
        if (errors.hasErrors()) {
            System.out.println("errors: " + errors.toString());
            return "design";
        }
        // Save the taco design...
        // We'll do this in chapter 3
        log.info("Processing design: " + design);
        Taco saved = designRepo.save(design);
        order.addDesign(saved);
        return "redirect:/orders/current";
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        List<Ingredient> filteredList;
//        List<Employee> originalList = buildEmployeeList();
        List<String> typeFilter = Arrays.asList(type.toString());

        filteredList = ingredients.stream()
                .filter(ingredient -> typeFilter.contains(ingredient.getType().toString()))
                .collect(Collectors.toList());

//        assertThat(filteredList.size(), is(nameFilter.size()));
        return filteredList;
    }
}