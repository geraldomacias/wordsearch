// Summer 2018 WordSearch project
// Geraldo Macias

// TODO:
// 1. Change csv file letters to lowercase
// 2. Force user input to lowercase

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;
import java.util.Scanner;


public class WordSearch {

  private Vector<String> puzzle;
  private int rows, columns = 0;

  public static void main(String[] args) {
    WordSearch ws = new WordSearch();
    String word_to_search_for = "0";
    // Read in csv file of crossword puzzle
    ws.setPuzzle(ws.newPuzzle(args[0]));
    ws.printPuzzle();

    while ((word_to_search_for = ws.getWord()) != "0") {
      if (ws.word_search(word_to_search_for)) {
        System.out.println(word_to_search_for + " found");
      } else {
        System.out.println(word_to_search_for + " not found");
      }
    }

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
  // Retrieves a a word from the command line
  private String getWord() {
    Scanner keyboard = new Scanner(System.in);
    System.out.println("--- Please enter a word to search for ---");
    System.out.println("--- Enter 0 to terminate program ---");
    return keyboard.nextLine();
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

  public boolean word_search(String word_to_search_for) {
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
          System.out.println("First letter match!");
          // * Search left to right
          if (searchRight(i, j, word_to_search_for)) {
            return true;
          }
          // * Search right to left
        }
      }
    }
    return result;
  }

  private boolean searchRight(int i, int j, String word_to_search_for) {
    char current_letter;
    int r = getRows();
    int c = getColumns();
    int l = word_to_search_for.length();
    int it;
    // If there is room to move to the right without hitting an edge
    // Check sequence of right characters
    if (c-j-1 >= l) {
      it = 1;
      do {
        current_letter = word_to_search_for.charAt(it);
        // If the last element of the search matches, return true
        if (it - l == 1 && puzzle.elementAt(i).charAt(j) == current_letter) {
          return true;
        }
        it++;
      } while (it < l && puzzle.elementAt(i).charAt(j++) == current_letter);
      // At this point the word is not found left to right
    }
    return false;
  }
}
