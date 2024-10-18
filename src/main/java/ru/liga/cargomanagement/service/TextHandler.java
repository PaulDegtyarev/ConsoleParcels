package ru.liga.cargomanagement.service;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface TextHandler {
    String handle(Message message);
}
