//package com.techsophy.tsf.commons.utils;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.techsophy.tsf.commons.exception.ExternalServiceErrorException;
//import lombok.AllArgsConstructor;
//import org.junit.jupiter.api.MethodOrderer;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import java.util.ArrayList;
//import java.util.List;
//
//import static com.techsophy.tsf.commons.CommonConstants.DELETE;
//import static com.techsophy.tsf.commons.CommonConstants.TEST_ACTIVE_PROFILE;
//import static com.techsophy.tsf.commons.constants.CommonConstants.*;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@ExtendWith({MockitoExtension.class})
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@ActiveProfiles(TEST_ACTIVE_PROFILE)
//@AllArgsConstructor
//class WebClientWrapperTest extends TestSuite
//{
//    @Mock
//    Exception exception;
//
//    @Test
//    void getWebClientRequestTest() {
//        assertThrows(ExternalServiceErrorException.class,()->webClientWrapper1.webclientRequest("/v1/notification",GET,null));
//    }
//
//    @Test
//    void posttWebClientRequestTest() {
//        assertThrows(ExternalServiceErrorException.class,()->webClientWrapper1.webclientRequest("/v1/notification",POST,"abc"));
//    }
//
////    @Test
////    void delete() {
////       webClientWrapper1.webclientRequest("/v1/notification",DELETE,"abc");
////    }
////    @Test
////    void deletedataNull() {
////       webClientWrapper1.webclientRequest("/v1/notification",DELETE,null);
////    }
////    @Test
////    void put() {
////       webClientWrapper1.webclientRequest("/v1/notification",PUT,"abc");
////    }
////    @Test
////    void availableMethod() throws JsonProcessingException {
////        webClientWrapper1.availableMethod(exception);
////    }
//}
