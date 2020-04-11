package com.wiredbrain.friends;

import com.wiredbrain.friends.controller.FriendController;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


//@RunWith(SpringRunner.class)
@SpringBootTest
class FriendsApplicationTests {

	@Autowired
	FriendController friendController;


	@Test
	void contextLoads() {
		Assertions.assertNotNull(friendController);
	}

}
