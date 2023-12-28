package com.example.Registation.Service;

public interface IEmailService {
    public boolean sendSimpleEmail(String to, String subject, String content);
}
