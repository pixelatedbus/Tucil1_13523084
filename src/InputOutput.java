import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class InputOutput {
    public static void readInput(String filename, Board board, BlockList blockList) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filename));
            String firstLine = reader.readLine();
            String[] firstLineArray = firstLine.split(" ");
            int row = Integer.parseInt(firstLineArray[0]);
            int col = Integer.parseInt(firstLineArray[1]);

            board.row = row;
            board.col = col;
            board.matrix = new char[row][col];
            for (int i = 0; i < row; i++){
                for (int j = 0; j < col; j++){
                    board.matrix[i][j] = '_';
                }
            }

            int shapeCount = Integer.parseInt(firstLineArray[2]);
            reader.readLine(); //SKIP LINE (CHECK IF EQUAL TO DEFAULT, IF NOT, PRINT UNABLE)
            String currentLine = reader.readLine();

            for (int i = 0; i < shapeCount; i++) {
                if (currentLine == null) {
                    break;
                }
                char currentChar = currentLine.charAt(currentLine.length() - 1);
                ArrayList<String> currentBlock = new ArrayList<String>();
                char testChar = currentChar;
                while (testChar == currentChar) {
                    currentBlock.add(currentLine);
                    currentLine = reader.readLine();
                    if (currentLine == null) {
                        break;
                    }
                    System.out.println(currentLine);
                    testChar = currentLine.charAt(currentLine.length() - 1);
                }
                blockList.setBlocks(new Block(currentBlock));
            }
            reader.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveBoard(Board board) {
        String filename = "../test/output.txt";
        try {
            FileWriter writer = new FileWriter(filename);
            for (int i = 0; i < board.row; i++) {
                for (int j = 0; j < board.col; j++) {
                    writer.write(board.matrix[i][j]);
                }
                writer.write("\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
