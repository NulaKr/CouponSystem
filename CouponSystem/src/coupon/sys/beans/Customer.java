package coupon.sys.beans;

import java.util.Set;

/**
 * @author hadar.kraus
 */
public class Customer {

    private long id;
    private String custName;
    private String password;
    private Set<Coupon> coupons;

    /**
     * Constructor for new customer (no IID)
     * @param custName
     * @param password
     */
    public Customer(String custName, String password) {
        this.custName = custName;
        this.password = password;
    }

    /**
     * Constructor for existing customer (with ID)
     * @param id
     * @param custName
     * @param password
     */
    public Customer(long id, String custName, String password) {
        this.id = id;
        this.custName = custName;
        this.password = password;
    }

    /**
     * Get ccustomer ID
     * @return ID
     */
    public long getId() {
        return id;
    }

    /**
     * Set customer ID
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get customer name
     * @return customer name
     */
    public String getCustName() {
        return custName;
    }

    /**
     * Set customer name
     * @param custName
     */
    public void setCustName(String custName) {
        this.custName = custName;
    }

    /**
     * Get customer's password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set customer password
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Create a printable string from the object
     * @return String
     */
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
