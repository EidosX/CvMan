package fr.diegoimbert.cvman.lib.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diegoimbert.cvman.lib.auth.JwtUtil;
import fr.diegoimbert.cvman.lib.dao.ActivityRepository;
import fr.diegoimbert.cvman.lib.dao.CVRepository;
import fr.diegoimbert.cvman.lib.dao.UserRepository;
import fr.diegoimbert.cvman.lib.dto.UserDTO;
import fr.diegoimbert.cvman.lib.model.CV;
import fr.diegoimbert.cvman.lib.model.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/user")
public class UserController {
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private CVRepository cvRepository;
  @Autowired
  private ActivityRepository activityRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private PasswordEncoder encoder;

  @Autowired
  private JwtUtil jwtUtil;

  @GetMapping("/list")
  public Page<UserDTO.ListOut> list(
      @RequestParam int pageNumber, @RequestParam Optional<String> searchBar,
      @RequestParam Optional<String> searchBy) {
    var like = "%" + searchBar.orElse("") + "%";
    var pr = PageRequest.of(pageNumber, 10);

    Page<User> page;

    if (searchBy.equals(Optional.of("cv"))) {
      page = userRepository.findAllByCv(pr, like);
    } else if (searchBy.equals(Optional.of("activity"))) {
      page = userRepository.findAllByActivity(pr, like);
    } else {
      page = userRepository.findAllByFullName(pr, like);
    }
    return page.map(u -> modelMapper.map(u, UserDTO.ListOut.class));
  }

  @GetMapping("/details/{id}")
  public UserDTO.DetailsOut details(@PathVariable Long id) {
    var user = userRepository.findById(id).get();
    return modelMapper.map(user, UserDTO.DetailsOut.class);
  }

  @PostMapping("/edit-password/{id}")
  public void editPassword(
      @PathVariable Long id,
      @RequestBody UserDTO.EditPassword dto,
      HttpServletRequest req) {
    var user = userRepository.findById(id).get();
    var tokenEmail = jwtUtil.getEmail(jwtUtil.resolveClaims(req));
    if (!user.getEmail().equals(tokenEmail)) {
      throw new RuntimeException("Unauthorized");
    }
    user.setHashedPassword(encoder.encode(dto.getRawPassword()));
    userRepository.save(user);
  }

  @PostMapping("/edit")
  public void edit(@RequestBody UserDTO.Edit dto, HttpServletRequest req) {
    var user = userRepository.findById(dto.getId()).get();
    var tokenEmail = jwtUtil.getEmail(jwtUtil.resolveClaims(req));
    if (!user.getEmail().equals(tokenEmail)) {
      throw new RuntimeException("Unauthorized");
    }
    if (dto.getAvatar() != null) {
      user.setAvatar(dto.getAvatar());
    }
    if (dto.getDescription() != null) {
      user.setDescription(dto.getDescription());
    }
    if (dto.getEmail() != null) {
      user.setEmail(dto.getEmail());
    }
    if (dto.getFirstName() != null) {
      user.setFirstName(dto.getFirstName());
    }
    if (dto.getLastName() != null) {
      user.setLastName(dto.getLastName());
    }
    if (dto.getWebsite() != null) {
      user.setWebsite(dto.getWebsite());
    }
    if (dto.getCvs() != null && !dto.getCvs().isEmpty()) {
      var cvs = dto.getCvs().stream().map(c -> modelMapper.map(c, CV.class)).toList();
      cvRepository.updateListFromUser(user, cvs, activityRepository);
    }
    userRepository.save(user);
  }
}