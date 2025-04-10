package com.exe.smartcontact.service;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {

    public void removeMessageFromSession() {
        try {
            System.out.println("removing message from session");
            HttpSession se = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                             .getRequest().getSession();
            se.removeAttribute("message");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
