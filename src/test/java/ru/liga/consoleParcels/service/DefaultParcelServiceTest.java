package ru.liga.consoleParcels.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.consoleParcels.dto.ParcelRequestDto;
import ru.liga.consoleParcels.dto.ParcelResponseDto;
import ru.liga.consoleParcels.exception.*;
import ru.liga.consoleParcels.factory.ParcelServiceResponseFactory;
import ru.liga.consoleParcels.entity.Parcel;
import ru.liga.consoleParcels.repository.ParcelRepository;
import ru.liga.consoleParcels.service.impl.DefaultParcelService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DefaultParcelServiceTest {
    @Mock
    private ParcelRepository parcelRepository;

    @InjectMocks
    private DefaultParcelService defaultParcelService;

    @Mock
    private ParcelServiceResponseFactory parcelServiceResponseFactory;

    @Mock
    private ParcelValidator parcelValidator;

    @Mock
    private ShapeParser shapeParser;

    @Test
    void testFindAllParcels_shouldReturnEmptyList() {
        when(parcelRepository.findAll()).thenReturn(Collections.emptyList());

        String result = defaultParcelService.findAllParcels();

        assertThat(result).isEmpty();
    }

    @Test
    void testFindAllParcels_shouldReturnValidOutput() {
        Parcel parcel1 = new Parcel("Чипсы", new char[][]{{'1'}}, '1');
        Parcel parcel2 = new Parcel("Макароны", new char[][]{{'2', '2'}}, '2');
        Parcel parcel3 = new Parcel("Конфеты", new char[][]{{'3', '3', '3'}}, '3');

        List<Parcel> parcels = new ArrayList<>(List.of(parcel1, parcel2, parcel3));
        when(parcelRepository.findAll()).thenReturn(parcels);

        ParcelResponseDto response1 = new ParcelResponseDto("Чипсы", new char[][]{{'1'}}, '1');
        ParcelResponseDto response2 = new ParcelResponseDto("Макароны", new char[][]{{'2', '2'}}, '2');
        ParcelResponseDto response3 = new ParcelResponseDto("Конфеты", new char[][]{{'3', '3', '3'}}, '3');

        when(parcelServiceResponseFactory.createServiceResponse(parcel1)).thenReturn(response1);
        when(parcelServiceResponseFactory.createServiceResponse(parcel2)).thenReturn(response2);
        when(parcelServiceResponseFactory.createServiceResponse(parcel3)).thenReturn(response3);

        String result = defaultParcelService.findAllParcels();

        String expectedResult = "name=Чипсы,\nshape=1,\nsymbol=1\n\n" +
                "name=Макароны,\nshape=22,\nsymbol=2\n\n" +
                "name=Конфеты,\nshape=333,\nsymbol=3";

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void findParcelByName_withValidName_shouldReturnParcelResponseDto() {
        String name = "Чипсы";
        Parcel parcel = new Parcel("Чипсы", new char[][]{{'1'}}, '1');
        ParcelResponseDto expectedResponse = new ParcelResponseDto("Чипсы", new char[][]{{'1'}}, '1');

        when(parcelRepository.findParcelByName(name.trim().toLowerCase())).thenReturn(Optional.of(parcel));
        when(parcelServiceResponseFactory.createServiceResponse(parcel)).thenReturn(expectedResponse);

        ParcelResponseDto actualResponse = defaultParcelService.findParcelByName(name);

        assertThat(actualResponse).isEqualTo(expectedResponse);
        verify(parcelRepository, times(1)).findParcelByName(name.trim().toLowerCase());
        verify(parcelServiceResponseFactory, times(1)).createServiceResponse(parcel);
    }

    @Test
    void findParcelByName_withNameContainingWhitespace_shouldTrimAndLowercase() {
        String name = "  Чипсы  ";
        Parcel parcel = new Parcel("Чипсы", new char[][]{{'1'}}, '1');
        ParcelResponseDto expectedResponse = new ParcelResponseDto("Чипсы", new char[][]{{'1'}}, '1');

        when(parcelRepository.findParcelByName("чипсы")).thenReturn(Optional.of(parcel));
        when(parcelServiceResponseFactory.createServiceResponse(parcel)).thenReturn(expectedResponse);

        ParcelResponseDto actualResponse = defaultParcelService.findParcelByName(name);

        assertThat(actualResponse).isEqualTo(expectedResponse);
        verify(parcelRepository, times(1)).findParcelByName("чипсы");
        verify(parcelServiceResponseFactory, times(1)).createServiceResponse(parcel);
    }

    @Test
    void findParcelByName_withNameContainingOnlyWhitespace_shouldThrowParcelNotFoundException() {
        String name = "   ";

        when(parcelRepository.findParcelByName(name.trim().toLowerCase())).thenReturn(Optional.empty());

        assertThrows(ParcelNotFoundException.class, () -> defaultParcelService.findParcelByName(name));
        verify(parcelRepository, times(1)).findParcelByName(name.trim().toLowerCase());
        verify(parcelServiceResponseFactory, never()).createServiceResponse(any());
    }

    @Test
    void addParcel_withValidInput_shouldAddParcel() {
        String name = "Чипсы";
        String shape = "1";
        char symbol = '1';
        ParcelRequestDto parcelRequestDto = new ParcelRequestDto(name, shape, symbol);

        when(parcelRepository.existsByName(anyString())).thenReturn(false);
        doNothing().when(parcelRepository).save(any(Parcel.class));

        ParcelResponseDto expectedResponse = new ParcelResponseDto("Чипсы", new char[][]{{'1'}}, '1');
        when(parcelServiceResponseFactory.createServiceResponse(any(Parcel.class))).thenReturn(expectedResponse);

        ParcelResponseDto result = defaultParcelService.addParcel(parcelRequestDto);

        assertThat(result).isEqualTo(expectedResponse);
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getSymbol()).isEqualTo(symbol);

        verify(parcelRepository, times(1)).existsByName(parcelRequestDto.getName().trim().toLowerCase());
        verify(parcelRepository, times(1)).save(any(Parcel.class));
    }

    @Test
    void addParcel_withExistingName_shouldThrowParcelNameConflictException() {
        String name = "Чипсы";
        String shape = "1";
        char symbol = '1';
        ParcelRequestDto parcelRequestDto = new ParcelRequestDto(name, shape, symbol);

        when(parcelRepository.existsByName(anyString())).thenReturn(true);

        assertThatThrownBy(() -> defaultParcelService.addParcel(parcelRequestDto))
                .isInstanceOf(ParcelNameConflictException.class);

        verify(parcelRepository, times(1)).existsByName(parcelRequestDto.getName().trim().toLowerCase());
        verify(parcelRepository, never()).save(any(Parcel.class));
    }

    @Test
    void addParcel_withInvalidShapeSymbol_shouldThrowWrongSymbolInShapeException() {
        String name = "Чипсы";
        String shape = "1\n12\n1";
        char symbol = '1';
        ParcelRequestDto parcelRequestDto = new ParcelRequestDto(name, shape, symbol);

        assertThatThrownBy(() -> defaultParcelService.addParcel(parcelRequestDto))
                .isInstanceOf(WrongSymbolInShapeException.class);

        verify(parcelRepository, never()).save(any(Parcel.class));
    }


    @Test
    void updateParcelByName_withValidInput_shouldUpdateParcel() {
        String name = "Чипсы";
        String shape = "999 999 999";
        char symbol = '9';
        ParcelRequestDto parcelRequestDto = new ParcelRequestDto(name, shape, symbol);

        Parcel existingParcel = new Parcel(name, new char[][]{{'0', '0', '0'}, {'0', '0', '0'}, {'0', '0', '0'}}, '0');
        when(parcelRepository.findParcelByName(name.trim().toLowerCase())).thenReturn(Optional.of(existingParcel));

        char[][] expectedShape = new char[][]{{'9', '9', '9'}, {'9', '9', '9'}, {'9', '9', '9'}};

        ParcelResponseDto expectedResponse = new ParcelResponseDto("Чипсы", new char[][]{{'9', '9', '9'}, {'9', '9', '9'}, {'9', '9', '9'}}, '9');
        when(parcelServiceResponseFactory.createServiceResponse(any(Parcel.class))).thenReturn(expectedResponse);

        ParcelResponseDto result = defaultParcelService.updateParcelByName(parcelRequestDto);

        assertThat(result).isEqualTo(expectedResponse);
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getShape()).isEqualTo(expectedShape);
        assertThat(result.getSymbol()).isEqualTo(symbol);

        verify(parcelRepository, times(1)).findParcelByName(name.trim().toLowerCase());
        verify(parcelRepository, times(1)).save(any(Parcel.class));
    }

    @Test
    void updateParcelByName_withInvalidName_shouldThrowParcelNotFoundException() {
        String name = "Некорректное имя";
        String shape = "1";
        char symbol = '1';
        ParcelRequestDto parcelRequestDto = new ParcelRequestDto(name, shape, symbol);

        when(parcelRepository.findParcelByName(name.trim().toLowerCase())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> defaultParcelService.updateParcelByName(parcelRequestDto))
                .isInstanceOf(ParcelNotFoundException.class)
                .hasMessage("Посылка с названием " + name + " не найдена");

        verify(parcelRepository, times(1)).findParcelByName(name.trim().toLowerCase());
        verify(parcelRepository, never()).save(any(Parcel.class));
    }

    @Test
    void updateParcelByName_withInvalidShapeSymbol_shouldThrowWrongSymbolInShapeException() {
        String name = "Чипсы";
        String shape = "111\n121\n111";
        char symbol = '1';
        ParcelRequestDto parcelRequestDto = new ParcelRequestDto(name, shape, symbol);

        assertThatThrownBy(() -> defaultParcelService.updateParcelByName(parcelRequestDto))
                .isInstanceOf(WrongSymbolInShapeException.class)
                .hasMessage("Некоторые символы посылки не являются указанным символом: " + symbol);

        verify(parcelRepository, never()).save(any(Parcel.class));
    }

    @Test
    void updateParcelByName_withInvalidShape_shouldThrowInvalidShapeException() {
        String name = "Чипсы";
        String shape = "  ";
        char symbol = ' ';
        ParcelRequestDto parcelRequestDto = new ParcelRequestDto(name, shape, symbol);

        doThrow(new InvalidShapeException("Форма посылки не может быть пустой")).when(parcelValidator).validateParcelShape(shape);

        assertThatThrownBy(() -> defaultParcelService.updateParcelByName(parcelRequestDto))
                .isInstanceOf(InvalidShapeException.class);

        verify(parcelRepository, never()).save(any(Parcel.class));
    }

    @Test
    void updateSymbolByParcelName_withValidInput_shouldUpdateParcel() {
        String name = "чипсы";
        char oldSymbol = '1';
        char newSymbol = '9';

        Parcel existingParcel = new Parcel(name, new char[][]{{oldSymbol, oldSymbol}, {oldSymbol, oldSymbol}}, oldSymbol);
        when(parcelRepository.findParcelByName(name)).thenReturn(Optional.of(existingParcel));

        ParcelResponseDto expectedResponse = new ParcelResponseDto("чипсы", new char[][]{{'9', '9'}, {'9', '9'}}, '9');
        when(parcelServiceResponseFactory.createServiceResponse(any(Parcel.class))).thenReturn(expectedResponse);

        ParcelResponseDto result = defaultParcelService.updateSymbolByParcelName(name, newSymbol);

        assertThat(expectedResponse).isEqualTo(result);
        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getSymbol()).isEqualTo(newSymbol);
        assertThat(result.getShape()).isEqualTo(new char[][]{{newSymbol, newSymbol}, {newSymbol, newSymbol}});

        verify(parcelRepository, times(1)).findParcelByName(name);
        verify(parcelRepository, times(1)).save(any(Parcel.class));
    }

    @Test
    void updateSymbolByParcelName_withInvalidName_shouldThrowParcelNotFoundException() {
        String name = "Некорректное имя";
        char newSymbol = '9';

        when(parcelRepository.findParcelByName(name.trim().toLowerCase())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> defaultParcelService.updateSymbolByParcelName(name, newSymbol))
                .isInstanceOf(ParcelNotFoundException.class);

        verify(parcelRepository, times(1)).findParcelByName(name.trim().toLowerCase());
        verify(parcelRepository, never()).save(any(Parcel.class));
    }

    @Test
    void updateSymbolByParcelName_withWhitespaceSymbol_shouldThrowInvalidCharacterException() {
        String name = "чипсы";
        char newSymbol = ' ';

        doThrow(new InvalidCharacterException("Нельзя сделать символ пробелом")).when(parcelValidator).validateParcelSymbol(newSymbol);

        assertThatThrownBy(() -> defaultParcelService.updateSymbolByParcelName(name, newSymbol))
                .isInstanceOf(InvalidCharacterException.class);

        verify(parcelRepository, never()).save(any(Parcel.class));
    }

    @Test
    void updateShapeByParcelName_withValidInput_shouldUpdateShape() {
        String name = "Чипсы";
        char symbol = '1';
        String newShape = "111 111";

        Parcel existingParcel = new Parcel(name, new char[][]{{'1', '1'}, {'1', '1'}}, symbol);
        when(parcelRepository.findParcelByName(name.trim().toLowerCase())).thenReturn(Optional.of(existingParcel));

        ParcelResponseDto expectedResponse = new ParcelResponseDto("Чипсы", new char[][]{{'1', '1', '1'}, {'1', '1', '1'}}, '1');
        when(parcelServiceResponseFactory.createServiceResponse(any(Parcel.class))).thenReturn(expectedResponse);

        ParcelResponseDto result = defaultParcelService.updateShapeByParcelName(name, newShape);

        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getShape()).isEqualTo(expectedResponse.getShape());
        assertThat(result.getSymbol()).isEqualTo(symbol);

        verify(parcelRepository, times(1)).findParcelByName(name.trim().toLowerCase());
        verify(parcelRepository, times(1)).save(existingParcel);
    }

    @Test
    void updateShapeByParcelName_withNonExistentParcel_shouldThrowParcelNotFoundException() {
        String name = "Некорректное имя";
        String newShape = "22 22";

        when(parcelRepository.findParcelByName(name.trim().toLowerCase())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> defaultParcelService.updateShapeByParcelName(name, newShape))
                .isInstanceOf(ParcelNotFoundException.class);

        verify(parcelRepository, times(1)).findParcelByName(name.trim().toLowerCase());
        verify(parcelRepository, never()).save(any(Parcel.class));
    }

    @Test
    void updateShapeByParcelName_withBlankShape_shouldThrowInvalidShapeException() {
        String name = "Чипсы";
        String newShape = "   ";

        doThrow(new InvalidShapeException("Новая форма не может быть пробелами")).when(parcelValidator).validateParcelShape(newShape);

        assertThatThrownBy(() -> defaultParcelService.updateShapeByParcelName(name, newShape))
                .isInstanceOf(InvalidShapeException.class);

        verify(parcelRepository, never()).findParcelByName(anyString());
        verify(parcelRepository, never()).save(any(Parcel.class));
    }

    @Test
    void deleteParcelByParcelName_withExistingParcel_shouldDeleteParcel() {
        String name = "существующая посылка";
        doNothing().when(parcelRepository).deleteParcelByParcelName(name.trim().toLowerCase());

        defaultParcelService.deleteParcelByParcelName(name);

        verify(parcelRepository, times(1)).deleteParcelByParcelName(name.trim().toLowerCase());
    }

    @Test
    void deleteParcelByParcelName_withNonExistentParcel_shouldThrowParcelNotFoundException() {
        String name = "Некорректное имя";
        String trimmedName = name.trim().toLowerCase();

        doThrow(new ParcelNotFoundException("Посылка с названием " + name + " не найдена"))
                .when(parcelRepository).deleteParcelByParcelName(trimmedName);

        assertThatThrownBy(() -> defaultParcelService.deleteParcelByParcelName(name))
                .isInstanceOf(ParcelNotFoundException.class);

        verify(parcelRepository, times(1)).deleteParcelByParcelName(trimmedName);
    }
}
