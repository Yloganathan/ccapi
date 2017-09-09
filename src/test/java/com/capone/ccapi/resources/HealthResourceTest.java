package com.capone.ccapi.resources;

import io.dropwizard.testing.junit.ResourceTestRule;
import org.mockito.Mockito.*;
import org.junit.*;
//import org.junit.assertThat;

import static org.assertj.core.api.Assertions.assertThat;

public class HealthResourceTest {

    String message = "A OK";

    @ClassRule
    public static final ResourceTestRule resources = ResourceTestRule.builder()
            .addResource(new HealthResource())
            .build();


    @Test
    public void testGetHealth() {
        assertThat(resources.target("/health").request().get(String.class))
                .isEqualTo(message);
    }

}