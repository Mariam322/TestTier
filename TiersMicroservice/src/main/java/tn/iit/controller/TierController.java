package tn.iit.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.iit.entites.Tiers;
import tn.iit.exceptions.TiersNotFoundException;
import tn.iit.services.TierService;


@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class TierController {
	private final TierService tierservice;
	
	public List<Tiers>tiers(){
		return tierservice.findAll();
	}
	
	public Tiers getTierById(@PathVariable(name = "id") Long tierId) throws TiersNotFoundException {
		return tierservice.getById(tierId);
	}
	 public Tiers createTier(@RequestBody Tiers tiers) {
	        return tierservice.saveOrUpdate(tiers);
	    }
	 public Tiers updateTier(@PathVariable Long id, @RequestBody Tiers updatedTier)
	            throws TiersNotFoundException {
	        return tierservice.update(id, updatedTier);
	    }
	 public void deleteTier(@PathVariable Long id) {
		 tierservice.deleteTier(id);
	    }

	

}
