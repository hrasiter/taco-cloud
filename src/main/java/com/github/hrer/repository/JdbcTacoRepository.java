package com.github.hrer.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.github.hrer.entity.Taco;

public class JdbcTacoRepository implements TacoRepository {

	private JdbcTemplate jdbc;
	
	@Autowired
	public JdbcTacoRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}
	
	@Override
	public Taco save(Taco design) {
		// TODO Auto-generated method stub
		return null;
	}

}
