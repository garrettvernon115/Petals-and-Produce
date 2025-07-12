package com.petalsandproduce.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/session")
public class SessionController {
    // I got all of this from GeeksForGeeks with some slight tweaking

    // Create a session and store an attribute
    @GetMapping("/create")
    public String createSession(HttpSession session) {
        // Create a guest with the session ID as it's ID
        String sessionId = session.getId();
        session.setAttribute("guest", session.getId());
        return "Session created with ID: " + sessionId;
    }

    // Retrieve session attribute
    @GetMapping("/get")
    public String getSession(HttpSession session) {
        String sessionId = (String) session.getAttribute("guest");
        // If no session exists, return an error message
        if (sessionId == null) {
            return "No session found!";
        }
        // Return session data to the client
        return "Session found for guest with the ID: " + sessionId;
    }

    // Invalidate the session
    @GetMapping("/invalidate")
    public String invalidateSession(HttpSession session) {
        // Invalidate the session
        session.invalidate();
        return "Session invalidated!";
    }
}