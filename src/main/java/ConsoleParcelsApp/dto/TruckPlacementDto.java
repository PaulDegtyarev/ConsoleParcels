package ConsoleParcelsApp.dto;

import ConsoleParcelsApp.model.Point;
import ConsoleParcelsApp.model.Truck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public class TruckPlacementDto {
    private Truck truck;
    private Point position;
}
