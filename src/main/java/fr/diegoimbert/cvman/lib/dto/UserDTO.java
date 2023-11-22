package fr.diegoimbert.cvman.lib.dto;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

public class UserDTO {
  @Component
  @Data
  @NoArgsConstructor
  public static class ListOut {
    private Long id;
    private String firstName;
    private String lastName;
    private String avatar;
    @JsonIgnore
    private String description;

    public String getShortDescription() {
      final int limit = 80;
      return description.length() < limit
          ? description
          : (description.substring(0, limit) + "...");
    }
  }

  @Component
  @Data
  @NoArgsConstructor
  public static class DetailsOut {
    private Long id;
    private String firstName;
    private String lastName;
    private String avatar;
    private String description;
    private String website;
    private String email;
    @JsonIgnore
    private Date birthday;
    private List<CvDTO> cvs;

    public int getAge() {
      return Period.between(
          LocalDate.ofInstant(birthday.toInstant(), ZoneId.systemDefault()),
          LocalDate.now()).getYears();
    }
  }

  @Component
  @Data
  @NoArgsConstructor
  public static class CreateIn {
    private String email;
    private String firstName;
    private String lastName;
    private Date birthday;
    private String rawPassword;
    private String hashedPassword = "";
  }
}
