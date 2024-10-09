package ru.liga.console_parcels.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.console_parcels.dto.ParcelRequestDto;
import ru.liga.console_parcels.dto.ParcelResponseDto;
import ru.liga.console_parcels.dto.ShapeRequestBody;
import ru.liga.console_parcels.dto.SymbolRequestBody;
import ru.liga.console_parcels.service.ParcelService;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/parcels")
public class ParcelRestController {
    private final ParcelService parcelService;

    @GetMapping
    public ResponseEntity<List<ParcelResponseDto>> findAll() {
        log.info("Пользователь выбрал достать все посылки.");
        return new ResponseEntity<>(parcelService.findAllParcels(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ParcelResponseDto> findByName(@RequestParam(required = false, defaultValue = "") String name) {
        log.info("Пользователь выбрал достать посылку по названию.");
        return new ResponseEntity<>(parcelService.findParcelByName(name), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ParcelResponseDto> add(@RequestBody ParcelRequestDto parcelRequest) {
        log.info("Пользователь выбрал добавить посылку.");
        return new ResponseEntity<>(parcelService.addParcel(parcelRequest), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<ParcelResponseDto> updateByName(@RequestBody ParcelRequestDto parcelRequest) {
        log.info("Пользователь выбрал обновить посылку по ее названию.");
        return new ResponseEntity<>(parcelService.updateParcelByName(parcelRequest), HttpStatus.OK);
    }

    @PutMapping("/symbol")
    public ResponseEntity<ParcelResponseDto> updateSymbolByName(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestBody SymbolRequestBody symbolRequest) {
        log.info("Пользователь выбрал обновить символ посылки.");
        return new ResponseEntity<>(parcelService.updateSymbolByParcelName(name, symbolRequest.getSymbol()), HttpStatus.OK);
    }

    @PutMapping("/shape")
    public ResponseEntity<ParcelResponseDto> updateShapeByName(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestBody ShapeRequestBody shapeRequest) {
        log.info("Пользователь выбрал обновить форму посылки.");
        return new ResponseEntity<>(parcelService.updateShapeByParcelName(name, shapeRequest.getShape()), HttpStatus.OK);
    }

    @DeleteMapping()
    public void delete(@RequestParam(required = false, defaultValue = "") String name) {
        log.info("Пользователь выбрал удалить посылку.");
        parcelService.deleteParcelByParcelName(name);
    }
}
