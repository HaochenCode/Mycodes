import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class TweetCollection {
    private ArrayList<Tweet> tweets = new ArrayList<>();

    public TweetCollection(ArrayList<Tweet> twts){
        tweets = twts;
    }

    public TweetCollection(){}

    public void readTweetsFromURL() throws IOException {
        URL url = new URL("https://homes.luddy.indiana.edu/classes/summer2022/csci/c343-mitja/test2022/tweet-data-May18.txt");
        Scanner in = new Scanner(url.openStream());

        String line,author, content;
        while(in.hasNext()){
            line = in.nextLine();
            author = line.substring(0,line.indexOf(" ")+1);
            //System.out.println(author);
            content = line.substring((line.indexOf(" "))+1);
            //System.out.println("test1");
            Tweet t = new Tweet(author,content);
            tweets.add(t);
        }
        in.close();
    }



    public ArrayList<Tweet> getTweetsByAuthor(String author){
        ArrayList<Tweet> temp = new ArrayList<>();
        for(int i = 0; i < tweets.size();i++){
            //System.out.println("test1");
            //System.out.println(tweets.get(i).getUser());
            if (tweets.get(i).getUser().equalsIgnoreCase(author)){
                //System.out.println("test");
                temp.add(tweets.get(i));
            }
        }
        if(temp.size()==0) {
            System.out.println("Author not found!");
        }
        return temp;
    }

    public boolean tweetedAbout(String author, String content){
        boolean bol = false;
        ArrayList<Tweet> t = new ArrayList<>();
        for(int i = 0; i < tweets.size(); i++){
            if(tweets.get(i).getUser().equals(author)){
                t.add(tweets.get(i));
            }
        }

        for(int i = 0; i < t.size();i++){
            bol = (t.get(i).contains(content));

        }

        return bol;
    }

    public static void main(String[] args) throws IOException {
        TweetCollection tc = new TweetCollection();
        // Read from URL
        tc.readTweetsFromURL();

        // Test for the getTweets function
        ArrayList<Tweet> tw = tc.getTweetsByAuthor("startupXcel ");
        for(Tweet t : tw){
            t.display();
        }

        System.out.println(tc.getTweetsByAuthor("abcd "));

        // Test for the tweetedAbout function
        System.out.println(tc.tweetedAbout("ShallowAddict ", "Neurological "));
        System.out.println(tc.tweetedAbout("cdcd ", "asd "));
    }
}
