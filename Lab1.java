/**
 *
 * @author zlee041
 */
import java.io.*;
public class Lab1 {

      public static void NaiveSearch(String Sequence, String pattern) 
    { 
        int patternLength = pattern.length(); 
        int sequenceLength = Sequence.length(); 
  
        /* A loop to slide over pattern one by one */
        // -patternLength since pattern cannot exist if remaining search space
        // < length of pattern.
        for (int OuterLoop = 0; OuterLoop <= sequenceLength - patternLength; OuterLoop++) { 
  
            int InnerLoop; 
  
            /* For current index i, check for pattern  
              match */
            //if innerloop < length of pattern, iteriate
            //iteriate through length of pattern, to try and match pattern substring
            //with subsection of sequence length.
            for (InnerLoop = 0; InnerLoop < patternLength; InnerLoop++) 
                // break if char at seq[mainPositionAtOuterloop+startSearchInnerLoopOffset] != pattern[InnerLoop traversal]
                if (Sequence.charAt(OuterLoop + InnerLoop) != pattern.charAt(InnerLoop)) 
                    break; 
            // if innerloop manage to completely traverse to full pattern lenght, then match is found.
            if (InnerLoop == patternLength) // if pat[0...M-1] = txt[i, i+1, ...i+M-1] 
                System.out.println("Pattern found at index " + OuterLoop); 
        } 
    }
      
      //Get the maximum of two integers, 
     static int max (int a, int b) 
     {
         if( a > b)
         {
            return  a;
         }
         else
            return b;
         
     } 
  
     //The preprocessing function for Boyer Moore's 
     //bad character heuristic 
     static void badCharHeuristic( char []str, int size,int badchar[]) 
     { 
      int i; 
  
      // Initialize all occurrences as -1 
      for (i = 0; i < 256; i++) 
           badchar[i] = -1; 
  
      // Fill the actual value of last occurrence  
      // of a character 
      for (i = 0; i < size; i++) 
           badchar[(int) str[i]] = i; 
     } 
  
     /* A pattern searching function that uses Bad 
     Character Heuristic of Boyer Moore Algorithm */
     static void BoyerMoore( char txt[],  char pat[]) 
     { 
      int m = pat.length; 
      int n = txt.length; 
  
      int badchar[] = new int[256]; 
  
      /* Fill the bad character array by calling  
         the preprocessing function badCharHeuristic()  
         for given pattern */
      badCharHeuristic(pat, m, badchar); 
  
      int s = 0;  // s is shift of the pattern with  
                  // respect to text 
      while(s <= (n - m)) 
      { 
          int j = m-1; 
  
          /* Keep reducing index j of pattern while  
             characters of pattern and text are  
             matching at this shift s */
          while(j >= 0 && pat[j] == txt[s+j]) 
              j--; 
  
          /* If the pattern is present at current 
             shift, then index j will become -1 after 
             the above loop */
          if (j < 0) 
          { 
              System.out.println("Patterns occur at shift = " + s); 
  
              /* Shift the pattern so that the next  
                 character in text aligns with the last  
                 occurrence of it in pattern. 
                 The condition s+m < n is necessary for  
                 the case when pattern occurs at the end  
                 of text */
              s += (s+m < n)? m-badchar[txt[s+m]] : 1; 
  
          } 
  
          else
              /* Shift the pattern so that the bad character 
                 in text aligns with the last occurrence of 
                 it in pattern. The max function is used to 
                 make sure that we get a positive shift.  
                 We may get a negative shift if the last  
                 occurrence  of bad character in pattern 
                 is on the right side of the current  
                 character. */
              s += max(1, j - badchar[txt[s+j]]); 
      } 
     } 
  // Boyer Moore algorithm starts matching from the last character of the pattern
      
    public static void main(String[] args)throws Exception
	{
		//Create a new file object and read in the file.
		File DNA = new File("C:\\Users\\zw\\OneDrive\\CZ2001\\dna.fna");
		BufferedReader fileReadIn = new BufferedReader(new FileReader(DNA));
		
		String text;
		String full ="";
		
		//Concatenate each row of the file DNA sequence into a single string.
		while((text = fileReadIn.readLine()) != null)
		{
			full += text;
		}

		//Close the file stream.
		fileReadIn.close();
		
		//System.out.print(full);
		//System.out.println();
                String pattern = "GAGTCGC";
		NaiveSearch(full,pattern);
                char txt[] = full.toCharArray(); 
                char pat[] = pattern.toCharArray();
		BoyerMoore(txt,pat);

    }
}