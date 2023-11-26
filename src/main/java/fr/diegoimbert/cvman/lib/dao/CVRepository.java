package fr.diegoimbert.cvman.lib.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.diegoimbert.cvman.lib.model.CV;
import fr.diegoimbert.cvman.lib.model.User;

@Repository
@Transactional
public interface CVRepository extends JpaRepository<CV, Long> {
  List<CV> findByUser(User user);

  default public void updateListFromUser(User user, List<CV> newCvs, ActivityRepository ar) {
    var existingCvs = this.findByUser(user);
    for (var existing : existingCvs) {
      if (newCvs.stream().noneMatch(c -> c.getId() == existing.getId())) {
        this.delete(existing);
      }
    }
    for (var cv : newCvs) {
      cv.setUser(user);
      this.save(cv);
      ar.updateListFromCv(cv, cv.getActivities());
    }
  }
}