class Camera {
    constructor(x, y, width, height, drawable_elements)
    {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.drawable_elements = drawable_elements;
    }

    move(x, y)
    {
        this.x += x;
        this.y += y;
    }

    getDrawableElements()
    {
        return this.drawable_elements;
    }

    addDrawableElement(drawable_element) {
        this.drawable_elements.push(drawable_element);
    }

    draw(ctx)
    {
        ctx.clearRect(0, 0, this.width, this.height);
        this.drawable_elements.forEach(drawable_element => {
                drawable_element.draw(ctx, this.x, this.y);
        });
    }
}

module.exports = Camera
