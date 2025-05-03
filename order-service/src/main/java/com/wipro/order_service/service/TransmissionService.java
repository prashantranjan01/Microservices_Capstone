package com.wipro.order_service.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wipro.order_service.dto.APIResponse;
import com.wipro.order_service.exception.APIException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class TransmissionService {

    private final ObjectMapper mapper = new ObjectMapper();

    public <T> T sendAndReceive(ResponseEntity<?> responseEntity, TypeReference<APIResponse<T>> typeRef) {
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new APIException("Service failed with status: " + responseEntity.getStatusCode());
        }

        APIResponse<T> apiResponse = mapper.convertValue(responseEntity.getBody(), typeRef);

        if (apiResponse.getData() == null) {
            throw new APIException("Failed to get data from service.");
        }

        return apiResponse.getData();
    }
}