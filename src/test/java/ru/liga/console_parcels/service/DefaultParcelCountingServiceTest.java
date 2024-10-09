package ru.liga.console_parcels.service;

import org.junit.jupiter.api.Test;
import ru.liga.console_parcels.dto.ParcelCountDto;
import ru.liga.console_parcels.dto.ParcelForPackagingDto;
import ru.liga.console_parcels.dto.TruckParcelCountDto;
import ru.liga.console_parcels.entity.Truck;
import ru.liga.console_parcels.service.impl.DefaultParcelCountingService;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class DefaultParcelCountingServiceTest {
    private DefaultParcelCountingService defaultParcelCountingService = new DefaultParcelCountingService();

    @Test
    void countParcelsInTrucks_emptyTrucks() {
        List<Truck> trucks = new ArrayList<>();
        List<TruckParcelCountDto> result = defaultParcelCountingService.countParcelsInTrucks(trucks);
        assertThat(result).isEmpty();
    }

    @Test
    void countParcelsInTrucks_emptyTruck() {
        List<Truck> trucks = List.of(new Truck(3, 3));
        List<TruckParcelCountDto> result = defaultParcelCountingService.countParcelsInTrucks(trucks);
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getParcelCounts()).isEmpty();
    }

    @Test
    void countParcelsInTrucks_oneTruckOneParcelType() {
        Truck truck = new Truck(3, 3);
        char[][] shape = {{'A', 'A', 'A'}, {'A', 'A', 'A'}};
        truck.place(new ParcelForPackagingDto(2, 3, shape), 0, 0);

        List<Truck> trucks = List.of(truck);
        List<TruckParcelCountDto> result = defaultParcelCountingService.countParcelsInTrucks(trucks);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTruckId()).isEqualTo(1);
        assertThat(result.get(0).getParcelCounts()).hasSize(1)
                .extracting(ParcelCountDto::getForm, ParcelCountDto::getQuantity)
                .containsExactly(tuple("A", 6));
    }

    @Test
    void countParcelsInTrucks_oneTruckMultipleParcelTypes() {
        Truck truck = new Truck(3, 3);
        truck.place(new ParcelForPackagingDto(1, 1, new char[][]{{'A'}}), 0, 0);
        truck.place(new ParcelForPackagingDto(1, 1, new char[][]{{'B'}}), 1, 0);
        truck.place(new ParcelForPackagingDto(1, 1, new char[][]{{'C'}}), 2, 0);

        List<Truck> trucks = List.of(truck);
        List<TruckParcelCountDto> result = defaultParcelCountingService.countParcelsInTrucks(trucks);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTruckId()).isEqualTo(1);
        assertThat(result.get(0).getParcelCounts()).hasSize(3)
                .extracting(ParcelCountDto::getForm, ParcelCountDto::getQuantity)
                .containsExactlyInAnyOrder(tuple("A", 1), tuple("B", 1), tuple("C", 1));
    }

    @Test
    void countParcelsInTrucks_multipleTrucks() {
        Truck truck1 = new Truck(2, 2);
        truck1.place(new ParcelForPackagingDto(1, 1, new char[][]{{'A'}}), 0, 0);

        Truck truck2 = new Truck(2, 2);
        truck2.place(new ParcelForPackagingDto(1, 1, new char[][]{{'B'}}), 0, 0);

        List<Truck> trucks = List.of(truck1, truck2);
        List<TruckParcelCountDto> result = defaultParcelCountingService.countParcelsInTrucks(trucks);

        assertThat(result).hasSize(2);

        assertThat(result.get(0).getTruckId()).isEqualTo(1);
        assertThat(result.get(0).getParcelCounts()).hasSize(1)
                .extracting(ParcelCountDto::getForm, ParcelCountDto::getQuantity)
                .containsExactly(tuple("A", 1));

        assertThat(result.get(1).getTruckId()).isEqualTo(2);
        assertThat(result.get(1).getParcelCounts()).hasSize(1)
                .extracting(ParcelCountDto::getForm, ParcelCountDto::getQuantity)
                .containsExactly(tuple("B", 1));
    }
}
