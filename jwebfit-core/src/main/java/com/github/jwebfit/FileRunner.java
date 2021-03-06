// Copyright (c) 2002 Cunningham & Cunningham, Inc.
// Released under the terms of the GNU General Public License version 2 or later.

// Modified by Wilkes Joiner and Jim Weaver

package com.github.jwebfit;

import fit.Fixture;
import fit.Parse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Stack;

public class FileRunner extends FitRunner {
	public Parse tables;
	public Fixture fixture;
	private File inFile;
	private File outFile;
	public static Stack stack = new Stack();

	public static String getCurrentDirectoryName() {
		File currentFile = (File) FileRunner.stack.peek();
		return currentFile.getParent();
	}	

	public static void pushFile(File currentFile) {
		FileRunner.stack.push(currentFile);
	}

	public static File popFile() {
		return (File) stack.pop();
	}

	public static FileRunner parseArgs(String argv[]) {
		if (argv.length != 2) {
			System.err.println("usage: java fit.FileRunner input-file output-file");
			System.exit(-1);
		}
		return new FileRunner(new File(argv[0]), new File(argv[1]));
	}

	public static void main(String argv[]) {
		FileRunner runner = parseArgs(argv);
		runner.run();
		runner.resultWriter.write();
		System.err.println(runner.resultWriter.getCounts());
		System.exit(runner.resultWriter.getWrong() + runner.resultWriter.getExceptions());
	}

	public FileRunner(File in, File out) {
		stack.push(in);
		this.inFile = in;
		this.outFile = out;
		fixture = new Fixture();
	}

	public void run() {
		try {
			tables = new Parse(read(inFile));
			fixture.doTables(tables);
		} catch (Exception e) {
			tables = new Parse("body", "Unable to parse input. Input ignored.", null, null);
			fixture.exception(tables, e);
		}
		resultWriter = new FileResultWriter(outFile, fixture.counts, tables);
		stack.pop();
	}

	public static String read(File input) throws IOException {
		return RunnerUtility.useWikiParser() ? readWikiFile(input) : readFile(input);
	}

	public static String readFile(File input) throws FileNotFoundException, IOException {
		FileReader in = null;
		try {
			char chars[] = new char[(int) (input.length())];
			in = new FileReader(input);
			in.read(chars);
			return new String(chars);
		} finally {
			if (in != null)
				in.close();
		}
	}

	public static String readWikiFile(File input) throws IOException {
		FileReader in = null;
		try {
			in = new FileReader(input);
			return new WikiParser().parse(in);
		} finally {
			if (in != null)
				in.close();
		}
	}

}
