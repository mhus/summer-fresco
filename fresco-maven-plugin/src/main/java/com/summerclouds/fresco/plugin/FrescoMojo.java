package com.summerclouds.fresco.plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

// Sample https://github.com/yahoo/freemarker-maven-plugin/blob/master/src/main/java/com/oath/maven/plugin/freemarker/FreeMarkerMojo.java
@Mojo(
		name = "generate",
		defaultPhase = LifecyclePhase.GENERATE_SOURCES
	)
public class FrescoMojo extends AbstractMojo {

	@Parameter(defaultValue = "${project}")
    protected MavenProject project;

	@Override
	public void execute() throws MojoExecutionException, MojoFailureException {
		getLog().info("doit");
		for (String sources : project.getCompileSourceRoots()) {
			getLog().info("Source: " + sources);
			parse(new File(sources));
		}
	}

	private void parse(File source) {
		if (!source.exists()) return;
		if (source.isDirectory()) {
			for (File sub : source.listFiles()) {
				if (sub.getName().startsWith(".")) return;
				parse(sub);
			}
			return;
		}
		if (!source.isFile()) return;
		getLog().info("Parse file " + source);
		
		if (source.getName().equals("TestFresco.java")) {
			try {
				FileOutputStream os = new FileOutputStream(source,true);
				os.write(("// " + new Date() + "\n").getBytes() );
				os.flush();
				os.close();
			} catch (IOException e) {}
		}
		
	}

}
