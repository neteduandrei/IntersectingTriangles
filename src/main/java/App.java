import exceptions.InvalidArguments;
import model.Rectangle;
import services.RectangleService;

import javax.json.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class App {
    private final RectangleService rectangleService;

    public App(RectangleService rectangleService) {
        this.rectangleService = rectangleService;
    }

    public static void main(String[] args) throws InvalidArguments {
        new App(new RectangleService()).processRectangles(args);
    }

    public void processRectangles(String[] args) throws InvalidArguments {
        Path jsonFilePath = getPathToJsonFile(args);
        JsonObject rectanglesJson = getRectanglesJson(jsonFilePath);
        List<Rectangle> rectangles = getRectangles(rectanglesJson);

        rectangleService.printRectangles(rectangles);
        rectangleService.printIntersections(rectangles);
    }

    private List<Rectangle> getRectangles(JsonObject rectanglesJson) throws InvalidArguments {
        JsonArray rectanglesJsonArray = rectanglesJson.getJsonArray("rects");

        try {
            return rectanglesJsonArray.stream().limit(10).map(JsonValue::asJsonObject)
                    .map(rectangleJson -> new Rectangle(rectangleJson.getInt("x"), rectangleJson.getInt("y"),
                            rectangleJson.getInt("delta_x"), rectangleJson.getInt("delta_y")))
                    .collect(Collectors.toList());
        } catch(NullPointerException ex) {
            throw new InvalidArguments("Json file was badly formatted and could not be read!");
        }
    }

    private Path getPathToJsonFile(String[] args) throws InvalidArguments {
        if(args.length != 1) {
            throw new InvalidArguments("A single command line argument, representing the json file, must be " +
                    "provided!");
        }
        return Paths.get(args[0]);
    }

    private JsonObject getRectanglesJson(Path pathToRectanglesFile) throws InvalidArguments {
        try (InputStream is = Files.newInputStream(pathToRectanglesFile); JsonReader rdr = Json.createReader(is)) {
            return rdr.readObject();
        } catch (IOException e) {
            throw new InvalidArguments("The command line argument is not a valid json file!");
        }
    }
}
