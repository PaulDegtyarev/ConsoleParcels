package ru.liga.consoleParcels.telegramBot;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.consoleParcels.dto.PackRequestDto;
import ru.liga.consoleParcels.model.UserAlgorithmChoice;
import ru.liga.consoleParcels.service.PackagingManager;
import ru.liga.consoleParcels.service.UnPackagingManager;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Log4j2
public class CargoManagementBot extends TelegramLongPollingBot {
    private final PackagingManager packagingManager;
    private final UnPackagingManager unPackagingManager;

    private Map<Long, List<File>> userFilesMap = new HashMap<>();

    @Autowired
    public CargoManagementBot(PackagingManager packagingManager, UnPackagingManager unPackagingManager) {
        this.packagingManager = packagingManager;
        this.unPackagingManager = unPackagingManager;
    }

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
        if (update.hasMessage() && update.getMessage().hasText()) {
            handleTextMessage(update);
        } else if (update.hasMessage() && update.getMessage().hasDocument()) {
            handleDocumentMessage(update);
        }
    }

    private void handleTextMessage(Update update) {
        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        if (messageText.equalsIgnoreCase("/start")) {
            sendMsg(chatId, "Привет! Я помогу тебе упаковать и распаковать грузы. Отправь мне файл с данными.");
        } else if (messageText.equalsIgnoreCase("/help")) {
            sendMsg(chatId, "Используй команды /pack и /unpack для упаковки и распаковки. Отправь мне файлы с данными.");
        } else if (messageText.startsWith("/pack")) {
            handlePackCommand(chatId, messageText);
        }
    }

    private void handlePackCommand(long chatId, String messageText) {
        final int INDEX_OF_FIRST_CHARACTER_OF_COMMAND_IN_MESSAGE = 6;
        String commandInMessage = messageText.substring(INDEX_OF_FIRST_CHARACTER_OF_COMMAND_IN_MESSAGE);

        final int CORRECT_COMMAND_LENGTH_FOR_PACKAGING = 4;
        final int POSITION_OF_TRUCKS_IN_MESSAGE = 0;
        final int POSITION_OF_INPUT_DATA_IN_MESSAGE = 1;
        final int POSITION_OF_ALGORITHM_CHOICE_IN_MESSAGE = 2;
        final int POSITION_OF_OUTPUT_FILEPATH_IN_MESSAGE = 3;

        try {
            String[] parts = commandInMessage.split("; ");
            if (parts.length != CORRECT_COMMAND_LENGTH_FOR_PACKAGING) {
                sendMsg(chatId, "Неправильный формат команды. Пожалуйста, отправьте команду в формате '/pack 6x6 MAX_SPACE output_filepath.json; данные'.");
                return;
            }

            String trucks = parts[POSITION_OF_TRUCKS_IN_MESSAGE];
            String inputData = parts[POSITION_OF_INPUT_DATA_IN_MESSAGE];
            UserAlgorithmChoice algorithmChoice = UserAlgorithmChoice.valueOf(parts[POSITION_OF_ALGORITHM_CHOICE_IN_MESSAGE]);
            String filePathToWrite = parts[POSITION_OF_OUTPUT_FILEPATH_IN_MESSAGE];

            PackRequestDto packRequestDto = new PackRequestDto(trucks, inputData, algorithmChoice, filePathToWrite);
            String result = packagingManager.packParcels(packRequestDto);
            sendMsg(chatId, result);
        } catch (Exception e) {
            log.error("Ошибка при упаковке", e);
            sendMsg(chatId, "Произошла ошибка при упаковке. Попробуйте снова.");
        }
    }

    private void handleDocumentMessage(Update update) {
        try {
            Message message = update.getMessage();
            long chatId = message.getChatId();

            org.telegram.telegrambots.meta.api.objects.File telegramFile = execute(new GetFile(message.getDocument().getFileId()));
            File file = downloadFile(telegramFile);

            log.info("Путь к файлу: {}", file.getAbsolutePath());
            if (message.getDocument().getFileName().endsWith(".txt")) {
                String caption = message.getCaption();
                if (caption == null || caption.isEmpty()) {
                    sendMsg(chatId, "К текстовому файлу должна быть добавлена подпись с параметрами упаковки.");
                    return;
                }
                processTextFile(chatId, file, caption);
            } else {
                List<File> files = userFilesMap.getOrDefault(chatId, new ArrayList<>());
                files.add(file);
                userFilesMap.put(chatId, files);

                if (files.size() == 2) {
                    processUnpackFiles(chatId, files.get(0), files.get(1));
                    userFilesMap.remove(chatId);
                } else {
                    sendMsg(chatId, "Файл получен. Отправьте второй файл.");
                }
            }
        } catch (TelegramApiException e) {
            log.error("Ошибка при обработке документа", e);
        }
    }

    private void processTextFile(long chatId, File file, String messageText) {
        final int CORRECT_COMMAND_LENGTH_FOR_PACKAGING = 3;
        final int POSITION_OF_TRUCKS_IN_MESSAGE = 0;
        final int POSITION_OF_ALGORITHM_CHOICE_IN_MESSAGE = 1;
        final int POSITION_OF_OUTPUT_FILEPATH_IN_MESSAGE = 2;

        try {
            String[] parts = messageText.split("; ");
            if (parts.length != CORRECT_COMMAND_LENGTH_FOR_PACKAGING) {
                sendMsg(chatId, "Неправильный формат сообщения. Пожалуйста, отправьте сообщение в формате '6x6' MAX_SPACE 'output_filepath.json'.");
                return;
            }

            String trucks = parts[POSITION_OF_TRUCKS_IN_MESSAGE];
            UserAlgorithmChoice algorithmChoice = UserAlgorithmChoice.valueOf(parts[POSITION_OF_ALGORITHM_CHOICE_IN_MESSAGE]);
            String filePathToWrite = parts[POSITION_OF_OUTPUT_FILEPATH_IN_MESSAGE];
            String inputFilePath = file.getAbsolutePath();

            String result = packagingManager.packParcels(new PackRequestDto(trucks, inputFilePath, algorithmChoice, filePathToWrite));
            sendMsg(chatId, result);
        } catch (Exception e) {
            log.error("Ошибка при упаковке", e);
            sendMsg(chatId, "Произошла ошибка при упаковке. Попробуйте снова.");
        }
    }

    private void processUnpackFiles(long chatId, File firstFile, File secondFile) {
        try {
            String truckFilePath = firstFile.getAbsolutePath();
            String parcelCountFilePath = secondFile.getAbsolutePath();

            String result = unPackagingManager.unpackParcels(truckFilePath, parcelCountFilePath);
            sendMsg(chatId, result);

            sendMsg(chatId, "Распаковка завершена. Результат отправлен.");
        } catch (Exception e) {
            log.error("Ошибка при распаковке", e);
            sendMsg(chatId, "Произошла ошибка при распаковке. Попробуйте снова.");
        }
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
