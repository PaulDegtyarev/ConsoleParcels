package ru.liga.console_parcels.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.liga.console_parcels.formatter.ResultFormatter;
import ru.liga.console_parcels.service.PackagingManager;
import ru.liga.console_parcels.service.ParcelService;
import ru.liga.console_parcels.service.TruckParcelsUnpackingService;
import ru.liga.console_parcels.telegramBot.CargoManagementBot;

@Configuration
public class BotConfig {
    @Bean
    public TelegramBotsApi telegramBotsApi(CargoManagementBot cargoManagementBot) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(cargoManagementBot);
        return telegramBotsApi;
    }

    @Bean
    public CargoManagementBot cargoManagementBot(PackagingManager packagingManager, TruckParcelsUnpackingService truckParcelsUnpackingService, ParcelService parcelService, ResultFormatter resultFormatter) {
        return new CargoManagementBot(packagingManager, truckParcelsUnpackingService, parcelService, resultFormatter);
    }
}
