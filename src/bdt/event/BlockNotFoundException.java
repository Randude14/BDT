package bdt.event;

public class BlockNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -1509287368499535069L;

    public BlockNotFoundException() {
    	super();
    }
    
    public BlockNotFoundException(String message) {
    	super(message);
    }
	
}
