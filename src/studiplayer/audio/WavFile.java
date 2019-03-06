package studiplayer.audio;
import studiplayer.basic.WavParamReader;

public class WavFile extends SampledFile {
	
	public WavFile() {
		super();
	}
	
	public WavFile(String u) throws NotPlayableException {
		super(u);
		readAndSetDurationFromFile(u);
	}
	
	public static long computeDuration(long numberOfFrames, float frameRate) {
		float gsd = (numberOfFrames / frameRate) * 1000000;
		return (long) gsd;
	}
	
	public void readAndSetDurationFromFile(String pathname) throws NotPlayableException {
		try {
		WavParamReader.readParams(pathname);
		}
		catch(Exception e) {
			throw new NotPlayableException(pathname, e);
		}
		long nof = WavParamReader.getNumberOfFrames();
		float fr = WavParamReader.getFrameRate();
		longLaenge =  computeDuration(nof, fr);
	}
	
	public String toString() {
		return interpret + " - " + titel + " - " + getFormattedDuration();
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
