package studiplayer.audio;

import java.util.Comparator;

public class DurationComparator implements Comparator<AudioFile>{

	public int compare(AudioFile af1, AudioFile af2) {
		
		SampledFile sf1 = (SampledFile) af1;
		SampledFile sf2 = (SampledFile) af2;
		
		if(af1 != null && af2 != null) {
			if((af1 instanceof SampledFile) && !(af2 instanceof SampledFile)) {
				return 1;
			}
			else if(!(af1 instanceof SampledFile) && (af2 instanceof SampledFile)) {
				return -1;
			}
			else if(!(af1 instanceof SampledFile) && !(af2 instanceof SampledFile)) {
				return 0;
			}
			else {
				if(sf1.getFormattedDuration() == null || sf2.getFormattedDuration() == null) {
					throw new NullPointerException("Fehler, der Wert Duration ist null ! (Duration");
				}
				else {
					return sf1.getFormattedDuration().compareTo(sf2.getFormattedDuration());
				}
			}
		}
		else {
			throw new NullPointerException("Fehler, eins der beiden Parameter ist null ! (Duration)");
		}
	}

}
