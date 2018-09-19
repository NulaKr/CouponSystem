package coupon.sys.beans;

import java.util.Date;

/**
 * @author hadar.kraus
 */
public class Coupon {

    private long id;
    private String title;
    private Date startDate;
    private Date endDate;
    private int amount;
    private CouponType type;
    private String message;
    private double price;
    private String image;

    /**
     * constructor for new coupon (no ID)
     * @param title
     * @param startDate
     * @param endDate
     * @param amount
     * @param type
     * @param message
     * @param price
     * @param s
     */
    public Coupon(String title, Date startDate, Date endDate, int amount, CouponType type, String message, double price, String s) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.type = type;
        this.message = message;
        this.price = price;
        this.image = image;
    }

    /**
     * Constructor for existing coupon (with ID)
     * @param id
     * @param title
     * @param startDate
     * @param endDate
     * @param amount
     * @param type
     * @param message
     * @param price
     * @param image
     */
    public Coupon(long id, String title, Date startDate, Date endDate, int amount, CouponType type, String message, double price, String image) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.amount = amount;
        this.type = type;
        this.message = message;
        this.price = price;
        this.image = image;
    }

    /**
     * Get ID
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
     * Get Title
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get start date
     * @return start date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Set Start date
     * @param startDate
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Get Expiration date
     * @return
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * Set expiration date
     * @param endDate
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * Get amount of coupons
     * @return amount
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Set amount of coupond
     * @param amount
     */
    public void setAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Get coupon type
     * @return CouponType ENUM
     */
    public CouponType getType() {
        return type;
    }

    /**
     * dry coupon type
     * @param type CouponType ENUM
     */
    public void setType(CouponType type) {
        this.type = type;
    }

    /**
     * Get message
     * @return message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set message
     * @param message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Get coupon price
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Set coupon price
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get coupon image
     * @return
     */
    public String getImage() {
        return image;
    }

    /**
     * Set coupon Image
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Create printable string of the object
     * @return
     */
    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", amount=" + amount +
                ", type=" + type +
                ", message='" + message + '\'' +
                ", price=" + price +
                '}';
    }
}
