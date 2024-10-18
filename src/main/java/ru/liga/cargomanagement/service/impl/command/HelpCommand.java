package ru.liga.cargomanagement.service.impl.command;

import org.springframework.stereotype.Service;
import ru.liga.cargomanagement.service.BotCommand;

@Service
public class HelpCommand implements BotCommand {
    @Override
    public String execute(String messageText) {
        return """
                Используй команды /pack и /unpack для упаковки и распаковки;
                /all - просмотр все имеющихся посылок;
                /findByName - поиск посылки по названию;
                /add - добавить посылку;
                /updateByName - редактировать форму и символ посылки по ее названию;
                /updateSymbolByName - обновить символ посылки по ее названию;
                /updateShapeByName - обновить форму по названию;
                /deleteByName - удалить посылку по названию.
                """;
    }
}
