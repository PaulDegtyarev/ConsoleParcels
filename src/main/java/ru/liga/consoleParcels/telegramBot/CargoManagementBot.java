package ru.liga.consoleParcels.telegramBot;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Document;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.consoleParcels.dto.PackRequestDto;
import ru.liga.consoleParcels.dto.ParcelRequestDto;
import ru.liga.consoleParcels.dto.ParcelResponseDto;
import ru.liga.consoleParcels.model.UserAlgorithmChoice;
import ru.liga.consoleParcels.service.PackagingManager;
import ru.liga.consoleParcels.service.ParcelService;
import ru.liga.consoleParcels.service.UnPackagingManager;

import java.io.File;

@Component
@Log4j2
public class CargoManagementBot extends TelegramLongPollingBot {
    private final PackagingManager packagingManager;
    private final UnPackagingManager unPackagingManager;
    private final ParcelService parcelService;

    @Autowired
    public CargoManagementBot(PackagingManager packagingManager, UnPackagingManager unPackagingManager, ParcelService parcelService) {
        this.packagingManager = packagingManager;
        this.unPackagingManager = unPackagingManager;
        this.parcelService = parcelService;
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

        switch (messageText.split(" ")[0]) {
            case "/start":
                sendMsg(chatId, "Привет! Я помогу тебе упаковать и распаковать грузы. Отправь мне файл с данными.");
                break;
            case "/help":
                sendMsg(chatId, "Используй команды /pack и /unpack для упаковки и распаковки. Отправь мне файлы с данными.");
                break;
            case "/pack":
                handlePackCommand(chatId, messageText);
                break;
            case "/allParcels":
                handleAllParcelsCommand(chatId);
                break;
            case "/findParcelByName":
                handleFindParcelByNameCommand(chatId, messageText);
                break;
            case "/addParcel":
                handleAddParcelCommand(chatId, messageText);
                break;
            case "/updateParcelByName":
                handleUpdateParcelByNameCommand(chatId, messageText);
                break;
            case "/updateSymbolByParcelName":
                handleUpdateSymbolByParcelNameCommand(chatId, messageText);
                break;
            case "/updateShapeByParcelName":
                handleUpdateShapeByParcelNameCommand(chatId, messageText);
                break;
            case "/deleteParcelByParcelName":
                handleDeleteParcelByParcelNameCommand(chatId, messageText);
                break;
            default:
                sendMsg(chatId, "Неизвестная команда. Используйте /help для получения списка команд.");
                break;
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
            Document messageDocument = message.getDocument();

            org.telegram.telegrambots.meta.api.objects.File telegramFile = execute(new GetFile(messageDocument.getFileId()));
            File file = downloadFile(telegramFile);

            log.info("Путь к файлу: {}", file.getAbsolutePath());
            if (messageDocument.getFileName().endsWith(".txt")) {
                String caption = message.getCaption();
                if (caption == null || caption.isEmpty()) {
                    sendMsg(chatId, "К текстовому файлу должна быть добавлена подпись с параметрами упаковки.");
                    return;
                }
                processTextFile(chatId, file, caption);
            } else if (messageDocument.getFileName().endsWith(".json")) {
                processUnpackFiles(chatId, file);
            } else {
                sendMsg(chatId, "Файл получен. Отправьте второй файл.");
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

    private void processUnpackFiles(long chatId, File firstFile) {
        try {
            String truckFilePath = firstFile.getAbsolutePath();

            String result = unPackagingManager.unpackParcels(truckFilePath);
            sendMsg(chatId, result);
        } catch (Exception e) {
            log.error("Ошибка при распаковке", e);
        }
    }

    private void handleAllParcelsCommand(long chatId) {
        try {
            String allParcels = parcelService.findAllParcels();
            sendMsg(chatId, allParcels);
        } catch (Exception e) {
            log.error("Ошибка при получении всех посылок", e);
            sendMsg(chatId, "Ошибка при получении всех посылок. Попробуйте снова.");
        }
    }

    private void handleFindParcelByNameCommand(long chatId, String messageText) {
        messageText = messageText.substring(18);
        try {
            String[] parts = messageText.split(" ");
            if (parts.length < 1) {
                sendMsg(chatId, "Пожалуйста, укажите имя посылки. Формат: /findParcelByName <имя>");
                return;
            }
            String parcelName = parts[0];
            ParcelResponseDto parcel = parcelService.findParcelByName(parcelName);
            sendMsg(chatId, parcel.toString());
        } catch (Exception e) {
            log.error("Ошибка при поиске посылки", e);
            sendMsg(chatId, "Ошибка при поиске посылки. Попробуйте снова.");
        }
    }

    private void handleAddParcelCommand(long chatId, String messageText) {
        messageText = messageText.substring(11);
        try {
            ParcelRequestDto parcelRequestDto = parseParcelRequestDto(messageText);
            ParcelResponseDto addedParcel = parcelService.addParcel(parcelRequestDto);
            sendMsg(chatId, "Посылка добавлена: " + addedParcel.toString());
        } catch (Exception e) {
            log.error("Ошибка при добавлении посылки", e);
            sendMsg(chatId, "Ошибка при добавлении посылки. Попробуйте снова.");
        }
    }

    private void handleUpdateParcelByNameCommand(long chatId, String messageText) {
        messageText = messageText.substring(20);
        try {
            ParcelRequestDto parcelRequestDto = parseParcelRequestDto(messageText);
            ParcelResponseDto updatedParcel = parcelService.updateParcelByName(parcelRequestDto);
            sendMsg(chatId, "Посылка обновлена: " + updatedParcel.toString());
        } catch (Exception e) {
            log.error("Ошибка при обновлении посылки", e);
            sendMsg(chatId, "Ошибка при обновлении посылки. Попробуйте снова.");
        }
    }

    private void handleUpdateSymbolByParcelNameCommand(long chatId, String messageText) {
        messageText = messageText.substring(26);
        try {
            String[] parts = messageText.split("; ");
            if (parts.length < 2) {
                sendMsg(chatId, "Пожалуйста, укажите имя и новый символ. Формат: /updateSymbolByParcelName <имя> <новый символ>");
                return;
            }
            String parcelName = parts[0];
            char newSymbol = parts[1].charAt(0);
            ParcelResponseDto updatedParcel = parcelService.updateSymbolByParcelName(parcelName, newSymbol);
            sendMsg(chatId, "Символ посылки обновлен: " + updatedParcel.toString());
        } catch (Exception e) {
            log.error("Ошибка при обновлении символа посылки", e);
            sendMsg(chatId, "Ошибка при обновлении символа посылки. Попробуйте снова.");
        }
    }

    private void handleUpdateShapeByParcelNameCommand(long chatId, String messageText) {
        messageText = messageText.substring(25);
        try {
            String[] parts = messageText.split("; ");
            if (parts.length < 2) {
                sendMsg(chatId, "Пожалуйста, укажите имя и новую форму. Формат: /updateShapeByParcelName <имя> <новая форма>");
                return;
            }
            String parcelName = parts[0];
            String newShape = parts[1];
            ParcelResponseDto updatedParcel = parcelService.updateShapeByParcelName(parcelName, newShape);
            sendMsg(chatId, "Форма посылки обновлена: " + updatedParcel.toString());
        } catch (Exception e) {
            log.error("Ошибка при обновлении формы посылки", e);
            sendMsg(chatId, "Ошибка при обновлении формы посылки. Попробуйте снова.");
        }
    }

    private void handleDeleteParcelByParcelNameCommand(long chatId, String messageText) {
        messageText = messageText.substring(26);
        try {
            String[] parts = messageText.split("; ");
            if (parts.length < 1) {
                sendMsg(chatId, "Пожалуйста, укажите имя посылки для удаления. Формат: /deleteParcelByParcelName <имя>");
                return;
            }
            String parcelName = parts[0];
            parcelService.deleteParcelByParcelName(parcelName);
            sendMsg(chatId, "Посылка удалена: " + parcelName);
        } catch (Exception e) {
            log.error("Ошибка при удалении посылки", e);
            sendMsg(chatId, "Ошибка при удалении посылки. Попробуйте снова.");
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

    private ParcelRequestDto parseParcelRequestDto(String data) {
        String[] parts = data.split("; ");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Неправильный формат данных для посылки");
        }
        String name = parts[0].trim();
        String shape = parts[1].trim();
        char symbol = parts[2].charAt(0);
        return new ParcelRequestDto(name, shape, symbol);
    }
}