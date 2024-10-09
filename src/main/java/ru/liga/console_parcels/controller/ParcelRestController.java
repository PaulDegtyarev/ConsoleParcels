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
    public ResponseEntity<List<ParcelResponseDto>> getAll() {
        return new ResponseEntity<>(parcelService.findAllParcels(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<ParcelResponseDto> get(@RequestParam(required = false, defaultValue = "") String name) {
        return new ResponseEntity<>(parcelService.findParcelByName(name), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<ParcelResponseDto> add(@RequestBody ParcelRequestDto parcelRequest) {
        return new ResponseEntity<>(parcelService.addParcel(parcelRequest), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<ParcelResponseDto> updateByName(@RequestBody ParcelRequestDto parcelRequest) {
        return new ResponseEntity<>(parcelService.updateParcelByName(parcelRequest), HttpStatus.OK);
    }

    @PutMapping("/symbol")
    public ResponseEntity<ParcelResponseDto> updateSymbolByName(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestBody SymbolRequestBody symbolRequest) {
        return new ResponseEntity<>(parcelService.updateSymbolByParcelName(name, symbolRequest.getSymbol()), HttpStatus.OK);
    }

    @PutMapping("/shape")
    public ResponseEntity<ParcelResponseDto> updateShapeByName(
            @RequestParam(required = false, defaultValue = "") String name,
            @RequestBody ShapeRequestBody shapeRequest) {
        return new ResponseEntity<>(parcelService.updateShapeByParcelName(name, shapeRequest.getShape()), HttpStatus.OK);
    }

    @DeleteMapping()
    public void delete(@RequestParam(required = false, defaultValue = "") String name) {
        parcelService.deleteParcelByParcelName(name);
    }
}
