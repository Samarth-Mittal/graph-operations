package graph;

import java.util.*;

public class BFS_DFS_Graph {

    static Scanner sc;
    static Direction direction;

    public static void main(String args[]){

        sc = new Scanner(System.in);

        //For making Graph generic.
        int type_of_node_value=typeOfNodeValue();
        //The value received from typeOfNodeValue() depicts which type of graph is to be built;
        //0 is for character values, 1 is for integer values, and 2 is for string values.
        switch(type_of_node_value){
            case 0:
                main_run('c');
                break;
            case 1:
                main_run(1);
                break;
            case 2:
                main_run("string");
                break;
        }

    }

    //After getting the type_of_node_value(char/int/string), this method is called for getting other details, for the program execution.
    public static <T> void main_run(T obj_type) {

        //Fields
        Graph<T> graph;

        //Getting Type of Graph
        direction=getDirection();

        //Building the graph
        graph=buildGraph(direction);

        boolean choice=true;
        while(choice){
            System.out.println("Do you wish to modify any node or edge(1 for yes / 0 for no): ");
            int modify_choice=sc.nextInt();
            while(modify_choice==1){
                graph=modify(graph, direction);
                System.out.println("Do you wish to modify any more(1 for yes / 0 for no): ");
                modify_choice=sc.nextInt();
            }
            operation(graph);
            System.out.println("Do you wish to continue(1 for yes / 0 for no): ");
            int ch=0;
            try{
                ch=sc.nextInt();
            }catch (InputMismatchException e){
                System.out.println("Wrong Input Type");
            }
            switch(ch){
                case 1:
                    choice=true;
                    break;
                case 0:
                    choice=false;
                    break;
                default:
                    System.out.println("Wrong Input");
            }
        }

    }

    private static <T> Graph<T> modify(Graph<T> graph, Direction direction_local) {

        System.out.println("\tMODIFY MENU");
        System.out.println("0- Add a node.\n1- Remove a node\n2- Add an edge\n3- Remove an edge\n4- Re-enter graph.");
        System.out.println("Enter your choice of modification");
        int choice=sc.nextInt();

        switch(choice){
            case 0:
                System.out.println("Enter a node value:");
                T value0=(T)sc.next();
                if(graph.addGraphNode(value0)){
                    System.out.println("Node added successfully.");
                }else{
                    System.out.println("Node skipped.");
                }
                break;
            case 1:
                System.out.println("Enter the node value to remove:");
                T value1=(T)sc.next();
                if(graph.removeGraphNode(value1)){
                    System.out.println("Node added successfully.");
                }else{
                    System.out.println("Node skipped.");
                }
                break;
            case 2:
                System.out.println("Enter edge:");
                T value2=(T)sc.next();
                T value3=(T)sc.next();
                if(graph.addGraphEdge(value2, value3, direction_local)){
                    System.out.println("Edge added successfully.");
                }else{
                    System.out.println("Edge skipped.");
                }
                break;
            case 3:
                System.out.println("Enter edge:");
                T value4=(T)sc.next();
                T value5=(T)sc.next();
                if(graph.removeGraphEdge(value4, value5, direction_local)){
                    System.out.println("Edge removed successfully.");
                }else{
                    System.out.println("Edge skipped.");
                }
                break;
            case 4:
                graph.deleteGraph();
                System.out.println(graph.getNodes());
                graph=buildGraph(direction_local);
                break;
        }

        return graph;
    }

    //Method to get the user choice that which type of values will a graph node hold- char or int or string.
    private static int typeOfNodeValue() {

        int type;

        System.out.println("\tMENU");
        System.out.println("0- Characters\n1- Integers\n2- Strings");
        System.out.println("Enter the type of data, a graph node will contain: ");
        type=sc.nextInt();

        return type;

    }

    //This Method is where the traverse or search operation takes place, depending on the functionType.
    private static <T> void operation(Graph<T> graph) {

        FunctionType functionType;
        TraverseType traverseType;

        //Getting the type of method that is to be performed: traverse or search.
        functionType=getFunctionType();
        //Getting the type of traversal that is to be done: BFS or DFS.
        traverseType=getTraverseType();

        if(functionType==FunctionType.TRAVERSE){

            System.out.println("Enter the starting node:");
            T start_value=(T)sc.next();
            System.out.print("Path:- ");
            traverse(graph, start_value, traverseType);

        }else{
            System.out.println("Enter the starting node:");
            T start_value=(T)sc.next();
            System.out.println("Enter the ending node:");
            T finish_value=(T)sc.next();
            String path=searchPath(graph, start_value, finish_value, traverseType);
            System.out.println("Path:-"+ path);
        }

    }

