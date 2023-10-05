package fr.diegoimbert.cvman.lib.user.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.diegoimbert.cvman.lib.user.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
	List<User> findByEmail(String email);
}