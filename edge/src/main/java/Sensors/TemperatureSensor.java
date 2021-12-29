package Sensors;

import java.util.ArrayList;

public class TemperatureSensor extends Sensor {

    Type type;
    float baseline;
    float baselineVariance;
    float posX;
    float posY;


    TemperatureSensor(float positionX, float positionY) {
        super();
        this.type = Type.VIBRATION;
        this.posX = positionX;
        this.posY = positionY;
        this.baseline = 40; //ºC
        this.baselineVariance = (float) (this.baseline * 0.05); // 5% variation
    }

    @Override
    public void generateData() {

    }

    public ArrayList<Float> getData() {
        ArrayList<Float> temperature = new ArrayList<>();
        if (!this.on){
            temperature.add((float) -1.0);
        }
        else {
            float random = (float) (-this.baselineVariance + Math.random() *  (this.baselineVariance + this.baselineVariance));
            temperature.add(this.baseline + random);
        }
        return temperature;
    }

    public void chaosUpSensor() {
        float up = (float) (this.baseline * 0.1); //ups the baseline 10%
        this.baseline += up;
    }

    public void chaosDownSensor() {
        float down = (float) (this.baseline * 0.1);
        this.baseline -= down;
    }
}
