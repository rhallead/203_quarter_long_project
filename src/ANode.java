import java.awt.geom.Point2D;
import java.util.Objects;

public class ANode {
    private Point pos;
    private Point end;
    private ANode prev;
    private double g;
    private double h;
    private double f;

    public ANode(int x, int y, ANode prev, double g, Point end) {
        this.pos = new Point(x, y);
        this.prev = prev;
        this.g = g;
        this.end = end;
        this.h = Point2D.distance(pos.x, pos.y, end.x, end.y);
        this.f = g + h;
    }

    public Point getPoint() {
        return pos;
    }
    public double getG() {
        return g;
    }

    public double getH() {
        return h;
    }

    public double getF() {
        return f;
    }

    public ANode getPrev() {
        return prev;
    }
    public int hashCode() {
        return Objects.hash(pos, end, prev, g, h, f);
    }

    public boolean equals(Object other) {
        if(other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        ANode nod = (ANode) other;
        return Objects.equals(pos, nod.pos) && Objects.equals(end, nod.end) &&
                Objects.equals(prev, nod.prev) && g == nod.g &&
                h == nod.h && f == nod.f;
    }


}