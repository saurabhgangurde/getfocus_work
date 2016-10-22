import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JsonSimpleExample {
     public static void main(String[] args) {

   
    //declaring object to read json
	JSONParser parser = new JSONParser();
	//scanbsssidList will contain two list of bssid of each scan
	ArrayList<ArrayList<String>> scanBssidList = new ArrayList<ArrayList<String>>();
	try {
		
		//loop to iterate over two scans
		for(int i=0;i<2;i++)
		{
			
			Object obj = parser.parse(new FileReader("test"+Integer.toString(i)+".json"));  
	
			JSONArray jsonObjectarray = (JSONArray) obj;
		
			//iterator for json array
			Iterator<JSONObject> jasoniterate=jsonObjectarray.iterator();
			//declaring list to add bssid of each wifi station
			ArrayList<String> singleList = new ArrayList<String>();
	
			while(jasoniterate.hasNext())
			{
				JSONObject factObj = (JSONObject) jasoniterate.next();
				
				//reading attributes of json object
				String ssid = (String) factObj.get("ssid");		
				String bssid = (String)factObj.get("bssid");				
				long signalStrength = (long)factObj.get("signalStrength");				
				long signalFrequency = (long)factObj.get("signalFrequency");
				
				//add entry to List
				singleList.add(bssid);

			
			}
			
			//adding List to list of list
			scanBssidList.add(singleList);
			
			System.out.println("file readed");
		}


	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (ParseException e) {
		e.printStackTrace();
	}
	
	ArrayList<String> scan1 = new ArrayList<String>();
    scan1=scanBssidList.get(0);
    remove_duplicate(scan1);

    
    ArrayList<String> scan2 = new ArrayList<String>();
    scan2=scanBssidList.get(1);
    
    remove_duplicate(scan2);
    
    //finding similarity index based on JI
    //i.e finding common elements and total elements in two lists(scans)based on bssid.
    int totalElements=0;
    int commonElements=0;
	for(int i=0;i<scan1.size();i++){
		
		String bssid=scan1.get(i);
		int retval=scan2.indexOf(bssid);
		if(retval!=-1) commonElements++;

	} 
	
	totalElements=scan1.size()+scan2.size()-commonElements;
	
	//calculating JI
	float JI=(float)commonElements/totalElements;
	
	//printing results
	System.out.println("common elements: "+Integer.toString(commonElements));
	System.out.println("total elements: "+Integer.toString(totalElements));
	System.out.println("JI: "+Float.toString(JI));
	System.out.println("similarity index: "+Integer.toString((int)(JI*5)));
	
     }
     
     //function to remove duplicate entries from a list
     public static void remove_duplicate(ArrayList<String>bssidList)
     {
    	 Iterator<String> iterator = bssidList.iterator();
    	 int j=0;
    	 while(j<bssidList.size())
    	 {    		 
    		 for(int i=j+1;i<bssidList.size();i++){
    				
    				if(bssidList.get(j)==bssidList.get(i))
    				{
    					bssidList.remove(i);    					
    				}
    			} 
    		 j++;	 
    		 
    	 } 	 
     
     }
     
}