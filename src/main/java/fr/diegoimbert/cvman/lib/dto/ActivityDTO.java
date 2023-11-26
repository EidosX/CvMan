// Copyright (c) 2023 Diego Imbert
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT
package fr.diegoimbert.cvman.lib.dto;

import org.springframework.stereotype.Component;

import fr.diegoimbert.cvman.lib.model.Activity;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ActivityDTO
 */
@Component
@Data
@NoArgsConstructor
public class ActivityDTO {
  private Long id;
  private int year;
  private Activity.Type type;
  private String title;
  private String description;
  private String website;

  @Component
  @Data
  @NoArgsConstructor
  public static class Create {
    private Long cvId;
  }
}