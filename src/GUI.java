import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUI {

    public static void mainMenu(){
        JFrame frame = new JFrame("Tucil 1");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("Sigma Puzzle Solver");
        label.setFont(new Font("Arial", Font.BOLD, 40));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(label);

        JButton button1 = new JButton("Solve!");
        JButton button2 = new JButton("Exit");
        ImageIcon gambarHina = new ImageIcon("../src/citlali.jpg");
        JLabel imageLabel = new JLabel(gambarHina);
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(imageLabel);


        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);

        button2.addActionListener(e -> {
            System.exit(0);
        });

        button1.addActionListener(e -> {
            String filename = fileChoose();
            long startTime = System.currentTimeMillis();
            if (filename != null) {
                Board readBoard = new Board(1, 1);
                BlockList readBlockList = new BlockList();
                InputOutput.readInput(filename, readBoard, readBlockList);
                readBoard.printBoard();
                for(Block[] blocks : readBlockList.blocks){
                    for(Block readblock : blocks){
                        readblock.printBlock();
                    }
                }
                if (readBoard.solveBoard(readBlockList, 0, 0)) {
                    System.out.println("Solved!");
                    readBoard.printBoard();
                } else {
                    System.out.println("No solution found.");
                }
                JFrame frameSolution = graphicsSolution(readBoard);
                frameSolution.setVisible(true);

                JPanel secondPanel = new JPanel();
                secondPanel.setLayout(new BorderLayout());
                JLabel secondLabel = new JLabel("The Result!");
                secondLabel.setFont(new Font("Arial", Font.BOLD, 40));
                secondLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                secondPanel.add(secondLabel, BorderLayout.NORTH);

                JLabel timeLabel = new JLabel("Time elapsed: " + (System.currentTimeMillis() - startTime) + " ms");
                timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                secondPanel.add(timeLabel, BorderLayout.CENTER);

                JLabel iterationLabel = new JLabel("Iteration: " + readBoard.iteration);
                iterationLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                secondPanel.add(iterationLabel, BorderLayout.SOUTH);

                JButton saveButtonImage = new JButton("Save Solution Image");
                saveButtonImage.setAlignmentX(Component.CENTER_ALIGNMENT);
                saveButtonImage.addActionListener(e1 -> {
                    saveSolution(readBoard);
                });

                JButton saveButtontxt = new JButton("Save Solution Text");
                saveButtontxt.setAlignmentX(Component.CENTER_ALIGNMENT);
                saveButtontxt.addActionListener(e1 -> {
                    InputOutput.saveBoard(readBoard);
                });

                JButton returnButton = new JButton("Return to Main Menu");
                returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                returnButton.addActionListener(e1 -> {
                    frameSolution.dispose();
                    frame.setContentPane(panel);
                    frame.revalidate();
                    frame.repaint();
                });

                secondPanel.setLayout(new BoxLayout(secondPanel, BoxLayout.Y_AXIS));
                secondPanel.add(Box.createVerticalGlue());
                secondPanel.add(saveButtonImage);
                secondPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
                secondPanel.add(saveButtontxt);
                secondPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
                secondPanel.add(returnButton);
                secondPanel.add(Box.createVerticalGlue());
                frame.setContentPane(secondPanel);
                frame.revalidate();
                frame.repaint();
            }
        });

        panel.add(Box.createVerticalGlue());
        panel.add(button1);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(button2);
        panel.add(Box.createVerticalGlue());

        frame.setContentPane(panel);
        frame.setVisible(true);

    }

    public static String fileChoose(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

    public static void saveSolution(Board board) {
        JFrame frame = graphicsSolution(board);
        frame.setVisible(true);


        try {
                BufferedImage image = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics2D = image.createGraphics();
                frame.paint(graphics2D);
                try {
                    ImageIO.write(image, "jpeg", new File("../test/output.jpeg"));
                } catch (IOException e) {
                    throw new RuntimeException(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            frame.dispose();
        }
    }


    public static JFrame graphicsSolution(Board board) {
        JFrame frame = new JFrame("Board");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setSize(500, 500);
        frame.setLayout(new GridLayout(board.row, board.col));
        for(int i = 0; i < board.row; i++){
            for(int j = 0; j < board.col; j++){
                JLabel label = getLabel(i, j, board);
                frame.add(label);
            }
        }
        return frame;
    }

    private static JLabel getLabel(int i, int j, Board board) {
        int[][] color26Character = {
                {103, 103, 15},
                {0, 0, 128},
                {0, 128, 0},
                {0, 128, 128},
                {128, 0, 0},
                {128, 0, 128},
                {128, 128, 0},
                {192, 192, 192},
                {128, 128, 128},
                {0, 0, 255},
                {0, 255, 0},
                {0, 255, 255},
                {255, 0, 0},
                {255, 0, 255},
                {255, 255, 0},
                {255, 255, 255},
                {0, 0, 139},
                {0, 139, 0},
                {0, 139, 139},
                {139, 0, 0},
                {139, 0, 139},
                {139, 139, 0},
                {255, 215, 0},
                {255, 165, 0},
                {255, 69, 0},
                {255, 99, 71}
        };
        JLabel label = new JLabel();
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        if(board.matrix[i][j] != '_'){
            int colorIndex = (int) board.matrix[i][j] - 65;
            label.setBackground(new Color(color26Character[colorIndex][0], color26Character[colorIndex][1], color26Character[colorIndex][2]));
            label.setOpaque(true);
        }
        return label;
    }
}

