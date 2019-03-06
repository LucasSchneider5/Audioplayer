package studiplayer.audio;

import java.util.Comparator;

public class AlbumComparator implements Comparator<AudioFile>{

	public int compare(AudioFile af1, AudioFile af2) {
		
		if(af1 != null && af2 != null) {
			if((af1 instanceof TaggedFile) && !(af2 instanceof TaggedFile)) {
				return 1;
			}
			else if(!(af1 instanceof TaggedFile) && (af2 instanceof TaggedFile)) {
				return -1;
			}
			else if(!(af1 instanceof TaggedFile) && !(af2 instanceof TaggedFile)) {
				return 0;
			}
			else {
				if(((TaggedFile) af1).getAlbum() == null || ((TaggedFile) af2).getAlbum() == null) {
					throw new NullPointerException("Fehler, ein String aus getAlbum() ist null ! (Album)");
				}
				else {
					return ((TaggedFile) af1).getAlbum().compareTo(((TaggedFile) af2).getAlbum());
				}
			}
		}
		else {
			throw new NullPointerException("Fehler, eins der beiden Parameter ist null ! (Album)");
		}
	}

}
