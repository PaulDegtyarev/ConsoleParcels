package ru.liga.cargomanagement.dto.enums;

import org.telegram.telegrambots.meta.api.objects.Message;

import static ru.liga.cargomanagement.dto.enums.FilePermission.JSON;
import static ru.liga.cargomanagement.dto.enums.FilePermission.TXT;

public enum MessageType {
    IS_JSON {
        @Override
        public boolean matches(Message message) {
            return message.hasDocument() && message.getDocument()
                    .getFileName()
                    .endsWith(String.valueOf(JSON.getExtension()));
        }
    },
    IS_TEXT {
        @Override
        public boolean matches(Message message) {
            return message.hasDocument() && message.getDocument()
                    .getFileName().endsWith(String.valueOf(TXT.getExtension()));
        }
    };

    public abstract boolean matches(Message message);
}
