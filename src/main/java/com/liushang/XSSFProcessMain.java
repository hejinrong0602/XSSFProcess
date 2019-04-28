package com.liushang;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

/**
 * @author 刘尚
 * @date 2019/4/28
 */
public class XSSFProcessMain {
    public static void main1(String[] args) throws Exception {
        System.out.println();
        long end, start = System.currentTimeMillis();
//              Path file = Paths.get("/home/skzrorg/tmp/2007.xlsx");
        Path file = Paths.get("src/100W.xlsx");
        SheetDatasHandler handler = UtilPoi.read(file);
        end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) / 1000f + "s");
        List<List<Object>> sheet = handler.getSheetData(0);
        System.out.println("sheet 大小：" + sheet.size());
//        System.out.println("sheet[0]" + sheet.get(0));
        int i = new Random().nextInt(sheet.size());
        System.out.println("sheet[" + i + "]" + sheet.get(i));
        System.out.println("sheet[" + (sheet.size() - 1) + "]" + sheet.get(sheet.size() - 1));
    }

    //100万数据5秒读完
    private static int rowCount;
    public static void main(String[] args) throws Exception {
        System.out.println();
        long end, start = System.currentTimeMillis();
        Path file = Paths.get("src/100W.xlsx");
        UtilPoi.read(file, new RowMapper() {
            @Override
            void mapRow(int sheetIndex, int rowIndex, List<Object> row) {
                rowCount++;
//                for (int i = 0; i < row.size(); i++) {
//                    System.out.print(row.get(i)+" ");
//                }
//                System.out.println();
            }
        });
        end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start) / 1000f + "s");
        System.out.println("sheet 大小：" + rowCount);
    }
}
