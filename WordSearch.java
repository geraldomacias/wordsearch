// Summer 2018 WordSearch project
// Geraldo Macias

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;


public class WordSearch {

  private Vector<String> puzzle;
  private int rows, columns = 0;

  public static void main(String[] args) {
    WordSearch ws = new WordSearch();
    // Read in csv file of crossword puzzle
    ws.setPuzzle(ws.newPuzzle(args[0]));
    ws.printPuzzle();
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


  public void printPuzzle() {
    System.out.println("--- printPuzzle() ---");
    Vector<String> grid = getPuzzle();
    for (int i = 0; i < getRows(); i++) {
      System.out.println(grid.elementAt(i));
    }
  }


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
        puzzle.add(compact);
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

  public bool word_search(String word_to_search_for) {
    bool result = false;
    int r = getRows();
    int c = getColumns();
    int l = word_to_search_for.length();
    int current_search_index = 0;

    if (!word_to_search_for) {
      return false;
    }

    char current_letter = word_to_search_for[current_search_index++];
    // Loop through each row
    for (int i = 0; i < getRows(); i++) {
      // Loop through each column in each row
      for (int j = 0; j < getColumns(); j++) {
        // Search for a matching first element
        if (puzzle.elementAt(i)[j] == current_letter) {
          int i_index = i;
          int j_index = j;
          // Matching first element
          // * Search left to right
          if (c-j-1 >= l) {
            // If there is room to move to the right without hitting an edge
            // Check sequence of right characters
            int it = 1;
            do {
              current_letter = word_to_search_for[current_search_index++];
              // If the last element of the search matches return true
              if (it - l == 1 && puzzle.getElementAt(i)[j_index++] == current_letter) {
                return true;
              }
            } while (it < l && puzzle.getElementAt(i)[j_index++] == current_letter);
            // At this point the word is not found left to right
            // Reset altered values
            j_index = j;
            current_search_index = 1;
            current_letter = word_to_search_for[current_search_index];
          }
          // * Search right to left
        }
      }
    }



    return result;
  }
}
