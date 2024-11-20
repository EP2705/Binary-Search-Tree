// import statements
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 *  @author Eliandro Pizzonia
 *  
 * This class implements the user interface
 */
public class Interface {
	
	/**
	 * @param args
	 * @throws MultimediaException
	 * @throws DictionaryException
	 * @throws IOException
	 * 
	 * main method
	 */
	public static void main(String[] args) throws MultimediaException, DictionaryException, IOException {
		
		// check if the file name is provided as a command line argument
		if (args.length < 1) {
			System.out.println("Please provide an imput file");
			return;
		}
		String filename = args[0];
		
		// reading the file input file
		BufferedReader file = new BufferedReader(new FileReader(filename));
		
		// creating a new dictionary
		BSTDictionary dictionary = new BSTDictionary();
		
		int keyType = 0;
		String keyLabel = "";
		String data = "";
		String line = "";
		String command = "";
		
		// reading the file and wile keeping track of each label and data
		while (file != null && data!=null) {
			keyLabel = file.readLine();
			data = file.readLine();
			
			if (keyLabel == null || data == null) { 
				break;
			}
			
			// determining the key based on the data type
			if (data.charAt(0) == '/'){
				keyType = 2;
				data = data.substring(1);
			}
			else if(data.charAt(0) == '-') {
				keyType = 3;
				data = data.substring(1);
			}
			else if(data.charAt(0) == '+') {
				keyType = 4;
				data = data.substring(1);
			}
			else if(data.charAt(0) == '*') {
				keyType = 5;
				data = data.substring(1);
			}
			else if(data.contains(".jpg")) {
				keyType = 6;
			}
			else if(data.contains(".gif")) {
				keyType = 7;
			}
			else if(data.contains(".html")) {
				keyType = 8;
			}
			else {
				keyType = 1;
			}
			
			// creating a new key and record and adding it to the dictionary
			Key newKey = new Key(keyLabel, keyType);
			Record newRecord = new Record(newKey, data);
	
			try {
				dictionary.put(newRecord);
			} catch (DictionaryException e) {
				System.out.println(e.getMessage());
			}
		}
		
		// reading user input for commands
		StringReader keyboard = new StringReader();
		
		do {
			// reading the command from the user
			line = keyboard.read("Enter next command: ");
			StringTokenizer inputLine = new StringTokenizer(line);
			
			// counting the the amount of inputs from the user and setting the corresponding command, label and key type variables
			if(inputLine.countTokens() == 2) {
				command = inputLine.nextToken();
				keyLabel = inputLine.nextToken();
			}
			else if(inputLine.countTokens() == 3) {
				command = inputLine.nextToken();
				keyLabel = inputLine.nextToken();
				keyType = Integer.parseInt(inputLine.nextToken());
			}
			else if(inputLine.countTokens() > 3) {
				command = inputLine.nextToken();
				keyLabel = inputLine.nextToken();
				keyType = Integer.parseInt(inputLine.nextToken());
				int excludeLength = command.length() + keyLabel.length() + 4;
				data = line.substring(excludeLength);
			}
			else {
				command = inputLine.nextToken();
				keyLabel = null;
				keyType = 0;
			}
			
			command = command.toLowerCase();
			
			// calling private helper methods based on user commands
			switch(command) {
				
			case "define": define(keyLabel, dictionary);
					break;
			case "translate": translate(keyLabel, dictionary, filename);
					break;
			case "sound": sound(keyLabel, dictionary, filename);
					break;
			case "play": play(keyLabel, dictionary, filename);
					break;
			case "say": say(keyLabel, dictionary, filename);
					break;
			case "show": show(keyLabel, dictionary, filename);
					break;
			case "animate": animate(keyLabel, dictionary, filename);
					break;
			case "browse": browse(keyLabel, dictionary, filename);
					break;
			case "delete": delete(keyLabel, dictionary, keyType);	
					break;
			case "add": add(keyLabel, dictionary, keyType, data);
					break;
			case "list": list(keyLabel, keyType, dictionary);
					break;
			case "first": first(dictionary);
					break;
			case "last": last(dictionary);
					break;
			case "exit":
					break;
			default: System.out.println("Invalid command.");
				break;
			}
			
		}while(!command.equals("exit"));

	}

	
	/**
	 * @param label 
	 * @param dictionary
	 * 
	 * If the ordered dictionary has a Record object d whose Key attribute has label = w and type = 1, then the method prints the 
	 * data attribute of this record
	 */
	private static void define(String label, BSTDictionary dictionary) {
		
		Key key = new Key(label, 1);
		Record d = dictionary.get(key);
		
		if(d == null) {
			System.out.println("The word " + label + " is not in the dictionary");
		}
		else {
			System.out.println(d.getDataItem());
		}
	}
	
	
	/**
	 * @param label
	 * @param dictionary
	 * @param filename
	 * 
	 * If the ordered dictionary has a Record object d whose Key attribute has label = w and type = 2, then the method prints the 
	 * data attribute of this record
	 */
	private static void translate(String label, BSTDictionary dictionary, String filename) {
		Key key = new Key(label, 2);
		Record d = dictionary.get(key);
		
		if(d == null) {
			System.out.println("There is no definition for the word " + label);
		}
		else {
			System.out.println(d.getDataItem());
		}
	}
	
