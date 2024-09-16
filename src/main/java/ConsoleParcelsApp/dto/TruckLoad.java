package ConsoleParcelsApp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class TruckLoad {
    @JsonProperty("truckId")
    public int truckId;
    @JsonProperty("packages")
    public List<List<String>> packages;
}
