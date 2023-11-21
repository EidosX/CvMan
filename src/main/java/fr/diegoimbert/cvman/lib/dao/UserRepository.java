package fr.diegoimbert.cvman.lib.dao;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.diegoimbert.cvman.lib.model.User;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	@Query(value = "SELECT u FROM User u WHERE CONCAT(u.firstName, ' ', u.lastName) ILIKE :searchBar")
	Page<User> findAllByFullName(Pageable pageable, String searchBar);

	@Query(value = "SELECT u FROM User u WHERE EXISTS(SELECT id FROM CV c WHERE c.user.id = u.id AND c.name ILIKE :searchBar)")
	Page<User> findAllByCv(Pageable pageable, String searchBar);

	@Query(value = "SELECT u FROM User u WHERE EXISTS(SELECT id FROM Activity a WHERE a.cv.user.id = u.id AND a.title ILIKE :searchBar)")
	Page<User> findAllByActivity(Pageable pageable, String searchBar);
}