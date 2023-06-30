package com.taiga.taiga;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class RestAuthTest {

    @Autowired
    RestAuth restAuth;

    @Test
    public void verifyEmptyAuthToke() {
        String authToken = restAuth.authToken;
        Assert.assertTrue( authToken == null);
    }

    @Test
    public void verifyUnauthorizedToken() {
        String authToken = restAuth.getAuthToken("test", "test");
        Assert.assertFalse(authToken != null);
    }
}
