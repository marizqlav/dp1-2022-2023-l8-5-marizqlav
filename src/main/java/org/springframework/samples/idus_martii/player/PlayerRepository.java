package org.springframework.samples.idus_martii.player;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player, Integer>{
	
	@Query("SELECT p FROM Player p")
	List<Player> findAllPlayers();
}
