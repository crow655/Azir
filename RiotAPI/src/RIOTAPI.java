import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;


public class RIOTAPI {
	public static final String API_KEY = "RGAPI-92b44a83-ae0f-48e7-9791-04a7b11b4a6d";
	public static ArrayList<String> mList;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		String summoner = "mikehct";
		//URL api_url = new URL("https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summoner+"?api_key="+api_key);
		//String puuid = getPuuid(summoner);
		String puuid =getPuuidBySumId("4Ub2BX_pn6BsNwlkIYzqU_nU780UJO8DobYv_oj2I-vwoSBai-Fq6731sA");
	
		//print(puuid);
		//summoner = getName(puuid);
		//print (summoner);
}
		//ArrayList<String> test = getSpec("loveless");
		
	
	
	public static String getName(String puuid) {
		String message =sendMessage("https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-puuid/"+puuid);
		//print(message);
		ArrayList<String> list = getArrayList(message);
		return list.get(3).substring(5).replaceAll(",", "");
	}
	public static void print(String a) {
		System.out.println(a);
	}
	public static void print(int a) {
		System.out.println(a);
	}
	public static ArrayList<String> getSumm(String name){

		String sum= sendMessage("https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/"+ name);
		mList = new ArrayList<String>();
		
		String word = "";
		for(char c: sum.toCharArray()) {
			if (c ==',') {
				mList.add(word);
				word = "";
			}
			else if(c=='"' || c == '{' || c=='}') {
			}
			else {
				word+=c;
			}		
		}
		mList.add(word);
		return mList;
	}
	public static String getSumID(String name) {
		ArrayList<String> mList = getSumm(name);
		String sumID = mList.get(0).substring(3);
		//print("*****TESTING________"+ mList.get(2));
		print("Sending SumID:" + sumID);
		return sumID;
	}
	public static String getPuuid(String name) {
		
		ArrayList<String> mList = getSumm(name);
		String puuid = mList.get(2).substring(6);
		//print("*****TESTING________"+ mList.get(2));
		//print("Sending Puuid:" + puuid);
		return puuid;
		
	}
	public static String getPuuidBySumId(String name) {
		ArrayList<String> list = getArrayList(sendMessage("https://na1.api.riotgames.com/lol/summoner/v4/summoners/"+name));
		String puuid = list.get(2).substring(6).replaceAll(",", "");
		for(String s: list) {
			print(s);
		}
		return puuid;
		
	}
	public static String sendMessage(String url) {
		String message = "";
		try {
			URL api_url = new URL(url+ "?api_key="+ API_KEY);
			
			HttpURLConnection one = (HttpURLConnection) api_url.openConnection();
			one.setRequestMethod("GET");
			one.connect();
			DataInputStream in = new DataInputStream(one.getInputStream());
			BufferedReader read = new BufferedReader(new InputStreamReader(in));
			message = read.readLine();
			in.close();
		
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		return message;
	}
	public static ArrayList<String> getSpec(String name){
		String sumID = getSumID(name);
		String message = sendMessage("https://na1.api.riotgames.com/lol/spectator/v4/active-games/by-summoner/"+ sumID);
		//print(message);
		message = message.replaceAll("}","");
		//message = removeSymbol("}{",message);
		mList = new ArrayList<String>();
		
		String word = "";
		for(char c: message.toCharArray()) {
			if (c =='}') {
				word+=c;
				mList.add(word);
				word = "";
			}
			
			else if(c=='"') {
			}
			else {
				word+=c;
			}		
		}
		mList.add(word);
		return mList;
	}
	public static ArrayList<String> getArrayList(String s){
		String word ="";
		ArrayList<String> message = new ArrayList<String>();
		for (char c: s.toCharArray()) {
			switch(c) {
			case(','):
				word+=c;
				message.add(word);
				word ="";
				break;
			case('"'):
			case('{'):
			case('}'):
				break;
			default:
				word+=c;
			}
				
		}
		return message;
		
	}
}
