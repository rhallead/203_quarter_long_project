import java.util.Objects;

public class Node {
    private Point pos;
    private Node priorNode;
    private double g;
    private double f;
    private double h;

    public Node(Point pos, Node priorNode, double g, Point end)
    {
        this.pos = pos;
        this.priorNode = priorNode;
        this.g = g;
        this.h = Math.sqrt(Math.pow(pos.x + end.x, 2) + Math.pow(pos.y + end.y, 2));
        calcF();
    }

    public int hashCode()
    {
        return Objects.hash(pos, priorNode, g, f, h);
    }

    public boolean equals(Object o)
    {
        if(o == null)
            return false;
        if(o.getClass() != this.getClass())
            return false;
        Node n = (Node) o;
        return pos.equals(n.getPos());
    }

    public double getG()
    {
        return g;
    }

    public double getF()
    {
        return f;
    }

    private void calcF()
    {
        f = g + h;
    }

    public Point getPos()
    {
        return pos;
    }

    public Node getPriorNode()
    {
        return priorNode;
    }
}
