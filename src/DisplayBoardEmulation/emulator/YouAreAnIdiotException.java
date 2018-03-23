package DisplayBoardEmulation.emulator;

public class YouAreAnIdiotException extends RuntimeException {
	//This is used for conveying that the user is an idiot or has done something idiotic for any reason.
	//Love, John
	public YouAreAnIdiotException() {
		super();
	}
	public YouAreAnIdiotException(String s) {
		super(s);
	}
	public YouAreAnIdiotException(String s, Throwable throwable) {
		super(s,throwable);
	}
	public YouAreAnIdiotException(Throwable throwable) {
		super(throwable);
	}
}
