package ru.liga.consoleParcels.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.liga.consoleParcels.dto.ParcelRequestDto;
import ru.liga.consoleParcels.dto.ParcelResponseDto;
import ru.liga.consoleParcels.exception.*;
import ru.liga.consoleParcels.model.Parcel;
import ru.liga.consoleParcels.repository.ParcelRepository;
import ru.liga.consoleParcels.service.impl.DefaultParcelService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class DefaultParcelServiceTest {
    @Mock
    private ParcelRepository parcelRepository;

    @InjectMocks
    private DefaultParcelService defaultParcelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindAllParcels() {
        Parcel parcel1 = new Parcel("Чипсы", new char[][]{{'1'}}, '1');
        Parcel parcel2 = new Parcel("Макароны", new char[][]{{'2', '2'}}, '2');

        List<Parcel> parcels = List.of(parcel1, parcel2);
        when(parcelRepository.findAll()).thenReturn(parcels);

        String result = defaultParcelService.findAllParcels();

        String expectedResult = "name=Чипсы,\nshape=1,\nsymbol=1\n\nname=Макароны,\nshape=22,\nsymbol=2";

        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void findParcelByName_withValidInput_shouldReturnValidOutput() {
        String name = "Чипсы";
        Parcel parcel = new Parcel(name, new char[][]{{'1'}}, '1');
        when(parcelRepository.findParcelByName(name.trim().toLowerCase())).thenReturn(Optional.of(parcel));

        ParcelResponseDto result = defaultParcelService.findParcelByName(name);

        assertThat(name).isEqualTo(result.getName());
        verify(parcelRepository, times(1)).findParcelByName(name.trim().toLowerCase());
    }

    @Test
    void findParcelByName_withInvalidName_shouldThrowParcelNotFoundException() {
        String name = "Некорректное имя";
        when(parcelRepository.findParcelByName(name.trim().toLowerCase())).thenReturn(Optional.empty());

        assertThrows(ParcelNotFoundException.class, () -> defaultParcelService.findParcelByName(name));
        verify(parcelRepository, times(1)).findParcelByName(name.trim().toLowerCase());
    }

    @Test
    void addParcel_withValidInput_shouldAddParcel() {
        String name = "Чипсы";
        String shape = "1";
        char symbol = '1';
        ParcelRequestDto parcelRequest = new ParcelRequestDto(name, shape, symbol);

        when(parcelRepository.existsByName(anyString())).thenReturn(false);
        doNothing().when(parcelRepository).save(any(Parcel.class));

        ParcelResponseDto result = defaultParcelService.addParcel(parcelRequest);

        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getSymbol()).isEqualTo(symbol);

        verify(parcelRepository, times(1)).existsByName(parcelRequest.getName().trim().toLowerCase());
        verify(parcelRepository, times(1)).save(any(Parcel.class));
    }

    @Test
    void addParcel_withExistingName_shouldThrowParcelNameConflictException() {
        String name = "Чипсы";
        String shape = "1";
        char symbol = '1';
        ParcelRequestDto parcelRequest = new ParcelRequestDto(name, shape, symbol);

        when(parcelRepository.existsByName(anyString())).thenReturn(true);

        assertThatThrownBy(() -> defaultParcelService.addParcel(parcelRequest))
                .isInstanceOf(ParcelNameConflictException.class);

        verify(parcelRepository, times(1)).existsByName(parcelRequest.getName().trim().toLowerCase());
        verify(parcelRepository, never()).save(any(Parcel.class));
    }

    @Test
    void addParcel_withInvalidShapeSymbol_shouldThrowWrongSymbolInShapeException() {
        String name = "Чипсы";
        String shape = "1\n12\n1";
        char symbol = '1';
        ParcelRequestDto parcelRequest = new ParcelRequestDto(name, shape, symbol);

        assertThatThrownBy(() -> defaultParcelService.addParcel(parcelRequest))
                .isInstanceOf(WrongSymbolInShapeException.class);

        verify(parcelRepository, never()).save(any(Parcel.class));
    }


    @Test
    void updateParcelByName_withValidInput_shouldUpdateParcel() {
        String name = "Чипсы";
        String shape = "999999999";
        char symbol = '9';
        ParcelRequestDto parcelRequest = new ParcelRequestDto(name, shape, symbol);

        Parcel existingParcel = new Parcel(name, new char[][]{{'0', '0', '0'}, {'0', '0', '0'}, {'0', '0', '0'}}, '0');
        when(parcelRepository.findParcelByName(name.trim().toLowerCase())).thenReturn(Optional.of(existingParcel));

        char[][] expectedShape = new char[][]{{'9', '9', '9', '9', '9', '9', '9', '9', '9'}};
        ParcelResponseDto result = defaultParcelService.updateParcelByName(parcelRequest);

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
        ParcelRequestDto parcelRequest = new ParcelRequestDto(name, shape, symbol);

        when(parcelRepository.findParcelByName(name.trim().toLowerCase())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> defaultParcelService.updateParcelByName(parcelRequest))
                .isInstanceOf(ParcelNotFoundException.class)
                .hasMessage("Посылка с названием " + name + " не найдена");

        verify(parcelRepository, times(1)).findParcelByName(name.trim().toLowerCase());
        verify(parcelRepository, never()).save(any(Parcel.class));
    }

    @Test
    void testUpdateParcelByName_withInvalidShapeSymbol_shouldThrowWrongSymbolInShapeException() {
        String name = "Чипсы";
        String shape = "111\n121\n111";
        char symbol = '1';
        ParcelRequestDto parcelRequest = new ParcelRequestDto(name, shape, symbol);

        assertThatThrownBy(() -> defaultParcelService.updateParcelByName(parcelRequest))
                .isInstanceOf(WrongSymbolInShapeException.class)
                .hasMessage("Некоторые символы посылки не являются указанным символом: " + symbol);

        verify(parcelRepository, never()).findParcelByName(anyString());
        verify(parcelRepository, never()).save(any(Parcel.class));
    }

    @Test
    void updateSymbolByParcelName_withValidInput_shouldUpdateParcel() {
        String name = "чипсы";
        char oldSymbol = '1';
        char newSymbol = '9';

        Parcel existingParcel = new Parcel(name, new char[][]{{oldSymbol, oldSymbol}, {oldSymbol, oldSymbol}}, oldSymbol);
        when(parcelRepository.findParcelByName(name)).thenReturn(Optional.of(existingParcel));

        ParcelResponseDto result = defaultParcelService.updateSymbolByParcelName(name, newSymbol);

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
                .isInstanceOf(ParcelNotFoundException.class)
                .hasMessage("Посылка с названием " + name + " не найдена");

        verify(parcelRepository, times(1)).findParcelByName(name.trim().toLowerCase());
        verify(parcelRepository, never()).save(any(Parcel.class));
    }

    @Test
    void updateSymbolByParcelName_withWhitespaceSymbol_shouldThrowIllegalArgumentException() {
        String name = "чипсы";
        char newSymbol = ' ';

        Parcel existingParcel = new Parcel(name, new char[][]{{'1', '1'}}, '1');
        when(parcelRepository.findParcelByName(name)).thenReturn(Optional.of(existingParcel));

        assertThatThrownBy(() -> defaultParcelService.updateSymbolByParcelName(name, newSymbol))
                .isInstanceOf(InvalidCharacterException.class);

        verify(parcelRepository, never()).save(any(Parcel.class));
    }

    @Test
    void updateSymbolByParcelName_withEmptyShape_shouldHReturnParcelWithEmptyShape() {
        String name = "чипсы";
        char oldSymbol = '1';
        char newSymbol = '9';

        Parcel existingParcel = new Parcel(name, new char[0][0], oldSymbol);
        when(parcelRepository.findParcelByName(name)).thenReturn(Optional.of(existingParcel));

        ParcelResponseDto result = defaultParcelService.updateSymbolByParcelName(name, newSymbol);

        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getSymbol()).isEqualTo(newSymbol);
        assertThat(result.getShape()).isEqualTo(new char[0][0]);

        verify(parcelRepository, times(1)).findParcelByName(name);
        verify(parcelRepository, times(1)).save(any(Parcel.class));
    }

    @Test
    void updateShapeByParcelName_withValidInput_shouldUpdateShape() {
        String name = "Чипсы";
        char symbol = '1';
        String newShape = "111\n111";

        Parcel existingParcel = new Parcel(name, new char[][]{{'1', '1'}, {'1', '1'}}, symbol);
        when(parcelRepository.findParcelByName(name.trim().toLowerCase())).thenReturn(Optional.of(existingParcel));

        ParcelResponseDto result = defaultParcelService.updateShapeByParcelName(name, newShape);

        char[][] expectedShape = new char[][]{{'1', '1', '1'}, {'1', '1', '1'}};

        assertThat(result.getName()).isEqualTo(name);
        assertThat(result.getShape()).isEqualTo(expectedShape);
        assertThat(result.getSymbol()).isEqualTo(symbol);

        verify(parcelRepository, times(1)).findParcelByName(name.trim().toLowerCase());
        verify(parcelRepository, times(1)).save(existingParcel);
    }

    @Test
    void updateShapeByParcelName_withNonExistentParcel_shouldThrowParcelNotFoundException() {
        String name = "Некорректное имя";
        String newShape = "22\n22";

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

        assertThatThrownBy(() -> defaultParcelService.updateShapeByParcelName(name, newShape))
                .isInstanceOf(InvalidShapeException.class);

        verify(parcelRepository, never()).findParcelByName(anyString());
        verify(parcelRepository, never()).save(any(Parcel.class));
    }
}
