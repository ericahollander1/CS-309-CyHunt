package grp_1_yn_4.Model;

import javax.persistence.*;
import java.util.Date;

/**
 * Place Class
 */
@Entity
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique=true)
    private String name;
    private String description;
    private String address;
    private float longitude;
    private float latitude;
    private Date created_ts;
    private Date updated_ts;

    /**
     * Constructor for Place with
     *
     * @param name
     * @param description
     * @param address
     * @param longitude
     * @param latitude
     */
    public Place(String name, String description, String address, float longitude, float latitude) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Default constructor for Place
     */
    public Place() {
    }

    // =============================== Getters and Setters for each field ================================== //

    /**
     * Get Place ID
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * Set Place ID
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Get Place Name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set Place Name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get Place Description
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set Place Description
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get Place address
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Set place Address
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Get Place Longitude
     * @return longitude
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * Set Place Longitude
     * @param longitude
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    /**
     * Get Place Latitude
     * @return latitude
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * Set Place Latitude
     * @param latitude
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /**
     * Get Date Created
     * @return created_time
     */
    public Date getCreated_ts() {
        return created_ts;
    }

    /**
     * Set Created Time
     * @param created_ts
     */
    public void setCreated_ts(Date created_ts) {
        this.created_ts = created_ts;
    }

    /**
     * Get Updated Time
     * @return
     */
    public Date getUpdated_ts() {
        return updated_ts;
    }

    /**
     * Update Time
     * @param updated_ts
     */
    public void setUpdated_ts(Date updated_ts) {
        this.updated_ts = updated_ts;
    }


}