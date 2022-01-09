class DrawableMachine {
    constructor(id, x, y)
    {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = 180;
        this.height = 180;
    }

    draw(ctx, cam_x, cam_y)
    {
        const color = "#000000";
        const id_color = "#FFFFFF";

        ctx.fillStyle = color;
        ctx.fillRect(this.x - cam_x, this.y - cam_y, this.width, this.height);
        ctx.fillStyle = id_color;
        ctx.font = "30px Arial";
        ctx.fillText(this.id, this.x - cam_x + this.width/8, this.y - cam_y + this.height/1.9);
    }
}

module.exports = DrawableMachine