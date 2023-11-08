package com.github.hrer.entity;

import java.sql.Date;
import java.util.List;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class Taco {
	
	public long id;
	public Date createdAt;
	@Size(min=5, message="Name must be at least 5 characters long")
	private String name;
	
	@NotEmpty(message="You must choose at least 1 ingredient")
	private List<String> ingredients;
}
