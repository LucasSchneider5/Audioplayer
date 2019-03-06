package studiplayer.audio;

import java.util.Comparator;

public class AuthorComparator implements Comparator<AudioFile>{

	public int compare(AudioFile af1, AudioFile af2) {
		if(af1 != null && af2 != null) {
			return af1.getAuthor().compareTo(af2.getAuthor());
		}
		else {
			throw new NullPointerException("Fehler, eins der beiden Parameter ist null ! (Autor)");
		}
	}
	
}
