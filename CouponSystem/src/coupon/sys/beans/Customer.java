package coupon.sys.beans;

import java.util.Set;

public class Customer {

    private long id;
    private String custName;
    private String password;
    private Set<Coupon> coupons;

    public Customer(String custName, String password) {
        this.custName = custName;
        this.password = password;
    }

    public Customer(long id, String custName, String password) {
        this.id = id;
        this.custName = custName;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustName() {
        return custName;
    }

    public void setCustName(String custName) {
        this.custName = custName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", custName='" + custName + '\'' +
                ", password='" + password + '\'' +
                ", coupons=" + coupons +
                '}';
    }
}
