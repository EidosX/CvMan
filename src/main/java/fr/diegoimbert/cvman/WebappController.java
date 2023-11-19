// Copyright (c) 2023 Diego Imbert
// 
// This software is released under the MIT License.
// https://opensource.org/licenses/MIT

package fr.diegoimbert.cvman;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("/")
public class WebappController {
  @GetMapping("/app/**")
  public ModelAndView app() {
    return new ModelAndView("/app-view.html");
  }
  @GetMapping("/")
  public String index() {
    return "redirect:/app";
  }
}