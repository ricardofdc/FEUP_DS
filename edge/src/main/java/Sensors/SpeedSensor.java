package Sensors;

import java.util.ArrayList;

public class SpeedSensor extends Sensor {


    Type type;
    float baseline;
    float baselineVariance;
    float posX;
    float posY;


    SpeedSensor(float positionX, float positionY) {
        super();
        this.type = Type.VIBRATION;
        this.posX = positionX;
        this.posY = positionY;
        this.baseline = 10; //antennas/min
        this.baselineVariance = (float) (this.baseline * 0.05); // 5% variation
    }

    @Override
    public void generateData() {

    }

    public ArrayList<Float> getData() {
        ArrayList<Float> speed = new ArrayList<>();
        if (!this.on) {
            speed.add((float) -1.0);
        }
        else {
            float random = (float) (-this.baselineVariance + Math.random() *  (this.baselineVariance + this.baselineVariance));
            speed.add(this.baseline + random);
        }
        return speed;
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