	/**
	 * @param label
	 * @param dictionary
	 * @param filename
	 * @throws MultimediaException
	 *
	 * If the ordered dictionary has a Record object d whose Key attribute has label = w and type = 3, then the method 
	 * plays the audio file whose name is stored in the data attribute of this record
	 */
	private static void sound(String label, BSTDictionary dictionary, String filename) throws MultimediaException {
		Key key = new Key(label, 3);
		Record d = dictionary.get(key);
		
		if(d == null) {
			System.out.println("There is no sound file for " + label);
		}
		else {
			SoundPlayer sound = new SoundPlayer();
			sound.play(d.getDataItem());
		}
	}

	
	/**
	 * @param label
	 * @param dictionary
	 * @param filename
	 * @throws MultimediaException
	 * 
	 * If the ordered dictionary has a Record object d whose Key attribute has label = w and type = 4, then method 
	 * plays the audio file whose name is stored in the data attribute of this record
	 */
	private static void play(String label, BSTDictionary dictionary, String filename) throws MultimediaException {
		Key key = new Key(label, 4);
		Record d = dictionary.get(key);
		
		if(d == null) {
			System.out.println("There is no music file for " + label);
		}
		else {
			SoundPlayer play = new SoundPlayer();
			play.play(d.getDataItem());
		}
	}
	
	
	/**
	 * @param label
	 * @param dictionary
	 * @param filename
	 * @throws MultimediaException
	 * 
	 * If the ordered dictionary has a Record object d whose Key attribute has label = w and type = 5, then the method plays the 
	 * audio file whose name is stored in the data attribute of this record
	 */
	private static void say(String label, BSTDictionary dictionary, String filename) throws MultimediaException {
		Key key = new Key(label, 5);
		Record d = dictionary.get(key);
		
		if(d == null) {
			System.out.println("There is no voice file for " + label);
		}
		else {
			SoundPlayer say = new SoundPlayer();
			say.play(d.getDataItem());
		}
	}

	
	/**
	 * @param label
	 * @param dictionary
	 * @param filename 
	 * @throws MultimediaException
	 * 
	 * If the ordered dictionary has a Record object d whose Key attribute has label = w and type = 6, then the method shows 
	 * the image file whose name is stored in the data attribute of this record
	 */
	private static void show(String label, BSTDictionary dictionary, String filename) throws MultimediaException {
		Key key = new Key(label, 6);
		Record d = dictionary.get(key);
		
		if(d == null) {
			System.out.println("There is no image file for " + label);
		}
		
		else {
			PictureViewer show = new PictureViewer();
			show.show(d.getDataItem());
		}
	}
	
	
	/**
	 * @param label
	 * @param dictionary
	 * @param filename
	 * @throws MultimediaException
	 * 
	 * If the ordered dictionary has a Record object d whose Key attribute has label = w and type = 7, then the method shows 
	 * the image file whose name is stored in the data attribute of this record
	 */
	private static void animate(String label, BSTDictionary dictionary, String filename) throws MultimediaException {
		Key key = new Key(label, 7);
		Record d = dictionary.get(key);
		
		if(d == null) {
			System.out.println("There is no animated image file for " + label);
		}
		else {
			PictureViewer animate = new PictureViewer();
			animate.show(d.getDataItem());
		}
	}
	
	
	/**
	 * @param label
	 * @param dictionary
	 * @param filename
	 * @throws MultimediaException
	 * 
	 * If the ordered dictionary has a Record object d whose Key attribute has label = w and type= 8, 
	 * then the method shows the webpage whose URL is stored in the data attribute of this record
	 */
	private static void browse(String label, BSTDictionary dictionary, String filename) throws MultimediaException {
		Key key = new Key(label, 8);
		Record d = dictionary.get(key);
		
		if(d == null) {
			System.out.println("There is no webpage called " + label);
		}
		else {
			ShowHTML browse = new ShowHTML();
			browse.show(d.getDataItem());
		}
	}
	
	
	/**
	 * @param label
	 * @param dictionary
	 * @param KeyType
	 * @throws DictionaryException
	 * 
	 * Removes from the ordered dictionary the Record object with key (w,k)
	 */
	private static void delete(String label, BSTDictionary dictionary, int KeyType) throws DictionaryException {
		Key key = new Key(label, KeyType);

		try {
			dictionary.remove(key);
		} catch (DictionaryException e) {
			System.out.println("no record in the ordered dictionary has key (" + label + "," + KeyType + ")");
		}	
	}
	
	
	/**
	 * @param label
	 * @param dictionary
	 * @param KeyType
	 * @param data
	 * @throws DictionaryException
	 * 
	 * Inserts a Record object ((w,t),c) into the ordered dictionary if there is no record with key (w,t) already there
	 */
	private static void add(String label, BSTDictionary dictionary, int KeyType, String data) throws DictionaryException {
		Key key = new Key(label, KeyType);
		Record d = new Record(key, data);
		
		try {
			dictionary.put(d);
		} catch (DictionaryException e) {
			System.out.println("A record with the given key (" + label +"," + KeyType + 
				"is already in the dictionary");
		}	
	}
	
	
	/**
	 * @param label
	 * @param KeyType
	 * @param dictionary
	 *  
	 * prints the label attributes (if any) of all the Record objects in the ordered dictionary that start with prefix; if prefix is
	 * the label attribute of a Record object in the ordered dictionary, then prefix is printed also. If several Record 
	 * objects in the dictionary have the same label attribute w, and w starts with prefix, then the string w will 
	 * be printed as many times as the number of Record objects in the ordered dictionary that contain it
	 * 
	 */
	private static void list(String label, int KeyType, BSTDictionary dictionary) {

		Key key =  new Key(label, 0);
		Record d = dictionary.successor(key);
		ArrayList<String> output = new ArrayList<String>();
		
		// iterating through the records and adding them to the array list
		while(d!= null && d.getKey().getLabel().startsWith(label)) {
			output.add(d.getKey().getLabel());
			d = dictionary.successor(d.getKey());
		}
		
		// removing the brackets of the array list
		String s = output.toString().substring(1);
		s = s.substring(0, s.length()-1);
		System.out.println(s);
	}
	
	
	/**
	 * @param dictionary
	 * 
	 * prints all the attributes of the Record object in the ordered dictionary with smallest key
	 */
	private static void first(BSTDictionary dictionary) {
		Record smallestRecord = dictionary.smallest();
		
		if(smallestRecord != null) {
			System.out.println(smallestRecord.getKey().getLabel() + "," + smallestRecord.getKey().getType() + "," + 
								smallestRecord.getDataItem());
		}
	}
	
	
	/**
	 * @param dictionary
	 * 
	 * prints all the attributes of the Record object in the ordered dictionary with largest key
	 */
	private static void last(BSTDictionary dictionary) {
		Record largestRecord = dictionary.largest();
		
		if(largestRecord != null) {
			System.out.println(largestRecord.getKey().getLabel() + "," + largestRecord.getKey().getType() + "," + 
								largestRecord.getDataItem());
		}
	}

}
