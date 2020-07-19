package graph;

//This class is used to hold the previous node of a node.
//This helps in creating the path from the start node to the end node
class NodePath<T>{

    //Fields.
    GraphNode<T> previousNode;

    //Constructor- Parameterized, to initialize the previousNode field.
    public NodePath(GraphNode<T> previousNode){
        this.previousNode=previousNode;
    }

    //Getter method.
    public GraphNode<T> getPreviousNode() {
        return previousNode;
    }
}