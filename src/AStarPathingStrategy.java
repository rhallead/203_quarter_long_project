import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy
        implements PathingStrategy
{


    public List<Point> computePath(Point start, Point end,
                                   Predicate<Point> canPassThrough,
                                   BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors)
    {
        List<Point> path = new LinkedList<>();
        PriorityQueue<Node> openList = new PriorityQueue<>(Comparator.comparing(Node :: getF));
        HashMap openMap = new HashMap();
        HashMap closedList = new HashMap();
        Node currNode = new Node(start, null, 0.0, end);
        openList.add(currNode);
        openMap.put(currNode.getPos(), currNode);
        while (openList.size() != 0 && !withinReach.test(currNode.getPos(), end))
        {
            currNode = openList.remove();
            openMap.remove(currNode.getPos());
            List<Point> neighbors = potentialNeighbors.apply(currNode.getPos()).filter(canPassThrough).filter(p -> !closedList.containsKey(p)).collect(Collectors.toList());
            for (Point neighbor : neighbors)
            {
                Node newNode = new Node(neighbor, currNode, currNode.getG() + 1, end);
                boolean addIt = true;

                if(openMap.containsKey(newNode.getPos()))
                {
                    Node otherNode = ((Node)openMap.get(newNode.getPos()));
                    if (otherNode.getG() > newNode.getG())
                    {
                        openList.remove(otherNode);
                        openMap.remove(otherNode.getPos());
                    }
                    else
                        addIt = false;
                }

                if(addIt) {
                    openList.add(newNode);
                    openMap.put(newNode.getPos(), newNode);
                }
            }
            closedList.put(currNode.getPos(), currNode);
        }

        while(currNode.getPriorNode() != null)
        {
            path.add(0, currNode.getPos());
            currNode = currNode.getPriorNode();
        }

        return path;
    }
}

//Stream<Point> neighbors = potentialNeighbors.apply(currNode.)
// recurse through prior nodes if you got a solution
// will give you a path and take the first step and then compute
// if breaking when hit space bar
// need to check if problem is in computePath or whatever is calling computePath