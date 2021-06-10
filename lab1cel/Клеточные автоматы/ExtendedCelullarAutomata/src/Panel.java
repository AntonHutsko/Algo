
import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {
    private Cells cells;

    public Panel(Cells cells) {
        this.cells = cells;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 1; i < cells.getN()+1; i++) {
            g.drawLine(0,20*i,cells.getN()*20,20*i);
            g.drawLine(20*i,0,20*i,cells.getN()*20);
        }

        for (int i = 0; i < cells.getN(); i++) {
            for (int j = 0; j < cells.getN(); j++) {
                if (cells.getElement(i,j)==1){
                    g.fillRect(i*20,j*20,20,20);
                }
            }
        }
    }

    public Cells getCells() {
        return cells;
    }
}
