import java.util.ArrayList;

class Node {

    private int id;

    private int x;
    private int y;

    private ArrayList<Node> neighbourNodes;

    Node(int id) {
        this.id = id;
        neighbourNodes = new ArrayList<>();
    }

    Node(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        neighbourNodes = new ArrayList<>();
    }

    void addNeighbour(Node n) {
        neighbourNodes.add(n);
    }

    int getX() {
        return x;
    }

    int getY() {
        return y;
    }

    int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() != Node.class) return false;
        Node n = (Node) obj;
        return n.getId() == this.getId();
    }
}
