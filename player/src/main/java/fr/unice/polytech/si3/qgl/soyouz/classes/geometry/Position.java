package fr.unice.polytech.si3.qgl.soyouz.classes.geometry;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import fr.unice.polytech.si3.qgl.soyouz.classes.utilities.Pair;

import java.util.Objects;

import static java.lang.Math.*;

/**
 * Position of a Shape : Defined by the coords of its central Point and its orientation.
 */
public class Position extends Point2d
{
    public static final Position ZERO = new Position(0, 0, 0);

    private final double orientation;

    /**
     * Constructor.
     *
     * @param x           Abscissa of the point.
     * @param y           Ordinate of the point.
     * @param orientation Orientation of the point.
     */
    public Position(@JsonProperty("x") double x,
                    @JsonProperty("y") double y,
                    @JsonProperty(value = "orientation", defaultValue = "0") double orientation)
    {
        super(x, y);
        this.orientation = orientation;
    }

    public static Position fromPolar(double r, double theta)
    {
        return new Position(r * Math.cos(theta), r * Math.sin(theta), 0);
    }

    /**
     * Getter.
     *
     * @return the abscissa of the central Point.
     */
    public double getX()
    {
        return x;
    }

    /**
     * Getter.
     *
     * @return the ordinate of the central Point.
     */
    public double getY()
    {
        return y;
    }

    /**
     * Getter.
     *
     * @return the orientation of the Shape.
     */
    public double getOrientation()
    {
        return orientation;
    }

    /**
     * Determine the distance between two points.
     *
     * @param pos The second point.
     * @return a pair that represent the distance between those two points.
     */
    public Pair<Double, Double> getDistance(Position pos)
    {
        return Pair.of(abs(this.getX() - pos.getX()), abs(this.getY() - pos.getY()));
    }

    /**
     * Determine the distance between two positions.
     *
     * @param pos The second position.
     * @return the distance between this and pos.
     */
    public double getLength(Position pos)
    {
        return sqrt(pow(this.getX() - pos.getX(), 2) + pow(this.getY() - pos.getY(), 2));
    }

    /**
     * Compare two position and determine if they are equals or not.
     *
     * @param o The second position.
     * @return true if they are, false otherwise.
     */
    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Double.compare(position.x, x) == 0 &&
            Double.compare(position.y, y) == 0 &&
            Double.compare(position.orientation, orientation) == 0;
    }

    /**
     * Getters.
     *
     * @return the hashcode of the position.
     */
    @Override
    public int hashCode()
    {
        return Objects.hash(x, y, orientation);
    }

    /**
     * Determine if a point is facing another (with a 0.01 error %).
     *
     * @param pos The second point position.
     * @return true if they are facing, false otherwise.
     */
    public boolean isFacingPosition(Position pos)
    {
        return abs(atan2(pos.getY() - this.getY(), pos.getX() - this.getX()) - this.getOrientation()) < 0.01;
    }

    /**
     * Get a new position after adding some parameters.
     *
     * @param x   A X translation.
     * @param y   A Y translation.
     * @param rot A rotation.
     * @return the new position after adding the new parameters.
     */
    public Position add(double x, double y, double rot)
    {
        return new Position(this.x + x, this.y + y, this.orientation + rot);
    }

    /**
     * Add a Position to another.
     *
     * @param other The second position
     * @return the new position.
     */
    public Position add(Position other)
    {
        return add(other.x, other.y, other.orientation);
    }

    /**
     * Applies a scalar factor to the position.
     *
     * @param d factor
     * @return the scaled position
     */
    public Position mul(double d)
    {
        return new Position(this.x * d, this.y * d, this.orientation * d);
    }

    @Override
    public String toString()
    {
        return "{" +
            "x=" + x +
            ", y=" + y +
            "; " + orientation +
            " rad}";
    }
}
