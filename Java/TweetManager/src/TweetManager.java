/*C343 / Summer 2022
2022-5-12
Haochen Sun
haocsun*/
import java.util.ArrayList;

public class TweetManager {
    private ArrayList<Tweet> tweets = new ArrayList<>();

    public void addTweet(Tweet tweet){
        tweets.add(tweet);
    }

    public void remove(String anAuthor){
        for(int i = 0; i < tweets.size(); i++){
            if(tweets.get(i).getAuthor().equals(anAuthor)){
                tweets.remove(i);
            }
        }
    }

    public Tweet get(String anAuthor){
        boolean found = false;
        for(int i = 0; i < tweets.size(); i++){
            if(tweets.get(i).getAuthor().equals(anAuthor)){
                found = true;
                return tweets.get(i);
            }
        }
        if(!found){
            return null;
        }
        else
            return null;

    }

    public String toString(){
        String str = "";
        for(int i = 0; i < tweets.size();i++){
            str += (tweets.get(i));
        }
        return str;
    }

    public static void main(String[] args) {
        Tweet t1 = new Tweet("I had a bad day.", "Haochen");
        Tweet t2 = new Tweet("I had a good day.", "Haochen");
        Tweet t3 = new Tweet("C343 is FUN", "JONNIE");
        Tweet t4 = new Tweet("C343 is HARD", "JONNIE");
        Tweet t5 = new Tweet("Summer is hot", "TOM");
        Tweet t6 = new Tweet("Winter is cold", "Betty");

        TweetManager tm = new TweetManager();
        tm.addTweet(t1);
        tm.addTweet(t2);
        tm.addTweet(t3);
        tm.addTweet(t4);
        tm.addTweet(t5);
        tm.addTweet(t6);

        System.out.println("Initial tweets: ");
        System.out.println(tm);
        System.out.println(tm.get("TOM"));
        tm.remove("Betty");
        System.out.println("After changes: ");
        System.out.println(tm);
    }



}