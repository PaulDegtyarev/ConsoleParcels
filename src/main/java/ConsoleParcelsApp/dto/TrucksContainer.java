package ConsoleParcelsApp.dto;

import ConsoleParcelsApp.model.Truck;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class TrucksContainer {
    @JsonProperty("trucks")
    private List<TruckLoad> trucks;
}
