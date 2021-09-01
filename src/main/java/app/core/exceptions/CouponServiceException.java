package app.core.exceptions;

public class CouponServiceException extends CouponSystemException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CouponServiceException() {
	}

	public CouponServiceException(String message) {
		super(message);
	}

	public CouponServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public CouponServiceException(Throwable cause) {
		super(cause);
	}

	public CouponServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
