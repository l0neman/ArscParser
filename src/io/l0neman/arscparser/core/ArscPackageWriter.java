package io.l0neman.arscparser.core;


import io.l0neman.arscparser.type.ResChunkHeader;
import io.l0neman.arscparser.type.ResTablePackage;
import io.l0neman.arscparser.type.ResourceTypes;
import io.l0neman.arscparser.util.objectio.ObjectInput;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@SuppressWarnings("ALL")
public class ArscPackageWriter {
    private long mIndex;
    private String packageName;

    private byte[] modifyTablePackageType(RandomAccessFile fis, ObjectInput objectInput) throws IOException {
        final long tablePackageIndex = mIndex;
        final ResTablePackage tablePackage = objectInput.read(ResTablePackage.class, tablePackageIndex);

        fis.seek(mIndex);
        byte[] bytes = new byte[tablePackage.header.headerSize];
        fis.read(bytes, 0, 12);
        byte[] packageNameBytes = packageName.getBytes(StandardCharsets.UTF_16LE);
        System.arraycopy(packageNameBytes, 0, bytes, 12, packageNameBytes.length);
        fis.seek(mIndex + 12 + 256);
        fis.read(bytes, 12 + 256, 20);

        System.out.println("table package type:");
        System.out.println(tablePackage);

        // 向下移动资源表元信息头部的大小。
        return bytes;
    }

    private int getResId(int packageResId, int typeResId, int entryId) {
        return (packageResId << 24) | (typeResId << 16) | entryId;
    }

    private void parseAndWriteToOutput(ObjectInput objectInput, String output) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(objectInput.getFileName(), "r");
        FileOutputStream fos = new FileOutputStream(output);

        while (!objectInput.isEof(mIndex)) {
            ResChunkHeader header = objectInput.read(ResChunkHeader.class, mIndex);

            switch (header.type) {
                case ResourceTypes.RES_TABLE_TYPE:
                    long end = mIndex + header.headerSize;
                    writeToOutput(raf, mIndex, end, fos);
                    mIndex += header.headerSize;
                    break;
                case ResourceTypes.RES_TABLE_PACKAGE_TYPE:
                    byte[] bytes = modifyTablePackageType(raf, objectInput);
                    writeToOutput(bytes, fos);

                    mIndex += header.headerSize;
                    break;
                default:
                    end = mIndex + header.size;
                    if (end > objectInput.size())
                        end = objectInput.size();

                    writeToOutput(raf, mIndex, end, fos);

                    mIndex += header.size;
            }
        }

        raf.close();
        fos.close();
    }

    private void writeToOutput(RandomAccessFile raf, long start, long end, FileOutputStream fos)
            throws IOException {
        raf.seek(start);
        byte[] bytes = new byte[(int) (end - start)];
        raf.read(bytes);
        fos.write(bytes);
    }

    private void writeToOutput(byte[] bytes, FileOutputStream fos)
            throws IOException {
        fos.write(bytes);
    }

    private static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignore) {
            } catch (RuntimeException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void write(String file, String output) throws IOException {
        mIndex = 0;
        ObjectInput objectInput = null;

        try {
            objectInput = new ObjectInput(file, false);
            parseAndWriteToOutput(objectInput, output);
        } finally {
            closeQuietly(objectInput);
        }
    }
}
