package fr.diegoimbert.cvman.lib.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@NoArgsConstructor
public class UserListDTO {
  private Long id;
  private String firstName;
  private String lastName;

  @JsonIgnore
  private String description;

  public String getShortDescription() {
    final int limit = 80;
    return description.length() < limit
        ? description
        : (description.substring(0, limit) + "...");
  }
}
