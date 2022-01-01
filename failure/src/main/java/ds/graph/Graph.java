package ds.graph;
import org.json.JSONObject; 
import org.json.JSONArray;
import java.nio.file.Files;
import java.io.IOException; 
import java.io.File;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentHashMap.KeySetView; 


public class Graph { 
    ConcurrentHashMap<String, MachineNode> nodes; 

    public Graph(){
        this("./data/graph/");
    }

    public Graph(String folderPath){  
        nodes = new ConcurrentHashMap<>(); 
        try{
            List<JSONObject> json = this.readJsonGraph(folderPath);  
            this.addEmptyNodes(json);
            this.addProperties(json);
        } catch(Exception e){
            e.printStackTrace();
        }
    } 
    /**
     * Reads all the files in the graph folder. 
     * @param filename Name of the file. 
     * @return Get's all the machine JSON objects. 
     * @throws IOException 
     */
    public List<JSONObject> readJsonGraph(String folderPath) throws IOException {  
        List<JSONObject> jsonFiles = new ArrayList<>(); 
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();  
        Arrays.stream(listOfFiles).forEach((file) -> {
            if (file.isFile()) {
                try { 
                    byte[] encoded = Files.readAllBytes(file.toPath()); 
                    jsonFiles.add(new JSONObject(new String(encoded))); 
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return jsonFiles; 
    }


    /**
     * Fills the this.nodes variable with empty nodes that just contains the id. 
     * Just after adding all the nodes to the HashMap, it's possible to add the next and previous nodes. 
     * @param json The json that contains the graph structure. 
     */
    public void addEmptyNodes(List<JSONObject> machinesJson){
        for(JSONObject machineJson : machinesJson){
            String input = machineJson.getString("input"); 
            String output = machineJson.getString("output"); 
            String id  = machineJson.getString("id");
            this.nodes.put(id, new MachineNode(id, input, output)); 
        }
    }

    /**
     * Adds the property for each node in the graph. 
     * @param json The json object describing the graph.
     */
    public void addProperties(List<JSONObject> machinesJson) {
        for(JSONObject machineJson : machinesJson){
            String id = machineJson.getString("id");
            MachineNode machineNode = nodes.get(id);  


            if(machineJson.has("nextMachineID")){
                String nextId = machineJson.getString("nextMachineID");
                MachineNode next = this.nodes.get(nextId);
                machineNode.setNext(next);
                next.setPrev(machineNode);
            }

            JSONArray sensors = machineJson.getJSONArray("sensors");
            for(Object sensorJson: sensors) { 
                machineNode.addSensor((JSONObject)sensorJson);
            }
        }
    }

    public MachineNode getMachineNode(String machineId){
        return this.nodes.get(machineId); 
    }

    public KeySetView<String, MachineNode> getMachines(){
        return this.nodes.keySet(); 
    }

    public List<MachineNode> getStartMachines(){
        List<MachineNode> startMachines = new ArrayList<>();

        for(MachineNode node: this.nodes.values()){
            if(node.getPrev() == null){
                startMachines.add(node);
            }
        }

        return startMachines;
    }

    public String toString(){ 
        StringBuilder s = new StringBuilder(); 
        nodes.keySet().forEach(id-> {
            s.append("==========================\n");
            s.append(nodes.get(id).toString());
        }); 

        return s.toString();
    }

}
