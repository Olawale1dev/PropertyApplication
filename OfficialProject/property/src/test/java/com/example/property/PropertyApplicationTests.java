package com.example.property;

import com.example.property.entity.User;
import com.example.property.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class PropertyApplicationTests {

	@Autowired
	private TestEntityManager testEntityManager;

	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {

		User user = User.builder()
				.email("dady@gmail.com")
				.firstName("biola")
				.lastName("laid")
				.genderTitle("female")
				.signUpAs("Landlord")
				.password("987654")
				.build();
		userRepository.save(user);
	}

}
