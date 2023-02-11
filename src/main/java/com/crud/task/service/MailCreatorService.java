package com.crud.task.service;

import com.crud.task.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;

@Service
public class MailCreatorService {

    @Autowired
    @Qualifier("templateEngine")
    TemplateEngine templateEngine;

    @Autowired
    AdminConfig adminConfig;

    public String buildTrelloCardMail(String message) {
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your task");
        functionality.add("Provides connection with Trello account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "https://trello.com/login/");
        context.setVariable("button", "Visit website Trello.com");
        context.setVariable("admin_config", adminConfig);
        context.setVariable("show_button", true);
        context.setVariable("is_friend", false);
        context.setVariable("application_functionality", functionality);
        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildScheduledMail(String message) {
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage your task");
        functionality.add("Provides connection with Trello account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/tasks_frontend/");
        context.setVariable("button", "Visit website TASKS CRUD");
        context.setVariable("admin_config", adminConfig);
        context.setVariable("show_button", true);
        context.setVariable("is_friend", true);
        context.setVariable("application_functionality", functionality);

        return templateEngine.process("mail/scheduled-mail", context);
    }
}