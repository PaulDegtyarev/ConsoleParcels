# Приложение для управления посылками

Это Java-приложение управляет упаковкой и распаковкой посылок в грузовики. Оно позволяет пользователям:

- Упаковать посылки в указанное количество грузовиков, используя различные алгоритмы (оптимизированная или равномерная загрузка).
- Распаковать грузовики и получить количество каждой разновидности посылки, находящейся в них.
- Просмотр, добавление, редатирование и удаление посылок.

## Функции

- **Удобный интерфейс:** Приложение взаимодействует с пользователем через консольный интерфейс, запрашивая ввод и отображая результаты.
- **Гибкие алгоритмы упаковки:** Поддерживает два алгоритма упаковки:
    - **Оптимизированная упаковка:** Старается максимально заполнить пространство каждого грузовика.
    - **Равномерная загрузка:** Старается равномерно распределить посылки по грузовикам.
- **Ввод данных о посылках:** Считывает данные о посылках из текстового файла, где каждая посылка представлена своей формой с помощью символов или считывает из консоли по названию посылки, хранящемся в базе данных.
- **Работа с посылками:** Просмотр, редактирование, добавление и удаление посылок, хранящихся в файловой системе.
- **Вывод данных о грузовиках:** Выводит данные о грузовиках в формате JSON, содержащие идентификаторы грузовиков и расположение посылок внутри них.
- **Распаковка грузовиков:** Считывает данные о грузовиках из JSON-файла и подсчитывает количество каждой разновидности посылки в каждом грузовике.
- **Обработка ошибок:** Предоставляет сообщения об ошибках для неверного ввода, отсутствия файла и других потенциальных проблем.
- **Логирование:** Использует Log4j2 для логирования информации о выполнении приложения, включая отладочные, информационные и сообщения об ошибках.

## Как это работает

Приложение использует несколько сервисов для достижения своей функциональности:

1. **Сервис ввода данных пользователя:** Обрабатывает взаимодействие с пользователем, запрашивая выбор и параметры (упаковка/распаковка, количество грузовиков, пути к файлам, выбор алгоритма).
2. **Выбор сервиса упаковки:** Выбирает соответствующий алгоритм упаковки на основе выбора пользователя.
3. **Чтение данных о посылках:** Считывает данные о посылках из текстового файла, проверяя корректность форм посылок.
4. **Фабрика грузовиков:** Создает указанное количество пустых грузовиков.
5. **Сервисы упаковки (оптимизированная/равномерная):** Реализуют алгоритмы упаковки для размещения посылок в грузовиках. Размеры грузовика пишутся в формате "6x6, 5x5 и т.д."
6. **Запись данных о грузовиках в JSON:** Записывает данные об упакованных грузовиках в JSON-файл.
7. **Сервис распаковки:** Считывает данные о грузовиках из JSON-файла и подсчитывает количество каждой разновидности посылки.
8. **Форматтер результатов:** Форматирует результаты упаковки и распаковки для отображения в консоли.
9. **Сервис работы с посылками:** Позволяет просматривать, редактировать, добавлять и удалять посылки.

## Использование

1. **Соберите проект с помощью Maven:**
   ```bash
   mvn package

2. **Запустите приложение с помощью Maven:**
    ```bash
    mvn exec:java -Dexec.mainClass="ru.liga.consoleParcels.ConsoleParcelsApplication"