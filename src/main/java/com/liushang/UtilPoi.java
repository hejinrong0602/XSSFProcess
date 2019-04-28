package com.liushang;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * @author 刘尚
 * @date 2019/4/28
 */
public class UtilPoi {
    public static SheetDatasHandler read(Path file) throws Exception {
        SheetDatasHandler handler = new SheetDatasHandler((int) (Files.size(file) / 50));
        read(file, handler);
        return handler;
    }
    public static void read(Path file, RowMapper mapper) throws Exception {
        final long size = Files.size(file);
        try (InputStream in = new BufferedInputStream(new FileInputStream(file.toFile()), size > Integer.MAX_VALUE ? 1024 * 1024 * 10 : (int) size)) {
            read(in, mapper);
        }
    }
    public static void read(InputStream in, RowMapper mapper) throws Exception {
        XSSFReader reader = new XSSFReader(OPCPackage.open(in));
        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");

        mapper.setSharedStringsTable(reader.getSharedStringsTable());
        parser.setContentHandler(mapper);

        for (Iterator<InputStream> iter = reader.getSheetsData(); iter.hasNext();) {
            try (InputStream sheetIn = iter.next()) {
                parser.parse(new InputSource(sheetIn));
            }
        }
    }
}
