/*
 * Student Name: Yao Huang
 * Lab Professor: Aman Kahlon
 * Due Date: August 10
 * Modified: August 6
 * Description: Assignment4 DisplayMyAddressOnTheMap class
*/

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DisplayMyAddressOnTheMap {
	private static String inputFileLocation = "C:\\CST7284\\input\\InputAddresses.txt";
	private static String outputFileLocation = "C:\\CST7284\\output\\OutputAddresses.csv";
	
	public static void main(String[] args) {	
		Address address = new Address();
		Person person = new Person();
		// Create two ArrayLists to store the person and address data
		ArrayList<Person> personList = new ArrayList<>();
		ArrayList<Address> addressList = new ArrayList<>();	
		
		// Read the input file using a BufferedReader and FileReader
        try {
        	FileReader fr = new FileReader(inputFileLocation);
        	BufferedReader br = new BufferedReader(fr); 
            String row;
    		int rowCounter = 0;
    		String[] words = null;

            // Read the input file row by row
            while ((row = br.readLine()) != null) {
            	// If the row is empty, set rowCounter to 0
            	if (row.isEmpty()) {
                    rowCounter = 0;
                } else {
                    rowCounter++;
                }

                //  If the row is not empty
                if (rowCounter != 0) {
                	// Split the row into an array using the space (" ") as the delimiter. 
                    words = row.split(" ");
                    removeComma(words);
                }
                
                    if (rowCounter == 1) { //If it is the first row (name)                  
                    if (words.length == 2) { // If there are 2 words in the name row
                        person.setFirst_name(words[0]);
                        person.setLast_name(words[1]);
                    } else if (words.length == 4) { // If there are 4 words in the name row
                        person.setFirst_name(words[0]);
                        if (!words[1].equals("and")) {
                            person.setLast_name(words[1]);
                        } else {
                            person.setLast_name(words[3]);
                        }
                        person.setSpouse_first_name(words[2]);
                        person.setSpouse_last_name(words[3]);
                    } else {
                    	System.out.println("Name info error: " + row);
                    }
                } else if (rowCounter == 2) { //If it is the second row (street)   
                    if (words.length >= 3) {
                        address.setStreet_number(transformStreetNum(words[0]));
               			if (words[1].equalsIgnoreCase("st")) {
            				address.setStreet_name(words[1]+" "+words[2]);
            				if (words.length>=4) address.setStreet_type(words[3]);
            				if (words.length>=5) address.setStreet_orientation(transformOrient(words[4]));
            				if (words.length>=6) System.out.println("Street info exception: "+row);
            			} else {
            				address.setStreet_name(words[1]);
            				address.setStreet_type(words[2]);
            				if (words.length>=4) address.setStreet_orientation(transformOrient(words[3]));
            				if (words.length>=5) System.out.println("Street info exception: "+row);
            			}
            		} else {
            			System.out.println("Street info error: "+row);
            		}
                } else if (rowCounter == 3) { //If it is the third row (city&province)
                	if (words.length==2) {
                			address.setCity_name(words[0]);
                			if (words[1].length()==2) address.setProvince(words[1]);
                			else System.out.println("Province format error: "+words[1]);
                		} else {
                			System.out.println("City&Province info error: "+row);
                		}
                } else if (rowCounter == 0) { //If the row is empty, one person's data ends here
                	// Store the data into the ArrayLists
                	personList.add(person);
                	addressList.add(address);
                	
            		// Recreate one Address object and one Person object to store the next person's data
                	address = new Address();
            		person = new Person();
                }
            } // End of while
            
            br.close();          
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Store the last person's data to the ArrayLists
        personList.add(person);
    	addressList.add(address);
    	
        // Output to a CSV file
    	try {
    		FileWriter fw = new FileWriter(outputFileLocation);
    				
    		// Output the ArrayLists one by one
    		for (int i=0; i<personList.size(); i++) {
    			fw.append(personList.get(i).getFirst_name()).append(',');
    			fw.append(personList.get(i).getLast_name()).append(',');
    			fw.append(personList.get(i).getSpouse_first_name()).append(',');
    			fw.append(personList.get(i).getSpouse_last_name()).append(',');
	    		
    			fw.append(addressList.get(i).getStreet_number()).append(',');
    			fw.append(addressList.get(i).getStreet_name()).append(',');
    			fw.append(addressList.get(i).getStreet_type()).append(',');
    			fw.append(addressList.get(i).getStreet_orientation()).append(',');
	    		
    			fw.append(addressList.get(i).getCity_name()).append(',');
    			fw.append(addressList.get(i).getProvince()).append("\n");
    		}
    		System.out.println(outputFileLocation+" created successfully!");
    		fw.close();
        } catch (IOException e) {
        	e.printStackTrace();
        }
    	
    	// Retrieve Latitude/Longitude (Lat/Long) set as part of the results returned
        for (Address add : addressList) { //Iterates each Address object in the addressList
            String queryAddress = add.getStreet_number() + " " + add.getStreet_name() + " " +
            		add.getStreet_type() + ", " + add.getCity_name() + ", " + add.getProvince();
            // Call the loaLong method to retrieve Lat/Long
            String latLongData = latLong(queryAddress);
            String[] latLongArray = latLongData.split(",");
            if (latLongArray.length == 2) {
            	add.setLatitude(latLongArray[0]);
            	add.setLongitude(latLongArray[1]);
            }
        }    	
       
        // Create the csv file
        String outputFilePath = "C:\\CST7284\\output\\LatLong.csv";

        try {
            // Create a FileWriter for the output file
            FileWriter outputWriter = new FileWriter(outputFilePath);

            try {
                // Write the title row
                outputWriter.append("Latitude,Longitude,Name,Icon,IconScale,IconAltitude\n");

                // Write the records
                for (int i = 0; i < addressList.size(); i++) {
                    Address currentAddress = addressList.get(i);
                    Person currentPerson = personList.get(i);

                    // Add information including Lat/Long
                    StringBuilder sb = new StringBuilder();
                    sb.append(currentAddress.getLatitude()).append(',')
                                 .append(currentAddress.getLongitude()).append(',')
                                 .append(currentPerson.getFirst_name()).append(' ').append(currentPerson.getLast_name());

                 // Add the spouse's name if there is one
                    if (!currentPerson.getSpouse_first_name().isEmpty()) {
                            sb.append(" and* ").append(currentPerson.getSpouse_first_name())
                              .append('*').append(currentPerson.getSpouse_last_name());                       
                    }

                    // Add icon information
                    sb.append(",111,1,1\n");

                    // Write the sb to the CSV file
                    outputWriter.append(sb.toString());
                }

                System.out.println(outputFilePath + " created successfully!");
            } finally {
                // Close the outputWriter
                outputWriter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	} // End of main method
	
	// Remove comma in the row
	public static void removeComma(String[] words) {
	    for (int i = 0; i < words.length; i++) {
	        words[i] = words[i].replace(",", "");
	    }
	}
	
	// Process streetNum to standard form
	// Extracts the last numeric portion from the given streetNum in the format "3-175".
	// Returns the extracted numeric portion or the original streetNum if no numeric portion is found.
	public static String transformStreetNum(String streetNum) {
	    String[] parts = streetNum.split("-");
	    if (parts.length > 1) {
	        return parts[parts.length - 1];
	    }
	    return streetNum;
	}
	
	// Transform orientation word to standard form
	public static String transformOrient(String orient) {
	    String lowerCaseOrient = orient.toLowerCase();
	    switch (lowerCaseOrient) {
	        case "east":
	        case "e":
	            return "E";
	        case "west":
	        case "w":
	            return "W";
	        case "north":
	        case "n":
	            return "N";
	        case "south":
	        case "s":
	            return "S";
	        default:
	            return null;
	    }
	}

	// Method to retrieve the Latitude/Longitude (Lat/Long) set as part of the results returned.
    public static String latLong(String address) {
        String latLong = "";
    	final String myKey = "AIzaSyAEHxo8FjxSQcve69NuJrElPSL-F3Qbfl0";
        try {
            // Encode the address to make it URL-safe
        	String encodedAddress = URLEncoder.encode(address, "UTF-8");
        	// Set the URL for the Google Geocoding API format
            String urlRequest = "https://maps.googleapis.com/maps/api/geocode/json?address=" + encodedAddress 
            		+ "\"&key=" + myKey;
          
            // Create a URL object and open a connection
            URL url = new URL(urlRequest);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            // Check if the response code is successful
            if (conn.getResponseCode() == 200) {
            	// Parse the Lat/Long from the JSON
                latLong = parseLatLongFromJson(conn);
            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return latLong;
    }

    // Parse Lat/Long from JSON 
    // Take a HttpURLConnection object as input 
    private static String parseLatLongFromJson(HttpURLConnection conn) {
        // Read input stream of the HTTP connection 
    	try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            StringBuilder sb = new StringBuilder();
            String line;
            
            // Read each line and append to the StringBuilder
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            // Create a JSONObject using the data in sb
            JSONObject jsonObject = new JSONObject(sb.toString());
            
            // Check if the status in the JSON response is "OK"
            if (jsonObject.getString("status").equals("OK")) {
                // Extract the location information from the response
                JSONArray results = jsonObject.getJSONArray("results");
                if (results.length() > 0) {
                    JSONObject location = results.getJSONObject(0).getJSONObject("geometry")
                            .getJSONObject("location");
                    double lat = location.getDouble("lat");
                    double lng = location.getDouble("lng");
                    return lat + "," + lng;
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        // Return an empty string if the status is not OK
    	return "";
    }

} // End of DisplayMyAddressOnTheMap class
