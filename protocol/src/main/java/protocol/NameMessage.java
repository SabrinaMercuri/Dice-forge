package protocol;

public class NameMessage {

	private String name;
	private boolean isModified;
	
	
	public NameMessage() {
		
	}
	
	public NameMessage(String name, boolean isModified) {
		this.name = name;
		this.isModified = isModified;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void setIsModified(boolean isModified) {
		this.isModified = isModified;
	}
	
	public boolean getIsModified() {
		return isModified;
	}
	
}
