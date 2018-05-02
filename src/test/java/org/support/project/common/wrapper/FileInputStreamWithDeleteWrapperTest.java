package org.support.project.common.wrapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FileInputStreamWithDeleteWrapperTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testClose() throws IOException {
		String userHome = System.getProperty("user.home");
		String dir = userHome.concat("/.knowledge/tmp/");
		File tmpdir = new File(dir);
		if (!tmpdir.exists()) {
			tmpdir.mkdirs();
		}
		File tmp = File.createTempFile("test-", ".txt", tmpdir);
		System.out.println(tmp.getAbsolutePath());

		PrintWriter p_writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tmp), "UTF-8")));
		p_writer.println("sample");
		p_writer.close();

		FileInputStreamWithDeleteWrapper inputStream = new FileInputStreamWithDeleteWrapper(tmp);
		Assert.assertEquals(7, inputStream.size());
		
		BufferedReader b_reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		String s;
		while ((s = b_reader.readLine()) != null) {
			System.out.println(s);
		}
		b_reader.close();
		
		if (tmp.exists()) {
			Assert.fail("削除されているはず");
		}

	}

}
