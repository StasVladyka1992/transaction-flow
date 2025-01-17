package com.gamingtec.services.provider.route;

//@SpringBootTest
//@CamelSpringBootTest
//@MockEndpointsAndSkip(
/// /    "rest:post:balance," +
//    "direct:grpcBalance")
/// /    "?inType: &outType })
//public class RestWalletRoutesTest {
//  @Autowired
//  private ProducerTemplate producerTemplate;
//  @Autowired
//  private ObjectMapper objectMapper;
////  @Autowired
////  private MockMvc mockMvc;
//
//  //  @EndpointInject("mock:rest:post:balance")
////  private MockEndpoint restMock;
////  @EndpointInject("mock:direct:grpcBalance")
////  private MockEndpoint grpcMock;
//
//
//  @Test
//  void balance_bodyConvertedToJson() throws Exception {
////    mock.expectedBodiesReceived("test-message");
//
//    var req = BalanceReqDto.builder()
//        .partyId(1)
//        .brandId(2)
//        .gameId("3")
//        .platformCode("platformCode")
//        .playerCurrency("USD")
//        .numDecimalParts(1)
//        .build();
//
//    var reqJson = objectMapper.writeValueAsString(req);
////
////    mockMvc.perform(MockMvcRequestBuilders.post("/api/user/create")
////            .contentType("application/json")
////            .content(reqJson))
////        .andExpect(MockMvcResultMatchers.status().isOk());
//////        .andExpect(MockMvcResultMatchers.content().string("User created successfully"));
//
//
//    // Send a test message to direct:start
//    producerTemplate.sendBody("rest:post:balance:", "test-message");
//
//    // Verify the mock endpoint received the message
////    mock.assertIsSatisfied();
//  }
//}
