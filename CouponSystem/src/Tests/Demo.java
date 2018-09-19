package Tests;

import coupon.ClientType;
import coupon.facade.AdminFacade;
import coupon.facade.Logic.CouponSystem;
import coupon.sys.exceptions.LoginException;

public class Demo {

    public static void main(String[] args) {

        CouponSystem cs = CouponSystem.getInstance();
        try {
            AdminFacade adminFacade = (AdminFacade)cs.login("admin", "1234", ClientType.ADMIN);
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
}
