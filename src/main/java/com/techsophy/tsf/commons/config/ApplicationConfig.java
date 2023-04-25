package com.techsophy.tsf.commons.config;

import com.techsophy.idgenerator.IdGeneratorImpl;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

import static com.techsophy.tsf.commons.constants.CommonConstants.*;



public class ApplicationConfig
{
    String gatewayUrl;

    @Bean
    public IdGeneratorImpl idGeneratorImpl()
    {
        return new IdGeneratorImpl();
    }

    @Bean
    public OpenAPI customOpenAPI()
    {
        return new OpenAPI()
                .info(new io.swagger.v3.oas.models.info.Info().title(RUNTIME_FORM).version(VERSION_1).description(RUNTIME_FORM_MODELER_API_VERSION_1))
                .servers( List.of(new Server().url(gatewayUrl)));
    }
}
