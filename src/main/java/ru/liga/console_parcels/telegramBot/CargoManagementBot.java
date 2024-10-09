package ru.liga.console_parcels.telegramBot;

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
import ru.liga.console_parcels.dto.*;
import ru.liga.console_parcels.entity.Truck;
import ru.liga.console_parcels.formatter.ResultFormatter;
import ru.liga.console_parcels.service.PackagingManager;
import ru.liga.console_parcels.service.ParcelService;
import ru.liga.console_parcels.service.TruckParcelsUnpackingService;

import java.io.File;
import java.util.List;

/**
 * Класс, реализующий бота для управления грузами.
 * Использует Telegram API для обработки входящих сообщений и команд.
 */
@Component
@Log4j2
@RequiredArgsConstructor
public class CargoManagementBot extends TelegramLongPollingBot {
    private final PackagingManager packagingManager;
    private final TruckParcelsUnpackingService truckParcelsUnpackingService;
    private final ParcelService parcelService;
    private final ResultFormatter resultFormatter;

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
        int indexOfFirstWord = 0;
        String messageText = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        switch (messageText.split(" ")[indexOfFirstWord]) {
            case "/start":
                sendMsg(chatId, """
                        Привет! Я помогу тебе упаковать и распаковать грузы, а так же просмотреть, добавить, редактировать и удалить посылку.
                        """);
                break;
            case "/help":
                sendMsg(chatId, """
                        Используй команды /pack и /unpack для упаковки и распаковки;
                        /all - просмотр все имеющихся посылок;
                        /findByName - поиск посылки по названию;
                        /add - добавить посылку;
                        /updateByName - редактировать форму и символ посылки по ее названию;
                        /updateSymbolByName - обновить символ посылки по ее названию;
                        /updateShapeByName - обновить форму по названию;
                        /deleteByName - удалить посылку по названию.
                        """);
                break;
            case "/pack":
                handlePackCommand(chatId, messageText);
                break;
            case "/all":
                handleAllParcelsCommand(chatId);
                break;
            case "/findByName":
                handleFindParcelByNameCommand(chatId, messageText);
                break;
            case "/add":
                handleAddParcelCommand(chatId, messageText);
                break;
            case "/updateByName":
                handleUpdateParcelByNameCommand(chatId, messageText);
                break;
            case "/updateSymbolByName":
                handleUpdateSymbolByParcelNameCommand(chatId, messageText);
                break;
            case "/updateShapeByName":
                handleUpdateShapeByParcelNameCommand(chatId, messageText);
                break;
            case "/deleteByName":
                handleDeleteParcelByParcelNameCommand(chatId, messageText);
                break;
            default:
                sendMsg(chatId, "Неизвестная команда. Используйте /help для получения списка команд.");
                break;
        }
    }

    private void handlePackCommand(long chatId, String messageText) {
        int indexOfFirstCharacterOfCommand = 6;
        String commandInMessage = messageText.substring(indexOfFirstCharacterOfCommand);

        int correctCommandLength = 4;
        int indexOfTrucks = 0;
        int indexOfInputData = 1;
        int indexOfPackageAlgorithm = 2;
        int indexOfOutputFilepath = 3;

        try {
            String[] parts = commandInMessage.split("; ");
            if (parts.length != correctCommandLength) {
                sendMsg(chatId, "Неправильный формат команды. Пожалуйста, отправьте команду в формате '/pack 6x6; входные данные; MAX_SPACE; output_filepath.json; данные'.");
                return;
            }

            String trucks = parts[indexOfTrucks];
            String inputData = parts[indexOfInputData];
            TruckPackageAlgorithm algorithmChoice = TruckPackageAlgorithm.valueOf(parts[indexOfPackageAlgorithm]);
            String filePathToWrite = parts[indexOfOutputFilepath];

            PackRequestDto packRequestDto = new PackRequestDto(trucks, inputData, algorithmChoice, filePathToWrite);

            List<Truck> packedTrucks = packagingManager.pack(packRequestDto);

            String result = resultFormatter.convertPackagingResultsToString(packedTrucks);

            sendMsg(chatId, result);
        } catch (Exception e) {
            sendMsg(chatId, e.getMessage());
        }
    }

    private void handleDocumentMessage(Update update) {
        Message message = update.getMessage();
        long chatId = message.getChatId();
        Document messageDocument = message.getDocument();

        try {
            org.telegram.telegrambots.meta.api.objects.File telegramFile = execute(new GetFile(messageDocument.getFileId()));
            File file = downloadFile(telegramFile);

            log.info("Путь к файлу: {}", file.getAbsolutePath());
            String fileName = messageDocument.getFileName();
            if (fileName.endsWith(".txt")) {
                String caption = message.getCaption();
                if (caption == null || caption.isEmpty()) {
                    sendMsg(chatId, "К текстовому файлу должна быть добавлена подпись с параметрами упаковки.");
                    return;
                }
                processTextFile(chatId, file, caption);
            } else if (fileName.endsWith(".json")) {
                processUnpackFiles(chatId, file);
            }
        } catch (Exception e) {
            sendMsg(chatId, e.getMessage());
        }
    }

    private void processTextFile(long chatId, File file, String messageText) {
        int correctCommandLength = 3;
        int indexOfTrucks = 0;
        int indexOfPackageAlgorithm = 1;
        int indexOfOutputFilepath = 2;

        try {
            String[] parts = messageText.split("; ");
            if (parts.length != correctCommandLength) {
                sendMsg(chatId, "Неправильный формат сообщения. Пожалуйста, отправьте сообщение в формате 6x6; MAX_SPACE; output_filepath.json.");
                return;
            }

            String trucks = parts[indexOfTrucks];
            TruckPackageAlgorithm algorithmChoice = TruckPackageAlgorithm.valueOf(parts[indexOfPackageAlgorithm]);
            String filePathToWrite = parts[indexOfOutputFilepath];
            String inputFilePath = file.getAbsolutePath();

            List<Truck> packedTrucks = packagingManager.pack(new PackRequestDto(trucks, inputFilePath, algorithmChoice, filePathToWrite));

            String result = resultFormatter.convertPackagingResultsToString(packedTrucks);

            sendMsg(chatId, result);
        } catch (Exception e) {
            sendMsg(chatId, e.getMessage());
        }
    }

    private void processUnpackFiles(long chatId, File firstFile) {
        try {
            String truckFilePath = firstFile.getAbsolutePath();

            List<UnpackedTruckDto> unpackedTruck = truckParcelsUnpackingService.unpack(truckFilePath);

            String result = resultFormatter.convertUnpackingResultsToString(unpackedTruck);

            sendMsg(chatId, result);
        } catch (Exception e) {
            sendMsg(chatId, e.getMessage());
        }
    }

    private void handleAllParcelsCommand(long chatId) {
        try {
            String allParcels = parcelService.findAllParcels().toString();
            sendMsg(chatId, allParcels);
        } catch (Exception e) {
            sendMsg(chatId, e.getMessage());
        }
    }

    private void handleFindParcelByNameCommand(long chatId, String messageText) {
        int indexOfFirstCharOfInputData = 18;
        int validPartsLength = 1;
        int indexOfParcelName = 0;
        messageText = messageText.substring(indexOfFirstCharOfInputData);
        try {
            String[] parts = messageText.split(" ");
            if (parts.length != validPartsLength) {
                sendMsg(chatId, "Пожалуйста, укажите имя посылки. Формат: /findByName имя");
                return;
            }
            String parcelName = parts[indexOfParcelName];
            ParcelResponseDto parcel = parcelService.findParcelByName(parcelName);
            sendMsg(chatId, parcel.toString());
        } catch (Exception e) {
            sendMsg(chatId, e.getMessage());
        }
    }

    private void handleAddParcelCommand(long chatId, String messageText) {
        int indexOfFirstCharOfInputData = 11;
        messageText = messageText.substring(indexOfFirstCharOfInputData);
        try {
            ParcelRequestDto parcelRequestDto = parseParcelRequestDto(messageText);
            ParcelResponseDto addedParcel = parcelService.addParcel(parcelRequestDto);
            sendMsg(chatId, "Посылка добавлена:%n" + addedParcel.toString());
        } catch (Exception e) {
            sendMsg(chatId, e.getMessage());
        }
    }

    private void handleUpdateParcelByNameCommand(long chatId, String messageText) {
        int indexOfFirstCharOfInputData = 20;
        messageText = messageText.substring(indexOfFirstCharOfInputData);
        try {
            ParcelRequestDto parcelRequestDto = parseParcelRequestDto(messageText);
            ParcelResponseDto updatedParcel = parcelService.updateByName(parcelRequestDto);
            sendMsg(chatId, "Посылка обновлена:%n" + updatedParcel.toString());
        } catch (Exception e) {
            sendMsg(chatId, e.getMessage());
        }
    }

    private void handleUpdateSymbolByParcelNameCommand(long chatId, String messageText) {
        int indexOfFirstCharOfInputData = 26;
        int validPartsLength = 2;
        int indexOfName = 0;
        int indexOfSymbol = 1;
        int symbolPosition = 0;

        messageText = messageText.substring(indexOfFirstCharOfInputData);
        try {
            String[] parts = messageText.split("; ");
            if (parts.length != validPartsLength) {
                sendMsg(chatId, "Пожалуйста, укажите имя и новый символ. Формат: /updateSymbolByName имя новый символ");
                return;
            }

            String parcelName = parts[indexOfName];
            char newSymbol = parts[indexOfSymbol].charAt(symbolPosition);
            ParcelResponseDto updatedParcel = parcelService.updateSymbolByParcelName(parcelName, newSymbol);

            sendMsg(chatId, "Символ посылки обновлен:%n" + updatedParcel.toString());
        } catch (Exception e) {
            sendMsg(chatId, e.getMessage());
        }
    }

    private void handleUpdateShapeByParcelNameCommand(long chatId, String messageText) {
        int indexOfFirstCharOfInputData = 25;
        int validPartsLength = 2;
        int indexOfName = 0;
        int indexOfShape = 1;

        messageText = messageText.substring(indexOfFirstCharOfInputData);
        try {
            String[] parts = messageText.split("; ");
            if (parts.length < validPartsLength) {
                sendMsg(chatId, "Пожалуйста, укажите имя и новую форму. Формат: /updateShapeByName имя новая форма");
                return;
            }
            String parcelName = parts[indexOfName];
            String newShape = parts[indexOfShape];
            ParcelResponseDto updatedParcel = parcelService.updateShapeByParcelName(parcelName, newShape);
            sendMsg(chatId, "Форма посылки обновлена:%n" + updatedParcel.toString());
        } catch (Exception e) {
            sendMsg(chatId, e.getMessage());
        }
    }

    private void handleDeleteParcelByParcelNameCommand(long chatId, String messageText) {
        int indexOfFirstCharOfInputData = 26;
        int validPartsLength = 1;
        int indexOfName = 0;

        messageText = messageText.substring(indexOfFirstCharOfInputData);
        try {
            String[] parts = messageText.split("; ");
            if (parts.length < validPartsLength) {
                sendMsg(chatId, "Пожалуйста, укажите имя посылки для удаления. Формат: /deleteByName имя");
                return;
            }
            String parcelName = parts[indexOfName];
            parcelService.deleteParcelByParcelName(parcelName);
            sendMsg(chatId, "Посылка удалена: " + parcelName);
        } catch (Exception e) {
            sendMsg(chatId, e.getMessage());
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
        int validPartsLength = 3;
        int indexOfName = 0;
        int indexOfShape = 1;
        int indexOfSymbol = 2;
        int symbolPosition = 0;

        String[] parts = data.split("; ");
        if (parts.length < validPartsLength) {
            throw new IllegalArgumentException("Неправильный формат данных для посылки");
        }
        String name = parts[indexOfName].trim();
        String shape = parts[indexOfShape].trim();
        char symbol = parts[indexOfSymbol].charAt(symbolPosition);
        return new ParcelRequestDto(name, shape, symbol);
    }
}