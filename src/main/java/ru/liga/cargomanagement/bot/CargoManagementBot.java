package ru.liga.cargomanagement.bot;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.cargomanagement.dto.enums.MessageType;
import ru.liga.cargomanagement.service.DocumentHandler;
import ru.liga.cargomanagement.service.TextHandler;

import java.io.File;
import java.util.Map;

/**
 * Класс, реализующий бота для управления грузами.
 * Использует Telegram API для обработки входящих сообщений и команд.
 */
@Component
@Log4j2
@RequiredArgsConstructor
public class CargoManagementBot extends TelegramLongPollingBot {
    private final Map<MessageType, DocumentHandler> messageHandlerMap;
    private final TextHandler textHandler;

    @Override
    public String getBotUsername() {
        return "CargoManagement_bot";
    }

    @Override
    public String getBotToken() {
        return "7840162814:AAH2J4UXH9YRySM_g5Fo1np_TzNf85-3sxM";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        long chatId = message.getChatId();

        messageHandlerMap.keySet()
                .stream()
                .filter(type -> type.matches(message))
                .findFirst()
                .ifPresentOrElse(
                        type -> {
                            try {
                                Document messageDocument = message.getDocument();

                                org.telegram.telegrambots.meta.api.objects.File telegramFile = execute(new GetFile(messageDocument.getFileId()));
                                File file = downloadFile(telegramFile);

                                String response = messageHandlerMap.get(type)
                                        .handle(file, message);

                                sendMsg(chatId, response);
                            } catch (Exception e) {
                                sendMsg(chatId, e.getMessage());
                            }
                        },
                        () -> {
                            try {
                                String response = textHandler.handle(message);

                                sendMsg(chatId, response);
                            } catch (Exception e) {
                                sendMsg(chatId, e.getMessage());
                            }
                        }
                );
    }

    private void sendMsg(long chatId, String textToSend) {
        SendMessage msg = new SendMessage();
        msg.setChatId(String.valueOf(chatId));
        msg.setText(textToSend);

        try {
            execute(msg);
        } catch (TelegramApiException e) {
            log.error("Ошибка при отправке сообщения", e);
        }
    }
}