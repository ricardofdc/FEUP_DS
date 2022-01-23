export const resolveStatus = (status) => {
  switch (status) {
    case 0:
      return "offline"
    case 1:
      return "online"
    default:
      return "unknown"
  }
}

export const colors = [
  "#F87171",
  "#FBBF24",
  "#2DD4BF",
  "#60A5FA",
  "#A78BFA",
  "#E11D48",
  "#34D399",
  "#38BDF8",
  "#0EA5E9",
  "#818CF8",
  "#C084FC",
  "#A3E635",
  "#4ADE80",
  "#FB7185",
  "#FB923C",
]

export const jsonStyle = {
  overflowY: "auto",
  overflowX: "hidden",
  padding: "1rem",
  width: "100%",
  fontSize: "small",
  lineHeight: 1,
  fontFamily: "JetBrains Mono, Consolas, sans-serif",
  backgroundColor: "inherit",
  borderRadius: "0.75rem",
}

export const options = [
  { name: "Machines", color: "bg-blue-400" },
  { name: "Sensors", color: "bg-teal-400" },
]

export const productionOptions = [
  { name: "Production", color: "bg-blue-400" },
  { name: "State", color: "bg-teal-400" },
]

const labels = [
  { type: "energy", color: "orange" },
  { type: "position", color: "teal" },
  { type: "velocity", color: "indigo" },
  { type: "temperature", color: "red" },
  { type: "vibration", color: "purple" },
  { type: "orientation", color: "lime" },
  { type: "speed ", color: "sky" },
]

export const findColor = (type) => {
  for (let i = 0; i < labels.length; i++) {
    let condition = type.toLowerCase().trim() === labels[i].type.toLowerCase().trim()
    if (condition) return labels[i].color
  }

  return "slate"
}

export const productionMockArray = [
  { productionRate: 1.2469087055009456 },
  {
    machineID: "machine1",
    nDefects: 0,
    defectRate: 0.0,
    nProducts: 5,
    meanProductionTime: 1616.6,
    productionRate: 0.5825,
  },
  {
    machineID: "machine2",
    nDefects: 1,
    defectRate: 1.0,
    nProducts: 1,
    meanProductionTime: 1476.6,
    productionRate: 0.0,
  },
  {
    machineID: "machine1",
    nDefects: 0,
    defectRate: 0.0,
    nProducts: 5,
    meanProductionTime: 1616.6,
    productionRate: 0.5825,
  },
  {
    machineID: "machine2",
    nDefects: 1,
    defectRate: 1.0,
    nProducts: 1,
    meanProductionTime: 1476.6,
    productionRate: 0.0,
  },
  {
    machineID: "machine1",
    nDefects: 0,
    defectRate: 0.0,
    nProducts: 5,
    meanProductionTime: 1616.6,
    productionRate: 0.5825,
  },
  {
    machineID: "machine2",
    nDefects: 1,
    defectRate: 1.0,
    nProducts: 1,
    meanProductionTime: 1476.6,
    productionRate: 0.0,
  },
  {
    machineID: "machine1",
    nDefects: 0,
    defectRate: 0.0,
    nProducts: 5,
    meanProductionTime: 1616.6,
    productionRate: 0.5825,
  },
  {
    machineID: "machine2",
    nDefects: 1,
    defectRate: 1.0,
    nProducts: 1,
    meanProductionTime: 1476.6,
    productionRate: 0.0,
  },
  {
    machineID: "machine1",
    nDefects: 0,
    defectRate: 0.0,
    nProducts: 5,
    meanProductionTime: 1616.6,
    productionRate: 0.5825,
  },
  {
    machineID: "machine2",
    nDefects: 1,
    defectRate: 1.0,
    nProducts: 1,
    meanProductionTime: 1476.6,
    productionRate: 0.0,
  },
]