package fr.eni.projectParcAutomobile.exception;

public class BusinessException extends Exception
{
	private static final long serialVersionUID = 1L;
	/**
	 * Constructeur.
	 */
	public BusinessException() {
	}
	
	public BusinessException(String message) {
		super(message);
	}
	public BusinessException(Throwable cause) {
		super(cause);
	}
	
	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
	
}
