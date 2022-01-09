class DrawableConnection {
    constructor(x_start, y_start, x_end, y_end)
    {
        this.x_start = x_start;
        this.y_start = y_start;
        this.x_end = x_end;
        this.y_end = y_end;
    }

    draw(ctx, cam_x, cam_y)
    {
        const color = "#000000";

        ctx.strokeStyle = color;
        ctx.beginPath();
        ctx.moveTo(this.x_start - cam_x, this.y_start - cam_y);
        ctx.lineTo(this.x_end - cam_x, this.y_end - cam_y);
        ctx.stroke(); 
    }
}

module.exports = DrawableConnection