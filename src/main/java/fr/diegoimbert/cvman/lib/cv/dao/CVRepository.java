package fr.diegoimbert.cvman.lib.cv.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.diegoimbert.cvman.lib.user.User;

@Repository
@Transactional
public interface CVRepository extends JpaRepository<User, Integer> {
}