package com.gamingtec.services.provider.route;

import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.MockEndpointsAndSkip;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@CamelSpringBootTest
@MockEndpointsAndSkip("direct:end")
class MyTestRoutesTest {
  @Autowired
  private ProducerTemplate producerTemplate;
  @EndpointInject("mock:direct:end")
  private MockEndpoint mock;

  @Test
  void whenSendBody_thenMessageIsForwardedCorrectly() throws InterruptedException {
    // Set up the mock endpoint to expect a specific message
    mock.expectedBodiesReceived("test-message");

    // Send a test message to direct:start
    producerTemplate.sendBody("direct:start", "test-message");

    // Verify the mock endpoint received the message
    mock.assertIsSatisfied();
  }
}
