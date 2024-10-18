package ru.liga.cargomanagement.service.impl.command;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.cargomanagement.service.BotCommand;
import ru.liga.cargomanagement.service.ParcelService;

@Service
@RequiredArgsConstructor
public class AllCommand implements BotCommand {
    private final ParcelService parcelService;

    @Override
    public String execute(String messageText) {
        return parcelService.findAllParcels().toString();
    }
}
