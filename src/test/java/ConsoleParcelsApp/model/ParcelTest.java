package ConsoleParcelsApp.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParcelTest {

    @Test
    public void testParcelInitialization() {
        String input = "777\n7777";
        Parcel parcel = new Parcel(input);

        assertEquals(2, parcel.getHeight());
        assertEquals(4, parcel.getWidth());
        assertEquals('7', parcel.getId());

        char[][] expectedShape = {
                {'7', '7', '7', ' '},
                {'7', '7', '7', '7'}
        };

        assertThat(expectedShape).isEqualTo(parcel.getShape());
    }

    @Test
    public void testParcelArea() {
        String input = "33\n33";
        Parcel parcel = new Parcel(input);

        assertThat(parcel.getArea()).isEqualTo(4);
    }

}
