package ru.liga.consoleParcels.model;

/**
 * Перечисление, представляющее выбор пользователя алгоритма упаковки.
 * <p>
 * Определяет два возможных варианта:
 * - MAX_SPACE: Алгоритм, который старается максимально заполнить
 * грузовик в первую очередь.
 * - EVEN_LOADING: Алгоритм, который старается равномерно
 * распределить посылки по грузовикам.
 */
public enum UserAlgorithmChoice {
    MAX_SPACE,
    EVEN_LOADING
}
