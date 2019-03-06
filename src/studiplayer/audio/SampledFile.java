package studiplayer.audio;
import studiplayer.basic.BasicPlayer;

public abstract class SampledFile extends AudioFile {
	
	protected String laenge;
	protected String autor;
	protected String titeltag;
	protected String album;
	protected long longLaenge;
	
	public SampledFile() {
		super();
	}
	
	public SampledFile(String d) throws NotPlayableException {
		super(d);
	}
	
	public void play() throws NotPlayableException {
		try {
			BasicPlayer.play(this.pathname);
		}
		catch(Exception e) {
			throw new NotPlayableException(pathname, e);
		}
	}
	
	public void togglePause() {
		BasicPlayer.togglePause();
	}
	
	public void stop() {
		BasicPlayer.stop();
	}
	
	public String getFormattedDuration() {
		laenge = timeFormatter(longLaenge);
		return laenge;
	}
	
	public String getFormattedPosition() {
		long zeit = studiplayer.basic.BasicPlayer.getPosition();
		String lz = timeFormatter(zeit);
		return lz;
	}
	
public static String timeFormatter(long microtime) {
		
		if(microtime < 0) {
			throw new RuntimeException("Negativ time value provided");
		}
		
		long minuten = (long) (((microtime / 1000000) / 60));
		long sekunden = (long) ((microtime / 1000000) % 60);
		
		String Minuten = "" + minuten;
		String Sekunden = "" + sekunden;
		
		if(minuten < 10) {
			Minuten = "0" + Minuten;
		}
		
		if(sekunden < 10) {
			Sekunden = "0" +  Sekunden;
		}
		
		if(minuten > 99) {
			throw new RuntimeException("Time value exceeds allowed format");
		}
		
		String zeit = new String(Minuten + ":" + Sekunden);
		
		return zeit;
	}
}
