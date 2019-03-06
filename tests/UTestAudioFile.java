import junit.framework.TestCase;

public class UTestAudioFile extends TestCase {
	/*
	public void test_parsePathname_01() throws Exception {
		AudioFile af = new AudioFile();
		af.parsePathname("");
		assertEquals("Pathname stored incorrectly", "", af.getPathname());
		assertEquals("Returned filename is incorrect", "", af.getFilename());
		
	}
	
	public void test_parsePathname_02() throws Exception {
		AudioFile af = new AudioFile();
		af.parsePathname("    ");
		assertEquals("Pathname stored incorrectly", "    ", af.getPathname());
		assertEquals("Returned filename is incorrect", "    ", af.getFilename());
		
	}
	
	public void test_parsePathname_03() throws Exception {
		AudioFile af = new AudioFile();
		af.parsePathname("file.mp3");
		assertEquals("Pathname stored incorrectly", "file.mp3", af.getPathname());
		assertEquals("Returned filename is incorrect", "file.mp3", af.getFilename());
		
	}
	
	public void test_parsePathname_04() throws Exception {
		AudioFile af = new AudioFile();
		af.parsePathname("/my-tmp/file.mp3");
		char sepchar = java.io.File.separatorChar;
		assertEquals("Pathname stored incorrectly", sepchar + "my-tmp" + sepchar + "file.mp3", af.getPathname());
		assertEquals("Returned filename is incorrect", "file.mp3", af.getFilename());
		
	}
	
	public void test_parsePathname_05() throws Exception {
		AudioFile af = new AudioFile();
		af.parsePathname("//my-tmp////part1//file.mp3/");
		char sepchar = java.io.File.separatorChar;
		assertEquals("Pathname stored incorrectly", sepchar + "my-tmp" + sepchar + "part1" + sepchar + "file.mp3" + sepchar, af.getPathname());
		assertEquals("Returned filename is incorrect", "", af.getFilename());
		
	}
	
	public void test_parsePathname_06() throws Exception {
		AudioFile af = new AudioFile();
		af.parsePathname("d:\\\\part1///file.mp3");
		char sepchar = java.io.File.separatorChar;
		assertEquals("Pathname stored incorrectly", "d:" + sepchar + "part1" + sepchar + "file.mp3", af.getPathname());
		assertEquals("Returned filename is incorrect", "file.mp3", af.getFilename());
		
	}
	
	public void test_parseFilename_11() throws Exception {
		AudioFile af = new AudioFile();
		af.parsePathname(" Falco  -  Rock me    Amadeus .mp3  ");
		af.parseFilename(af.getFilename());
		assertEquals("Filename stored incorretly", " Falco  -  Rock me    Amadeus .mp3  ", af.getFilename());
		assertEquals("Author stored incorrectly", "Falco", af.getAuthor());
		assertEquals("Title stored incorrectly", "Rock me    Amadeus", af.getTitle());
	}
	
	public void test_parseFilename_12() throws Exception {
		AudioFile af = new AudioFile();
		af.parsePathname("Frankie Goes To Hollywood - The Power Of Love.ogg");
		af.parseFilename(af.getFilename());
		assertEquals("Filename stored incorretly", "Frankie Goes To Hollywood - The Power Of Love.ogg", af.getFilename());
		assertEquals("Author stored incorrectly", "Frankie Goes To Hollywood", af.getAuthor());
		assertEquals("Title stored incorrectly", "The Power Of Love", af.getTitle());
	}
	
	public void test_parseFilename_13() throws Exception {
		AudioFile af = new AudioFile();
		af.parsePathname("audiofile.aux");
		af.parseFilename(af.getFilename());
		assertEquals("Filename stored incorretly", "audiofile.aux", af.getFilename());
		assertEquals("Author stored incorrectly", "", af.getAuthor());
		assertEquals("Title stored incorrectly", "audiofile", af.getTitle());
	}
	
	public void test_parseFilename_14() throws Exception {
		AudioFile af = new AudioFile();
		af.parsePathname("   A.U.T.O.R   -  T.I.T.E.L  .EXTENSION");
		af.parseFilename(af.getFilename());
		assertEquals("Filename stored incorretly", "   A.U.T.O.R   -  T.I.T.E.L  .EXTENSION", af.getFilename());
		assertEquals("Author stored incorrectly", "A.U.T.O.R", af.getAuthor());
		assertEquals("Title stored incorrectly", "T.I.T.E.L", af.getTitle());
	}
	
	public void test_parseFilename_15() throws Exception {
		AudioFile af = new AudioFile();
		af.parsePathname("Hans-Georg Sonstwas - Blue-eyed boy-friend.mp3");
		af.parseFilename(af.getFilename());
		assertEquals("Filename stored incorretly", "Hans-Georg Sonstwas - Blue-eyed boy-friend.mp3", af.getFilename());
		assertEquals("Author stored incorrectly", "Hans-Georg Sonstwas", af.getAuthor());
		assertEquals("Title stored incorrectly", "Blue-eyed boy-friend", af.getTitle());
	}
	
	public void test_parseFilename_16() throws Exception {
		AudioFile af = new AudioFile();
		af.parsePathname(".mp3");
		af.parseFilename(af.getFilename());
		assertEquals("Filename stored incorretly", ".mp3", af.getFilename());
		assertEquals("Author stored incorrectly", "", af.getAuthor());
		assertEquals("Title stored incorrectly", "", af.getTitle());
	}
	
	public void test_parseFilename_17() throws Exception {
		AudioFile af = new AudioFile();
		af.parsePathname("Falco - Rock me Amadeus.");
		af.parseFilename(af.getFilename());
		assertEquals("Filename stored incorretly", "Falco - Rock me Amadeus.", af.getFilename());
		assertEquals("Author stored incorrectly", "Falco", af.getAuthor());
		assertEquals("Title stored incorrectly", "Rock me Amadeus", af.getTitle());
	}
	
	public void test_parseFilename_18() throws Exception {
		AudioFile af = new AudioFile();
		af.parsePathname("-");
		af.parseFilename(af.getFilename());
		assertEquals("Filename stored incorretly", "-", af.getFilename());
		assertEquals("Author stored incorrectly", "", af.getAuthor());
		assertEquals("Title stored incorrectly", "-", af.getTitle());
	}
	
	public void test_parseFilename_19() throws Exception {
		AudioFile af = new AudioFile();
		af.parsePathname(" - ");
		af.parseFilename(af.getFilename());
		assertEquals("Filename stored incorretly", " - ", af.getFilename());
		assertEquals("Author stored incorrectly", "", af.getAuthor());
		assertEquals("Title stored incorrectly", "", af.getTitle());
	}
	
	public void test_AudioFile_21() throws Exception {
		AudioFile af = new AudioFile(" Falco  -  Rock me   Amadeus .mp3");
		assertEquals("Fehler", "Falco" + " - " + "Rock me   Amadeus", af.toString());
	}
	
	public void test_AudioFile_22() throws Exception {
		AudioFile af = new AudioFile("Frankie Goes To Hollywood - The Power Of Love.ogg");
		assertEquals("Fehler", "Frankie Goes To Hollywood" + " - " + "The Power Of Love", af.toString());
	}
	
	public void test_AudioFile_23() throws Exception {
		AudioFile af = new AudioFile("audiofile.mp3");
		assertEquals("Fehler", "audiofile", af.toString());
	}
	
	public void test_AudioFile_24() throws Exception {
		AudioFile af = new AudioFile("   A.U.T.O.R   -  T.I.T.E.L  .EXTENSION");
		assertEquals("Fehler", "A.U.T.O.R" + " - " + "T.I.T.E.L", af.toString());
	}
	
	public void test_AudioFile_25() throws Exception {
		AudioFile af = new AudioFile("Hans-Georg Sonstwas - Blue-eyed boy-friend.mp3");
		assertEquals("Fehler", "Hans-Georg Sonstwas" + " - " + "Blue-eyed boy-friend", af.toString());
	}
	
	public void test_AudioFile_26() throws Exception {
		AudioFile af = new AudioFile(".mp3");
		assertEquals("Fehler", "", af.toString());
	}
	
	public void test_AudioFile_27() throws Exception {
		AudioFile af = new AudioFile("Falco - Rock me Amadeus.");
		assertEquals("Fehler", "Falco" + " - " + "Rock me Amadeus", af.toString());
	}
	
	public void test_AudioFile_28() throws Exception {
		AudioFile af = new AudioFile("-");
		assertEquals("Fehler", "-", af.toString());
	}
	
	public void test_AudioFile_29() throws Exception {
		AudioFile af = new AudioFile(" - ");
		assertEquals("Fehler", "", af.toString());
	}*/
}
