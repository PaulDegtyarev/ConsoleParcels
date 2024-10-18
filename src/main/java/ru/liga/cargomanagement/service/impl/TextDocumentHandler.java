package ru.liga.cargomanagement.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.liga.cargomanagement.dto.PackRequestDto;
import ru.liga.cargomanagement.entity.Truck;
import ru.liga.cargomanagement.exception.EmptyMessageException;
import ru.liga.cargomanagement.formatter.ResultFormatter;
import ru.liga.cargomanagement.mapper.DocumentMessageMapper;
import ru.liga.cargomanagement.service.DocumentHandler;
import ru.liga.cargomanagement.service.PackagingManager;

import java.io.File;
import java.util.List;


@Service("textDocumentHandler")
@Log4j2
@RequiredArgsConstructor
public class TextDocumentHandler implements DocumentHandler {
    private final PackagingManager packagingManager;
    private final ResultFormatter resultFormatter;
    private final DocumentMessageMapper documentMessageMapper;

    @Override
    public String handle(File file, Message message) {
        String caption = message.getCaption();

        if (caption == null || caption.isEmpty()) {
            throw new EmptyMessageException("Введите текст сообщения");
        }

        PackRequestDto packRequest = documentMessageMapper.toRequest(file, caption);

        List<Truck> packedTrucks = packagingManager.pack(packRequest);

        return resultFormatter.convertPackagingResultsToString(packedTrucks);
    }
}
