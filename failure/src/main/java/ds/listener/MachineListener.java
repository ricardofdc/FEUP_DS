package ds.listener;
import ds.graph.Graph;
import ds.graph.sensor.*;
import ds.state.*; 
import ds.state.sensor.*;
import ds.failures.*;

import java.util.*;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

public class MachineListener extends Listener {
    private State state; // Stores the current state of all machines.
    public static final Integer INFO_SIZE = 5; // Number of previous states to save 
    public static final Integer FUTURE_BEHAVIOR = 2; // Number of previous with increasing/decreasing values to send an alert

    private FailurePublisher failurePublisher; 

    public MachineListener(Graph graph) {
        super("production/machine", graph);
        this.state = new State(graph); 
        // TODO: perhaps in the future implement send to the machine topic.
        this.failurePublisher = new FailurePublisher("failure");
        this.failurePublisher.init();
    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        JSONObject messageParsed = new JSONObject(new String(message.getPayload()));

        try {
            this.updateState(messageParsed);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Updates a machine state.
     * @param messageParsed JSONObject with the message content.
     */
    public void updateState(JSONObject messageParsed){
        String machineID = messageParsed.get("machineID").toString(); 
        String sensorID = messageParsed.get("sensorID").toString();
        SensorState sensorState = this.state.getMachineState(machineID).getSensorState(sensorID);
        JSONObject measureValues  = messageParsed.getJSONObject("values"); 

        measureValues.keySet().forEach((key) -> {
            float measureValue = measureValues.getFloat(key);
            boolean isMeasureAllowed = sensorState.updateMeasureState(key, measureValue);

            if (!isMeasureAllowed) sendFailure(messageParsed, sensorState, key);
            else analyseFutureBehavior(messageParsed, sensorState, key);
        });
    }

    public void sendFailure(JSONObject messageParsed, SensorState sensorState, String measureType){  
        String machineID = messageParsed.get("machineID").toString(); 
        String readingTime = messageParsed.get("readingTime").toString();
        float measureValue = messageParsed.getJSONObject("values").getFloat(measureType);
        Failure failure = new Failure(sensorState, machineID, readingTime); 
        Values expectedValues = sensorState.getMeasureState(measureType).getExpectedValues();

        //TODO: change severity according to what the clients considers high priority.
        if (measureValue > expectedValues.getMax()) {
            failure.setFailureType(FailureType.ABOVE_EXPECTED);
            failure.setFailureSeverity(FailureSeverity.HIGH);
            failure.setDescription("Detected value: " + measureValue);
        }
        else if (measureValue < expectedValues.getMin()) {
            failure.setFailureType(FailureType.UNDER_EXPECTED);
            failure.setFailureSeverity(FailureSeverity.HIGH);
            failure.setDescription("Detected value: " + measureValue);
        }

        System.out.println(failure.getMessage());
        this.failurePublisher.publish(failure.getMessage());
    }

    private void analyseFutureBehavior(JSONObject messageParsed, SensorState sensorState, String measureType){  
        String machineID = messageParsed.get("machineID").toString(); 
        String readingTime = messageParsed.get("readingTime").toString();
        float measureValue = messageParsed.getJSONObject("values").getFloat(measureType);
        Failure failure = new Failure(sensorState, machineID, readingTime); 
        MeasureState measureState = sensorState.getMeasureState(measureType);
        
        Queue<Float> measures = measureState.getLastMeasures();    
        Iterator<Float> iterator = measures.iterator();

        Float prevVal = iterator.next();
        int numIncrease = 0;
        int numDecrease = 0;

        System.out.println("Values: \n-> " + prevVal);
        while (iterator.hasNext()) {
            Float currentVal = iterator.next();
            System.out.print("-> " + currentVal + "\n");

            if (currentVal >= prevVal) {
                numIncrease += 1;
                numDecrease = 0;
            } 
            else {
                numDecrease += 1;
                numIncrease = 0;
            } 

            prevVal = currentVal;
        }
        System.out.println("\n Consecutive Increase: " + numIncrease);
        float proximityMax = measureState.getMaxProximity();
        this.sendFailureFuture(failure, proximityMax, numIncrease, FailureType.ABOVE_EXPECTED, "increasing");

        System.out.println("\n Consecutive Decrease: " + numDecrease);
        float proximityMin = measureState.getMinProximity();
        this.sendFailureFuture(failure, proximityMin, numDecrease, FailureType.UNDER_EXPECTED, "decreasing");
    }

    private void sendFailureFuture(Failure failure, float proximity, int numConsecutive, FailureType type, String log) {
        if (proximity < 10.0) {
            if (numConsecutive > FUTURE_BEHAVIOR) {          
                failure.setFailureType(type);
                failure.setFailureSeverity(FailureSeverity.MEDIUM);
                failure.setDescription("Values " + log + " too fast and near the max limit");
            } else {
                failure.setFailureType(type);
                failure.setFailureSeverity(FailureSeverity.LOW);
                failure.setDescription("Values near the max limit");
            }
        } else if (numConsecutive > FUTURE_BEHAVIOR) {
                failure.setFailureType(type);
                failure.setFailureSeverity(FailureSeverity.LOW);
                failure.setDescription("Values " + log + " too fast");
        } else {
            return;
        }

        System.out.println(failure.getMessage());
        this.failurePublisher.publish(failure.getMessage());
    }
}
