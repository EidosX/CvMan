// Copyright (c) 2023 Diego Imbert
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package fr.diegoimbert.cvman.lib.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class AuthDTO {
  public static class Login {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Req {
      private String email;
      private String password;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Res {
      private Long id;
      private String email;
      private String firstName;
      private String lastName;
      private String token;
    }
  }
}