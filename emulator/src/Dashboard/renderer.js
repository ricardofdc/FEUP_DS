const { ipcRenderer } = require("electron");
const Camera = require('./Camera.js');
const DrawableMachine = require('./DrawableMachine.js');
const DrawableConnection = require('./DrawableConnection.js');


const canvas = document.querySelector("canvas");
const ctx = canvas.getContext("2d");
document.body.style.cursor = 'grab';
canvas.width = window.innerWidth;
canvas.height = window.innerHeight;

let cam = new Camera(10,0, canvas.width, canvas.height, []);
cam.draw(ctx);


ipcRenderer.on('asynchronous-message', function (evt, message) {
    const json_msg = JSON.parse(message);
    const machine_id = json_msg.id;

    const next_machine_id = json_msg.nextMachineID;
    const existing_machines = getMachinesFromCamera();
    const last_added_machine = existing_machines[existing_machines.length-1];
    let new_machine_x = 20;
    const space_between_machines = 120;
    const new_machine_y = canvas.height/2.4;
    if (last_added_machine)
        new_machine_x = last_added_machine.x + last_added_machine.width + space_between_machines;
    const new_machine = new DrawableMachine(machine_id, new_machine_x, new_machine_y);
    cam.addDrawableElement(new_machine);
    cam.draw(ctx);
});


let isMouseDown = false;
let mouseMoveStart = {x:0, y:0};

canvas.addEventListener('mousedown', function(event) {
    isMouseDown = true;
    mouseMoveStart.x = event.clientX;
    mouseMoveStart.y = event.clientY;
});

canvas.addEventListener('mouseup', function(event) {
    isMouseDown = false;
    document.body.style.cursor = 'grab';
})

canvas.addEventListener('mousemove', function(event) {
    if (isMouseDown)
    {
        document.body.style.cursor = 'grabbing';
        const offset_x = -(event.clientX - mouseMoveStart.x);
        const offset_y = -(event.clientY - mouseMoveStart.y);
        cam.move(offset_x, offset_y);
        cam.draw(ctx);
        mouseMoveStart.x = event.clientX;
        mouseMoveStart.y = event.clientY;
    }
})

function getMachinesFromCamera()
{
    const machines = [];
    const cam_elements = cam.getDrawableElements();
    cam_elements.forEach(element => {
        if(element instanceof DrawableMachine)
            machines.push(element);
    });

    return machines;
}
