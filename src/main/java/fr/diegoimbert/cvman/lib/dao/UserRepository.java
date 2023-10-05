package fr.diegoimbert.cvman.lib.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.diegoimbert.cvman.lib.model.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);
}