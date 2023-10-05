package fr.diegoimbert.cvman.lib.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.diegoimbert.cvman.lib.model.CV;

@Repository
@Transactional
public interface CVRepository extends JpaRepository<CV, Long> {
}