package ru.liga.consoleParcels.controller;

import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.liga.consoleParcels.dto.ParcelRequestDto;
import ru.liga.consoleParcels.dto.ParcelResponseDto;
import ru.liga.consoleParcels.service.ParcelService;

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
}
