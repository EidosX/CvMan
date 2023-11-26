// Copyright (c) 2023 Diego Imbert
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package fr.diegoimbert.cvman.lib.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diegoimbert.cvman.lib.auth.JwtUtil;
import fr.diegoimbert.cvman.lib.dao.CVRepository;
import fr.diegoimbert.cvman.lib.dao.UserRepository;
import fr.diegoimbert.cvman.lib.dto.CvDTO;
import fr.diegoimbert.cvman.lib.model.CV;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/cv")
public class CVController {
  @Autowired
  private CVRepository cr;
  @Autowired
  private UserRepository ur;
  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private JwtUtil jwtUtil;

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id, HttpServletRequest req) {
    var tokenEmail = jwtUtil.getEmail(jwtUtil.resolveClaims(req));
    var cv = cr.findById(id).get();
    if (!cv.getUser().getEmail().equals(tokenEmail)) {
      throw new RuntimeException("Unauthorized");
    }
    cr.deleteById(id);
  }

  @PostMapping("")
  public CvDTO create(@RequestBody CvDTO.Create dto, HttpServletRequest req) {
    var user = ur.findById(dto.getUserId()).get();
    var tokenEmail = jwtUtil.getEmail(jwtUtil.resolveClaims(req));
    if (!user.getEmail().equals(tokenEmail)) {
      throw new RuntimeException("Unauthorized");
    }
    var cv = cr.save(new CV(
        null, "Nouveau CV", user, List.of()));
    return modelMapper.map(cv, CvDTO.class);
  }
}