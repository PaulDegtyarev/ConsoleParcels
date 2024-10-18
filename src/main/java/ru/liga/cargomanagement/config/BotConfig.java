package ru.liga.cargomanagement.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.liga.cargomanagement.bot.CargoManagementBot;
import ru.liga.cargomanagement.dto.enums.MessageType;
import ru.liga.cargomanagement.dto.enums.ParcelCommandType;
import ru.liga.cargomanagement.formatter.ResultFormatter;
import ru.liga.cargomanagement.mapper.TextMessageMapper;
import ru.liga.cargomanagement.service.*;
import ru.liga.cargomanagement.service.impl.command.*;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static ru.liga.cargomanagement.dto.enums.ParcelCommandType.*;

/**
 * Конфигурационный класс для настройки и регистрации Telegram-бота.
 */
@Configuration
public class BotConfig {

    /**
     * Создает и настраивает экземпляр {@link TelegramBotsApi} для регистрации бота.
     *
     * @param cargoManagementBot экземпляр бота для управления грузами
     * @return настроенный {@link TelegramBotsApi} с зарегистрированным ботом
     * @throws TelegramApiException если возникает ошибка при регистрации бота
     */
    @Bean
    public TelegramBotsApi telegramBotsApi(CargoManagementBot cargoManagementBot) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(cargoManagementBot);
        return telegramBotsApi;
    }

    @Bean
    public Map<MessageType, DocumentHandler> messageHandlerMap(@Qualifier("textDocumentHandler") DocumentHandler textDocumentHandler,
                                                               @Qualifier("jsonDocumentHandler") DocumentHandler jsonDocumentHandler) {
        Map<MessageType, DocumentHandler> handlerMap = new EnumMap<>(MessageType.class);
        handlerMap.put(MessageType.IS_TEXT, textDocumentHandler);
        handlerMap.put(MessageType.IS_JSON, jsonDocumentHandler);

        return handlerMap;
    }

    @Bean
    public Map<ParcelCommandType, BotCommand> parcelCommandMap(PackagingManager packagingManager,
                                                               ResultFormatter resultFormatter,
                                                               ParcelService parcelService,
                                                               TextMessageMapper textMessageMapper) {
        return new HashMap<>(Map.of(
                START, new StartCommand(),
                HELP, new HelpCommand(),
                PACK, new PackCommand(
                        packagingManager,
                        resultFormatter
                ),
                ALL, new AllCommand(parcelService),
                FIND_BY_NAME, new FindByNameCommand(parcelService),
                ADD, new AddCommand(
                        parcelService,
                        textMessageMapper
                ),
                UPDATE, new UpdateCommand(
                        parcelService,
                        textMessageMapper
                ),
                UPDATE_SYMBOL, new UpdateSymbolCommand(parcelService),
                UPDATE_SHAPE, new UpdateShapeCommand(parcelService),
                DELETE, new DeleteCommand(parcelService)
        ));
    }


    @Bean
    public CargoManagementBot cargoManagementBot(DocumentHandler textDocumentHandler,
                                                 DocumentHandler jsonDocumentHandler,
                                                 TextHandler textHandler) {
        return new CargoManagementBot(messageHandlerMap(textDocumentHandler, jsonDocumentHandler), textHandler);
    }
}
