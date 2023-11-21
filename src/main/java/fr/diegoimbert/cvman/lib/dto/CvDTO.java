// Copyright (c) 2023 Diego Imbert
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT
package fr.diegoimbert.cvman.lib.dto;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CvDTO
 */
@Component
@Data
@NoArgsConstructor
public class CvDTO {
  private Long id;
  private String name;
  private List<ActivityDTO> activities;
}