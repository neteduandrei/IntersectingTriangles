import exceptions.InvalidArguments;
import org.junit.Test;
import services.RectangleService;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class AppTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final App app;

    public AppTest() {
        System.setOut(new PrintStream(outputStreamCaptor));
        app = new App(new RectangleService());
    }

    @Test
    public void givenAnInvalidJsonFileThrowExpectedError() {
        Path path = Paths.get("src","test", "resources", "testRectanglesInvalid.json");

        try {
            app.processRectangles(new String[]{path.toString()});
        } catch(InvalidArguments ex) {
            assertEquals(ex.getMessage(), "Json file was badly formatted and could not be read!");
        }
    }

    @Test
    public void givenAValidJsonFileShowExpectedOutput() throws InvalidArguments {
        Path path = Paths.get("src","test", "resources", "testRectanglesValid.json");
        app.processRectangles(new String[]{path.toString()});

        assertEquals(
                "Input:\n" +
                        "\t1: (0,0), delta_x=100, delta_y=100\n" +
                        "\t2: (10,90), delta_x=10, delta_y=100\n" +
                        "\n" +
                        "Intersections:\n" +
                        "\t1: Between rectangle: [1, 2] at (10,90), delta_x=10, delta_y=10",
                outputStreamCaptor.toString().trim().replace("\r",""));
    }
}
