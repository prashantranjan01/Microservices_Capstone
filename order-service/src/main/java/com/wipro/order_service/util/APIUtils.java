package com.wipro.order_service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.order_service.dto.APIResponse;
import com.wipro.order_service.dto.UserDTO;
import com.wipro.order_service.exception.APIException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

//class TransmissionOperation<T>{
//
//}
//
public class APIUtils {
//
//    public void getData(){
//        ObjectMapper mapper = new ObjectMapper();
//        ResponseEntity<?> responseEntity = authServiceClient.getUserByIdOrUsername(getCurrentUserId() , null);
//
//        if (responseEntity.getStatusCode() != HttpStatus.OK) {
//            throw new APIException("Failed to get user from auth microservice.");
//        }
//        APIResponse<?> apiResponse = mapper.convertValue(responseEntity.getBody(), APIResponse.class);
//
//        if (apiResponse.getData() == null) {
//            throw new APIException("Failed to get user from auth microservice.");
//        }
//
//        UserDTO user = mapper.convertValue(apiResponse.getData(), UserDTO.class);
//    }
}
