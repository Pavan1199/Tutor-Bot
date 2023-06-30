package com.taiga.taiga.configuration;

import com.taiga.taiga.RestAuth;
import com.taiga.taiga.services.TaigaService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class TaigaTestConfiguration {

    public TaigaService taigaService() {
        return Mockito.mock(TaigaService.class);
    }

    public RestAuth restAuth() {
        return Mockito.mock(RestAuth.class);
    }
}
