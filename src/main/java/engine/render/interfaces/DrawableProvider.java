package engine.render.interfaces;

import interfaces.Placeable;

import java.util.List;

public interface DrawableProvider {
    List<Drawable> getDrawable();

    public Placeable getCamera();
}
