package org.basic.deck;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private int id;
    private String name;
    private List<Card> hand;
    
    
    public Player(int id , String name) {
    	this.id=id;
    	this.name=name;
    	this.hand=new ArrayList<Card>();
    }
    
    public void dealCard(Card card) {
    	this.hand.add(card);
    }
    
    public List<Card> getHand(){
    	return hand; 
    }
    
    public long getTotalHandValue() {
    	int total = 0 ;
    	for(Card card : hand) {
    		total += card.getFace().value();
    	}
    	return total;
    }
    
    public String toString() {
    	return name;
    }
    
    public int getId() {
    	return this.id;
    }

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Player) || obj== null )
			return false;
		return ((Player)obj).getId()==this.getId();
		
	}
    
    
}
