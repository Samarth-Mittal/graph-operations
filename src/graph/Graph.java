package graph;

import java.util.ArrayList;
import java.util.List;

//Object of this class is used as the graph.
class Graph<T>{

    //Fields
    List<GraphNode<T>> nodes;

    //Constructor
    public Graph(){
        nodes=new ArrayList<GraphNode<T>>();
    }

    //Method to add a node to the graph.
    public boolean addGraphNode(T value){
        if(isNodeValuePresent(value)){
            return false;
        }else {
            GraphNode<T> node=new GraphNode<T>(value);
            this.nodes.add(node);
            return true;
        }
    }

    //Getter method for nodes field.
    public List<GraphNode<T>> getNodes() {
        return nodes;
    }

    //Method to remove a node from the graph.
    public boolean removeGraphNode(T value){
        if(isNodeValuePresent(value)){
            GraphNode<T> node_to_remove=getNodeWithValue(value);

            //Removing node from graph
            nodes.remove(node_to_remove);

            //Removing the node from the neighbours of node
            for(GraphNode<T> temp: node_to_remove.getNeighbours()){
                temp.removeNeighbour(node_to_remove);
            }

            return true;

        }else{
            return false;
        }
    }

    //Method to add an edge to the graph.
    public boolean addGraphEdge(T value1, T value2, Direction direction){

        //Both nodes must exist in the graph.
        if(isNodeValuePresent(value1)&&isNodeValuePresent(value2)) {
            //Both nodes are present.
            GraphNode<T> node1 = getNodeWithValue(value1);
            GraphNode<T> node2 = getNodeWithValue(value2);

            //Edge already present.
            if(node1.containsNeighbour(node2)){
                return false;
            }
            //Adding edge.
            node1.addNeighbour(node2);
            if(direction==Direction.UNDIRECTED){
                node2.addNeighbour(node1);
            }

            return true;
        }else{
            return false;
        }
    }

    //Method to remove an edge from the graph.
    public boolean removeGraphEdge(T value1, T value2, Direction direction){
        if(isNodeValuePresent(value1)&&isNodeValuePresent(value2)){
            //Both values are present.
            GraphNode<T> node1=getNodeWithValue(value1);
            GraphNode<T> node2=getNodeWithValue(value2);
            if(node1.getNeighbours().contains(node2)){
                node1.getNeighbours().remove(node2);
                if(direction==Direction.UNDIRECTED){
                    node2.getNeighbours().remove(node1);
                }
            }else{
                return false;
            }
            return true;
        }
        return false;
    }

    //Method to remove the graph.
    public List<GraphNode<T>> deleteGraph(){
        for(GraphNode<T> node: nodes){
            node.removeAllNeighbours();
        }
        nodes.clear();
        nodes=null;
        return nodes;
    }

    //Method to get the node with the given value.
    public GraphNode<T> getNodeWithValue(T value) {
        GraphNode<T> node=null;
        for(GraphNode<T> temp: nodes){
            if(temp.getValue().equals(value)){
                node=temp;
                break;
            }
        }
        return node;
    }

    //Method to check if a node with the given value is present in the graph or not.
    public boolean isNodeValuePresent(T value) {
        for(GraphNode<T> node: nodes){
            if(node.getValue().equals(value)){
                return true;
            }
        }
        return false;
    }
}