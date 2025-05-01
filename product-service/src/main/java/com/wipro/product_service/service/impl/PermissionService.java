package com.wipro.product_service.service.impl;

import com.wipro.product_service.client.AuthServiceClient;
import com.wipro.product_service.exception.PermissionDeniedException;
import com.wipro.product_service.util.AppConstant;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissionService {

    @Autowired
    AuthServiceClient authServiceClient;

    public boolean hasPermission(HttpServletRequest request, String action) throws PermissionDeniedException{
        String authHeader = request.getHeader(AppConstant.AUTH_HEADER);
        try {
            return authServiceClient.hasPermission(authHeader, action);
        }catch (Exception e){
            return false;
        }
    }
}