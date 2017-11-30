package spenpal;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class MyFilter extends FileFilter{

	@Override
	  public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
        if (f.getName().endsWith(".csv")) {
            return true;
        }
        return false;
    }

	@Override
	 public String getDescription() {
        return "*.csv";
    }
	
}
