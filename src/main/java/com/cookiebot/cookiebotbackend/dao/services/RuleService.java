package com.cookiebot.cookiebotbackend.dao.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domains.Rule;
import com.cookiebot.cookiebotbackend.dao.repository.RuleRepository;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.BadRequestException;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.ObjectNotFoundException;

@Service
public class RuleService {

	private final RuleRepository repository;
	
	@Autowired
	public RuleService(RuleRepository repository) {
		this.repository = repository;
	}
	
	public List<Rule> findAll(){
		return repository.findAll();
	}
	
	public Rule findById(String id) {
		return repository.findById(id).orElseThrow(ObjectNotFoundException::new);
	}
	
	public Rule insert(String id, Rule rule) {
		if (repository.findById(id).orElse(null) != null) {
			throw new BadRequestException("ID Already Exists");	
		} 
		
		rule.setId(id);
		return repository.insert(rule);
	}
	
	public void delete(String id) {
		repository.deleteById(id);
	}
	
	public Rule update(String id, Rule rule) {
		Rule newRule = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
		rule.setId(id);
		updateRules(newRule, rule);
		return repository.save(newRule);
	}

	private void updateRules(Rule newRule, Rule rule) {
		if (rule.getRules() != null) {
			newRule.setRules(rule.getRules());
		}
	}
}
