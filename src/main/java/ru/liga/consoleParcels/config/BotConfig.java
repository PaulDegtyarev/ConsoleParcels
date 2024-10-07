package ru.liga.consoleParcels.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.liga.consoleParcels.service.PackagingManager;
import ru.liga.consoleParcels.service.ParcelService;
import ru.liga.consoleParcels.service.UnPackagingManager;
import ru.liga.consoleParcels.telegramBot.CargoManagementBot;

@Configuration
public class BotConfig {
    @Bean
    public TelegramBotsApi telegramBotsApi(CargoManagementBot cargoManagementBot) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(cargoManagementBot);
        return telegramBotsApi;
    }

    @Bean
    public CargoManagementBot cargoManagementBot(PackagingManager packagingManager, UnPackagingManager unPackagingManager, ParcelService parcelService) {
        return new CargoManagementBot(packagingManager, unPackagingManager, parcelService);
    }
}
