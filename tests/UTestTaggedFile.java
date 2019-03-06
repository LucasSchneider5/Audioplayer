import java.util.Map;

import junit.framework.TestCase;
import studiplayer.audio.SampledFile;
import studiplayer.audio.TaggedFile;
import studiplayer.audio.WavFile;
import studiplayer.basic.TagReader;

public class UTestTaggedFile extends TestCase {
	public void test_play_01() throws Exception {
		SampledFile tf = new TaggedFile("audiofiles\\\\\\\\\\\\Rock 812.mp3");
		tf.play();
	}
	
	public void test_timeFormatter_10() throws Exception {
		assertEquals("Wromt time format", "05:05", TaggedFile.timeFormatter(305862000L));
	}
	
	public void test_timeFormatter_08() throws Exception {
		try {
			TaggedFile.timeFormatter(-1L);
			fail("Time value underflows format; expecting exception");
		} catch (RuntimeException e) {
		}
	}
	
	public void test_readTags_01() throws Exception {
		TaggedFile tf = new TaggedFile("audiofiles/Rock 812.mp3");
		Map<String, Object> tag_map = TagReader.readTags(tf.getPathname());
		for(String key : tag_map.keySet()) {
			System.out.printf("\n: %s\n", key);
			System.out.printf("Type of value: %s\n", tag_map.get(key).getClass().toString());
			System.out.println("Value: " + tag_map.get(key));
		}
	}
	
	public void test_readAndStoreTags_03() throws Exception {
		TaggedFile tf = new TaggedFile();
		tf.readAndStoreTags("audiofiles/Rock 812.mp3");
		assertEquals("Wrong author", "Eisbach", tf.getAuthor());
		assertEquals("Wrong title", "Rock 812", tf.getTitle());
		assertEquals("Wrong album", "The Sea, the Sky", tf.getAlbum());
		assertEquals("Wrong time format", "05:31", tf.getFormattedDuration());
	}
	
	public void test_computeDuration_02() throws Exception {
		assertEquals("Wrong duration", 2000000L , WavFile.computeDuration(88200L, 44100.0f));
	}
	
	public void test_readAndSetDurationFromFile_01() throws Exception {
		WavFile wf = new WavFile();
		wf.parsePathname("audiofiles/wellenmeister - tranquility.wav");
		wf.readAndSetDurationFromFile(wf.getPathname());
		assertEquals("Wrong time format", "02:21", wf.getFormattedDuration());
	}
}
