package fr.diegoimbert.cvman.lib.activity;

import org.hibernate.validator.constraints.URL;

import fr.diegoimbert.cvman.lib.cv.CV;
import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "activity")
@NoArgsConstructor
@Data
public class Activity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn
  @NotNull
  private CV cv;

  @Basic
  @NotNull
  private int year;

  @Basic
  @NotNull
  private Activity.Type type;

  @Basic
  @NotBlank
  private String title;

  @Basic
  @NotNull
  private String description;

  @Basic
  @NotNull
  @URL
  private String website;

  public static enum Type {
    PROFESSIONAL_EXPERIENCE, TRAINING, PROJECT, OTHER
  }
}
