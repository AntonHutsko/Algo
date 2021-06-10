
import javax.swing.*;
import java.awt.*;

public class Field extends JPanel {
    private final Cells cells;

    public Field(Cells cells) {
        this.cells = cells;
        JFrame jframe = new JFrame("Graphics");

        jframe.setLocation(750, 150);
        jframe.setMinimumSize(new Dimension(cells.getN() * 20 + 18, cells.getM() * 20 + 45));
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jframe.getContentPane().add(this);

        jframe.pack();
        jframe.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int i = 1; i < cells.getN(); i++) {
            g.drawLine(0,20*i,cells.getN()*20,20*i);
            g.drawLine(20*i,0,20*i,cells.getM()*20);
        }

        for (int i = 0; i < cells.getN(); i++) {
            for (int j = 0; j < cells.getM(); j++) {
                if (cells.getElement(i,j)==1){
                    g.fillRect(i*20,j*20,20,20);
                }
            }
        }
    }
}
