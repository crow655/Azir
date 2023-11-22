package shurima;

import java.util.ArrayList;

public class Summoner {

	public static class builder{
		public builder() {
			
		}
		private String name;
		private Puuid puuid;
		private SumId id;
		private boolean nameValid;
		public void sumName(String name) {
			this.name = name;
			nameValid = true;
		}
		public void puuid(String id) {
			this.puuid = new Puuid(id);
		}
		public void sumId(String id) {
			this.id = new SumId(id);
		}
		public Summoner build() {
			if (nameValid) {
				return new Summoner(name);
			}
			if (puuid!=null) {
				return new Summoner(puuid);
			}
			if (id!=null) {
				return new Summoner(id);
			}
			return new Summoner("idk");
		}
	}
	public builder getBuilder() {
		return new builder();
	}
	protected static class Puuid {
		private String id;
		private boolean valid = false;
		@SuppressWarnings("unused")
		Puuid(String id) {
			this.id = id;
			valid = true;
		}
		public boolean isValid() {
			return valid;
		}
		@SuppressWarnings("unused")
		public String getId() {
			return this.id;
		}
	}
	protected static class SumId{
		private String id;
		boolean valid = false;
		@SuppressWarnings("unused")
		SumId(String id){
			this.id = id;
			this.valid = true;
		}
		public boolean isValid() {
			return valid;
		}
		@SuppressWarnings("unused")
		public String getId() {
			return id;
		}
	}
		
	//private fields for Summoner
	private String puuid;
	private String name;
	private String id;
	
	//Begin constructors for summoner
	public Summoner(String name) {
		this.name = name;
		ArrayList<String> account= RIOTAPI.getAccount(name);
		String properName = name.replaceAll(" ","");
		this.puuid = RIOTAPI.getPuuid(properName);
		this.id = RIOTAPI.getSumID(properName);
	}
	
	
	public Summoner(Puuid id) {
		this.puuid = id.getId();
		this.name = RIOTAPI.getName(puuid);
		this.id = RIOTAPI.getSumID(name.replaceAll(" ", ""));
		
	}
	public Summoner(SumId id) {
		this.id = id.getId();
		this.puuid = RIOTAPI.getPuuidBySumId(this.id);
		this.name = RIOTAPI.getName(puuid);
	}
	
	
	public String getName() {
		return name;
	}
	public String getPuuid() {
		return puuid;
	}
	public String getSumId() {
		return id;
	}

	public String toString() {
		String name = "";
		name = name+=this.name;
		name = name+="\n";
		name = name+=this.puuid;
		name = name+="\n";
		name = name+=this.id;
		name = name+="\n";
		return name;
		
	}
}
