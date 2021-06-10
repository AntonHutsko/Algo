
import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Cells cells = new Cells(30, 30);
        Field field = new Field(cells);
        field.repaint();


        while(true) {
            Thread.sleep(1000);

            cells.checkAllCells();
            field.repaint();
        }
    }
}