// Summer 2018 WordSearch project
// Geraldo Macias

/* TODO:
 1. Reformat to 4 space tabs.
 2. Search:
    a. Up
    b. Down
    c. Diagonal ur
    d. Diagonal ul
    e. Diagonal dl
    f. Diagonal dr
    (hint - use distance flags to reduce checks)
*/

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.util.Scanner;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;


public class WordSearch {

    private Vector<String> puzzle;
    private int rows, columns = 0;

    public static void main(String[] args) {


        // Open file
        WordSearch ws = new WordSearch();
        String wordToSearchFor = "exit";

        // Read in CSV file of crossword puzzle
        ws.setPuzzle(ws.newPuzzle(ws.getFilePath()));
        ws.printPuzzle();

        // Play the game
        while (!(wordToSearchFor = ws.getWord()).equals("exit")) {
            if (ws.wordSearch(wordToSearchFor)) {
                 wordToSearchFor += (" found");
            }
            else {
               wordToSearchFor += (" not found");
            }
            System.out.println("\n++++ " + wordToSearchFor + " ++++");
        }

        System.out.println("Thank you for playing :)");
        return;
  }

  // Setters
  public void setRows(int rows) {
        this.rows = rows;
  }
  public void setColumns(int columns) {
        this.columns = columns;
  }
  public void setPuzzle(Vector<String> puzzle) {
        this.puzzle = puzzle;
  }

  // Getters
  public int getRows() {
        return rows;
  }
  public int getColumns() {
        return columns;
  }
  public Vector<String> getPuzzle() {
        return puzzle;
  }

    /*************** getWord ****************************************
     * Uses the Scanner class to process and return input from the
     * comand line
     * @return
     */
  private String getWord() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("--- Please enter a word to search for ---");
        System.out.println("--- or type and return 'exit' to terminate program ---");
        return keyboard.nextLine().toLowerCase();
  }

    /*************** printPuzzle ****************************************
     * Print the current puzzle
     * TODO: May not need to use getPuzzle since we have access to puzzle
     */
  public void printPuzzle() {
    System.out.println("--- printPuzzle() ---");
    Vector<String> grid = getPuzzle();
    for (int i = 0; i < getRows(); i++) {
      System.out.println(grid.elementAt(i));
    }
  }

    /*************** newPuzzle ****************************************
     * Creates a new matrix containing the characters found in a csv file
     * @param file
     * @return the created matrix
     */
  public Vector<String> newPuzzle(String file) {
    BufferedReader br = null;
    puzzle = new Vector<>();
    String line = "";
    String csvSplitBy = ",";
    int row, col;

    try {
      br = new BufferedReader(new FileReader(file));
      while ((line = br.readLine()) != null) {
        String[] cleanLine = line.split(csvSplitBy);
        String compact = "";

        setColumns(cleanLine.length);
        for (int i = 0; i < cleanLine.length; i++) {
          compact += cleanLine[i];
        }
        // Increase row count
        setRows(getRows() + 1);
        puzzle.add(compact.toLowerCase());
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      // The finally always executes when the try block exits.
      // This ensures that the finally block is executed even if
      // and unexpected exception occurs.
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return puzzle;
  }

    /*************** wordSearch ****************************************
     * Responsible for calling different search directions
     * @param word_to_search_for
     * @return
     */
  public boolean wordSearch(String word_to_search_for) {
    boolean result = false;
    int current_search_index = 0;

    if (word_to_search_for.isEmpty()) {
      return false;
    }

    char first_letter = word_to_search_for.charAt(0);
    // Loop through each row
    for (int i = 0; i < getRows(); i++) {
      // Loop through each column in each row
      for (int j = 0; j < getColumns(); j++) {
        // Search for a matching first element
        if (puzzle.elementAt(i).charAt(j) == first_letter) {
          System.out.print("\nFirst letter match. ");
          // * Search left to right
          if (searchRight(i, j, word_to_search_for)) {
            return true;
          } else if (searchLeft(i, j, word_to_search_for)) {
              return true;
          }

          // * Search right to left
        }
      }
    }
    return result;
  }

    /*************** searchRight ***************************************
     * Given i,j search to the right for a match
     * @param i row to start search
     * @param j column to start search
     * @param word_to_search_for
     * @return
     */
  private boolean searchRight(int i, int j, String word_to_search_for) {
    String right = "";
    int c = getColumns();
    int l = word_to_search_for.length();
    int it;

    // If there is room to move to the right without hitting an edge
    // Check sequence of right characters
    if (c-j >= l) {
        System.out.print(". . . searching right");
        for (int y = j; y < l + j; y++) {
            right += puzzle.elementAt(i).charAt(y);
        }
        if (right.equals(word_to_search_for)) {
            return true;
        }
    } else {
        System.out.print(". . . no room to the right");
    }
    return false;
  }

    /*************** searchLeft ****************************************
     * Given i,j search to the left for a match
     * @param i row to start search
     * @param j column to start search
     * @param word_to_search_for
     * @return
     */
  private boolean searchLeft(int i, int j, String word_to_search_for) {
      String left = "";
      int c = getColumns();
      int l = word_to_search_for.length();

      if((j+1) - l >= 0) {
          System.out.print(". . . searching left");
          for(int y = j; y >= (j+1-l); y--) {
              left = left + puzzle.elementAt(i).charAt(y);
          }
          if (left.equals(word_to_search_for)) {
              return true;
          }
      } else {
          System.out.print(". . . no room to the left");
      }
      return false;
  }


    /*************** getFilePath ****************************************
     * Uses JAVA Swing to open a CSV file.
     * TODO: Consider using a try catch for file opening.
     * @return
     */
   public String getFilePath() {

      File selectedFile = null;
      JFileChooser jfc = new JFileChooser(".", FileSystemView.getFileSystemView());
      jfc.setDialogTitle("Select a csv file");
      jfc.setAcceptAllFileFilterUsed(false);
      FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV", "csv");
      jfc.addChoosableFileFilter(filter);

      int returnValue = jfc.showOpenDialog(null);
      // int returnValue = jfc.showSaveDialog(null);

      if (returnValue == JFileChooser.APPROVE_OPTION) {
          selectedFile = jfc.getSelectedFile();
          return(selectedFile.getAbsolutePath());

      }
      return null;
   }
}
