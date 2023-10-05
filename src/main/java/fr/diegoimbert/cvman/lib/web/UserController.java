package fr.diegoimbert.cvman.lib.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diegoimbert.cvman.lib.dao.UserRepository;
import fr.diegoimbert.cvman.lib.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/user")
public class UserController {
  @Autowired
  private UserRepository userRepository;

  @GetMapping("/list")
  public Page<User> list(@RequestParam int pageNumber) {
    var page = userRepository.findAll(PageRequest.of(pageNumber, 20));
    return page;
  }
}