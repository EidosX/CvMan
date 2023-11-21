// Copyright (c) 2023 Diego Imbert
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package fr.diegoimbert.cvman.lib.model.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class Login {
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
    private String email;
    private String token;
  }
}