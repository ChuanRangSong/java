package com.whlscr.freemarker;

import java.io.FileWriter;
import java.util.Collections;

import org.junit.Test;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;

public class Demo {
	@Test
	public void fun(){
		Configuration configuration = new Configuration(new Version("2.3.0"));
    	try {
			Template template = configuration.getTemplate("a.temp");
//			template.dump(new FileWriter("a.java"));
			template.process(Collections.singletonMap("name", "小胖"), new FileWriter("a.java"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
