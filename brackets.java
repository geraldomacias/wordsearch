import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

public class brackets {

    // Complete the isBalanced function below.
    static String isBalanced(String s) {
        String solution = "YES";
        char[] c = s.toCharArray();
        Stack<Character> pile = new Stack<Character>();

        for (int i = 0; i < c.length; i++) {
            char currentChar = c[i];

            // Push open brackets to the top of the stack
            if (currentChar == '[' || currentChar == '{' || currentChar == '(') {
                pile.push(currentChar);
            } else {
                char openBracket;
                // Check if stack is empty
                // If stack is not empty, pop
                if (pile.isEmpty()) {
                    System.out.println("Found an empty stack");
                    return "NO";
                } else {
                    openBracket = pile.pop();
                }

                // If bracket styles don't match return "NO"
                if (currentChar == ']' && openBracket != '[') {
                    return "NO";
                } else if (currentChar == '}' && openBracket != '{') {
                    return "NO";
                } else if (currentChar == ')' && openBracket != '(') {
                    return "NO";
                }
            }
        }
        return "YES";
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        //BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        //int t = scanner.nextInt();
        //scanner.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
        /*
        for (int tItr = 0; tItr < t; tItr++) {
            String s = scanner.nextLine();

            String result = isBalanced(s);

            bufferedWriter.write(result);
            bufferedWriter.newLine();
        }
        */
        System.out.println(isBalanced("}(]}){"));

        //bufferedWriter.close();

        //scanner.close();
    }
}
