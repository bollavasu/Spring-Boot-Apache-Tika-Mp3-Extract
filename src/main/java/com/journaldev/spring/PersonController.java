package com.journaldev.spring;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.mp3.LyricsHandler;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

@RestController
public class PersonController {
	
	@RequestMapping("/")
	public String welcome() throws Exception {
		readExcelFile();
		return "Welcome to Apache Tika Mp3 Extract...";
	}
	
	public void readExcelFile() throws Exception {
	//detecting the file type
      BodyContentHandler handler = new BodyContentHandler();
      Metadata metadata = new Metadata();
	  ClassLoader classLoader = this.getClass().getClassLoader();
      FileInputStream inputstream = new FileInputStream(new File(classLoader.getResource("Sample.mp3").getFile()));
      ParseContext pcontext = new ParseContext();
      
      //Mp3 parser
      Mp3Parser  Mp3Parser = new  Mp3Parser();
      Mp3Parser.parse(inputstream, handler, metadata, pcontext);
      LyricsHandler lyrics = new LyricsHandler(inputstream,handler);
      
      while(lyrics.hasLyrics()) {
    	  System.out.println(lyrics.toString());
      }
      
      System.out.println("Contents of the document:" + handler.toString());
      System.out.println("Metadata of the document:");
      String[] metadataNames = metadata.names();

      for(String name : metadataNames) {		        
    	  System.out.println(name + ": " + metadata.get(name));
      }
	}
	
}
