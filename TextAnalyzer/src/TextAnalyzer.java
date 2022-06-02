
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The TextAnalyzer class that will  drive  the main user interaction.
 * This class will ask for the user's directory of which the files are kept.
 * It will call methods to compare the similarities between each files.
 */
public class TextAnalyzer {

    public static void main(String[] args) throws FileNotFoundException {
        FrequencyTable table = new FrequencyTable();
        ArrayList<Passage> passages = new ArrayList<>();

        Scanner stdin = new Scanner(System.in);
        System.out.print("Enter the directory of a folder of text files: ");
        String directoryPath = stdin.nextLine(); //folder
        System.out.println("Reading texts . . .");

        //make stop word hash map
        File[]  directoryOfFiles  = new File(directoryPath).listFiles();
        for(File i : directoryOfFiles) {  //files
            if (i.getName().equals("StopWords.txt")) {
                Passage stopWords = new Passage(i.getName(), i);
            }
        }
        // for each file
        for(File i : directoryOfFiles){  //files
            if(!i.getName().equals("StopWords.txt")){
                if (i.getName().endsWith(".txt")){
                    String filename = i.getName().replace(".txt", "");
                    Passage textPassage = new Passage(filename, i);
                    passages.add(textPassage);
                }
            }
        }

       //table.buildTable(passages);

        //print


        System.out.println(String.format("%-24s %18s", "Text (title)", "| " +
          "Similarities (%)"));
        ArrayList<Passage> author = new ArrayList();
        for(Passage i : passages){
            String similar1 = "";
            String similar2 = "";
            int x = 0;
            for(Passage j : passages){
                if(!i.getTitle().equals(j.getTitle())){
                    if(x<2) {
                        similar1 += j.getTitle() + " (" +
                          Math.round(i.cosineSimilarity(i, j)) + "%), ";
                    }
                    if(x>=2){
                        similar2 += j.getTitle() + " (" +
                          Math.round(i.cosineSimilarity(i, j)) + "%), ";;
                    }
                    x++;
                }
            }
            System.out.println("--------------------------------------------" +
              "-------------------------------------");
            System.out.println(String.format("%-24s | %25s", i.getTitle(),
              similar1));
            System.out.println(String.format("%-24s | %25s", "", similar2));
        }

        //print 60% and up
        System.out.println("\nSuspected Texts With Same Authors");
        System.out.println("------------------------------------------------" +
          "--------------------------------");
        for(Passage i : passages) {
            for (Passage j : passages) {
                if (!i.getTitle().equals(j.getTitle())) {
                    long percentage = Math.round(i.cosineSimilarity(i, j));
                    if (percentage >= 60) {
                        //and not in the array (meaning duplicate)
                        if(author.isEmpty()){
                            author.add(i);
                            author.add(j);
                        }
                        for (int k = 0; k < author.size(); k = k + 2) {
                            if (author.get(k).getTitle().equals(i.getTitle())
                              && author.get(k+1).getTitle().
                              equals(j.getTitle()) || author.get(k).getTitle()
                              .equals(j.getTitle()) && author.get(k+1).
                              getTitle().equals(i.getTitle())) {
                                continue;
                            } else {
                                author.add(i);
                                author.add(j);
                            }
                            //if position 1 and 2 not have then add/ for
                            // loop by i +2
                            //System.out.println("'" + i.getTitle() + "' and'"
                            // + j.getTitle() +  "' may have the same author
                            // (" + percentage + "% similar).");
                        }
                    }
                }
            }
        }
        for(int k = 0; k < author.size(); k = k + 2) {
            long num = Math.round(author.get(k).cosineSimilarity
              (author.get(k), author.get(k+1)));
            System.out.println("'" + author.get(k).getTitle() + "' and '" +
              author.get(k+1).getTitle() + "' may have the same author (" + num
              + "% similar).");
        }
        System.out.println("\nProgram terminating . . .");
    }
}
