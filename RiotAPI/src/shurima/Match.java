package shurima;
import java.lang.*;
import java.util.ArrayList;

import shurima.Summoner.Puuid;
public class Match {
	private String matchId;
	private String message;
	private ArrayList<String> messageList;
	private ArrayList<Summoner> summoners;
	//con
	public Match(String s) {
		matchId = s;
		message = RIOTAPI.sendMessage("https://americas.api.riotgames.com/lol/match/v5/matches/" + matchId);
		messageList = makeList(message);
		//printList(getPlayerNames());
		summoners = makeSumList(message);
		for (Summoner su: summoners) {
			RIOTAPI.print(su.toString());
		}
		RIOTAPI.print("Requests to riot: "+RIOTAPI.requests);
		
		
	}
	private ArrayList<String> makeViewableList(String m){
		char[] charList = m.toCharArray();
		int indent = 0;
		ArrayList<String> list = new ArrayList<String>();
		String curr = "";
		String space = "  ";
		for (char c: charList) {
			switch(c) {
			case(','):
				curr+=c;
				list.add(space.repeat(indent)+curr);
				curr = "";
				break;
			case('{'):
			case('['):
				list.add(space.repeat(indent)+curr);
				curr ="";
				curr+=c;
				list.add(space.repeat(indent)+curr);
				curr = "";
				indent++;
				
				break;
			case('}'):
			case(']'):
				list.add(space.repeat(indent)+curr);
				curr = "";
				indent--;
				curr+=c;
				list.add(space.repeat(indent)+curr);
				curr = "";
				break;
			default:
				curr+=c;
			}
		}
		return list;
	}
	public static ArrayList<String> makeList (String m){
		
		char[] charList = m.toCharArray();
		ArrayList<String> list = new ArrayList<String>();
		String curr = "";
		String space = "  ";
		for (char c: charList) {
			switch(c) {
			case(','):
				//curr+=c;
				list.add(curr);
				curr = "";
				break;
			case('{'):
			case('['):
				list.add(curr);
				curr ="";
				//curr+=c;
				//list.add(curr);
				curr = "";
				break;
			case('}'):
			case(']'):
				list.add(curr);
				curr = "";
				//curr+=c;
				//list.add(curr);
				curr = "";
				break;
			case('"'):
				break;
			default:
				curr+=c;
			}
		}
		return list;
	}
	
	public String find (String st, int num) {
			
			String info = "";
			int colon = 0;
			int size = st.length();
			int count = 0;
			boolean cont = true;
			//RIOTAPI.print("looking for:" + st + "  Length: "+ size);
			for (String s: messageList) {
				//RIOTAPI.print(s.substring(0,size-1));
				if(s.length()>=size) {
					//RIOTAPI.print(s.toLowerCase().substring(0,size));
					
					if (s.substring(0, size).toLowerCase().equals(st)) {
						
						info += s.substring(size+1)+"\n";
						count++;
					}
					if (count == num) {
						return info;
					}
					
				}
				
				
			}
			
			//RIOTAPI.print("Loops iterations: "+count);
			
			
			
		
		 
		return"-1";
	}
	private String removeColon(String info) {
		int colon = 0;
	
		for (char c: info.toCharArray()) {
			
			if (c==':') {
				info = info.substring(colon+1);
				return info;
			}
			else {
				colon++;
			}
			
		
		}
		return "Couldnt find colon";
		
	}
	private void printList(ArrayList<String> list) {
		for (String s: list) {
			RIOTAPI.print(s);
		}
	}
	public ArrayList<String> getPlayers(ArrayList<String> list){
		ArrayList<String> players = new ArrayList<String>();
		
		for (int i = 0; i < 10; i++) {
			players.add(list.get(5+i));
		}
		return players;
	}
	//Might be irrelevant due to arguments
	public static ArrayList<String> getPlayerNames(ArrayList<String> list){
		
		ArrayList<String> players = new ArrayList<String>();
		
		for (int i = 0; i < 10; i++) {
			Puuid id = new Puuid(list.get(5+i));
			players.add(RIOTAPI.getName(list.get(5+i)));
			//players.add(currPlayer.getName());
			//players.add(list.get(5+i));
		}
		return players;
	}
public ArrayList<String> getPlayerNames(){
		
		ArrayList<String> players = new ArrayList<String>();
		
		for (int i = 0; i < 10; i++) {
			Puuid id = new Puuid(messageList.get(5+i));
			players.add(RIOTAPI.getName(messageList.get(5+i)));
			//players.add(currPlayer.getName());
			//players.add(list.get(5+i));
		}
		return players;
	}
public static ArrayList<Summoner> makeSumList(String message){
	ArrayList<Summoner> temp = new ArrayList<Summoner>();
	ArrayList<String> data = makeList(message);
	ArrayList<String> players=getPlayerNames(data);
	for (String player: players) {
		//RIOTAPI.print(player);
		Summoner.builder b = new Summoner.builder();
		b.sumName(player);
		Summoner s = b.build();
		temp.add(s);
	}
	
	return temp;
	
}
	
	
}
