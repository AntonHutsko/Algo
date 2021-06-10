
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Main extends JFrame {
    public boolean isActive;
    public Panel panel;

    public Main(Panel panel){
        JFrame jframe = new JFrame("Graphics");
        isActive = false;
        this.panel = panel;

        jframe.setLocation(750, 150);
        jframe.setMinimumSize(new Dimension(panel.getCells().getN() * 20 + 18 + 105, panel.getCells().getN() * 20 + 45));
        jframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jframe.pack();
        jframe.setVisible(true);

        JPanel buttons = new JPanel();
        buttons.setLayout(new GridLayout(6,1,0,0));

        JButton start = new JButton("Start");
        start.addActionListener(new StartListener());

        JButton pause = new JButton("Pause");
        pause.addActionListener(new PauseListener());

        JButton clear = new JButton("Clear Board");
        clear.addActionListener(new ClearBordListener());

        JButton generating = new JButton("Generate");
        generating.addActionListener(new GenerateListener());

        buttons.add(start);
        buttons.add(pause);
        buttons.add(clear);
        buttons.add(generating);

        panel.addMouseListener(new PanelMouseListener());

        jframe.getContentPane().add(panel, BorderLayout.CENTER);
        jframe.getContentPane().add(buttons, BorderLayout.EAST);
    }

    public class StartListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            isActive = true;
        }
    }

    public class PauseListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            isActive = false;
        }
    }

    public class ClearBordListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            panel.getCells().clearCells();
            isActive = false;
        }
    }

    public class GenerateListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            panel.getCells().setCells(panel.getCells().generate(panel.getCells().getN()));
        }
    }

    public class PanelMouseListener implements MouseListener{
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getX() < panel.getCells().getN() * 20 + 18 && e.getY() < panel.getCells().getN() * 20 + 45) {
                int i = e.getX()/20;
                int j = e.getY()/20;

                if (panel.getCells().getElement(i,j)==1){
                    panel.getCells().setCell(i,j,0);
                }else{
                    panel.getCells().setCell(i,j,1);
                }
            }
        }
        @Override
        public void mousePressed(MouseEvent e) {

        }
        @Override
        public void mouseReleased(MouseEvent e) {

        }
        @Override
        public void mouseEntered(MouseEvent e) {

        }
        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    public static void main(String[] args) throws InterruptedException {
        Cells cells = new Cells(20);
        Panel panel = new Panel(cells);
        Main main = new Main(panel);

        while(true) {
            System.out.print("");
            panel.repaint();

            while(main.isActive) {
                Thread.sleep(1000);

                cells.checkAllCells();
                panel.repaint();
            }
        }
    }
}