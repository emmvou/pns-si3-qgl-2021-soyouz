package fr.unice.polytech.si3.qgl.soyouz.classes.geometry.shapes;

import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.soyouz.classes.geometry.Point2d;
import fr.unice.polytech.si3.qgl.soyouz.classes.geometry.Position;

/**
 * Polygon shape.
 */
public class Polygon implements Shape
{
    private final double orientation;
    private final Point2d[] vertices;

    /**
     * Constructor.
     *
     * @param orientation The orientation of the polygon.
     * @param vertices All points that compose the shape.
     */
    public Polygon(@JsonProperty("orientation")double orientation,@JsonProperty("vertices") Point2d[] vertices)
    {
        this.orientation = orientation;
        this.vertices = vertices;
    }

    /**
     * Getter.
     *
     * @return the Orientation of the Polygon.
     */
    public double getOrientation()
    {
        return orientation;
    }

    /**
     * Getter.
     *
     * @return all Points that compose the Polygon.
     */
    public Point2d[] getVertices()
    {
        return vertices.clone();
    }

    static double IsLeft(Point2d p0, Point2d p1, Point2d p2)
    {
        return (p1.getX() - p0.getX()) * (p2.getY() - p0.getY())
            - (p2.getX() - p0.getX()) * (p1.getY() - p0.getY());
    }

    @Override
    public boolean contains(Point2d p)
    {
        var wn = 0; // the winding number counter
        var j = vertices.length - 1;

        // loop through all edges of the polygon
        for (var i = 0; i < vertices.length; j = i++)
        {
            if (p.isOnLine(vertices[i], vertices[j]))
                return true;
            if (vertices[i].getY() <= p.getY())
            {
                // start y <= P.y
                if (vertices[j].getY() > p.getY()) // an upward crossing
                    if (IsLeft(vertices[i], vertices[j], p) > 0) // P left of  edge
                        ++wn; // have  a valid up intersect
            }
            else
            {
                // start y > P.y (no test needed)
                if (vertices[j].getY() <= p.getY()) // a downward crossing
                    if (IsLeft(vertices[i], vertices[j], p) < 0) // P right of  edge
                        --wn; // have  a valid down intersect
            }
        }

        return wn != 0;
    }
}
