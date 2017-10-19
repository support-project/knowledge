package org.support.project.common.wrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.support.project.common.util.FileUtil;

public class FileInputStreamWithDeleteWrapper extends InputStream {
	
	private FileInputStream inputStream;
	private File file;
	
	public FileInputStreamWithDeleteWrapper(File file) throws FileNotFoundException {
		super();
		this.file = file;
		this.inputStream = new FileInputStream(file);
	}
	
	/* (non-Javadoc)
	 * @see java.io.InputStream#close()
	 */
	@Override
	public void close() throws IOException {
		inputStream.close();
		// クローズしたら削除
		FileUtil.delete(file);
	}
	
	public long size() {
		return file.length();
	}
	
	

	@Override
	public int read() throws IOException {
		return inputStream.read();
	}

	/* (non-Javadoc)
	 * @see java.io.InputStream#read(byte[])
	 */
	@Override
	public int read(byte[] b) throws IOException {
		return inputStream.read(b);
	}

	/* (non-Javadoc)
	 * @see java.io.InputStream#read(byte[], int, int)
	 */
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		return inputStream.read(b, off, len);
	}

	/* (non-Javadoc)
	 * @see java.io.InputStream#skip(long)
	 */
	@Override
	public long skip(long n) throws IOException {
		return inputStream.skip(n);
	}

	/* (non-Javadoc)
	 * @see java.io.InputStream#available()
	 */
	@Override
	public int available() throws IOException {
		return inputStream.available();
	}


	/* (non-Javadoc)
	 * @see java.io.InputStream#mark(int)
	 */
	@Override
	public synchronized void mark(int readlimit) {
		inputStream.mark(readlimit);
	}

	/* (non-Javadoc)
	 * @see java.io.InputStream#reset()
	 */
	@Override
	public synchronized void reset() throws IOException {
		inputStream.reset();
	}

	/* (non-Javadoc)
	 * @see java.io.InputStream#markSupported()
	 */
	@Override
	public boolean markSupported() {
		return inputStream.markSupported();
	}
	
	
}
