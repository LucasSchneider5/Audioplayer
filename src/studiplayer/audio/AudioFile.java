package studiplayer.audio;
import java.io.File;
import java.util.regex.Matcher;

public abstract class AudioFile {
	
	public abstract void play() throws NotPlayableException;
	public abstract void togglePause();
	public abstract void stop();
	public abstract String getFormattedDuration();
	public abstract String getFormattedPosition();
	public abstract String[] fields();
	
	protected String pathname;
	protected String filename;
	
	protected String interpret;
	protected String titel;
	
	public AudioFile() {
		
	}

	public AudioFile(String path) throws NotPlayableException {
	
		parsePathname(path);
		getFilename();
		parseFilename(filename);
		
		File f = null;
		f = new File(pathname);
		if(f.canRead() == false) {
			throw new NotPlayableException(pathname, "Datei kann nicht gelesen werden !");
		}
	}
 	
	public void parsePathname(String path) {	
		
		String trennung = File.separator;
		
		pathname = path;
		
		pathname = pathname.replaceAll("/+", Matcher.quoteReplacement(File.separator));
		pathname = pathname.replaceAll("\\\\+", Matcher.quoteReplacement(File.separator));
		
		if("Linux".equals(System.getProperty("os.name"))) {
			if(pathname.contains(":")) {
				pathname = trennung + pathname;
				pathname = pathname.replace(":", "");
			}
		}
		
		else if("Mac OS".equals(System.getProperty("os.name"))) {
			if(pathname.contains(":")) {
				pathname = trennung + pathname;
				pathname = pathname.replace(":", "");
			}
		}
	
		filename = pathname;
		if(filename.endsWith(trennung)) {
			filename = "";
		}
		int LetztesZeichen = filename.lastIndexOf(trennung);
		filename = filename.substring(LetztesZeichen + 1);
	}
	
	public String getPathname() {
		return pathname;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void parseFilename(String file) {
		
		interpret = file;
		titel = file;

		if(interpret.contains("-") && interpret.length() > 5) {
		interpret = interpret.substring(0, interpret.indexOf("-"));
		interpret = interpret.trim();
		}
		else if(interpret.matches("-") == false && interpret.length() < 20) {
			interpret = "";
		}
		else if(interpret.length() < 5) {
			interpret = "";
		}
		else if(interpret.contains(".")) {
			interpret = "";
		}
		
		titel = titel.substring(titel.lastIndexOf("- ") + 1);
		if(titel.contains(".")) {
		titel = titel.substring(0, titel.lastIndexOf("."));
		}
		titel = titel.trim();
		if(titel.startsWith(".")) {
			titel = "";
		}
	}
	
	public String getAuthor() {
		return interpret;
	}
	
	public String getTitle() {
		return titel;
	}
	
	public String toString() {
		if(getAuthor().equals("")) {
			return titel;
		}
		else {
			return interpret + " - " + titel;
		}
	}
}