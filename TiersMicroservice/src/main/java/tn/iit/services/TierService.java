package tn.iit.services;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import tn.iit.entites.Tiers;
import tn.iit.exceptions.TiersNotFoundException;
import tn.iit.repository.TierRepository;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class TierService {
	private final TierRepository tierrepository;



	public Tiers saveOrUpdate(Tiers tiers) {
		return tierrepository.save(tiers);

	}

	public List<Tiers> findAll() {
		return tierrepository.findAll();
	}

	public void deleteTier(Long id) {
		tierrepository.deleteById(id);
	}

	public Tiers getById(Long id) throws TiersNotFoundException {
		return tierrepository.findById(id)
				.orElseThrow(() -> new TiersNotFoundException("Tiers avec ID " + id + " non trouvé"));
	}

	public Tiers update(Long id, Tiers updatedTier) throws TiersNotFoundException {
		Tiers existingTier = getById(id); // Vérifie que le tiers existe

		// Modifier les champs
		existingTier.setNom(updatedTier.getNom());
		existingTier.setEmail(updatedTier.getEmail());
		existingTier.setTelephone(updatedTier.getTelephone());
		existingTier.setTypetier(updatedTier.getTypetier());

		// Sauvegarder les modifications
		return tierrepository.save(existingTier);
	}
}
