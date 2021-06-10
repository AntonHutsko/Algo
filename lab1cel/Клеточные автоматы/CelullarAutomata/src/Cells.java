
import java.util.Random;

public class Cells {
    private int[][] cells;
    private int n;
    private int m;

    public Cells(int n, int m) {
        this.n = n;//строки
        this.m = m;//столбцы
        cells = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                cells[i][j] = new Random().nextBoolean() ? 1 : 0;
            }
        }
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public int getElement(int i,int j){
        return cells[i][j];
    }

    public int countNeighbours(int i,int j) {
        int neighbours = 0;

        if (cells[i][j] == 1) {
            neighbours--;
        }

        for (int k = -1; k <= 1; k++) {
            for (int l = -1; l <= 1; l++) {
                if (!(i + k < 0 || i + k >= n || j + l < 0 || j + l >= m)) {
                    if (cells[i + k][l + j] == 1) {
                        neighbours++;
                    }
                }
            }
        }

        return neighbours;
    }

    public boolean isCellAlive(int i,int j){
        int neighbours = this.countNeighbours(i,j);

        if (cells[i][j] == 1){
            if (neighbours == 2 || neighbours ==3){
                return true;
            }else {
                return false;
            }
        }else{
            if (neighbours == 3){
                return true;
            }else {
                return false;
            }
        }
    }

    public void checkAllCells(){
        int[][] newCells = new int[n][m];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                newCells[i][j] = 0;
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (isCellAlive(i,j)){
                    newCells[i][j] = 1;
                }
            }
        }

        cells = newCells;
    }
}