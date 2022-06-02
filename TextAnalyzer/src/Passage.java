
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

/**
 * A class that has a is-a relationship with has map which maps a string to an
 * integer value The passage class represent a single text file and all of its
 * words' occurrences.
 */
public class Passage extends HashMap<String, Integer>{

    private String title; //title of passage
    private int wordCount; //number of distinct words occuring excluding stop
    // words
    private HashMap<String, Double> similarTitles;
    private static ArrayList stopWordArray;

    /**
     * A Constructor for Passage which  sets the title of the passage and calls
     * pareseFile method and stopword if neccessary
     * @param title the title of the text file
     * @param file one file that was passed from a folder of files to be parsed
     * @throws FileNotFoundException an error to be thrown in the parseFile
     * method
     */
    public Passage(String title, File file) throws FileNotFoundException {
        this.title = title;
        if(title.equals("StopWords.txt")) {
            stopWord(file);
        }else {
            parseFile(file);
        }
    }
    //THIS METHOD IS A TEST
    /*public static void main(String[] args) throws FileNotFoundException {
        try {
            File stop2 = new File("/Users/youkim/IdeaProjects/CSE214/books/
            StopWords.txt");
            File file = new File("/Users/youkim/IdeaProjects/CSE214/books/
            Frankenstein.txt");
            File file2 = new File("/Users/youkim/IdeaProjects/CSE214/books/
            ThatLittleSquareBox.txt");
            Passage stop = new Passage("StopWords.txt", stop2);
            Passage one = new Passage("Frank", file);
            Passage two = new Passage("lit", file2);
            System.out.println(Math.round(cosineSimilarity(one, two)));
        }catch (Exception e){
            System.out.print(e.getMessage());
        }
    }*/

    /**
     * A method  that  calculates the similarity between two passage objects
     * using the cosine formula and modifies similarTitles.
     * @param p1 passage one
     * @param p2  passage two
     * @return a double number representing the percentage between two files.
     */
    public static double cosineSimilarity(Passage p1, Passage p2) {
        //put  all words in an arraylist
        ArrayList<String> listOfTotalWords = new ArrayList<>();
        for(String word : p1.getWords()) {
            if(!listOfTotalWords.contains(word))
                listOfTotalWords.add(word);
        }
        for(String word : p2.getWords()){
            if(!listOfTotalWords.contains(word)) {
                listOfTotalWords.add(word);
            }
        }
        //calculate the  numerator
        double numerator = 0.0;
        for(String word : listOfTotalWords){
            double u = p1.getWordFrequency(word) / p1.getWordCount();
            double v = p2.getWordFrequency(word) / p2.getWordCount();
            numerator += u * v;
        }

        //calculate  the denominator
        double denominator = 0.0;
        double one = 0.0;
        double two = 0.0;
        for (String word : listOfTotalWords) {
            //the frequency of a word
            double u = p1.getWordFrequency(word) / p1.getWordCount();
            //divided by the wword count
            double v = p2.getWordFrequency(word) / p2.getWordCount();
            one += Math.pow(u, 2);
            two += Math.pow(v, 2);
        }
        denominator += Math.sqrt(one) * Math.sqrt(two);
        double ans = (numerator / denominator) * 100;
        //modify similarTitles accordingly; but i cant without table
//        p1.similarTitles.put(p1.getTitle(), ans);
//        p1.setSimilarTitles(p1.getSimilarTitles());
        return ans;
    }

    /**
     * A helper method to store all the stopWord's into an Arraylist for easy
     * access
     * @param file specifically stopWords.txt file
     * @throws FileNotFoundException an error for when the file is null or not
     * found
     */
    public void stopWord(File file) throws FileNotFoundException {
        stopWordArray = new ArrayList();
        try(Scanner input = new Scanner(new FileInputStream(file))) {
            while (input.hasNextLine()) {
                String word = input.nextLine();
                stopWordArray.add(word);
            }
        }
    }

    /**
     * This method reads the given text file and counts each word's occurrence,
     * excluding stop words, and inserts them into the table
     * @param file a single passage to be read through
     * @throws FileNotFoundException an error for when the file  is null or
     * not found
     */
    public void parseFile(File file) throws FileNotFoundException {
        //count each word's occurrence, excluding stop words
        //word, punc, lowercase
        //ArrayList listOfWords = new ArrayList();
        try(Scanner input = new Scanner(new FileInputStream(file))){
            while(input.hasNextLine()) {
                String word = input.nextLine();
                String[] arrayWords = word.toLowerCase().split(" ");
                for(int j = 0; j < arrayWords.length; j++){
                    arrayWords[j] = arrayWords[j].replaceAll("[^a-zA-Z]", "");
                }
                wordCount += arrayWords.length; //length count spaces
                //if a stopword is in the array of words put it to null
                for(int i = 0; i < arrayWords.length; i++) {
                    if (stopWordArray.contains(arrayWords[i] )||
                      arrayWords[i].equals("")) {
                        arrayWords[i] = null;
                        this.wordCount--;
                    }
                }
                //go through the list of words
                for (int i = 0; i < arrayWords.length; i++) {
                    if(arrayWords[i] != null){
                        //not in the hash table put it in with 1, frequency at
                        // the time currently.
                        if(!this.containsKey(arrayWords[i])) {
                            this.put(arrayWords[i], 1);
                        } else if(this.containsKey(arrayWords[i])) {
                            //add one more to the frequency of that word
                            int freq = this.get(arrayWords[i]) + 1;
                            this.replace(arrayWords[i], freq);
                        }
                    }
                }
            }
        }
    }

    /**
     * A method that returns the frequency of a given word in this passage
     * @param word the word to search for its value  pair
     * @return a double representing its frequency and zero if key word does
     * not exist in this passage
     */
    public double getWordFrequency(String word)
    {
        //return similarTitles.get(word);
        if(!this.containsKey(word)){
            return 0.0;
        }else {
            return this.get(word);
        }
    }

    /**
     * A getter method for all the word's value
     * @return all the values in the hash map
     */
    public Set<String> getWords(){
        //return  similarTitles.keySet();
        return  this.keySet();
    }

    /**
     * A getter method for the title of this passage
     * @return the title
     */
    public String getTitle(){
        if (this.title.endsWith(".txt")) {
            this.title = this.title.replace(".txt", "");
        }
        return  this.title;
    }

    /**
     * A setter method to set the passage name
     * @param title the title
     */
    public void setTitle(String title){
        this.title = title;
    }

    /**
     * A getter method for the total word count of this passage
     * @return the amount of words in this passage minus stop words
     */
    public int getWordCount(){
        return  wordCount;
    }

    /**
     * A setter method for word count of this passage
     * @param wordCount the total amount of words
     */
    public void setWordCount(int wordCount){
        this.wordCount = wordCount;
    }

    /**
     * a getter method for similarTitles representing string (title) and
     * double(percentage)
     * @return similartitles
     */
    public HashMap<String, Double> getSimilarTitles(){
        return similarTitles;
    }

    /**
     * A setter method for this hash map to modify the similarity percentage
     * @param similarTitles a collection which contains other passages and the
     *                     similarity percentage
     */
    public void setSimilarTitles(HashMap<String, Double> similarTitles){
        this.similarTitles = similarTitles;
    }

    /**
     * A toString method that represents how to format this hashmap
     * @return a formated string of keys and values
     */
    @Override
    public String toString() {
        return similarTitles.toString();
    }
}


