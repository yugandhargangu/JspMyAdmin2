package com.tracknix.jspmyadmin.framework.web.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Yugandhar Gangu
 */
class FileInputImpl implements FileInput {

    private static final long serialVersionUID = 3496879720472670900L;

    private final String _fileName;
    private final List<byte[]> _contents = new ArrayList<byte[]>();
    private Long _size = 0L;

    /**
     * @param fileName string
     * @throws NullPointerException e
     */
    FileInputImpl(String fileName) throws NullPointerException {
        if (fileName == null) {
            throw new NullPointerException();
        }
        _fileName = fileName;
    }

    void write(byte[] bytes) {
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
        File file;
        try {
            file = new File(path);
            file.setExecutable(true, false);
            file.setWritable(true, false);
            file.setReadable(true, false);
            fileOutputStream = new FileOutputStream(file);
            bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
            for (byte[] _content : _contents) {
                bufferedOutputStream.write(_content);
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
        return new ByteArrayInputStream(bytes);
    }

}
