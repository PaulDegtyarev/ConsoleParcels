package ru.liga.cargomanagement.service.impl.command;

import org.springframework.stereotype.Service;
import ru.liga.cargomanagement.service.BotCommand;

@Service
public class StartCommand implements BotCommand {
    @Override
    public String execute(String messageText) {
        return """
                Привет! Я помогу тебе упаковать и распаковать грузы, а так же просмотреть, добавить, редактировать и удалить посылку.
                """;
    }
}
