package ds.graph;
import java.util.*;

public class MachineNode {  

    private String id;                          
    private List<MachineNode> prev;             // Parent machines.   [machine receives input from these]
    private List<MachineNode> next;             // Children machines. [machine output to these]
    HashMap<String, Double> defaultValues;      // The maximum values allowed by the machine. 
    // Necessary materials to make the machine produce the output. The key is the product id and the value is the amount.
    HashMap<String, Integer> inputs;            // Produces subproduct. 
    String output;    
    HashMap<String, Integer> currentInput; 
    Integer productCounter;                              // How many subproducts were produced. TODO. 


    public MachineNode(String id){
        this.id = id;
        this.next = new ArrayList<>();
        this.prev = new ArrayList<>();  
        this.defaultValues = new HashMap<>();  
        this.inputs = new HashMap<>();
    }

    public String getId(){
        return this.id;
    } 

    public String getOutput(){
        return this.output;
    } 

    public Integer getProductCount(){
        return this.productCounter;
    } 

    public void addPrevMachine(MachineNode machineNode){
        this.prev.add(machineNode);
    } 

    public void addNextMachine(MachineNode machineNode){
        this.next.add(machineNode);
    } 

    public void addDefault(String name, Double value){
        defaultValues.put(name, value); 
    }

    public void addInput(String id, Integer amount) {
        this.inputs.put(id, amount);
    }

    public void addOutput(String id){
        this.output = id; 
    } 

    public void addCurrentInput(String prod){
        Integer currAmountProd = this.currentInput.getOrDefault(prod, 0);
        this.currentInput.put(prod, currAmountProd + 1);  
        this.updateCounter();
    }

    public void updateCounter(){ 
        this.productCounter = Integer.MAX_VALUE; 
        for (String key: this.inputs.keySet()){ 
            Integer expectedAmount = this.inputs.get(key); 
            Integer currAmount = this.currentInput.get(key); 
            this.productCounter = Math.min(this.productCounter, currAmount / expectedAmount);  
        } 
    }

    public void cleanCurrentInput(){
        for (String key: this.currentInput.keySet()){
            this.currentInput.put(key, 0);
        }
    }

    public Integer getCurrentInput(String prod){
        return this.currentInput.get(prod);
    }

    /**
     * Case the product can be produced.
     * @return 
     */
    public boolean canProduce(){ 
        for (String key: this.currentInput.keySet()){ 
            Integer expectedValue = this.inputs.get(key); 
            Integer currentValue = this.currentInput.get(key); 
            if (expectedValue != currentValue) return false; 
       };    
        return true; 
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();  
        s.append("[ID]: ").append(this.id).append("\n"); 
 
        s.append("[PREV]: ");
        prev.forEach(prevNode-> {
            s.append(prevNode.getId()).append(" ");
        });  
        s.append("\n"); 

        s.append("[NEXT]: ");
        next.forEach(prevNode-> {
            s.append(prevNode.getId()).append(" ");
        });
        s.append("\n");
        s.append("[DFLT]:\n"); 
        defaultValues.keySet().forEach(propertyName -> {
            s.append("- ").append(propertyName).append(" ").append(defaultValues.get(propertyName)).append("\n"); 
        });
        s.append("[INPUT]:\n");
        inputs.keySet().forEach(prodId -> {
            s.append("- ").append(prodId).append(" : ").append(inputs.get(prodId)).append("\n");
        });
        s.append("[OUTPUT]:").append(this.output).append("\n"); 

        return s.toString();
    }

}
