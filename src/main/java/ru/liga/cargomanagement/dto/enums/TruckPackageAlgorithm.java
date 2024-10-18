package ru.liga.cargomanagement.dto.enums;

/**
 * Перечисление возможных алгоритмов для упаковки посылок.
 */
public enum TruckPackageAlgorithm {
    /**
     * Алгоритм, который максимально использует пространство грузовика.
     */
    MAX_SPACE,
    /**
     * Алгоритм, который обеспечивает равномерное распределение посылок.
     */
    EVEN_LOADING
}
