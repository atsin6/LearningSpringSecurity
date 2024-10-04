package com.atulpal.LearningSpringSecurity.LearningSpringSecurity;

import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.entities.User;
import com.atulpal.LearningSpringSecurity.LearningSpringSecurity.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LearningSpringSecurityApplicationTests {

	@Autowired
	private JwtService jwtService;


	@Test
	void contextLoads(){
		User user = new User(4L, "atul@atsin.com", "1234", "atula2");

		String token = jwtService.generateToken(user);
		System.out.println(token);

		Long id = jwtService.getUserIdFromToken(token);
		System.out.println(id);

	}

}
