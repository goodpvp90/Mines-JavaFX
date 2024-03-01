package mines;

import java.util.Random;

public class Mines {

    private int width, height, board[][], flags[][], mines[][], neighbors[][];
    private boolean showAll;

    public Mines(int height, int width, int numMines) {
        this.width = width;
        this.height = height;
        showAll = false;
        board = new int[height][width];
        flags = new int[height][width];
        mines = new int[height][width];
        neighbors = new int[height][width];

        Random random = new Random();
        int cnt = 0;
        while (cnt < numMines) {
            int i = random.nextInt(height);
            int j = random.nextInt(width);
            if (mines[i][j] != 1) {
                addMine(i, j);
                cnt++;
            }
        }
    }

    public boolean addMine(int i, int j) {
        // Clear the neighbors array
        for (int d = 0; d < neighbors.length; d++) {
            for (int l = 0; l < neighbors[i].length; l++) {
                neighbors[d][l] = 0;
            }
        }

        mines[i][j] = 1; // Mark the cell as containing a mine

        // Update the neighbors count for each cell
        for (int h = 0; h < height; h++) {
            for (int c = 0; c < width; c++) {
                if (mines[h][c] != 1) {
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            if ((h + dx >= 0) && (h + dx < height) && (c + dy >= 0) && (c + dy < width)) {
                                if (mines[h + dx][c + dy] == 1)
                                    neighbors[h][c]++;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public boolean open(int i, int j) {
        if (i >= 0 && i < height && j >= 0 && j < width) {
            if (mines[i][j] == 1)
                return false;
            else if (board[i][j] == 0 && mines[i][j] != 1) {
                board[i][j] = 1; // Opened cell is marked as 1
                if (neighbors[i][j] == 0) {
                    // Recursively open neighboring cells
                    open(i - 1, j - 1);
                    open(i - 1, j);
                    open(i - 1, j + 1);
                    open(i, j - 1);
                    open(i, j + 1);
                    open(i + 1, j - 1);
                    open(i + 1, j);
                    open(i + 1, j + 1);
                }
            }
        }
        return true;
    }

    public void toggleFlag(int x, int y) {
        if (flags[x][y] == 1)
            flags[x][y] = 0; // Remove the flag
        else
            flags[x][y] = 1; // Place the flag
    }

    public boolean isDone() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (board[i][j] == 0 && mines[i][j] == 0)
                    return false; // There are unopened cells without mines
            }
        }
        return true; // All cells without mines are opened
    }

    public String get(int i, int j) {
        String cell = "";
        if (showAll) {
            if (mines[i][j] != 1) {
                if (neighbors[i][j] != 0)
                    cell += neighbors[i][j]; // Show the number of neighboring mines
                else
                    cell += " "; // Show an empty cell
            } else {
                cell += "X"; // Show a mine
            }
        } else {
            if (board[i][j] == 0 && flags[i][j] == 1)
                cell += "F"; // Show a flagged cell
            else if (board[i][j] == 0 && flags[i][j] == 0)
                cell += "."; // Show an unopened cell
            else if (board[i][j] == 1 && mines[i][j] == 1)
                cell += "X"; // Show a mine that has been opened
            else if (neighbors[i][j] != 0)
                cell += neighbors[i][j]; // Show the number of neighboring mines
            else
                cell += " "; // Show an empty cell
        }
        return cell;
    }

    public void setShowAll(boolean showAll) {
        this.showAll = showAll;
    }

    public String toString() {
        StringBuilder boardStr = new StringBuilder();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                boardStr.append(get(i, j));
            }
            boardStr.append("\n");
        }
        return boardStr.toString();
    }
}
