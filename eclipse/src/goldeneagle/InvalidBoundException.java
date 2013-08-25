package goldeneagle;

public class InvalidBoundException extends RuntimeException {
	public InvalidBoundException(String canonicalName) {
		super(canonicalName);
	}

	private static final long serialVersionUID = 1L;

}
