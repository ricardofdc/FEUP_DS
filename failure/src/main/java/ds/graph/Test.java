package ds.graph;
public class Test {
    public static void main(String[] args){
        Graph graph = new Graph("./data/graph.json"); 
        Phases phase = new Phases("./data/phases.json", graph);
        //System.out.println(graph.toString());
        System.out.println(phase);
    }
}