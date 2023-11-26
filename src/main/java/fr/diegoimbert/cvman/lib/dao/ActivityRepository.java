package fr.diegoimbert.cvman.lib.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.diegoimbert.cvman.lib.model.Activity;
import fr.diegoimbert.cvman.lib.model.CV;

@Repository
@Transactional
public interface ActivityRepository extends JpaRepository<Activity, Long> {
  List<Activity> findByCv(CV cv);

  default public void updateListFromCv(CV cv, List<Activity> newActivities) {
    var existingActivities = this.findByCv(cv);
    for (var existing : existingActivities) {
      if (newActivities.stream().noneMatch(a -> a.getId() == existing.getId())) {
        this.delete(existing);
      }
    }
    for (var activity : newActivities) {
      activity.setCv(cv);
      this.save(activity);
    }
  }
}