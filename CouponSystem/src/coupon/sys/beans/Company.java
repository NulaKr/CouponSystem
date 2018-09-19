package coupon.sys.beans;



import java.util.Set;

/**
 * @author hadar.kraus
 */
public class Company {

    private long id;
    private String compName;
    private String password;
    private String email;
    private Set<Coupon> coupons;

    /**
     * Constructor to create new company (no ID)
     * @param compName
     * @param password
     * @param email
     */
    public Company(String compName, String password, String email) {
        this.compName = compName;
        this.password = password;
        this.email = email;
    }

    /**
     * Constructor to get existing company (with ID)
     * @param id
     * @param compName
     * @param password
     * @param email
     */
    public Company(long id, String compName, String password, String email) {
        this.id = id;
        this.compName = compName;
        this.password = password;
        this.email = email;
    }

    /**
     *
     * @return ID
     */
    public long getId() {
        return id;
    }

    /**
     * Set ID
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Get company name
     * @return company name
     */
    public String getCompName() {
        return compName;
    }

    /**
     * Set company name
     * @param compName
     */
    public void setCompName(String compName) {
        this.compName = compName;
    }

    /**
     * Get password
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set passowrd
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get Email
     * @return Email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set Email
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Print object
     * @return Company in String
     */
    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", compName='" + compName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
