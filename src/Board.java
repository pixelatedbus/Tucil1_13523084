import javax.swing.*;
import java.awt.*;

public class Board {
    public int row;
    public int col;
    public char[][] matrix;
    public int iteration;

    // Constructor
    public Board(int rowInput, int colInput) {
        row = rowInput;
        col = colInput;
        matrix = new char[row][col];

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matrix[i][j] = '_';
            }
        }
        iteration = 0;
    }

    // Methods
    public boolean checkFor(char n) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == n) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isFull() {
        return !checkFor('_');
    }

    public boolean checkBlock(Block block, int x, int y) {
        for (int[] coords : block.coordinates) {
            int xCoord = coords[0] + x;
            int yCoord = coords[1] + y;
            if (xCoord < 0 || xCoord >= row || yCoord < 0 || yCoord >= col || matrix[xCoord][yCoord] != '_') {
                return false;
            }
        }
        return true;
    }

    public boolean placeBlock(Block block, int x, int y) {
//        if (checkBlock(block, x, y)) {
//            for (int[] coords : block.coordinates) {
//                int xCoord = coords[0] + x;
//                int yCoord = coords[1] + y;
//                matrix[xCoord][yCoord] = block.letter;
//            }
//            return true;
//        }
//        iteration++;
//        System.out.println("CHANGING ITERATION");
//        return false;
        for (int i = -1; i < 1; i++){
            for (int j = -1; j < 1; j++){
                if (checkBlock(block, x + i, y + j)){
                    for (int[] coords : block.coordinates){
                        int xCoord = coords[0] + x + i;
                        int yCoord = coords[1] + y + j;
                        if(xCoord >= 0 && xCoord < row && yCoord >= 0 && yCoord < col){
                            matrix[xCoord][yCoord] = block.letter;
                        } else {
                            break;
                        }
                    }
                    return true;
                }
                System.out.println("CHANGING ITERATION");
            }
        }
        return false;
    }

    public int[] findEmpty() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == '_') {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    public void removeBlock(char letter) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (matrix[i][j] == letter) {
                    matrix[i][j] = '_';
                }
            }
        }
    }

    public boolean solveBoard(BlockList blockList, int x, int y) {
        if (isFull()) {
            return blockList.blackList.size() == blockList.blocks.size();
        }
        for (Block[] blocks : blockList.blocks) {
            for (Block currentBlock : blocks) {
                if (!blockList.blackList.contains(currentBlock.letter)) {
                    currentBlock.printBlock();
                    char currentLetter = currentBlock.letter;
                    if (placeBlock(currentBlock, x, y)) {
                        printBoard();
                        System.out.println("CURRENT LETTER: " + currentLetter);
                        int[] nextEmpty = findEmpty();
                        x = nextEmpty[0];
                        y = nextEmpty[1];
                        blockList.blackList.add(currentBlock.letter);
                        if (solveBoard(blockList, x, y)) {
                            return true;
                        } else {
                            System.out.println("REMOVING BLOCK");
                            blockList.blackList.removeLast();
                            removeBlock(currentLetter);
                        }
                    }
                }
            }
        }
        iteration++;
        System.out.println("BACKTRACKING");
        return false;
    }

    public void printBoard() {
        System.out.println("Iteration: " + iteration);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }
}

