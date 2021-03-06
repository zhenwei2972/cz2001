
/**
 *
 * @author zlee041 hi 
 */
import java.io.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Lab1 {

    public static void NaiveSearch(String Sequence, String pattern) {
        int patternLength = pattern.length();
        int sequenceLength = Sequence.length();

        /* A loop to slide over pattern one by one */
        // -patternLength since pattern cannot exist if remaining search space
        // < length of pattern.
        long startTime = System.nanoTime();
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
            if (InnerLoop == patternLength) // 
                System.out.println("[Naive Search] match found at index " + OuterLoop);
        }
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        System.out.println("Execution time " + timeElapsed);
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
    static void preprocessingBM(char[] str, int size, int charTable[]) {
        int i;
        // array to hold the number of skips
        // Initialize all occurrences as -1

        for (i = 0; i < 256; i++)
            charTable[i] = -1;

        // Fill the actual value of last occurrence
        // of a character
        for (i = 0; i < size; i++)
            charTable[(int) str[i]] = i;
    }

    /*
     * A pattern searching function that uses Bad Character Heuristic of Boyer Moore
     * Algorithm
     */
    static void BoyerMoore(char txt[], char pat[]) {
        int patternLength = pat.length;
        int textLength = txt.length;

        int charTable[] = new int[256];
        long startTime = System.nanoTime();
        // generate the table for number of characters to skip
        // based on pattern properties.
        preprocessingBM(pat, patternLength, charTable);

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
                System.out.println("[Booyer Moore] match found at index " + s);

                 // if the pattern not at the end of the text
                 if(s+ patternLength < textLength)
                 {
                     // shift pattern to next character in text, after the index of the matching pattern.
                     s +=patternLength - charTable[txt[s + patternLength]];
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
                s += max(1, j - charTable[txt[s + j]]);
        }
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        System.out.println("Execution time " + timeElapsed);
    }

    public static void PrefixSkipSearch(String txt, String pat)
    {
        // for successful matches thus far,
        // look for suffix and prefix that matches

        // gabcdefgX g a  b  c
        // gabcdefgY
//index
        // 012345678 9 10 11 12
        // if we can find g, we can start at index 10 instead
        // after mismatch, try to find suffix that is = prefix
        // so we can save some comparisons

        //banananbaz
        //0000000120
        int patternLength = pat.length();
        int textLength = txt.length();

        long startTime = System.nanoTime();

        int table[] =new int[patternLength];
        int j =0; // index to traverse pattern
        int i =0; // index to traverse text
        BuildPSTable(txt, pat, table);

        for(int a=0; a<textLength; a++)
        {
            //match first character of pattern with first character of text
            //continue matching as i increments
            if(txt.charAt(i) == pat.charAt(j))
            {
                i++;
                j++;
            }
            //if we manage to increment j to patternLenght,
            //means we have consecutive matches 
            if(j==patternLength)
            {
                //index identified via i-j 
                // i is the index in the text
                //j is the index of the pattern
                //so if you match until index j
                //and you want the starting index of the pattern/text where you align
                //you need deduct off j
                //hence i-j is due to the traversal of the pattern.
                System.out.println("[Prefix Skip Search] match found at index "+(i-j));
                //set j to value of element before it.
                j =table[j-1];
            }
            else if (i<textLength&&pat.charAt(j)!=txt.charAt(i))
            {
                if(j != 0)
                {
                    j = table[j-1];
                }
                else
                    i = i + 1;
            }
        }
        long endTime = System.nanoTime();
        long timeElapsed = endTime - startTime;
        System.out.println("Execution time " + timeElapsed);
        
    }
    public static void BuildPSTable(String txt, String pat, int table[])
    {
    // build table
    // dsgwadsg
    // 00000123
    // skip 1 , skip 2 , skip 3, since we can 
    // continue  after d/s/g mismatches.
    //banananbaz
    //0000000120
    int patternLength = pat.length();
    int i = 1;
    table[0] =0;
    //end value of suffix
    int end=0;
    while(i < patternLength)
    {
        if(pat.charAt(i)==pat.charAt(end))
        {
            // increment end/length value 
            end++;
            // set skippable value in table
            // for this matching prefix/suffix character
            table[i] = end;
            // continue traversing pattern
            i++;
        }
        else 
        {
            if(end!=0)
            {
                end = table[end-1];
            }
            else //catch situation where len is 0, no match at all
            {
                // if no match,continue traversing 
                // expand the search space
                //populate table with '0' value for no skippable
                //prefix/suffix matching characters
                table[i] =end;
                //continue traversing 
                i++;
                
            }
        }
        /*
        LPS debug code
        for(int x = 0; x< table.length; x++)
        System.out.print(table[x]);

        System.out.println("");
        */
    }
    
    }

    public static String readDNA(String filename) throws IOException {
        String path = System.getProperty("user.dir");
        File DNA = new File(path + "\\"+filename+".fna");
        BufferedReader fileReadIn = new BufferedReader(new FileReader(DNA));
        String text;
        String full = "";

        // Concatenate each row of the file DNA sequence into a single string.
        while ((text = fileReadIn.readLine()) != null) {
            full += text;
        }
         // Close the file stream.
         fileReadIn.close();
         return full;
    }

    public static void main(String[] args) throws Exception {


        Scanner sc = new Scanner(System.in);
        int choice = 0;

        System.out.println("Select Search Algorithm: ");
        System.out.println("1 All Algorithms");
        System.out.println("2 Brute Force");
        System.out.println("3 Booyer Moore");
        System.out.println("4 KMP");

        choice = sc.nextInt();
        switch (choice) {
            case 1:
                // Create a new file object and read in the file.
            // relative file path 
            System.out.println("1st case");
            String full ="";
            full =readDNA("NC_008724");
            String pattern = "TTGCTCTGTC";

            NaiveSearch(full, pattern);
            char txt[] = full.toCharArray();
            char pat[] = pattern.toCharArray();
            // Boyer Moore algorithm starts matching from the last character of the pattern
            // skips characters that heuristically does not match due to positioning of said characters
            BoyerMoore(txt, pat);
            PrefixSkipSearch(full, pattern);
            System.out.println("\n");

            //2nd test case
            System.out.println("2nd case");
            full = readDNA("dna");
            pattern = "GCGCCA";
            NaiveSearch(full, pattern);
            txt = full.toCharArray();
            pat = pattern.toCharArray();
            BoyerMoore(txt, pat);
            PrefixSkipSearch(full, pattern);
            System.out.println("\n");

            System.out.println("3rd case");
            full = readDNA("NC_002670");
            pattern = "AAACTGA";
            NaiveSearch(full, pattern);
            txt = full.toCharArray();
            pat = pattern.toCharArray();
            BoyerMoore(txt, pat);
            PrefixSkipSearch(full, pattern);
            break;

            case 2:
            System.out.println("1st case");
            full ="";
            full =readDNA("NC_008724");
            pattern = "TTGCTCTGTC";
            NaiveSearch(full, pattern);
            System.out.println("2nd case");
            full = readDNA("dna");
            pattern = "GCGCCA";
            NaiveSearch(full, pattern);
            System.out.println("3rd case");
            full = readDNA("NC_002670");
            pattern = "AAACTGA";
            NaiveSearch(full, pattern);
            break;

            case 3:
            full =readDNA("NC_008724");
            pattern = "TTGCTCTGTC";
            txt = full.toCharArray();
            pat = pattern.toCharArray();
            System.out.println("1st case");
            BoyerMoore(txt, pat);
            full = readDNA("dna");
            pattern = "GCGCCA";
            txt = full.toCharArray();
            pat = pattern.toCharArray();
            System.out.println("2nd case");
            BoyerMoore(txt, pat);
            System.out.println("3rd case");
            full = readDNA("NC_002670");
            pattern = "AAACTGA";
            txt = full.toCharArray();
            pat = pattern.toCharArray();
            BoyerMoore(txt, pat);
            break;

            case 4:
            full =readDNA("NC_008724");
            pattern = "TTGCTCTGTC";
            System.out.println("1st case");
            PrefixSkipSearch(full, pattern);
            System.out.println("2nd case");
            full = readDNA("dna");
            pattern = "GCGCCA";
            PrefixSkipSearch(full, pattern);
            System.out.println("3rd case");
            full = readDNA("NC_002670");
            pattern = "AAACTGA";
            PrefixSkipSearch(full, pattern);
            break;

            default:
                sc.close();
                System.exit(0);
        }  
        

    }
}