    //Method to get the nodes and edges from the user, and to build the graph accordingly.
    private static <T> Graph<T> buildGraph(Direction direction) {

        Graph<T> graph=new Graph<T>();

        int no_of_nodes, no_of_edges;

        //Getting the nodes of the graph.
        System.out.println("Enter number of nodes in the graph");
        no_of_nodes=sc.nextInt();
        System.out.println("Enter node values-");
        int counter_nodes_added_successfully=0;

        for(int i=0;i<no_of_nodes;i++){
            T node_value=(T)sc.next();
            if(graph.addGraphNode(node_value)){
                counter_nodes_added_successfully++;
            }else{
                System.out.println("Node skipped. Please enter a valid node.");
                i--;
            }
        }
        System.out.println("\nTotal " + counter_nodes_added_successfully + " nodes added successfully.");

        //Getting the edges of the graph.
        System.out.println("Enter the number of edges in the graph");
        no_of_edges=sc.nextInt();
        System.out.println("Enter edges-");
        int counter_edges_added_successfully=0;

        for(int i=0;i<no_of_edges;i++){
            T node_value1=(T)sc.next();
            T node_value2=(T)sc.next();
            if(graph.addGraphEdge(node_value1, node_value2, direction)){
                counter_edges_added_successfully++;
            }else{
                System.out.println("Edge skipped. Please enter a valid edge.");
                i--;
            }
        }
        System.out.println("\nTotal " + counter_edges_added_successfully + " edges added successfully.");

        return graph;

    }

    //Method to traverse the graph starting from the node with value start_value.
    private static <T> void traverse(Graph<T> graph, T start_value, TraverseType traverseType) {

        if(graph.isNodeValuePresent(start_value)){
            //Node is present in the graph.

            //Creating a LL searchList to hold the nodes that are yet to be traversed.
            LinkedList<GraphNode<T>> searchList = new LinkedList<GraphNode<T>>();
            GraphNode<T> start_node=graph.getNodeWithValue(start_value);

            //Creating an object of NodePath class, to store the previous node of the current node, so as to form a path.
            Map<GraphNode<T>, NodePath<T>> nodePathMap=new HashMap<GraphNode<T>, NodePath<T>>();
            nodePathMap.put(start_node, new NodePath<T>(null));

            //Adding the first node, from where search has to be started.
            searchList.addFirst(start_node);

            //Processing all the elements of searchList till no other elements is left to traverse.
            while(!searchList.isEmpty()){

                GraphNode<T> current_node=searchList.getFirst();
                searchList.removeFirst();
                System.out.print(current_node.getValue()+" ");

                //traversing each neighbour of the current node.
                for(GraphNode<T> current_node_neighbour: current_node.getNeighbours()){
                    if(nodePathMap.containsKey(current_node_neighbour)){
                        //A cycle is forming at this neighbour, so do not add it to the path.
                        continue;
                    }else{
                        //Adding the current node as the path for current_node_neighbour.
                        nodePathMap.put(current_node_neighbour, new NodePath<T>(current_node));
                        if(traverseType==TraverseType.DEPTH_FIRST){
                            searchList.addFirst(current_node_neighbour);
                        }else{
                            searchList.addLast(current_node_neighbour);
                        }
                    }
                }
            }

            //The graph has been traversed at this point.
            System.out.println("\nPath completed");
        }else{
            //Node is not present in the graph.
            System.out.println("Node not present in the graph");
        }
    }

