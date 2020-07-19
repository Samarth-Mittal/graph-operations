package graph;

import java.util.ArrayList;
import java.util.List;

//Object of this class is used as a node of a graph.
class GraphNode<T>{

    //Fields
    T value;
    List<GraphNode<T>> neighbours;

    //Setter method for value field.
    public GraphNode(T value){
        this.value=value;
        neighbours=new ArrayList<GraphNode<T>>();
    }

    //Getter method for value field.
    public T getValue() {
        return value;
    }

    //Getter method for neighbours field.
    public List<GraphNode<T>> getNeighbours() {
        return neighbours;
    }

    //Setter method for neighbours field is not required as neighbours of the node(s) are added using the addGraphNode() in class Graph.
//    public void setNeighbours(List<GraphNode<T>> neighbours) {
//        this.neighbours = neighbours;
//    }

    //Method to add neighbour node to list neighbours.
    public void addNeighbour(GraphNode<T> neighbour){
        this.neighbours.add(neighbour);
    }

    //Method to check if a particular node is present in the list of neighbours of the calling node.
    public boolean containsNeighbour(GraphNode<T> neighbour){
        if(this.neighbours.contains(neighbour)){
            return true;
        }
        return false;
    }

    //Method to remove a node from the list of neighbours.
    public void removeNeighbour(GraphNode<T> node_to_remove) {
        this.getNeighbours().remove(node_to_remove);
    }

    //Method to clear the list neighbours.
    public void removeAllNeighbours() {
        this.getNeighbours().clear();
    }
}