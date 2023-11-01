import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class AStarPathingStrategy
        implements PathingStrategy {


    public List<Point> computePath(Point start, Point end, Predicate<Point> canPassThrough, BiPredicate<Point, Point> withinReach, Function<Point, Stream<Point>> potentialNeighbors) {
        //know start and end of path
        List<Point> path = new LinkedList<Point>();
        //create closed and open list
        HashMap<Point, ANode> closedList = new HashMap<>();
        HashMap<Point, ANode> openList = new HashMap<>();
        PriorityQueue<ANode> queue = new PriorityQueue<>((node1, node2) -> {
            if (node1.getF() < node2.getF()) return -1;
            if (node1.getF() > node2.getF()) return 1;
            return 0;
        });
       //add start node to open list and mark it as current node
        ANode current = new ANode(start.x, start.y, null, 0, end);
        openList.put(start, current);
        queue.add(current);
        while (!openList.isEmpty()) {
            // analyze adjacent nodes if valid and are not on closed list
            Stream<Point> neighbors = potentialNeighbors.apply(current.getPoint()).filter(canPassThrough).filter(p -> !closedList.containsKey(p));
            List<Point> points = neighbors.collect(Collectors.toList());
            ArrayList<ANode> node = new ArrayList<>();
            // creating list of valid adjacent not on closed list nodes
            for (Point p : points) {
                node.add(new ANode(p.x, p.y, current, current.getG() + 1, end));
            }
            // for looping through each valid neighbor
            for (ANode n : node) {
                 // Check if point already in openList
                Point nodePoint = n.getPoint();
                if (openList.containsKey(nodePoint)) {
                    //if point is in openList then check if new g value better than previous g value
                    ANode openNode = openList.get(nodePoint);
                    if(n.getG() > openNode.getG()) {
                        openList.remove(nodePoint);
                        queue.remove(n);
                        // if better get rid of old node
                    }
                    else {
                        // if old node has better g value go get next node
                        continue;
                    }
                }
                openList.put(n.getPoint(), n);
                queue.add(n);
            }
            openList.remove(current.getPoint());
            queue.remove(current);

            closedList.put(current.getPoint(), current);

            current = queue.peek();
            // open list get node with smallest f value and make it current node
            // current = openList.values().stream().min(Comparator.comparing(ANode::getF)).orElse(null);
            if(current == null) return path;
            if (withinReach.test(current.getPoint(), end)){ break; }

        }
        if(current.getH() <= 1) {
            while (current.getPrev() != null && !current.getPoint().equals(start)) {
                path.add(0, current.getPoint());
                current = current.getPrev();
            }
        }

        return path;
    }
}