    //Method to search for a path between nodes with values start_value and finish_value.
    private static <T> String searchPath(Graph<T> graph, T start_value, T finish_value, TraverseType traverseType) {

        if(start_value.equals(finish_value)){
            //Start node and end node are same.
            return start_value.toString();
        }else if(graph.isNodeValuePresent(start_value)&&graph.isNodeValuePresent(finish_value)){
            //Both nodes are present in the graph.

            //Creating a LL searchList to hold the nodes that are yet to be traversed.
            LinkedList<GraphNode<T>> searchList = new LinkedList<GraphNode<T>>();
            GraphNode<T> start_node=graph.getNodeWithValue(start_value);

            //Creating an object of NodePath class, to store the previous node of the current node, so as to form a path.
            Map<GraphNode<T>, NodePath<T>> nodePathMap = new HashMap<GraphNode<T>, NodePath<T>>();
            nodePathMap.put(start_node, new NodePath<T>(null));

            //Adding the first node, from where search has to be started.
            searchList.addFirst(start_node);

            //Processing all the elements of searchList till no other elements is left to traverse.
            while(!searchList.isEmpty()){

                GraphNode<T> currentNode=searchList.getFirst();
                searchList.removeFirst();

                for(GraphNode<T> current_node_neighbour: currentNode.getNeighbours()){
                    //traversing each neighbour of the current node.
                    if(nodePathMap.containsKey(current_node_neighbour)){
                        //A cycle is forming at this neighbour, so do not add it to the path.
                        continue;
                    }else{
                        nodePathMap.put(current_node_neighbour, new NodePath<T>(currentNode));
                        if(current_node_neighbour.getValue().equals(finish_value)){
                            //Path found.
                            return convertPathToString(current_node_neighbour, nodePathMap);
                        }else{
                            //Path not found YET, add the neighbours to searchList according to traverseType.
                            if(traverseType==TraverseType.DEPTH_FIRST){
                                searchList.addFirst(current_node_neighbour);
                            }else{
                                searchList.addLast(current_node_neighbour);
                            }
                            //System.out.println("Added "+current_node_neighbour.getValue()+" to the search list.");
                        }
                    }
                }
            }
        }else{
            //Either one or both node(s) are not present in the graph.
            return "Node(s) not present in the graph.";
        }
        //No path is there between the given nodes.
        return "No path found between the given nodes.";
    }

    //Method to change the map(which holds the path) to a string for output purpose.
    private static <T> String convertPathToString(GraphNode<T> last_node, Map<GraphNode<T>, NodePath<T>> nodePathMap) {
        LinkedList<GraphNode<T>> path=new LinkedList<GraphNode<T>>();
        path.addFirst(last_node);
        GraphNode<T> previous=nodePathMap.get(last_node).getPreviousNode();
        while(previous!=null){
            path.addFirst(previous);
            previous=nodePathMap.get(previous).getPreviousNode();
        }

        StringBuilder pathString=new StringBuilder();
        for(int i=0;i<path.size();i++){
            pathString.append(path.get(i).getValue().toString()+" ");
        }
        return pathString.toString().trim();
    }

    //Method to get what the user wants to do: traverse from a node or search if a path is present between 2 nodes.
    //MethodType is an enum to hold the method selected.
    private static FunctionType getFunctionType() {

        FunctionType functionType;

        System.out.println("\n\tFUNCTION");
        System.out.println("0-Traverse the graph from a node.\n1-Search for a path between two nodes.");
        System.out.println("Enter your choice(0/1): ");
        int choice_of_function=sc.nextInt();

        if(choice_of_function==0){
            functionType=FunctionType.TRAVERSE;
        }else{
            functionType=FunctionType.SEARCH_PATH;
        }

        return functionType;

    }

    //Method to get the direction of edges in the graph: Directed graph or Undirected graph.
    //Direction is an enum which holds the direction of edges.
    private static Direction getDirection() {

        Direction direction;

        System.out.println("\tDIRECTION\n0-Directed Graph\n1-Undirected Graph\nEnter the type of graph to be created(0/1): ");
        int type_of_graph=sc.nextInt();

        if(type_of_graph==0){
            direction=Direction.DIRECTED;
        }else{
            direction=Direction.UNDIRECTED;
        }

        return direction;
    }

    //Method to get how to traverse the graph: BFS or DFS.
    //TraverseType is an enum which holds the choice.
    private static TraverseType getTraverseType() {

        TraverseType traverseType;

        System.out.println("\tTRAVERSE\n0- Breadth first Search\n1- Depth First Search\nEnter the type of search(0/1): ");
        int choice_of_search=sc.nextInt();

        if(choice_of_search==0){
            traverseType=TraverseType.BREADTH_FIRST;
        }else{
            traverseType=TraverseType.DEPTH_FIRST;
        }

        return traverseType;
    }

}