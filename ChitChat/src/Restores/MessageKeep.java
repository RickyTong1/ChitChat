package Restores;

import java.io.Serializable;
import java.util.Vector;

public class MessageKeep implements Serializable{
	public Vector<Msg> message = new Vector<Msg>();
	
}
class Msg implements Serializable{
	String spoke;
	String time;
	int spkerID;
	Msg(int spokerID, String spoke, String time){
		  this.spoke = spoke;
		  this.spkerID = spokerID;
		  this.time = time;
	}
}
