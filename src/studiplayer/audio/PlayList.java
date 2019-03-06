package studiplayer.audio;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Scanner;

@SuppressWarnings("serial")
public class PlayList extends LinkedList<AudioFile>  {
	
	private int position = 0;
	
	private boolean modus = true;
	
	public PlayList() {
		
	}
	
	public PlayList(String pathname) {
		loadFromM3U(pathname);
	}

	public int getCurrent() {
		return position;
	}
	
	public void setCurrent(int current) {
		position = current;
	}
	
	public AudioFile getCurrentAudioFile() {
		if(this.size() <= position || position < 0) {
			return null;
		}
		else {
			return this.get(position);
		}
	}
	
	public void changeCurrent() {
		if(this.size() -1 <= position) {
			if(modus == true) {
				Collections.shuffle(this);
				setCurrent(0);
			}
			else {
				setCurrent(0);
			}
		}
		else {
			setCurrent(position + 1);
		}
	}
	
	public void setRandomOrder(boolean randomOrder) {
		if(randomOrder == true) {
			modus = true;
			Collections.shuffle(this);
		}
		else {
			modus = false;
		}
	}
	
	public void saveAsM3U(String pathname) {
		FileWriter writer = null;
		String linesep = System.getProperty("line.separator");

		try {
			writer = new FileWriter(pathname);
			for(int i = 0; i < this.size(); i++) {
				writer.write(this.get(i).getPathname() + linesep);
			}
		}
		catch(IOException e) {
			throw new RuntimeException("Unable to write to file " + pathname + ":" + e.getMessage());
		}
		finally {
			try {
				writer.close();
			}
			catch(Exception e) {
				throw new RuntimeException("Unable to close file " + pathname + ":" + e.getMessage());
			}
		}
	}
	
	public void loadFromM3U(String pathname) {
		Scanner scanner = null;
		String line;
		AudioFile lied; 
		this.clear();
		try {
			scanner = new Scanner(new File(pathname));
			while(scanner.hasNextLine()) {
				line = scanner.nextLine();
				if(!line.startsWith("#") && line.length() > 10) {
					try {
							lied = AudioFileFactory.getInstance(line);
							this.add(lied);
						}
						catch(Exception e) {
							e.printStackTrace();
					}
				}
			}
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
		finally {
			try {
				scanner.close();
			}
			catch(Exception e) {
				throw new RuntimeException("Unable to close file " + pathname + ":" + e.getMessage());
			}
		}
	}
	
	public void sort(SortCriterion order)  {
		switch(order) {
		case AUTHOR: {
			Collections.sort(this, new AuthorComparator());
			break;
		}
		case TITLE: {
			Collections.sort(this, new TitleComparator());
			break;
		}
		case ALBUM: {
			Collections.sort(this, new AlbumComparator());
			break;
		}
		case DURATION: {
			Collections.sort(this, new DurationComparator());
			break;
		}
		}
	}
}