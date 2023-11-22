package shurima;

public class Azir {
	public class builder{
		private String key;
		private String region = "americas";
		public void setKey(String key) {
			this.key = key;
		}
		public void setRegion(String region) {
			this.region = region;
		}
		public Azir build() {
			return new Azir(key,region);
		}
	}
		
	private String region;
	private String api_key;
	
	public Azir(String key, String region) {
		this.api_key = key;
		this.region = region;
		
	}
}
