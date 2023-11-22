package shurima;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;

import shurima.Summoner.Puuid;
import shurima.Summoner.SumId;
import shurima.Summoner.builder;


public class RIOTAPI {
	public static int requests;
	public static final String API_KEY = "RGAPI-7716a748-e880-4ea9-b046-7b95ab26cd03";
	public static ArrayList<String> mList;
	public static void main(String[] args) throws IOException {
	
		Match match1 = new Match("NA1_4833705248");
		
		
	
	}
	
	public static ArrayList<String> getAccount(Puuid id) {
		String message = getName(id.getId());
		ArrayList<String>account = new ArrayList<String>();
		ArrayList<String>list = getArrayList(message);
		account.add(list.get(3).substring(5).replaceAll(",", ""));
		account.add(list.get(2).substring(6).replaceAll(",", ""));
		account.add(list.get(0).substring(3).replaceAll(",", ""));
		return account;
	}
	public static ArrayList<String> getAccount(SumId id) {
		String message = getPuuidBySumId(id.getId());
		ArrayList<String>account = new ArrayList<String>();
		ArrayList<String>list = getArrayList(message);
		account.add(list.get(3).substring(5).replaceAll(",", ""));
		account.add(list.get(2).substring(6).replaceAll(",", ""));
		account.add(list.get(0).substring(3).replaceAll(",", ""));
		return account;
	}
	public static ArrayList<String> getAccount(String name) {
		String message = getPuuid(name);
		ArrayList<String>account = new ArrayList<String>();
		ArrayList<String>list = getArrayList(message);
		account.add(list.get(3).substring(5).replaceAll(",", ""));
		account.add(list.get(2).substring(6).replaceAll(",", ""));
		account.add(list.get(0).substring(3).replaceAll(",", ""));
		return account;

	}
	
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
		//print("Sending SumID:" + sumID);
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
		
		return puuid;
		
	}
	public static String sendMessage(String url) {
		requests++;
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
