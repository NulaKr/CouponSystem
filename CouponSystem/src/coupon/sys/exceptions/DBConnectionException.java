package coupon.sys.exceptions;

public class DBConnectionException extends CouponSystemException {

    public DBConnectionException() {
        super();
    }

    public DBConnectionException(String message) {
        super(message);
    }

    public DBConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBConnectionException(Throwable cause) {
        super(cause);
    }

    public DBConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public DBConnectionException(CouponSystemException e) {
        super(e);
    }
}
