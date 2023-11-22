// Copyright (c) 2023 Diego Imbert
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package fr.diegoimbert.cvman.lib.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.diegoimbert.cvman.lib.auth.JwtUtil;
import fr.diegoimbert.cvman.lib.dao.UserRepository;
import fr.diegoimbert.cvman.lib.dto.AuthDTO;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;

  @Autowired
  JwtUtil jwtUtil;

  @Autowired
  UserRepository ur;

  @PostMapping("/login")
  @ResponseBody
  public ResponseEntity<Object> login(@RequestBody AuthDTO.Login.Req loginReq) {
    try {
      Authentication authentication = authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));
      var email = authentication.getName();
      var user = ur.findByEmail(email).orElseThrow();
      var token = jwtUtil.createToken(user);
      var loginRes = new AuthDTO.Login.Res(email, token);

      return ResponseEntity.ok(loginRes);

    } catch (BadCredentialsException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or password");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

}