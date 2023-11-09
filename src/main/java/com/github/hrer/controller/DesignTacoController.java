package com.github.hrer.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.github.hrer.entity.Ingredient;
import com.github.hrer.entity.Taco;

import jakarta.validation.Valid;

import com.github.hrer.entity.Ingredient.Type;
import com.github.hrer.entity.Order;
import com.github.hrer.repository.IngredientRepository;
import com.github.hrer.repository.TacoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
public class DesignTacoController {

	private final IngredientRepository ingredientRepo;
	
	private TacoRepository tacoRepo;
	
	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository tacoRepo) {
	this.ingredientRepo = ingredientRepo;
	this.tacoRepo = tacoRepo;
	}
	
	@ModelAttribute(name = "order")
	public Order order() {
	return new Order();
	}
	
	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}
	
	@GetMapping
	public String showDesignForm(Model model) {
		
		addIngrediensToModel(model);
		model.addAttribute("design", new Taco());
		return "design";
	}

	@ModelAttribute
	private void addIngrediensToModel(Model model) {
		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepo.findAll().forEach(i -> ingredients.add(i));

		Type types [] = Ingredient.Type.values();
		
		for (Type type: types) {
			model.addAttribute(type.toString().toLowerCase(),
					filterByType(ingredients, type));
		}
	}
	
	@PostMapping
	public String processDesign(@Valid @ModelAttribute("design") Taco design, BindingResult binding,
			@ModelAttribute Order order) {
		
		if(binding.hasErrors()) {
			log.info("Form has errors:" + binding.toString());
			return "design";
		}
		
		log.info("Processing design" + design);
		
		Taco saved = tacoRepo.save(design);
		order.addDesign(saved);
		
		return "redirect:/orders/current";
	}
	
	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {

	    return ingredients.stream()
	            .filter(x -> x.getType().equals(type))
	            .collect(Collectors.toList());

	}
}
