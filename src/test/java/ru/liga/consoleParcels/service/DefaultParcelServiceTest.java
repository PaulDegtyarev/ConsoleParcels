package ru.liga.consoleParcels.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.liga.consoleParcels.dto.ParcelRequestDto;
import ru.liga.consoleParcels.dto.ParcelResponseDto;
import ru.liga.consoleParcels.exception.ParcelNameConflictException;
import ru.liga.consoleParcels.exception.ParcelNotFoundException;
import ru.liga.consoleParcels.exception.WrongSymbolInShapeException;
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
}
