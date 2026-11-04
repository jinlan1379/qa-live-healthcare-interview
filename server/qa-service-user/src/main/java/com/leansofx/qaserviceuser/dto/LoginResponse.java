package com.leansofx.qaserviceuser.dto;

public record LoginResponse(boolean success, String message, Object user) {}
