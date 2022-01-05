import Batch from "./Batch.js"

export default class Machine {
  //TODO: Add more arguments based on the factory.json
  constructor(machineID) {
    this.id = machineID
    this.sensors = []
    this.isOccupied = false;
  }

  isOccupied(){
    return this.isOccupied;
  }
  
  setStatus(status) {
    this.status = status
  }

  setDefectProbability(defProb) {
    this.defectProbability = defProb
  }

  setInput(inp) {
    this.input = inp
  }

  setOutput(out) {
    this.output = out
  }

  setTimePerBatch(time){
    this.timePerBatch =time;
  }
  setNextMachineID(id) {
    this.nextMachineID = id
  }

  addSensor(sensor) {
    this.sensors.push(sensor)
  }

  toggleOccupationOff(){
    this.isOccupied = false;
  }

  update() {
    console.log("updating")
  }

  treatBatch(batch){
    let rand = Math.floor(Math.random() * 101);

    if(rand < this.defectProbability){
      batch.setHasDefect(true);
    }

    batch.setMaterialName(this.output);
    batch.setCurrentMachineID(this.nextMachineID);
    
    this.isOccupied = true;

    return batch;
  }

  //TODO: Add more information about the machine
  getRepresentation() {
    let representation = `I'm okay;`
    return representation
  }
}
