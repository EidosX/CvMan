package fr.diegoimbert.cvman.lib.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diegoimbert.cvman.lib.dao.UserRepository;
import fr.diegoimbert.cvman.lib.dto.UserDTO;
import fr.diegoimbert.cvman.lib.model.User;

import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/user")
public class UserController {
  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ModelMapper modelMapper;

  @GetMapping("/list")
  public Page<UserDTO.ListOut> list(
      @RequestParam int pageNumber, @RequestParam Optional<String> searchBar, @RequestParam Optional<String> searchBy) {
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
}