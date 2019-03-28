import java.util.ArrayList;

class Router {

    private ArrayList<Node> nodes;

    Router() {
        nodes = new ArrayList<>();

        nodes.add(new Node(0));
        nodes.add(new Node(1));
        nodes.add(new Node(2));
        nodes.add(new Node(3));
        nodes.add(new Node(4));
        nodes.add(new Node(5));
        nodes.add(new Node(6));
        nodes.add(new Node(7));

        connectNodes(nodes.get(0), nodes.get(1));
        connectNodes(nodes.get(1), nodes.get(3));
        connectNodes(nodes.get(3), nodes.get(2));
        connectNodes(nodes.get(3), nodes.get(4));
        connectNodes(nodes.get(3), nodes.get(6));
        connectNodes(nodes.get(5), nodes.get(6));
        connectNodes(nodes.get(7), nodes.get(6));
    }

    private void connectNodes(Node n1, Node n2) {
        n1.addNeighbour(n2);
        n2.addNeighbour(n1);
    }

    ArrayList<Node> getRoute(Node start, Node destination) {
        return null;
    }
}
