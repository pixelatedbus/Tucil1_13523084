import java.util.ArrayList;

/**
 * Block in game, represented by X (vertical down) and Y (horizontal right)
 */
public class Block {
    public ArrayList<int[]> coordinates;
    public char letter;

    public Block(ArrayList<int[]> inputCoordinates, char letterInput){
        // Construct
        coordinates = inputCoordinates;
        letter = letterInput;
    }

    public Block(ArrayList<String> blockInput) {
        ArrayList<int[]> coordinatesList = new ArrayList<int[]>();
        char letterBlock = '_'; // temp
        boolean letterSet = false;
        for (int x = 0; x < blockInput.size(); x++) {
            String currentString = blockInput.get(x);
            for (int y = 0; y < currentString.length(); y++) {
                if (currentString.charAt(y) != ' ') {
                    if (!letterSet) {
                        letterBlock = currentString.charAt(y);
                        letterSet = true;
                    }
                    coordinatesList.add(new int[]{x, y});
                }
            }
        }
        coordinates = coordinatesList;
        letter = letterBlock;
    }

    public Block forcePositive(){
        int minX = 0;
        int minY = 0;
        for (int[] coords : coordinates) {
            if (coords[0] < minX) {
                minX = coords[0];
            }
            if (coords[1] < minY) {
                minY = coords[1];
            }
        }
        ArrayList<int[]> coordinatesList = new ArrayList<int[]>();
        for (int[] coords : coordinates) {
            int[] newCoords = {coords[0] - minX, coords[1] - minY};
            coordinatesList.add(newCoords);
        }
        return new Block(coordinatesList, letter);
    }

    public Block rotateBlock(){
        ArrayList<int[]> coordinatesList = new ArrayList<int[]>();


        for (int[] currentCoordinate : coordinates) {
            int newCoordinateX = currentCoordinate[1];
            int newCoordinateY = -currentCoordinate[0];
            int[] newCoordinate = {newCoordinateX, newCoordinateY};
            coordinatesList.add(newCoordinate);
        }

        return new Block(coordinatesList, letter);
    }

    public Block invertBlock(){
        ArrayList<int[]> coordinatesList = new ArrayList<int[]>();
        for (int[] currentCoordinate : coordinates) {
            int[] newCoordinate = {currentCoordinate[0], -currentCoordinate[1]};
            coordinatesList.add(newCoordinate);
        }

        return new Block(coordinatesList, letter);
    }

    // DEBUGGING
    public void printBlock() {
        System.out.println("Letter: " + letter);
        System.out.print("Coordinates: ");
        for (int[] coords : coordinates) {
            System.out.print("[" + coords[0] + ", " + coords[1] + "] ");
        }
        System.out.println();
    }

    public void printDrawnBlock(){
        int maxX = 0;
        int maxY = 0;
        for (int[] coords : coordinates) {
            if (coords[0] > maxX) {
                maxX = coords[0];
            }
            if (coords[1] > maxY) {
                maxY = coords[1];
            }
        }
        for (int i = 0; i <= maxX; i++) {
            for (int j = 0; j <= maxY; j++) {
                boolean found = false;
                for (int[] coords : coordinates) {
                    if (coords[0] == i && coords[1] == j) {
                        System.out.print(letter);
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.print("_");
                }
            }
            System.out.println();
        }
    }
}
