package DisplayBoardEmulation.emulator;

public class YouAreAnIdiotException extends RuntimeException {
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
