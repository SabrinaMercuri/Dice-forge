package protocol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import core.Face;

public class RollMessage {
	
	private HashMap<String, ArrayList<Face>> rollResults;
	
	public RollMessage() {
		
	}
	
	public RollMessage(HashMap<String, ArrayList<Face>> rollResults)
	{
		this.rollResults = rollResults;
	}
	
	public HashMap<String, ArrayList<Face>> getRollResults(){
		return rollResults;
	}
	
	public void setRollResults(Map<String, List<Face>> rollResults) {
		this.rollResults = new HashMap<String, ArrayList<Face>>();
		for(String name : rollResults.keySet())
			this.rollResults.put(name, new ArrayList<Face>(rollResults.get(name)));
	}
}
