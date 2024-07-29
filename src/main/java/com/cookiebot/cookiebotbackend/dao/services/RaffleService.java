package com.cookiebot.cookiebotbackend.dao.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.cookiebot.cookiebotbackend.core.domains.Raffle;
import com.cookiebot.cookiebotbackend.core.domains.RaffleParticipant;
import com.cookiebot.cookiebotbackend.dao.repository.RaffleRepository;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.BadRequestException;
import com.cookiebot.cookiebotbackend.dao.services.exceptions.ObjectNotFoundException;

@Service
public class RaffleService {
	
	private final RaffleRepository repository;

	private final MongoOperations mongoOperations;

	public RaffleService(RaffleRepository repository, MongoOperations mongoOperations) {
		this.repository = repository;
        this.mongoOperations = mongoOperations;
    }

	public List<Raffle> findAll(){
		return repository.findAll();
	}
	
	public Raffle findByName(String name) {
		Raffle raffle = repository.findById(name)
				.orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
		return raffle;
	}
	
	public Raffle insert(Raffle raffle) {
		try {
			return repository.insert(raffle);
		} catch (DuplicateKeyException e) {
			throw new BadRequestException("Raffle with name " + raffle.getName() + " already exists");
		}
	}

	public void delete(String name) {
		repository.deleteById(name);
	}
	
	public Raffle update(String name, Raffle raffle) {
		Raffle newRaffle = repository.findById(name)
				.orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
		raffle.setName(name);
		updateRaffle(newRaffle, raffle);
		return repository.save(newRaffle);
	}
	
	private void updateRaffle(Raffle newRaffle, Raffle raffle) {
		if (raffle.getAward() != null) {
			newRaffle.setAward(raffle.getAward());
		}
		
		if (raffle.getDeadline() != null) {
			newRaffle.setDeadline(raffle.getDeadline());
		}
	}
	
	public List<RaffleParticipant> findParticipants(String name) {
		Raffle raffle = repository.findById(name)
				.orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
		List<RaffleParticipant> participantList = raffle.getParticipants();
		return participantList;
	}
	
	public void insertParticipant(String raffleId, RaffleParticipant participant) {
		if (participant.getUser() == null) {
			throw new BadRequestException("User Must Not Be Null");
		}

		repository.findById(raffleId)
				.orElseThrow(() -> new ObjectNotFoundException("Not found raffled with id " + raffleId));

		Query query = new Query(Criteria.where("_id").is(raffleId));
		Update update = new Update().addToSet("participants", participant);

		this.mongoOperations.updateFirst(query, update, Raffle.class);
	}

	public void deleteParticipant(String name, RaffleParticipant participant) {
		if (participant.getUser() == null) {
			throw new BadRequestException("User Must Not Be Null");
		}
		
		Raffle raffle = repository.findById(name)
				.orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
		List<RaffleParticipant> participantList = new ArrayList<RaffleParticipant>(raffle.getParticipants());
		String participantToDelete = participant.getUser();
		Integer participantSize = participantList.size();
		
		boolean foundParticipant = false;
		for (int participantArray = participantSize-1; participantArray >= 0; participantArray--) {
			if (participantList.get(participantArray).getUser().matches(participantToDelete)){	
				foundParticipant = true;
				participantList.remove(participantArray);
				raffle.setParticipants(participantList);
				repository.save(raffle);
			} 
		}
		
		if (foundParticipant == false) {
			throw new ObjectNotFoundException("Participant Not Found");
		}
	}
	
	public void updateParticipant(String name, RaffleParticipant participant) {
		if (participant.getUser() == null) {
			throw new BadRequestException("User Must Not Be Null");
		}
		
		Raffle raffle = repository.findById(name)
				.orElseThrow(() -> new ObjectNotFoundException("Object Not Found"));
		List<RaffleParticipant> participantList = new ArrayList<RaffleParticipant>(raffle.getParticipants());
		Integer participantSize = participantList.size();
		
		boolean foundParticipant = false;
		for (int participantArray = participantSize-1; participantArray >= 0; participantArray--) {	
			if (participantList.get(participantArray).getUser().matches(participant.getUser())){
				foundParticipant = true;
				participantList.remove(participantArray);
				participantList.addAll(Arrays.asList(participant));
				raffle.setParticipants(participantList);
				repository.save(raffle);
			} 
		}
		
		if (foundParticipant == false) {
			throw new ObjectNotFoundException("User Not Found");
		}
	}
}
