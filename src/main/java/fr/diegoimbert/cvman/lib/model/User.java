package fr.diegoimbert.cvman.lib.model;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.URL;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString.Exclude;

@Entity
@Table(name = "appUser", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "email" }) })
@NoArgsConstructor
@AllArgsConstructor
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
  @URL
  private String avatar;

  @Basic
  @NotBlank
  @Email
  private String email;

  @Basic
  @URL
  private String website;

  @Basic
  @NotNull
  private Date birthday;

  @Basic
  @NotNull
  @Exclude
  private String description = "";

  @Basic
  @NotBlank
  @Exclude
  private String hashedPassword;

  @Basic
  @NotNull
  @Enumerated(EnumType.STRING)
  private User.Role role = User.Role.VISITOR;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
  @Exclude
  private List<CV> cvs;

  public static enum Role {
    VISITOR,
    ADMIN,
  };
}