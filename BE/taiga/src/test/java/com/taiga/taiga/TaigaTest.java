package com.taiga.taiga;

import com.taiga.taiga.models.TaigaAuthModel;
import com.taiga.taiga.services.TaigaService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
public class TaigaTest {

    @Autowired
    TaigaService taigaService;

    @Value("${taiga.username}")
    private String taigaUsername;

    @Value("${taiga.password}")
    private String taigaPassword;

    @Test
    public void verifyAuthTokenInitialization() {
        String auth_token = taigaService.getTaigaAuthToken();
        Assert.assertTrue(auth_token != null);
    }

    @Test
    public void verifyAuth() {
        TaigaAuthModel taigaAuthModel = new TaigaAuthModel();
        taigaAuthModel.setUsername(taigaUsername);
        taigaAuthModel.setPassword(taigaPassword);
        Boolean authResult = taigaService.authenticateTaiga(taigaAuthModel);
        Assert.assertEquals(authResult, Boolean.TRUE);
    }
}
