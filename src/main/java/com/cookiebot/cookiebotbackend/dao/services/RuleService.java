package com.cookiebot.cookiebotbackend.dao.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domain.Rule;
import com.cookiebot.cookiebotbackend.dao.repository.RuleRepository;
import com.cookiebot.cookiebotbackend.dao.services.exception.BadRequestException;
import com.cookiebot.cookiebotbackend.dao.services.exception.ObjectNotFoundException;

@Service
public class RuleService {

	@Autowired
	private RuleRepository repository;
	
	public List<Rule> findAll(){
		return repository.findAll();
	}
	
	public Rule findById(String id) {
		Rule rule = repository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
		return rule;
	}
	
	public Rule insert(Rule rule) {
		
		if (rule.getId() == null) {
			throw new BadRequestException("ID Must Not Be Null");
		}
		
		Rule findId = repository.findById(rule.getId()).orElse(null);
		
		if (findId == null) {
			return repository.insert(rule);
		} else {
			throw new BadRequestException("ID Already Exists");
		}
	}
	
	public void delete(String id) {
		
		if (id == null) {
			throw new BadRequestException("ID Must Not Be Null");
		}
		
		repository.deleteById(id);
	}
	
	public Rule update(Rule rule) {
		Rule newRule = repository.findById(rule.getId())
				.orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
		updateRules(newRule, rule);
		return repository.save(newRule);
	}

	private void updateRules(Rule newRule, Rule rule) {
		
		if (rule.getRules() != null) {
			newRule.setRules(rule.getRules());
		}
	}
}
