package ru.liga.consoleParcels.model;

/**
 * Перечисление, представляющее возможные команды пользователя.
 * <p>
 * Определяет три возможных действия пользователя:
 * - PACK: Команда для упаковки посылок в грузовики.
 * - UNPACK: Команда для распаковки грузовиков.
 * - EXIT: Команда для выхода из приложения.
 */
public enum UserCommand {
    PACK,
    UNPACK,
    EXIT
}
