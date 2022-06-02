import java.util.ArrayList;

/**
 * A Class / HashMap that stores Frequency List objects in an array list.
 */
public class FrequencyTable{
    ArrayList<FrequencyList> table = new ArrayList<>();
    /**
     * This static method iterates through passages and constructs
     * frequencylists wwith each  passage's  appropriate word frequencies.
     * @param passages a collection containing passage objects
     * @return the frequency  table constructed from each passage in passages.
     */
    public static FrequencyTable buildTable(ArrayList<Passage> passages){
        FrequencyTable table1 = new FrequencyTable();
        ArrayList<String> filter  = new ArrayList<>();
        for(Passage i : passages){
            for(String word : i.keySet()) {
                for(int j = 0; j <= table1.table.size(); j++){
                    if(!filter.contains(word)) {
                        FrequencyList list = new FrequencyList(word, passages);
                        filter.add(word);
                        table1.table.add(list);
                    }
                }
            }
        }
        return table1;
    }

    /**
     * adds a passage into the frequencytable and  updates the frequencylists
     * @param p  the passage being iterated and put into the table
     * @throws IllegalArgumentException Error for when the  passage is null
     * or empty
     */
    public void addPassage(Passage p) throws IllegalArgumentException{
        try{

        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    /**
     * This method returns  the frequency of the given  word in the given
     * passage
     * @param p the  passage being iterated over
     * @param word the word/ index in the  frequency table we are currently in
     * @return the frequency of a given word in the passage
     * @throws IllegalArgumentException error for when the  passage is null or
     * empty
     */
    public int getFrequency(Passage p, String word)
      throws IllegalArgumentException{
        try{
            return (int)p.getWordFrequency(word);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return 0;
        }
    }
}
