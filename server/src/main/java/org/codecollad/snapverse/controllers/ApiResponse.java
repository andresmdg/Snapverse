package org.codecollad.snapverse.controllers;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
  private boolean success;
  private int status;
  private String message;
  private String token;
  private List<T> data;

  // Constructor sin token (para respuestas sin token)
  public ApiResponse(boolean success, int status, String message) {
    this.success = success;
    this.status = status;
    this.message = message;
  }

  // Constructor con token (para respuestas que incluyan un token)
  public ApiResponse(boolean success, int status, String message, List<T> data) {
    this.success = success;
    this.status = status;
    this.message = message;
    this.data = data;
  }

  // Getters y Setters
  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public List<T> getData() {
    return data;
  }

  public void setData(List<T> data) {
    this.data = data;
  }
}