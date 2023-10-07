package fr.diegoimbert.cvman;

import java.util.Collections;
import java.util.Random;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.github.javafaker.Faker;

import fr.diegoimbert.cvman.lib.dao.ActivityRepository;
import fr.diegoimbert.cvman.lib.dao.CVRepository;
import fr.diegoimbert.cvman.lib.dao.UserRepository;
import fr.diegoimbert.cvman.lib.model.Activity;
import fr.diegoimbert.cvman.lib.model.CV;
import fr.diegoimbert.cvman.lib.model.User;
import fr.diegoimbert.cvman.lib.model.Activity.Type;
import fr.diegoimbert.cvman.lib.model.User.Role;
import jakarta.annotation.PostConstruct;

@Component
public class Populate {
  private Faker faker = new Faker();
  private Random random = new Random();

  @Autowired
  UserRepository ur;

  @Autowired
  CVRepository cr;

  @Autowired
  ActivityRepository ar;

  @Autowired
  PasswordEncoder passwordEncoder;

  @PostConstruct
  public void init() {
    var users = IntStream.range(0, 10).mapToObj(this::randomUser).toList();
    ur.saveAll(users);

    var cvs = users.stream()
        .flatMap(u -> Collections.nCopies(random.nextInt(0, 4), u).stream())
        .map(this::randomCV)
        .toList();
    cr.saveAll(cvs);

    var activities = cvs.stream()
        .flatMap(c -> Collections.nCopies(random.nextInt(0, 8), c).stream())
        .map(this::randomActivity)
        .toList();
    ar.saveAll(activities);
  }

  private User randomUser(int i) {
    return new User(null, faker.name().firstName(), faker.name().lastName(),
        "https://randomuser.me/api/portraits/"
            + (faker.random().nextBoolean() ? "men/" : "women/")
            + i % 70 + ".jpg",
        faker.internet().emailAddress(), "http://" + faker.internet().domainName(),
        faker.date().birthday(17, 68),
        "I am a very happy and chill person! It will be a pleasure to work with you.",
        passwordEncoder.encode(faker.internet().password()), Role.VISITOR, null);
  }

  private CV randomCV(User user) {
    return new CV(null, "Mon CV de " + random.nextInt(1980, 2023),
        user, null);
  }

  private Activity randomActivity(CV cv) {
    var year = random.nextInt(1980, 2023);
    return new Activity(null, cv, year,
        Type.values()[random.nextInt(Type.values().length)],
        "Experience de " + year,
        "Voici une activité que j'ai effectué en " + year + ", qui a" +
            "consisté en beaucoup de taches très intéressantes",
        "http://activity-website.fr");
  }
}
