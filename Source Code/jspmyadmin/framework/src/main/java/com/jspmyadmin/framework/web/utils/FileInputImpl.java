/**
 * 
 */
package com.jspmyadmin.framework.web.utils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Yugandhar Gangu
 * @created_at 2016/07/12
 *
 */
class FileInputImpl implements FileInput {

	private static final long serialVersionUID = 3496879720472670900L;

	private final String _fileName;
	private final List<byte[]> _contents = new ArrayList<byte[]>();
	private Long _size = 0L;

	/**
	 * 
	 * @param fileName
	 * @throws NullPointerException
	 */
	FileInputImpl(String fileName) throws NullPointerException {
		if (fileName == null) {
			throw new NullPointerException();
		}
		_fileName = fileName;
	}

	public void write(byte[] bytes) {
		if (bytes != null && bytes.length > 0) {
			_size += bytes.length;
			_contents.add(bytes);
		}
	}

	public String getFileName() {
		return _fileName;
	}

	public long getFileSize() {
		return _size;
	}

	public void copyTo(String path) throws IOException {
		FileOutputStream fileOutputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		File file = null;
		try {
			file = new File(path);
			file.setExecutable(true, false);
			file.setWritable(true, false);
			file.setReadable(true, false);
			fileOutputStream = new FileOutputStream(file);
			bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
			Iterator<byte[]> iterator = _contents.iterator();
			while (iterator.hasNext()) {
				bufferedOutputStream.write(iterator.next());
			}
		} finally {
			if (bufferedOutputStream != null) {
				bufferedOutputStream.flush();
				bufferedOutputStream.close();
			}
			if (fileOutputStream != null) {
				fileOutputStream.flush();
				fileOutputStream.close();
			}
		}
	}

	public InputStream getInputStream() throws IOException {
		byte[] bytes = new byte[_size.intValue()];
		Iterator<byte[]> iterator = _contents.iterator();
		int count = 0;
		while (iterator.hasNext()) {
			byte[] temp = iterator.next();
			System.arraycopy(temp, 0, bytes, count, temp.length);
			count += temp.length;
		}
		ByteArrayInputStream byteArrayInputStream = null;
		if (bytes != null) {
			byteArrayInputStream = new ByteArrayInputStream(bytes);
		}
		return byteArrayInputStream;
	}

}
