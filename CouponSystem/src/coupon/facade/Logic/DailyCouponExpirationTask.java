package coupon.facade.Logic;

import coupon.sys.beans.Coupon;
import coupon.sys.dao.CouponDBDAO;
import coupon.sys.exceptions.CouponSystemException;
import coupon.sys.exceptions.DBConnectionException;
import coupon.sys.exceptions.DaoException;

import java.util.Date;
import java.util.Iterator;
import java.util.Set;
    /**
    Thread to remove expired coupons from the DB. Run at the coupon system start and then every 24 hours
     @author hadar.kraus
     **/
public class DailyCouponExpirationTask implements Runnable {

    CouponDBDAO couponDBDAO = new CouponDBDAO();
    Boolean quit = false;

    public void setQuit(Boolean quit) {
        this.quit = quit;
    }


    @Override
    public void run() {
        while (quit = false) {
            this.removeExpiredCoupons();
            try {
                Thread.sleep(86400000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeExpiredCoupons(){
        try {
            Set<Coupon> coupons = couponDBDAO.getAllCoupons();
            Iterator it = coupons.iterator();
            while (it.hasNext()){
                Coupon coupon = (Coupon)it.next();
                Date now = new Date(System.currentTimeMillis());
                if (coupon.getEndDate().before(now)){
                    couponDBDAO.removeCoupon(coupon);
                }
            }
        } catch (DBConnectionException e) {
            e.printStackTrace();
        } catch (DaoException e) {
            e.printStackTrace();
        }
    }

    public void stopTask() throws CouponSystemException {
        while (quit) {
            try {
                this.wait();
                Thread.currentThread().interrupt();
            } catch (InterruptedException e) {
                throw new CouponSystemException("Thread is closed");
            }
        }


    }
}
