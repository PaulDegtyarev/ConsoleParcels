package ru.liga.cargomanagement.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ParcelCommandType {
    START("/start"),
    HELP("/help"),
    PACK("/pack"),
    ALL("/all"),
    FIND_BY_NAME("/findByName"),
    ADD("/add"),
    UPDATE("/updateByName"),
    UPDATE_SYMBOL("/updateSymbolByName"),
    UPDATE_SHAPE("/updateShapeByName"),
    DELETE("/deleteByName");

    private final String command;

    public static ParcelCommandType fromString(String text) {
        for (ParcelCommandType b : ParcelCommandType.values()) {
            if (b.getCommand().equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }

}
