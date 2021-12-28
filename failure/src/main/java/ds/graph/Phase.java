package ds.graph;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class Phase {
    private String id; 
    private ConcurrentHashMap<String, MachineNode> outMachines;
    private ConcurrentHashMap<String, Integer> amounts; 
    
    public Phase(String id){
        this.id = id; 
        this.outMachines = new ConcurrentHashMap<>();
        this.amounts = new ConcurrentHashMap<>();
    } 

    public ConcurrentHashMap<String, MachineNode> getOutMachines() {
        return outMachines;
    }

    public void addAmount(String prod, Integer amount){
        amounts.put(prod, amount); 
    }

    public void addOutMachine(String machineId, MachineNode machineNode){
        this.outMachines.put(machineId, machineNode);
    }

    public ProductionState getProductionState(){
        HashMap<String, Integer> currentAmounts = new HashMap<>();
        HashMap<String, Integer> defectiveAmounts = new HashMap<>();

        int totalProducts = Integer.MAX_VALUE;
        int defectiveProducts = Integer.MAX_VALUE;

        // Get total subproducts produced in this phase
        for(String machineId: outMachines.keySet()){
            MachineNode machine = outMachines.get(machineId);
            String output = machine.getOutput();
            
            Integer currentAmount = currentAmounts.getOrDefault(output,0);
            Integer defectiveAmount = defectiveAmounts.getOrDefault(output,0);

            currentAmount += machine.getProductCount();
            currentAmounts.put(output, currentAmount);

            defectiveAmount += machine.getDefectiveCount();
            defectiveAmounts.put(output, defectiveAmount);
        }

        // Verify how many output products were produced
        for(String output: amounts.keySet()){
            Integer currentAmount = currentAmounts.getOrDefault(output,0);
            Integer defectiveAmount = defectiveAmounts.getOrDefault(output,0);

            Integer producedSubProducts = currentAmount / amounts.get(output);
            Integer defectiveSubProducts = defectiveAmount / amounts.get(output);

            totalProducts = Math.min(producedSubProducts.intValue(), totalProducts);
            defectiveProducts = Math.min(defectiveSubProducts.intValue(), defectiveProducts);
        }

        return new ProductionState(totalProducts, defectiveProducts);
    }

    public String toString(){
        StringBuilder builder = new StringBuilder(); 
        builder.append("[ID]:").append(id).append("\n");
        
        builder.append("[OUT MACHINES]: "); 
        this.outMachines.keySet().forEach(machineId -> {
            builder.append(machineId).append(" "); 
        }); 
        builder.append("\n");
        this.amounts.keySet().forEach(machineId -> {
            builder.append("- "); 
            builder.append(machineId).append(": ").append(this.amounts.get(machineId)); 
            builder.append("\n"); 
        });
        
        return builder.toString(); 
    }

    public String getState(){
        StringBuilder builder = new StringBuilder(); 
        builder.append("Production Phase ").append(id).append(" :: ");
        builder.append(this.getProductionState());
        
        return builder.toString(); 
    }
}