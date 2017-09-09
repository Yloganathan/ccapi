package com.capone.ccapi;

import com.capone.ccapi.resources.*;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import com.washingtonpost.dropwizard.exceptions.mappers.JsonProcessingExceptionMapper;
import com.washingtonpost.dropwizard.exceptions.mappers.RuntimeExceptionMapper;

public class CreditCardApplication extends Application<CreditCardServiceConfiguration> {

    public static void main(final String[] args) throws Exception{
        new CreditCardApplication().run(args);
    }
 
    @Override
    public String getName() {
        return "CreditCardService";
    }

    @Override
    public void initialize(final Bootstrap<CreditCardServiceConfiguration> bootstrap) {

        bootstrap.setConfigurationSourceProvider(
            new SubstitutingSourceProvider(
                bootstrap.getConfigurationSourceProvider(),
                new EnvironmentVariableSubstitutor(false)
            ));

    }

    @Override
    public void run(final CreditCardServiceConfiguration configuration,
                    final Environment environment) {

        environment.jersey().register(new HealthResource());
        environment.jersey().register(new JsonProcessingExceptionMapper());
        environment.jersey().register(new RuntimeExceptionMapper());
    }
}
