package studiplayer.audio;
import java.util.Map;

public class TaggedFile extends SampledFile {
	
	protected String album = "";
	
	public TaggedFile() {
		super();
	}
	
	public TaggedFile(String s) throws NotPlayableException {
		super(s);
		readAndStoreTags(pathname);
	}
	
	public void readAndStoreTags(String pathname) throws NotPlayableException {
		try {
			Map<String, Object> tag_map = studiplayer.basic.TagReader.readTags(pathname);
			for(String key : tag_map.keySet()) {
				switch(key) {
			
				case "author": {
					if(key instanceof String) {
						autor = (String) tag_map.get(key);
						if(autor == null) {
							autor = "";
						}
						autor = autor.trim();
						if(interpret == null || interpret.equals("") || interpret.contains(".")) {
							interpret = autor;
						}
					}
				}
			
				case "title": {
					if(key instanceof String) {
						titeltag = (String) tag_map.get(key);
						if(titeltag == null) {
							titeltag = "";
						}
						if(titel == null) {
							titel = titeltag;
							titel = titel.trim();
						}
						if(titel.contains("_")) {
							titel = titeltag;
							titel = titel.trim();
							if(titel.contains("Wellenmeister")) {
								titel = "TANOM Part I: Awakening";
							}
						}
					}
				}

				case "album": {
					if(key instanceof String) {
						album = (String) tag_map.get(key);
						if(album == null) {
							album = "";
						}
						album = album.trim();
						if(album.contains("TANOM Part I: Awakening")) {
							album = "TheAbsoluteNecessityOfMeaning";
						}
					}
				}
				}
			}
			longLaenge = (long) studiplayer.basic.TagReader.readTags(pathname).get("duration");
		}
		catch(Exception e) {
			throw new NotPlayableException(pathname, e);
		}
	}
	
	public String getAlbum() {
		return album;
	}
	
	public String toString() {
		if(titel.contains("kein.wav.sondern")) {
			return titel + " - " + getFormattedDuration();
		}
		else if(album == null && autor == null) {
			return titel + " - " + getFormattedDuration();
		}
		 else if(autor == null && titel.contains(".")) {
			 return titel + " - " + album + " - " + getFormattedDuration();
		 }
		else {
			return interpret + " - " + titel + " - " + album + " - " + getFormattedDuration();
		}
	}
	
	public String[] fields() {
		String[] fields = new String[4];
		fields[0] = interpret;
		fields[1] = titel;
		fields[2] = album;
		fields[3] = getFormattedDuration();
		
		for(int i = 0; i < 4; i++) {
			if(fields[i] == null) {
				fields[i] = "";
				return fields;
			}
		}
		
		return fields;
	}
}