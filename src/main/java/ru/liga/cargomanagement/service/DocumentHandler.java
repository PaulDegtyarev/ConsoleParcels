package ru.liga.cargomanagement.service;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

public interface DocumentHandler {

    String handle(File file, Message message) throws TelegramApiException;
}
