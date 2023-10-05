package fr.diegoimbert.cvman.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.diegoimbert.cvman.lib.dao.ActivityRepository;
import fr.diegoimbert.cvman.lib.dao.CVRepository;
import fr.diegoimbert.cvman.lib.dao.UserRepository;
import fr.diegoimbert.cvman.lib.model.Activity;
import fr.diegoimbert.cvman.lib.model.CV;
import fr.diegoimbert.cvman.lib.model.User;
import fr.diegoimbert.cvman.lib.model.Activity.Type;
import fr.diegoimbert.cvman.lib.model.User.Role;

@SpringBootTest
class TestModel {
	@Autowired
	UserRepository ur;

	@Autowired
	CVRepository cr;

	@Autowired
	ActivityRepository ar;

	@Autowired
	PasswordEncoder passwordEncoder;

	@AfterEach
	public void clean() {
		for (var r : new CrudRepository[] { ar, cr, ur })
			r.deleteAll();
	}

	@Test
	public void testUser() {
		ur.save(createDiegoUser());
		assertEquals("dm@eidos.cc", ur.findByEmail("dm@eidos.cc").get().getEmail());
	}

	@Test
	public void testCV() {
		var user = createDiegoUser();
		ur.save(user);
		cr.save(new CV(null, user, null));
	}

	@Test
	public void testActivity() {
		var user = ur.save(createDiegoUser());
		var cv = cr.save(new CV(null, user, null));
		ar.save(new Activity(null, cv, 2023, Type.PROFESSIONAL_EXPERIENCE,
				"Stage Kanso", "Stage en web fullstack", "http://kanso.dev"));
	}

	private User createDiegoUser() {
		try {
			return new User(null, "Diego", "Imbert", "dm@eidos.cc", "https://eidos.cc",
					new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2002"), "A User",
					passwordEncoder.encode("password123"), Role.VISITOR, null);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
