
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.ArrayList;
//importing all the necessary packages

public class Spell {

    // creating instanmce variables for both files
    static Hashtable<String, Boolean> dictionary; 
    private ArrayList<String> wordsToCheck;

    /**
     * Spell constructor
     * loads both files and sets up spelling corrections
     * @param s the dictionary file
     * @param k the file to check for the spell class
     */
    Spell(String s, String k){
        // Load dictionary words from file into Hashtable
        dictionary = new Hashtable<String, Boolean>(); // intializing the dictionary variable
        //try-catch block to read the file
        try {
            //using buffered reader  to open and load the dictionary file
            BufferedReader reader = new BufferedReader(new FileReader(s));
            String line = reader.readLine();
            while (line != null) {
                dictionary.put(line, true);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) { // catch an input/output exception
            System.err.println("Error reading dictionary file: " + e);
        }

        // Load words in fileToCheck.txt
        wordsToCheck = new ArrayList<String>(); // intializing the array list for the words to check
        // try-catching the file
        try {
            BufferedReader reader = new BufferedReader(new FileReader(k));
            String line = reader.readLine();
            while (line != null) {
                String[] words = line.split("\\s+");
                for (String word : words) { //for each words in words
                    if (!word.isEmpty()) { // if word is not empty
                        wordsToCheck.add(word.toLowerCase()); // add to the array list but first convert every letter to lower case
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Check each word for spelling errors
        for (String word : wordsToCheck) { // for every word in wordsToCheck
            if (!checkSpelling(word)) { // if not checkspelling
                System.out.println(word + " is misspelled.");
            }
        }
       
    }

    public static void main(String[] args) {
        // init an object of type Spell
        Scanner scanner = new Scanner(System.in); // creating the scanner object
        
        //getting the input files from the user
        System.out.print("Enter dictionary file name: ");
        String dictionaryFile = scanner.nextLine();
        
        System.out.print("Enter file to check name: ");
        String fileToCheck = scanner.nextLine();
        
        // init an object of type Spell
        Spell spell = new Spell(dictionaryFile, fileToCheck);

    }

    /**
     * // this function check if the dictionay is loaded or not
     * @return the dictionary
     */
    public static Hashtable<String, Boolean> getDictionary(){
        // add your code here
        return dictionary;
    }

    /**
     * This function checks the spelling of the word moves forward accordingly
     * @param word the word to check for spelling mistakes
     * @return true or false if the word is mispelled or not
     */
    public static boolean checkSpelling(String word){
        if (word.matches("[a-zA-Z]+")) { // if the word is all letters in the alphabet
            if(dictionary.containsKey(word)){ // if the dictionary has the word
                // no corrections are needed
                System.out.println("Correct Spelling: " + word);
                System.out.println("No suggestions");
                return true;
            }else{
                suggestCorrections(word); // call the suggestCorrections class
                return true;
            }
        }
        return false;
        
    }

    /**
     * This class picks 1 of 4 ways to correct the spelling of the word and check 1-4
     * @param word the word fix for spelling
     * @return true or false 
     */
    public static boolean suggestCorrections(String word) {
        // local variables and output statement for the class
        System.out.println(word + ": Incorrect Spelling");
        ArrayList<String> correction = new ArrayList<>();
        boolean correctionFound = false;

        // checking to see if correction by substition corrects the word
        correction = correctSpellingSubstitution(word); // returns the corrected word if this option is possible
        if(correction != null){// if not null
            for(int i = 0;i < correction.size();i++){// for looping through the size of correction
                if (dictionary.containsKey(correction.get(i))) {
                    System.out.println("Suggested Correction: " + correction.get(i));
                    correctionFound = true;
                }
            }
        }
        

        // Check spelling with omission

        correction = correctSpellingWithOmission(word);// returns the corrected word if this option is possible
        if(correction != null){// if not null
            for(int i = 0;i < correction.size();i++){// for looping through the size of correction
                if (dictionary.containsKey(correction.get(i))) {
                    System.out.println("Suggested Correction: " + correction.get(i));
                    correctionFound = true;
                }
            }
        }
       

        // Check spelling with insertion

        correction = correctSpellingWithInsertion(word);// returns the corrected word if this option is possible
        if(correction != null){// if not null
            for(int i = 0;i < correction.size();i++){// for looping through the size of correction

                if (dictionary.containsKey(correction.get(i))) {
                    System.out.println("Suggested Correction: " + correction.get(i));
                    correctionFound = true;
                }
            }
        }


        // Check spelling with reversal
        
        correction = correctSpellingWithReversal(word);// returns the corrected word if this option is possible
        if(correction != null){// if not null
            for(int i = 0;i < correction.size();i++){// for looping through the size of correction
                if (dictionary.containsKey(correction.get(i))) {
                    System.out.println("Suggested Correction: " + correction.get(i));
                    correctionFound = true;
                }
            }
        }
       


        return correctionFound;
        // add your code here
    }

    /**
     * This method substitutes every letter to see if it matches with a correctly spelled word
     * @param word the mispelled word
     * @return an arraylist of options of correvt words
     */
    static ArrayList<String> correctSpellingSubstitution(String word) {
        String correctedWord = null;
        ArrayList<String> correctedWords = new ArrayList<>(); // intializes and create the array list
        for (int i = 0; i < word.length(); i++) {
            // Replace each character with every letter in the alphabet to check for possible substitutions
            for (char j = 'a'; j <= 'z'; j++) { // loop all possible letters for substution
                StringBuilder sb = new StringBuilder(word);
                sb.setCharAt(i, j);
                String possibleWord = sb.toString();
                // Check if the new word is in the dictionary
                if (dictionary.containsKey(possibleWord)) {
                    correctedWord = possibleWord;
                    correctedWords.add(correctedWord);
                    
                }
            }
            if (correctedWord != null) {
                break;
            }
        }
        return correctedWords; // return the arraylist with corrected words
        // add your code here
    }

    /**
     * imits every letter one by one to see if it fixes the word
     * @param word the mispelled word
     * @return the array list of correted words
     */
    static ArrayList<String> correctSpellingWithOmission(String word) {
        // add your code here
        String result = "";
        ArrayList<String> correctedWords = new ArrayList<>();// intializes and create the array list
        for (int i = 0; i < word.length(); i++) { // loop tyhrough the word
            String omitted = word.substring(0, i) + word.substring(i+1); // remove the letter and store it in omitted
            //verify its a word and add it to the array list
            if (dictionary.containsKey(omitted)) {
                result = omitted;
                correctedWords.add(result);
            }
        }
        return correctedWords;// return the arraylist with corrected words
    }

    /**
     * Inserts a letter at every index to see if it creates a word
     * @param word the misspelled word
     * @return the array list of new words
     */
    static ArrayList<String> correctSpellingWithInsertion(String word) {
        ArrayList<String> correctedWords = new ArrayList<>();// intializes and create the array list
        String suggestions = null;
        for (int i = 0; i < word.length() + 1; i++) {//loop through the word
            for (char c = 'a'; c <= 'z'; c++) { // nested loop to loop through possible letters
                //using the paskage string builder to insert and create the new word
                StringBuilder sb = new StringBuilder(word);
                sb.insert(i, c);
                String newWord = sb.toString();
                // verify its a word and add to array list
                if (dictionary.containsKey(newWord)) {
                    suggestions = newWord;
                    correctedWords.add(suggestions);
                }
            }
        }
        return correctedWords;// return the arraylist with corrected words
    }
    
    /**
     * Reverses the word to see if it's correct
     * @param word the misspelled word
     * @return the array list of correct words
     */
    static ArrayList<String> correctSpellingWithReversal(String word) {
        ArrayList<String> correctedWords = new ArrayList<>();// intializes and create the array list
        if (word == null || word.length() < 2) {
            return null;
        }

        // Convert the word into an array of characters
        char[] letters = word.toCharArray();

        // Swap adjacent characters
        for (int i = 0; i < letters.length - 1; i++) {
            char temp = letters[i];
            letters[i] = letters[i + 1];
            letters[i + 1] = temp;

            // Check if the new word exists in the dictionary
            String newWord = new String(letters);
            if (dictionary.containsKey(newWord)) {
                correctedWords.add(newWord);
                return correctedWords;
            }

            // Swap back to the original order for the next iteration
            temp = letters[i];
            letters[i] = letters[i + 1];
            letters[i + 1] = temp;
        }

        // No correction found
        return null;

    }

}
