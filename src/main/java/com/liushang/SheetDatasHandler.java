package com.liushang;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
public class SheetDatasHandler extends RowMapper {
    private int bufRowSize, curSheetIndex = -1;
    private List<List<List<Object>>> sheetDatas = new ArrayList<List<List<Object>>>();
    private List<List<Object>> sheetData;

    public List<List<List<Object>>> getSheetDatas() {
        return sheetDatas;
    }
    public List<List<Object>> getSheetData(int sheetIndex) {
        return sheetDatas.get(sheetIndex);
    }

    SheetDatasHandler(int bufRowSize) {
        this.bufRowSize = bufRowSize;
    }

    @Override
    void mapRow(int sheetIndex, int rowIndex, List<Object> row) {
        if (curSheetIndex != sheetIndex) {
            sheetData = new ArrayList<>(sheetIndex == 0 ? bufRowSize : sheetData.size() / 2);
            sheetDatas.add(sheetData);
            curSheetIndex = sheetIndex;
        }

        sheetData.add(row);
    }
}
