package ru.liga.cargomanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.liga.cargomanagement.formatter.ResultFormatter;
import ru.liga.cargomanagement.service.PackagingManager;
import ru.liga.cargomanagement.service.ParcelService;
import ru.liga.cargomanagement.service.TruckParcelsUnpackingService;
import ru.liga.cargomanagement.telegramBot.CargoManagementBot;

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

    /**
     * Создает и настраивает экземпляр {@link CargoManagementBot} с необходимыми зависимостями.
     *
     * @param packagingManager             менеджер упаковки
     * @param truckParcelsUnpackingService сервис для распаковки посылок из грузовика
     * @param parcelService                сервис для работы с посылками
     * @param resultFormatter              форматтер результатов
     * @return настроенный экземпляр {@link CargoManagementBot}
     */
    @Bean
    public CargoManagementBot cargoManagementBot(PackagingManager packagingManager, TruckParcelsUnpackingService truckParcelsUnpackingService, ParcelService parcelService, ResultFormatter resultFormatter) {
        return new CargoManagementBot(packagingManager, truckParcelsUnpackingService, parcelService, resultFormatter);
    }
}
