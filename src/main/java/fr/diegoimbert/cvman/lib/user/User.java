package fr.diegoimbert.cvman.lib.user;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.URL;

import fr.diegoimbert.cvman.lib.cv.CV;
import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "appUser")
@NoArgsConstructor
@Data
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Basic
  @NotBlank
  @Size(min = 2, max = 20)
  private String firstName;

  @Basic
  @NotBlank
  @Size(min = 2, max = 20)
  private String lastName;

  @Basic
  @NotBlank
  @Email
  private String email;

  @Basic
  @NotNull
  @URL
  private String website;

  @Basic
  @NotNull
  private Date birthday;

  @Basic
  @NotNull
  private String description;

  @Basic
  @NotBlank
  private String hashedPassword;

  @OneToMany
  private List<CV> cvs;
}