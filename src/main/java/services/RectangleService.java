package services;

import model.Point;
import model.Rectangle;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RectangleService {

    public Rectangle getIntersection(Rectangle first, Rectangle second) {
        if (!this.doOverlap(first, second)) {
            return null;
        }

        Point A1 = first.getTopLeftCorner();
        Point D1 = first.getDownRightCorner();
        Point A2 = second.getTopLeftCorner();
        Point D2 = second.getDownRightCorner();

        Point intersectionTopLeftCorner = new Point(Math.max(A1.getX(), A2.getX()), Math.min(A1.getY(), A2.getY()));
        Point intersectionDownRightCorner = new Point(Math.min(D1.getX(), D2.getX()), Math.max(D1.getY(), D2.getY()));

        return new Rectangle(intersectionTopLeftCorner, intersectionDownRightCorner);
    }

    public Boolean doOverlap(Rectangle first, Rectangle second) {
        Point A1 = first.getTopLeftCorner();
        Point D1 = first.getDownRightCorner();
        Point A2 = second.getTopLeftCorner();
        Point D2 = second.getDownRightCorner();

        if (A1.getX() >= D2.getX() || A2.getX() >= D1.getX())
            return false;

        return A1.getY() > D2.getY() && A2.getY() > D1.getY();
    }

    public void printRectangles(List<Rectangle> rectangleList) {
        System.out.println("Input:");
        for (int i = 0; i < rectangleList.size(); i++) {
            System.out.println(String.format("\t%d: %s", i + 1, rectangleList.get(i)));
        }
        System.out.println();
    }

    public void printIntersections(List<Rectangle> rectangles) {

        Map<List<Integer>, Rectangle> intersections = calculateIntersections(rectangles);

        System.out.println("Intersections:");

        int noRectangle = 0;

        for(Map.Entry<List<Integer>, Rectangle> entry : intersections.entrySet()) {
            noRectangle++;

            System.out.println(String.format("\t%d: Between rectangle: %s at %s", noRectangle,
                    entry.getKey(), entry.getValue()));
        }

        System.out.println();
    }

    private static class RectangleWithIds {
        final List<Integer> ids;
        final Rectangle rectangle;

        RectangleWithIds(List<Integer> ids, Rectangle rectangle) {
            this.ids = ids;
            this.rectangle = rectangle;
        }
    }

    public Map<List<Integer>, Rectangle> calculateIntersections(List<Rectangle> rectangles) {
        Map<List<Integer>, Rectangle> intersections = new HashMap<>();

        List<RectangleWithIds> rectanglesWithIds = IntStream.range(0, rectangles.size()).mapToObj(i ->
                new RectangleWithIds(Collections.singletonList(i + 1), rectangles.get(i))).collect(Collectors.toList());

        while (!rectanglesWithIds.isEmpty()) {
            List<RectangleWithIds> updatedRectangleWithIds = new ArrayList<>();

            for(int i = 0; i < rectanglesWithIds.size(); i++) {
                for(int j = i + 1; j < rectanglesWithIds.size(); j++) {
                    Rectangle intersection = getIntersection(rectanglesWithIds.get(i).rectangle, rectanglesWithIds.get(j).rectangle);
                    List<Integer> idsUnion = getIdsUnion(rectanglesWithIds.get(i).ids, rectanglesWithIds.get(j).ids);

                    if(intersection != null && !intersections.containsKey(idsUnion)) {
                        updatedRectangleWithIds.add(new RectangleWithIds(idsUnion, intersection));
                        intersections.put(idsUnion, intersection);
                    }
                }
            }
            rectanglesWithIds = updatedRectangleWithIds;
        }

        return intersections;
    }

    public List<Integer> getIdsUnion(List<Integer> ids1, List<Integer> ids2) {
        Set<Integer> uniqueIds = new HashSet<>(ids1);
        uniqueIds.addAll(ids2);
        return new ArrayList<>(uniqueIds);
    }
}
