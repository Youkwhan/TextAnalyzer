import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class stores the frequency  of a given word to each of it's related
 * passage and it's title of each passage and it's corresponding entry  in
 * the arraylist
 */
public class FrequencyList {

    private String word;
    private ArrayList<Integer> frequencies;
    private HashMap<String, Integer> passageIndieces;

    /**
     * A constructor for FrequencyList containing a string variable
     * corresponding to its word that it's keeping track of.
     * @param word the word this frequency list represents
     * @param passages a collection containing all passage objects
     */
    public FrequencyList(String word, ArrayList<Passage> passages){
        this.word = word;//bcz some text might  not have the word xd.
        passageIndieces = new HashMap<>();//make  empty hash
        frequencies = new ArrayList<>();

        for(Passage i : passages){
            frequencies.add(getFrequency(i));
            // !!!cast to INT)!!! is that okay??? the  number of
            // frequency of a word
            addPassage(i);//the index
        }
    }

    /**
     * A helper method for adding passage to this frequency list
     * @param p the passage being added into the frequency hash map
     */
    public void addPassage(Passage p){
        passageIndieces.put(p.getTitle() ,frequencies.size()-1);
    }

    /**
     * This method returns the frequency of variable word in the given passage
     * @param p  the passage that will be  checked to see what the value to
     *           the corresponding key "word".
     * @return the frequency of that word or 0 if the passage is not contained
     * in this frequency list
     */
    public int getFrequency(Passage p){
        if(p.containsKey(word)) {
            return (int) p.getWordFrequency(word);
        }else{
            return 0;
        }
    }

    /**
     * A getter method to return  the word we are iterating for
     * @return word
     */
    public String getWord() {
        return word;
    }
}
