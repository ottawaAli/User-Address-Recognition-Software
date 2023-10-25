/*
 * Student Name: Yao Huang
 * Lab Professor: Aman Kahlon
 * Due Date: August 10
 * Modified: August 6
 * Description: Assignment4 Person class
*/

public class Person {
	private String first_name;
	private String last_name;
	private String spouse_first_name;
	private String spouse_last_name;
	
	public Person() {
		first_name="";
		last_name="";
		spouse_first_name="";
		spouse_last_name="";
	}
	
	public Person(String first_name, String last_name, String spouse_first_name, String spouse_last_name) {
		this.first_name = first_name;
		this.last_name = last_name;
		this.spouse_first_name = spouse_first_name;
		this.spouse_last_name = spouse_last_name;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getSpouse_first_name() {
		return spouse_first_name;
	}

	public void setSpouse_first_name(String spouse_first_name) {
		this.spouse_first_name = spouse_first_name;
	}

	public String getSpouse_last_name() {
		return spouse_last_name;
	}

	public void setSpouse_last_name(String spouse_last_name) {
		this.spouse_last_name = spouse_last_name;
	}
	
} // end of Person class
