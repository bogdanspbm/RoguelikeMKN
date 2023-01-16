package inventory.ui;

import ui.buttons.JSmoothPanel;

import java.awt.*;

public class ItemTooltip extends JSmoothPanel {

    private String text = "";

    public ItemTooltip(String text) {
        super(0, 0, 0, 0);
        this.text = text;

    }


    @Override
    protected void paintComponent(Graphics grphcs) {
        int width = grphcs.getFontMetrics().stringWidth(this.text);
        setSize(width, 32);

        super.paintComponent(grphcs);
        grphcs.setColor(new Color(20, 20, 20));
        grphcs.drawString(this.text, 0, 0);
    }
}
