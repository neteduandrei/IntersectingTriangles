package services;

import model.Rectangle;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class RectangleServiceTest {

    public RectangleService rectangleService = new RectangleService();

    @Test
    public void doOverlapShouldBeTrue() {
        Rectangle rectangle1 = new Rectangle(0, 0, 100, 100);
        Rectangle rectangle2 = new Rectangle( 50, 50, 10, 10);

        Boolean result = rectangleService.doOverlap(rectangle1, rectangle2);

        assertTrue(result);

        rectangle1 = new Rectangle(0, 0, 100, 100);
        rectangle2 = new Rectangle( 90, 90, 50, 50);

        result = rectangleService.doOverlap(rectangle1, rectangle2);

        assertTrue(result);

        rectangle1 = new Rectangle(0, 0, 100, 100);
        rectangle2 = new Rectangle( -20, -20, 30, 30);

        result = rectangleService.doOverlap(rectangle1, rectangle2);

        assertTrue(result);
    }

    @Test
    public void doOverlapShouldBeFalse() {
        Rectangle rectangle1 = new Rectangle(0, 0, 100, 100);
        Rectangle rectangle2 = new Rectangle( 100, 100, 10, 10);

        Boolean result = rectangleService.doOverlap(rectangle1, rectangle2);

        assertFalse(result);
    }

    @Test
    public void getIntersectionShouldGetCorrectRectangle() {
        Rectangle rectangle1 = new Rectangle(0, 0, 100, 100);
        Rectangle rectangle2 = new Rectangle( 50, 50, 10, 10);

        Rectangle result = rectangleService.getIntersection(rectangle1, rectangle2);

        assertEquals(50, result.getTopLeftCorner().getX());
        assertEquals(60, result.getTopLeftCorner().getY());
        assertEquals(60, result.getDownRightCorner().getX());
        assertEquals(50, result.getDownRightCorner().getY());
    }

    @Test
    public void getIntersectionShouldGetNull() {
        Rectangle rectangle1 = new Rectangle(0, 0, 100, 100);
        Rectangle rectangle2 = new Rectangle( 100, 100, 10, 10);

        Rectangle result = rectangleService.getIntersection(rectangle1, rectangle2);

        assertNull(result);
    }

    @Test
    public void shouldCalculateIntersections() {
        Rectangle rectangle1 = new Rectangle(0, 0, 100, 100);
        Rectangle rectangle2 = new Rectangle( 10, 90, 10, 100);
        Rectangle rectangle3 = new Rectangle( 0, 110, 100, 10);

        List<Rectangle> rectangles = Arrays.asList(rectangle1, rectangle2, rectangle3);

        Map<List<Integer>, Rectangle> result = rectangleService.calculateIntersections(rectangles);

        assertEquals(2, result.size());
        assertEquals(new Rectangle(10, 90, 10, 10), result.get(Arrays.asList(1, 2)));
        assertEquals(new Rectangle(10, 110, 10, 10), result.get(Arrays.asList(2, 3)));
    }
}
