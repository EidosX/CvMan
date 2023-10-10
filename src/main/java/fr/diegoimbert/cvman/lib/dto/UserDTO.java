package fr.diegoimbert.cvman.lib.dto;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import fr.diegoimbert.cvman.lib.model.CV;
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
    private String username;
    private String firstName;
    private String lastName;
    private String avatar;
    private String description;
    private String website;
    private String email;
    private Date birthday;
    private List<CV> cvs;
  }
}
