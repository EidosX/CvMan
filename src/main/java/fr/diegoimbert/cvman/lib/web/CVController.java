package fr.diegoimbert.cvman.lib.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.diegoimbert.cvman.lib.dao.CVRepository;
import fr.diegoimbert.cvman.lib.model.CV;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/cv")
public class CVController {
  @Autowired
  private CVRepository cvRepository;

  @GetMapping("/list")
  public Page<CV> list(@RequestParam int pageNumber) {
    var page = cvRepository.findAll(PageRequest.of(pageNumber, 20));
    return page;
  }
}