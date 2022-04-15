package com.demo.admin.custom.helpers;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguracionCustom {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
