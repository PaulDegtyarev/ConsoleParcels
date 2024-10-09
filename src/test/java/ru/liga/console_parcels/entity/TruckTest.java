package ru.liga.console_parcels.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.liga.console_parcels.dto.ParcelForPackagingDto;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TruckTest {
    private Truck truck;

    @BeforeEach
    void setUp() {
        truck = new Truck(5, 5);
    }

    @Test
    void findPosition_withCorrectInput_shouldReturnCorrectPoints() {
        ParcelForPackagingDto smallParcel = new ParcelForPackagingDto(1, 1, new char[][]{{'1'}});
        ParcelForPackagingDto mediumParcel = new ParcelForPackagingDto(2, 2, new char[][]{{'2', '2'}, {'2', '2'}});

        Optional<ParcelPosition> positionSmall = truck.findPosition(smallParcel);
        assertThat(positionSmall.isPresent()).isTrue();
        assertThat(positionSmall.get().getX()).isEqualTo(0);
        assertThat(positionSmall.get().getY()).isEqualTo(4);

        Optional<ParcelPosition> positionMedium = truck.findPosition(mediumParcel);
        assertThat(positionMedium.isPresent()).isTrue();
        assertThat(positionMedium.get().getX()).isEqualTo(0);
        assertThat(positionMedium.get().getY()).isEqualTo(3);
    }

    @Test
    void place_shouldPlaceParcelCorrectly() {
        ParcelForPackagingDto parcel = new ParcelForPackagingDto(2, 3, new char[][]{
                {'3', '3', '3'},
                {'3', '3', '3'}
        });

        truck.place(parcel, 1, 2);

        assertThat(truck.getSpace()[2][1]).isEqualTo('3');
        assertThat(truck.getSpace()[2][2]).isEqualTo('3');
        assertThat(truck.getSpace()[2][3]).isEqualTo('3');
        assertThat(truck.getSpace()[3][1]).isEqualTo('3');
        assertThat(truck.getSpace()[3][2]).isEqualTo('3');
        assertThat(truck.getSpace()[3][3]).isEqualTo('3');
    }
}
