
/**
 *
 * @author zlee041
 */
import java.io.*;

public class Lab1 {

    public static void NaiveSearch(String Sequence, String pattern) {
        int patternLength = pattern.length();
        int sequenceLength = Sequence.length();

        /* A loop to slide over pattern one by one */
        // -patternLength since pattern cannot exist if remaining search space
        // < length of pattern.
        for (int OuterLoop = 0; OuterLoop <= sequenceLength - patternLength; OuterLoop++) {

            int InnerLoop;

            /*
             * For current index i, check for pattern match
             */
            // if innerloop < length of pattern, iteriate
            // iteriate through length of pattern, to try and match pattern substring
            // with subsection of sequence length.
            for (InnerLoop = 0; InnerLoop < patternLength; InnerLoop++)
                // break if char at seq[mainPositionAtOuterloop+startSearchInnerLoopOffset] !=
                // pattern[InnerLoop traversal]
                if (Sequence.charAt(OuterLoop + InnerLoop) != pattern.charAt(InnerLoop))
                    break;
            // if innerloop manage to completely traverse to full pattern lenght, then match
            // is found.
            if (InnerLoop == patternLength) // if pat[0...M-1] = txt[i, i+1, ...i+M-1]
                System.out.println("Pattern found at index " + OuterLoop);
        }
    }

    // Get the maximum of two integers,
    static int max(int a, int b) {
        if (a > b) {
            return a;
        } else
            return b;

    }

    // The preprocessing function for Boyer Moore's
    // bad character heuristic
    static void preprocessingBM(char[] str, int size, int badchar[]) {
        int i;
        // array to hold the number of skips
        // Initialize all occurrences as -1

        for (i = 0; i < 256; i++)
            badchar[i] = -1;

        // Fill the actual value of last occurrence
        // of a character for bad character heuristic
        for (i = 0; i < size; i++)
            badchar[(int) str[i]] = i;
    }

    /*
     * A pattern searching function that uses Bad Character Heuristic of Boyer Moore
     * Algorithm
     */
    static void BoyerMoore(char txt[], char pat[]) {
        int patternLength = pat.length;
        int textLength = txt.length;

        int badchar[] = new int[256];

        // generate the table for number of characters to skip
        // based on pattern properties.
        preprocessingBM(pat, patternLength, badchar);

        int s = 0; // s is shift of the pattern with
                   // respect to text
        //
        int searchSpace = textLength - patternLength;
        while (s <= searchSpace) {
            int j = patternLength - 1;

            /*
             * 2 dimensional loop controlled as denoted by s and j reduce index j when
             * pattern[i] = text[i] to continue traversing the pattern and continue matching
             */

            // s+j refers to offset from shift + traversal of j (position of character in
            // the pattern that we are trying to match)
            while (j >= 0 && pat[j] == txt[s + j])
                j--;

            // if j has been decremented to < 0,means that we have successfully matched all
            // characters of pattern
            if (j < 0) {
                // hence print the patterns that occur
                System.out.println("One pattern match found at this index = " + s);

                 // if the pattern not at the end of the text
                 if(s+ patternLength < textLength)
                 {
                     // shift pattern to next character in text, after the index of the matching pattern.
                     s +=patternLength - badchar[txt[s + patternLength]];
                 }
                 else
                 {
                     // else we have already reached the end, simply +1
                     s +=1;
                 }

            }

            else
                /*
                 * Shift the pattern so that the bad character in text aligns with the last
                 * occurrence of it in pattern. max to ensure positive shift in edge case where character is on right side.
                 */
                s += max(1, j - badchar[txt[s + j]]);
        }
    }


    public static void main(String[] args) throws Exception {
        // Create a new file object and read in the file.
        File DNA = new File("C:\\Users\\zw\\Desktop\\cz2001\\new.fna");
        BufferedReader fileReadIn = new BufferedReader(new FileReader(DNA));

        String text;
        String full = "";

        // Concatenate each row of the file DNA sequence into a single string.
        while ((text = fileReadIn.readLine()) != null) {
            full += text;
        }

        // Close the file stream.
        fileReadIn.close();

        // System.out.print(full);
        // System.out.println();
        String pattern = "na";
        NaiveSearch(full, pattern);
        char txt[] = full.toCharArray();
        char pat[] = pattern.toCharArray();
        // Boyer Moore algorithm starts matching from the last character of the pattern
        // skips characters that heuristically does not match due to positioning of said characters
        BoyerMoore(txt, pat);

    }
}