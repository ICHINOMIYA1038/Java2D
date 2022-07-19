package test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class testMain {

	public testMain() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File file = new File("./src/test","testdir");
		file.mkdirs();
		
		 Path p = Paths.get("./src/test/testdir/test.txt");
		 try{
		      Files.createFile(p);
		    }catch(IOException e){
		      System.out.println(e);
		    }
	}

}
