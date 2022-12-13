package ui.buttons;

import java.awt.Color;
import java.awt.Event;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class JSmoothButton extends JSmoothPanel {

    Color fixBackground, fixForeground;
    boolean flagFixFG = false;
    boolean flagFixBG = false;

    public JSmoothButton(int a, int b, int c, int d) {
        super(a, b, c, d);
        setBackground(new Color(fixBackground.getRGB()));
        fixForeground = new Color(180, 180, 180);
        flagFixBG = false;
    }

    @Override
    public void setBackground(Color color) {
        if (flagFixBG == false) {
            fixBackground = new Color(color.getRGB());
            this.color = new Color(color.getRGB());
            flagFixBG = true;
        }
    }

    public void setFixedBackground(Color color) {
        this.color = new Color(color.getRGB());
        fixBackground = new Color(color.getRGB());
    }

    @Override
    public void setForeground(Color color) {
        if (flagFixFG == false) {
            fixForeground = new Color(color.getRGB());
            flagFixFG = true;
        }
    }

    @Override
    protected void processMouseEvent(MouseEvent me) {
        switch (me.getID()) {
            case MouseEvent.MOUSE_ENTERED:
                color = new Color(fixForeground.getRGB());
                updateUI();
                break;
            case MouseEvent.MOUSE_EXITED:
                color = new Color(fixBackground.getRGB());
                updateUI();
                break;
        }

        super.processMouseEvent(me); //To change body of generated methods, choose Tools | Templates.
    }

}