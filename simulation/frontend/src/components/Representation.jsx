import React, { useEffect, useMemo, useState } from "react"
import Machine from "./Machine"
import ReactJson from "react-json-view"
import Tabs from "./utilities/Tabs"
import InputBox from "./utilities/InputBox"
import DetailedSwitch from "./utilities/switches/DetailedSwitch"
import CopyClipboard from "./utilities/CopyClipboard"
import ForceGraph from "./ForceGraph"
import PhaseSwitch from "./utilities/switches/PhaseSwitch"
import { TrashIcon } from "@heroicons/react/outline"
import { jsonStyle, options } from "../utils"
import AlternateMachine from "./AlternateMachine"
import Scrollbar from "react-scrollbars-custom"
import Select from "./Select"
import Sensor from "./Sensor"

export default function Representation({ factoryInitialState, factoryFinalState }) {
  const [factoryInitial] = factoryInitialState //used for presets
  const [factoryFinal] = factoryFinalState //used for result
  const [phase, setPhase] = useState(false) //false is initial, true is final
  const [detailed, setDetailed] = useState(false)
  const [searchValue, setSearchValue] = useState("")
  const [displayType, setDisplayType] = useState(options[0])

  useEffect(() => {}, [displayType])

  const factory = useMemo(() => {
    if (phase) return factoryFinal
    else return factoryInitial
  }, [factoryFinal, factoryInitial, phase])

  const Tab = (props) => <>{props.children}</>

  return (
    <Tabs activeIndex={1}>
      {/* Graph schema */}
      <Tab label="Graph">
        <ForceGraph factory={factoryInitial} phaseHook={[phase, setPhase]} />
        <div className={`absolute bottom-8 right-6 z-50`}>
          {/* <PhaseSwitch hook={[phase, setPhase]} toggle={() => setPhase(!phase)} alt={true} /> */}
          <span className="p-2 rounded bg-indigo-400/50 text-white">Displaying initial state</span>
        </div>
      </Tab>

      {/* Detailed list view */}
      <Tab label="Detailed">
        <div className="grid w-full grid-cols-1 md:grid-cols-2 lg:grid-cols-3 xl:grid-cols-3 2xl:grid-cols-4 gap-4">
          <div className="px-1 flex flex-col space-y-4 md:space-y-0 md:flex-row items-center justify-between col-span-1 md:col-span-2 lg:col-span-3 xl:col-span-3 2xl:col-span-4 min-w-full">
            <div className="flex self-start">
              <Select selectedHook={[displayType, setDisplayType]} options={options} />
            </div>
            <div className="flex items-center justify-start md:justify-end space-x-6 w-full">
              <DetailedSwitch hook={[detailed, setDetailed]} toggle={() => setDetailed(!detailed)} />
              <PhaseSwitch hook={[phase, setPhase]} toggle={() => setPhase(!phase)} />
            </div>
          </div>
          {displayType.name === "Machines"
            ? phase
              ? factory.length === 0
                ? null
                : factory.machines.map((machine, machineIdx) => (
                    <AlternateMachine
                      data={machine}
                      key={`detailed-final-${machineIdx}`}
                      classnames="col-span-1 min-w-full"
                      isDetailed={detailed}
                    />
                  ))
              : factory.map((machine, machineIdx) => (
                  <Machine
                    data={machine}
                    key={`detailed-initial-${machineIdx}`}
                    classnames="col-span-1 min-w-full"
                    isDetailed={detailed}
                  />
                ))
            : null}
          {displayType.name === "Sensors"
            ? phase
              ? null
              : factory.map((machine, machineIdx) =>
                  machine.sensors.map((sensor, sensorIdx) => (
                    <Sensor
                      data={sensor}
                      parent={machine.id}
                      key={`sensor-${machineIdx}-${sensorIdx}`}
                      classnames="col-span-1 min-w-full"
                      isDetailed={detailed}
                    />
                  ))
                )
            : null}
        </div>
      </Tab>

      {/* Inspect view with search */}
      <Tab label="Inspect">
        <div className="grid w-full grid-cols-1 lg:grid-cols-2 gap-4">
          <div className="flex items-center justify-between space-x-2 col-span-1 lg:col-span-2 min-w-full">
            <Select selectedHook={[displayType, setDisplayType]} options={options} />
            <InputBox
              label=""
              classnames="flex-1"
              types={[
                displayType.name === "Sensors" ? "Sensor ID" : "",
                displayType.name === "Machines" ? "Machine ID" : "",
              ].filter((item) => item !== "")}
              placeholder={`What are you searching for?`}
              state={[searchValue, setSearchValue]}
            />
            <button
              type="button"
              title="Clear input"
              onClick={() => setSearchValue("")}
              className="flex items-center h-12 p-3 rounded text-sm duration-200
               bg-rose-500 hover:bg-rose-500/80 text-white"
            >
              <span>Clear&nbsp;</span>
              <TrashIcon className="h-4 w-4" />
            </button>
            <DetailedSwitch hook={[detailed, setDetailed]} toggle={() => setDetailed(!detailed)} compact={true} />
            <PhaseSwitch hook={[phase, setPhase]} toggle={() => setPhase(!phase)} compact={true} />
          </div>
          {displayType.name === "Machines"
            ? phase
              ? factory.length === 0
                ? null
                : factory.machines
                    .filter((machine) => {
                      if (searchValue === "") return true
                      else return machine.id.toUpperCase().includes(searchValue.toUpperCase())
                    })
                    .map((machine, index) => {
                      return (
                        <AlternateMachine
                          data={machine}
                          key={`inspect-final-${index}`}
                          classnames="col-span-1 min-w-full"
                          isDetailed={detailed}
                        />
                      )
                    })
              : factory
                  .filter((machine) => {
                    if (searchValue === "") return true
                    else return machine.id.toUpperCase().includes(searchValue.toUpperCase())
                  })
                  .map((machine, index) => {
                    return (
                      <Machine
                        data={machine}
                        key={`inspect-initial-${index}`}
                        classnames="col-span-1 min-w-full"
                        isDetailed={detailed}
                      />
                    )
                  })
            : null}
          {displayType.name === "Sensors"
            ? phase
              ? null
              : factory.map((machine, machineIdx) =>
                  machine.sensors
                    .filter((sensor, sensorIdx) => {
                      if (searchValue === "") return true
                      else return sensor.id.toUpperCase().includes(searchValue.toUpperCase())
                    })
                    .map((sensor, sensorIdx) => (
                      <Sensor
                        data={sensor}
                        parent={machine.id}
                        key={`sensor-${machineIdx}-${sensorIdx}`}
                        classnames="col-span-1 min-w-full"
                        isDetailed={detailed}
                      />
                    ))
                )
            : null}
        </div>
      </Tab>

      {/* JSON Representations */}
      <Tab label="JSON">
        <div className="grid w-full grid-cols-1 gap-4">
          <div className="grid grid-cols-2 gap-4">
            <div className="text-center text-white tracking-wider capitalize bg-slate-400 p-3 rounded-lg ">Initial State</div>
            <div className="text-center text-white tracking-wider capitalize bg-slate-400 p-3 rounded-lg ">Final State</div>
          </div>

          {/* Initial JSON */}
          <div className="grid grid-cols-2 gap-4 w-full">
            <div className="relative w-full overflow-y-auto overflow-x-hidden rounded-xl bg-[#3c4553] pr-2">
              <CopyClipboard json={factoryInitial} />
              <Scrollbar style={{ minHeight: "56vh" }}>
                <ReactJson
                  indentWidth={4}
                  iconStyle="triangle"
                  name={false}
                  collapsed={1}
                  displayObjectSize={false}
                  displayDataTypes={false}
                  enableClipboard={false}
                  src={factoryInitial}
                  theme="threezerotwofour"
                  style={jsonStyle}
                />
              </Scrollbar>
            </div>

            {/* Final JSON */}
            <div className="relative w-full overflow-y-auto overflow-x-hidden rounded-xl bg-[#3c4553] pr-2">
              <CopyClipboard json={factoryFinal} />
              <Scrollbar style={{ minHeight: "56vh" }}>
                <ReactJson
                  id="react-json-final"
                  indentWidth={4}
                  iconStyle="triangle"
                  name={false}
                  collapsed={1}
                  displayObjectSize={false}
                  displayDataTypes={false}
                  enableClipboard={false}
                  src={factoryFinal}
                  theme="threezerotwofour"
                  style={jsonStyle}
                />
              </Scrollbar>
            </div>
          </div>
        </div>
      </Tab>
    </Tabs>
  )
}
