/*
 * Student Name: Yao Huang
 * Lab Professor: Aman Kahlon
 * Due Date: August 10
 * Modified: August 6
 * Description: Assignment4 Address class
*/

public class Address {
	private String street_number;
	private String street_name;
	private String street_type;
	private String street_orientation;
	private String city_name;
	private String province;
    private String latitude;
    private String longitude;
	
	public Address() {
		street_number = "";
		street_name = "";
		street_type = "";
		street_orientation = "";
		city_name = "";
		province = "";
	}
	
	public Address(String street_number, String street_name, String street_type, String street_orientation,
			String city_name, String province) {
		this.street_number = street_number;
		this.street_name = street_name;
		this.street_type = street_type;
		this.street_orientation = street_orientation;
		this.city_name = city_name;
		this.province = province;
	}

	public String getStreet_number() {
		return street_number;
	}

	public void setStreet_number(String street_number) {
		this.street_number = street_number;
	}

	public String getStreet_name() {
		return street_name;
	}

	public void setStreet_name(String street_name) {
		this.street_name = street_name;
	}

	public String getStreet_type() {
		return street_type;
	}

	public void setStreet_type(String street_type) {
		this.street_type = street_type;
	}

	public String getStreet_orientation() {
		return street_orientation;
	}

	public void setStreet_orientation(String street_orientation) {
		this.street_orientation = street_orientation;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}
	
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
	
} // end of Address class
