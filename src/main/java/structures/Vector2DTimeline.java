package structures;

import java.util.ArrayList;
import java.util.List;

public class Vector2DTimeline {
    private List<Vector2D> timeline = new ArrayList<>();

    public Vector2DTimeline(Vector2D start, Vector2D end) {
        for (int i = start.x(); i <= end.x(); i++) {
            for (int k = start.y(); k <= end.y(); k++) {
                timeline.add(new Vector2D(i, k));
            }
        }
    }

    public Vector2DTimeline(int xl, int yl, int xr, int yr) {
        for (int i = xl; i <= xr; i++) {
            for (int k = yl; k <= yr; k++) {
                timeline.add(new Vector2D(i, k));
            }
        }
    }

    public Vector2DTimeline(List<Vector2D> timeline) {
        this.timeline = timeline;
    }

    public Vector2DTimeline(Vector2D frame) {
        this.timeline.add(frame);
    }

    public Vector2DTimeline(int x, int y) {
        this.timeline.add(new Vector2D(x, y));
    }

    public List<Vector2D> getTimeline() {
        return timeline;
    }


}
