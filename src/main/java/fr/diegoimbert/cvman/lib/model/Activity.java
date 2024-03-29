package fr.diegoimbert.cvman.lib.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "activity")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Activity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn
  @NotNull
  @JsonIgnore
  private CV cv;

  @Basic
  @NotNull
  private int year;

  @Basic
  @NotNull
  @Enumerated(EnumType.STRING)
  private Activity.Type type;

  @Basic
  @NotBlank
  private String title;

  @Basic
  @NotNull
  private String description;

  @Basic
  private String website;

  public static enum Type {
    PROFESSIONAL_EXPERIENCE, TRAINING, PROJECT, OTHER
  }
}
