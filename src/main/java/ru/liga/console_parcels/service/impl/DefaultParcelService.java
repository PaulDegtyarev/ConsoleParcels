package ru.liga.console_parcels.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import ru.liga.console_parcels.dto.ParcelRequestDto;
import ru.liga.console_parcels.dto.ParcelResponseDto;
import ru.liga.console_parcels.entity.Parcel;
import ru.liga.console_parcels.exception.ParcelNameConflictException;
import ru.liga.console_parcels.exception.ParcelNotFoundException;
import ru.liga.console_parcels.exception.WrongSymbolInShapeException;
import ru.liga.console_parcels.factory.ParcelServiceResponseFactory;
import ru.liga.console_parcels.repository.ParcelRepository;
import ru.liga.console_parcels.service.ParcelRequestValidator;
import ru.liga.console_parcels.service.ParcelService;

import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class DefaultParcelService implements ParcelService {
    private final ParcelRepository parcelRepository;
    private final ParcelServiceResponseFactory parcelServiceResponseFactory;
    private final ParcelRequestValidator parcelRequestValidator;

    @Override
    public List<ParcelResponseDto> findAllParcels() {
        log.info("Получение списка всех посылок");
        return parcelRepository.findAll()
                .stream()
                .map(parcelServiceResponseFactory::createServiceResponse)
                .toList();
    }

    @Override
    public ParcelResponseDto findParcelByName(String name) {
        log.info("Начинается поиск посылки с названием: {}", name);
        return parcelRepository.findParcelByName(name.trim().toLowerCase())
                .map(parcelServiceResponseFactory::createServiceResponse)
                .orElseThrow(() -> new ParcelNotFoundException("Посылка с названием: " + name + " не найдена"));
    }

    @Override
    public ParcelResponseDto addParcel(ParcelRequestDto parcelRequestDto) {
        log.info("Начинается добавление посылки с данными: {}", parcelRequestDto);
        String name = parcelRequestDto.getName();

        String shape = parcelRequestDto.getShape();
        parcelRequestValidator.validateParcelShape(shape);

        char symbol = parcelRequestDto.getSymbol();
        parcelRequestValidator.validateParcelSymbol(symbol);

        if (parcelRequestValidator.isThereSymbolThatIsNotSpecified(parcelRequestDto)) {
            throw new WrongSymbolInShapeException("Некоторые символы посылки не являются указанным символом: " + symbol);
        }

        String trimmedName = name.trim()
                .toLowerCase();
        if (parcelRepository.existsByName(trimmedName)) {
            throw new ParcelNameConflictException("Посылка с названием " + name + " уже существует");
        }

        shape = shape.replace(" ", "\n");
        Parcel newParcel = new Parcel(trimmedName, shape, symbol);
        parcelRepository.save(newParcel);

        return parcelServiceResponseFactory.createServiceResponse(newParcel);
    }

    @Override
    public ParcelResponseDto updateByName(ParcelRequestDto parcelRequestDto) {
        log.info("Начинается обновление посылки с данными: {}", parcelRequestDto);
        String nameOfSavedParcel = parcelRequestDto.getName();

        String newShape = parcelRequestDto.getShape();
        parcelRequestValidator.validateParcelShape(newShape);

        char newSymbol = parcelRequestDto.getSymbol();
        parcelRequestValidator.validateParcelSymbol(newSymbol);

        if (parcelRequestValidator.isThereSymbolThatIsNotSpecified(parcelRequestDto)) {
            throw new WrongSymbolInShapeException("Некоторые символы посылки не являются указанным символом: " + newSymbol);
        }

        String trimmedNameOfSavedParcel = nameOfSavedParcel.trim().toLowerCase();
        Parcel parcelToUpdate = parcelRepository.findParcelByName(trimmedNameOfSavedParcel)
                .orElseThrow(() -> new ParcelNotFoundException("Посылка с названием " + nameOfSavedParcel + " не найдена"));

        newShape = newShape.replace(" ", "\n");
        parcelToUpdate.updateShapeWithNewSymbol(newShape, newSymbol);
        parcelRepository.save(parcelToUpdate);

        log.info("Посылка успешно обновлена: {}", parcelToUpdate);
        return parcelServiceResponseFactory.createServiceResponse(parcelToUpdate);
    }

    @Override
    public ParcelResponseDto updateSymbolByParcelName(String nameOfSavedParcel, char newSymbol) {
        log.info("Начинается обновление символа посылки с названием: {}, новый символ: {}", nameOfSavedParcel, newSymbol);
        parcelRequestValidator.validateParcelSymbol(newSymbol);

        String trimmedNameOfSavedParcel = nameOfSavedParcel.trim().toLowerCase();
        Parcel parcelWithUpdateSymbol = parcelRepository.findParcelByName(trimmedNameOfSavedParcel)
                .orElseThrow(() -> new ParcelNotFoundException("Посылка с названием " + nameOfSavedParcel + " не найдена"));

        String shape = parcelWithUpdateSymbol.getShape();
        char oldSymbol = parcelWithUpdateSymbol.getSymbol();
        shape = shape.replace(oldSymbol, newSymbol);

        parcelWithUpdateSymbol.updateShapeWithNewSymbol(shape, newSymbol);
        parcelRepository.save(parcelWithUpdateSymbol);

        log.info("Символ посылки успешно обновлен: {}", parcelWithUpdateSymbol);
        return parcelServiceResponseFactory.createServiceResponse(parcelWithUpdateSymbol);
    }

    @Override
    public ParcelResponseDto updateShapeByParcelName(String nameOfSavedParcel, String newShape) {
        log.info("Начинается обновление формы посылки с названием: {}, новая форма: {}", nameOfSavedParcel, newShape);
        parcelRequestValidator.validateParcelShape(newShape);

        String trimmedNameOfSavedParcel = nameOfSavedParcel.trim().toLowerCase();
        Parcel parcelWithUpdateShape = parcelRepository.findParcelByName(trimmedNameOfSavedParcel)
                .orElseThrow(() -> new ParcelNotFoundException("Посылка с названием " + nameOfSavedParcel + " не найдена"));

        newShape = newShape.replace(" ", "\n");
        parcelWithUpdateShape.setShape(newShape);
        parcelRepository.save(parcelWithUpdateShape);

        log.info("Форма посылки успешно обновлена: {}", parcelWithUpdateShape);
        return parcelServiceResponseFactory.createServiceResponse(parcelWithUpdateShape);
    }

    @Override
    public void deleteParcelByParcelName(String nameOfParcelForDelete) {
        log.info("Начинается удаление посылки с названием: {}", nameOfParcelForDelete);
        String trimmedNameOfSavedParcel = nameOfParcelForDelete.trim().toLowerCase();

        parcelRepository.deleteById(trimmedNameOfSavedParcel);
        log.info("Посылка с названием: {} успешно удалена", nameOfParcelForDelete);
    }
}