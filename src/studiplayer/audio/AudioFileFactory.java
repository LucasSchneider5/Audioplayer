package studiplayer.audio;
public class AudioFileFactory {
	
	public static AudioFile getInstance(String pathname) throws NotPlayableException {

		String newpath = pathname.substring(pathname.length() - 4);

		if(newpath.toLowerCase().contains(".wav")) {
			WavFile filewav = new WavFile(pathname);
			return filewav;
		}
		else if(newpath.toLowerCase().contains(".ogg") || pathname.toLowerCase().contains(".mp3")) {
			TaggedFile filetagged = new TaggedFile(pathname);
			return filetagged;
		}
		else {
			throw new NotPlayableException(pathname, "Unknown suffix for AudioFile: \""+ pathname + "\"");
		}
	}
}
