package com.github.hrer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.github.hrer.entity.Order;
import com.github.hrer.repository.OrderRepository;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
	
	private OrderRepository orderRepo;
	
	@Autowired
	public OrderController(OrderRepository orderRepo) {
		this.orderRepo = orderRepo;
	}

	@GetMapping("/current")
	public String orderForm(Model model) {
		model.addAttribute("order", new Order());
		return "orderForm";
	}
	


	@PostMapping
	public String processOrder(@Valid @ModelAttribute("order") Order order, BindingResult binding, SessionStatus sessionStatus) {
		
		if(binding.hasErrors()) {
			log.info("Order has errors:" + binding.toString());
			return "orderForm";
		}
		log.info("order submitted" + order);

		orderRepo.save(order);
		sessionStatus.setComplete();
		return "redirect:/";
	}
}	
