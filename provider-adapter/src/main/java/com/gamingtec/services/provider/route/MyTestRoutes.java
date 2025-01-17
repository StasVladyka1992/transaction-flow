package com.gamingtec.services.provider.route;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class MyTestRoutes extends RouteBuilder {
//  private static final Logger LOG = w;

  @Override
  public void configure() {
    from("direct:start")
        .to("direct:end");
  }
}
