package fr.diegoimbert.cvman.lib.cv;

import java.util.List;

import fr.diegoimbert.cvman.lib.activity.Activity;
import fr.diegoimbert.cvman.lib.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cv")
@NoArgsConstructor
@Data
public class CV {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn
  @NotNull
  private User user;

  @OneToMany
  private List<Activity> activities;
}
