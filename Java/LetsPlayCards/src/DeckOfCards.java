/*C343 / Summer 2022
2022-5-12
Haochen Sun
haocsun*/


import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class DeckOfCards implements DeckOfCardsADT{
    private ArrayList<String> cards = new ArrayList<>();

    public DeckOfCards(){};

    public void initialize(){
        for(int i = 0; i < 13; i++){
            cards.add((i+1) + "S");
        }
        for(int i = 0; i < 13; i++){
            cards.add((i+1) + "C");
        }
        for(int i = 0; i < 13; i++){
            cards.add((i+1) + "H");
        }
        for(int i = 0; i < 13; i++){
            cards.add((i+1) + "D");
        }

    }

    public String drawRandomCard(){
        Random rdm = new Random();
        int n = rdm.nextInt(52);
        return cards.get(n);
    }

    public String drawTheTopCard(){
        return cards.get(0);
    }

    public void shuffleTheCards(){
        Collections.shuffle(cards);
    }

    public void pushCardOnTop(String card){
        int index = 0;
        for (int i = 0; i < cards.size();i++){
            if(cards.get(i).equals(card)){
                index = i;
            }
        }
        String theCard = cards.get(index);
        cards.remove(index);
        cards.add(theCard);

    }

    public static void main(String[] args) {
        DeckOfCards c1 = new DeckOfCards();
        DeckOfCards c2 = new DeckOfCards();
        DeckOfCards c3 = new DeckOfCards();

        c1.initialize();
        System.out.println("Before doing anything: ");
        System.out.println(c1.cards);
        System.out.println(c1.drawRandomCard());
        System.out.println(c1.drawTheTopCard());
        c1.shuffleTheCards();
        c1.pushCardOnTop("2S");
        System.out.println("After management: ");
        System.out.println(c1.cards);
        System.out.println();

        c2.initialize();
        System.out.println("Before doing anything: ");
        System.out.println(c2.cards);
        System.out.println(c2.drawRandomCard());
        System.out.println(c2.drawTheTopCard());
        c2.shuffleTheCards();
        c2.pushCardOnTop("4C");
        System.out.println("After management: ");
        System.out.println(c2.cards);
        System.out.println();

        c3.initialize();
        System.out.println("Before doing anything: ");
        System.out.println(c3.cards);
        System.out.println(c3.drawRandomCard());
        System.out.println(c3.drawTheTopCard());
        c3.shuffleTheCards();
        c3.pushCardOnTop("10H");
        System.out.println("After management: ");
        System.out.println(c3.cards);
        System.out.println();
    }
}
