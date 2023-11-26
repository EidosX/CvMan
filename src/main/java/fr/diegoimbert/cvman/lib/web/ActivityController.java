// Copyright (c) 2023 Diego Imbert
//
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package fr.diegoimbert.cvman.lib.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diegoimbert.cvman.lib.auth.JwtUtil;
import fr.diegoimbert.cvman.lib.dao.ActivityRepository;
import fr.diegoimbert.cvman.lib.dao.CVRepository;
import fr.diegoimbert.cvman.lib.dto.ActivityDTO;
import fr.diegoimbert.cvman.lib.model.Activity;
import jakarta.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/activity")
public class ActivityController {
  @Autowired
  private ActivityRepository ar;
  @Autowired
  private CVRepository cr;
  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private JwtUtil jwtUtil;

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id, HttpServletRequest req) {
    var activity = ar.findById(id).get();
    var tokenEmail = jwtUtil.getEmail(jwtUtil.resolveClaims(req));
    if (!activity.getCv().getUser().getEmail().equals(tokenEmail)) {
      throw new RuntimeException("Unauthorized");
    }
    ar.deleteById(id);
  }

  @PostMapping("")
  public ActivityDTO create(
      @RequestBody ActivityDTO.Create dto, HttpServletRequest req) {
    var cv = cr.findById(dto.getCvId()).get();
    var tokenEmail = jwtUtil.getEmail(jwtUtil.resolveClaims(req));
    if (!cv.getUser().getEmail().equals(tokenEmail)) {
      throw new RuntimeException("Unauthorized");
    }
    var activity = ar
        .save(new Activity(null, cv, 2023, Activity.Type.PROFESSIONAL_EXPERIENCE, "Nouvelle activité", "", null));
    return modelMapper.map(activity, ActivityDTO.class);
  }
}