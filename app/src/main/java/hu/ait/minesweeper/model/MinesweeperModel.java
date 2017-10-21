package hu.ait.minesweeper.model;


import java.util.Random;

public class MinesweeperModel {
    private static MinesweeperModel instance = null;

    private MinesweeperModel() {
    }

    public static MinesweeperModel getInstance() {
        if (instance == null) {
            instance = new MinesweeperModel();
        }
        return instance;
    }

    public static int MinesArray[] = new int[6]; //storing coordinates of the mines
    public static final short EMPTY = 10;
    public static final short FLAG = 11;
    public static final short MINE = 12;
    public static int flag=0;

    public static short[][] model = {
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY},
            {EMPTY, EMPTY, EMPTY, EMPTY, EMPTY}
    };


    public void resetModel() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                model[i][j] = EMPTY;
            }
        }
        InsertMines();
    }


    private void InsertMines(){

        Random rand = new Random();
        int i=0, count=0;

        while (count != 3){
            int x = rand.nextInt(5);
            int y = rand.nextInt(5);

            if (model[x][y] != MINE){
                model[x][y] = MINE;
                count++;
                MinesArray[i] = x;
                MinesArray[i+1] = y;
                i = i+2;
            }
        }
    }

    public void setField(int x, int y, short content) {
        model[x][y] = content;
    }

    public short getField(int x, int y) {
        return model[x][y];
    }

    public short CheckNeighborMines(int x, int y){

        short counter = 0;

        if ((x-1) >= 0){
            if (model[x-1][y] == MINE || model[x-1][y] == FLAG){
                counter++;
            }
        }

        if ((x+1) < 5){
            if (model[x+1][y] == MINE || model[x+1][y] == FLAG){
                counter++;
            }
        }

        if ((y-1) >= 0){
            if (model[x][y-1] == MINE || model[x][y-1] == FLAG){
                counter++;
            }
        }

        if ((y+1) < 5){
            if (model[x][y+1] == MINE || model[x][y+1] == FLAG){
                counter++;
            }
        }

        if ((x-1) >= 0 && (y-1) >= 0) {
            if (model[x - 1][y - 1] == MINE || model[x - 1][y - 1] == FLAG) {
                counter++;
            }
        }

        if ((x+1) < 5 && (y+1) < 5) {
            if (model[x + 1][y + 1] == MINE || model[x + 1][y + 1] == FLAG) {
                counter++;
            }
        }

        if ((x-1) >= 0 && (y+1) < 5) {
            if (model[x - 1][y + 1] == MINE || model[x - 1][y + 1] == FLAG) {
                counter++;
            }
        }

        if ((x+1) < 5 && (y-1) >= 0) {
            if (model[x + 1][y - 1] == MINE || model[x + 1][y - 1] == FLAG) {
                counter++;
            }
        }

        return counter;
    }

}
