package com.vtest.it.excelModel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ExcelInitModel {
    public final XSSFWorkbook workbook;
    public final XSSFCellStyle Left_Style;
    public final XSSFCellStyle Right_Style;
    public final XSSFCellStyle Center_Style;
    public final XSSFCellStyle Data_Style;
    public final XSSFCellStyle Bin_1;
    public final XSSFCellStyle Bin_2;
    public final XSSFCellStyle Bin_3;
    public final XSSFCellStyle Bin_4;
    public final XSSFCellStyle Bin_5;
    public final XSSFCellStyle Bin_6;
    public final XSSFCellStyle Bin_7;
    public final XSSFCellStyle Bin_8;
    public final XSSFCellStyle Bin_9;
    public final XSSFCellStyle Bin_10;
    public final XSSFCellStyle Bin_11;
    public final XSSFCellStyle Bin_12;
    public final XSSFCellStyle Bin_13;
    public final XSSFCellStyle Bin_14;
    public final XSSFCellStyle Bin_15;
    public final XSSFCellStyle Bin_16;
    public final XSSFCellStyle Bin_17;
    public final XSSFCellStyle Bin_18;
    public final XSSFCellStyle Bin_19;
    public final XSSFCellStyle Bin_20;
    public final XSSFCellStyle Bin_21;
    public final XSSFCellStyle Bin_22;
    public final XSSFCellStyle Bin_23;
    public final XSSFCellStyle Bin_24;
    public final XSSFCellStyle Bin_25;
    public final XSSFCellStyle Bin_26;
    public final XSSFCellStyle Bin_27;
    public final XSSFCellStyle Bin_28;
    public final XSSFCellStyle Bin_29;
    public final XSSFCellStyle Bin_30;
    public final XSSFCellStyle Bin_31;
    public final XSSFCellStyle Bin_32;
    public final ArrayList<XSSFCellStyle> Colors_Array;

    public ExcelInitModel(File model) throws IOException{
        workbook = new XSSFWorkbook(new FileInputStream(model));
        XSSFFont font = workbook.createFont();
        font.setFontHeight(6);
        workbook.setForceFormulaRecalculation(true);
        Center_Style = workbook.createCellStyle();
        Center_Style.setAlignment(HorizontalAlignment.CENTER);
        Center_Style.setVerticalAlignment(VerticalAlignment.CENTER);

        Left_Style = workbook.createCellStyle();
        Left_Style.setAlignment(HorizontalAlignment.LEFT);
        Left_Style.setVerticalAlignment(VerticalAlignment.CENTER);

        Right_Style = workbook.createCellStyle();
        Right_Style.setAlignment(HorizontalAlignment.RIGHT);
        Right_Style.setVerticalAlignment(VerticalAlignment.CENTER);

        Data_Style = workbook.createCellStyle();
        XSSFDataFormat dataFormat = workbook.createDataFormat();
        Data_Style.setDataFormat(dataFormat.getFormat("0.00%"));
        Data_Style.setAlignment(HorizontalAlignment.CENTER);
        Data_Style.setVerticalAlignment(VerticalAlignment.CENTER);

        Bin_1 = workbook.createCellStyle();
        Bin_1.setAlignment(HorizontalAlignment.CENTER);
        Bin_1.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_1.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        Bin_1.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_2 = workbook.createCellStyle();
        Bin_2.setAlignment(HorizontalAlignment.CENTER);
        Bin_2.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_2.setFillForegroundColor(IndexedColors.RED.getIndex());
        Bin_2.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_3 = workbook.createCellStyle();
        Bin_3.setAlignment(HorizontalAlignment.CENTER);
        Bin_3.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_3.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        Bin_3.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_4 = workbook.createCellStyle();
        Bin_4.setAlignment(HorizontalAlignment.CENTER);
        Bin_4.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_4.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        Bin_4.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_5 = workbook.createCellStyle();
        Bin_5.setAlignment(HorizontalAlignment.CENTER);
        Bin_5.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_5.setFillForegroundColor(IndexedColors.PINK.getIndex());
        Bin_5.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_6 = workbook.createCellStyle();
        Bin_6.setAlignment(HorizontalAlignment.CENTER);
        Bin_6.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_6.setFillForegroundColor(IndexedColors.TURQUOISE.getIndex());
        Bin_6.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_7 = workbook.createCellStyle();
        Bin_7.setAlignment(HorizontalAlignment.CENTER);
        Bin_7.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_7.setFillForegroundColor(IndexedColors.DARK_RED.getIndex());
        Bin_7.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_8 = workbook.createCellStyle();
        Bin_8.setAlignment(HorizontalAlignment.CENTER);
        Bin_8.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_8.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        Bin_8.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_9 = workbook.createCellStyle();
        Bin_9.setAlignment(HorizontalAlignment.CENTER);
        Bin_9.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_9.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        Bin_9.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_10 = workbook.createCellStyle();
        Bin_10.setAlignment(HorizontalAlignment.CENTER);
        Bin_10.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_10.setFillForegroundColor(IndexedColors.DARK_YELLOW.getIndex());
        Bin_10.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_11 = workbook.createCellStyle();
        Bin_11.setAlignment(HorizontalAlignment.CENTER);
        Bin_11.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_11.setFillForegroundColor(IndexedColors.VIOLET.getIndex());
        Bin_11.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_12 = workbook.createCellStyle();
        Bin_12.setAlignment(HorizontalAlignment.CENTER);
        Bin_12.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_12.setFillForegroundColor(IndexedColors.TEAL.getIndex());
        Bin_12.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_13 = workbook.createCellStyle();
        Bin_13.setAlignment(HorizontalAlignment.CENTER);
        Bin_13.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_13.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
        Bin_13.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_14 = workbook.createCellStyle();
        Bin_14.setAlignment(HorizontalAlignment.CENTER);
        Bin_14.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_14.setFillForegroundColor(IndexedColors.MAROON.getIndex());
        Bin_14.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_15 = workbook.createCellStyle();
        Bin_15.setAlignment(HorizontalAlignment.CENTER);
        Bin_15.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_15.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
        Bin_15.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_16 = workbook.createCellStyle();
        Bin_16.setAlignment(HorizontalAlignment.CENTER);
        Bin_16.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_16.setFillForegroundColor(IndexedColors.ORCHID.getIndex());
        Bin_16.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_17 = workbook.createCellStyle();
        Bin_17.setAlignment(HorizontalAlignment.CENTER);
        Bin_17.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_17.setFillForegroundColor(IndexedColors.CORAL.getIndex());
        Bin_17.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_18 = workbook.createCellStyle();
        Bin_18.setAlignment(HorizontalAlignment.CENTER);
        Bin_18.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_18.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
        Bin_18.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_19 = workbook.createCellStyle();
        Bin_19.setAlignment(HorizontalAlignment.CENTER);
        Bin_19.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_19.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        Bin_19.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_20 = workbook.createCellStyle();
        Bin_20.setAlignment(HorizontalAlignment.CENTER);
        Bin_20.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_20.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        Bin_20.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_21 = workbook.createCellStyle();
        Bin_21.setAlignment(HorizontalAlignment.CENTER);
        Bin_21.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_21.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
        Bin_21.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_22 = workbook.createCellStyle();
        Bin_22.setAlignment(HorizontalAlignment.CENTER);
        Bin_22.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_22.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        Bin_22.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_23 = workbook.createCellStyle();
        Bin_23.setAlignment(HorizontalAlignment.CENTER);
        Bin_23.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_23.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        Bin_23.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_24 = workbook.createCellStyle();
        Bin_24.setAlignment(HorizontalAlignment.CENTER);
        Bin_24.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_24.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        Bin_24.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_25 = workbook.createCellStyle();
        Bin_25.setAlignment(HorizontalAlignment.CENTER);
        Bin_25.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_25.setFillForegroundColor(IndexedColors.ROSE.getIndex());
        Bin_25.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_26 = workbook.createCellStyle();
        Bin_26.setAlignment(HorizontalAlignment.CENTER);
        Bin_26.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_26.setFillForegroundColor(IndexedColors.LAVENDER.getIndex());
        Bin_26.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_27 = workbook.createCellStyle();
        Bin_27.setAlignment(HorizontalAlignment.CENTER);
        Bin_27.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_27.setFillForegroundColor(IndexedColors.TAN.getIndex());
        Bin_27.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_28 = workbook.createCellStyle();
        Bin_28.setAlignment(HorizontalAlignment.CENTER);
        Bin_28.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_28.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        Bin_28.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_29 = workbook.createCellStyle();
        Bin_29.setAlignment(HorizontalAlignment.CENTER);
        Bin_29.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_29.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        Bin_29.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_30 = workbook.createCellStyle();
        Bin_30.setAlignment(HorizontalAlignment.CENTER);
        Bin_30.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_30.setFillForegroundColor(IndexedColors.GOLD.getIndex());
        Bin_30.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_31 = workbook.createCellStyle();
        Bin_31.setAlignment(HorizontalAlignment.CENTER);
        Bin_31.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_31.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
        Bin_31.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_32 = workbook.createCellStyle();
        Bin_32.setAlignment(HorizontalAlignment.CENTER);
        Bin_32.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_32.setFillForegroundColor(IndexedColors.BROWN.getIndex());
        Bin_32.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Colors_Array = new ArrayList<>();
        Colors_Array.add(Bin_1);
        Colors_Array.add(Bin_2);
        Colors_Array.add(Bin_3);
        Colors_Array.add(Bin_4);
        Colors_Array.add(Bin_5);
        Colors_Array.add(Bin_6);
        Colors_Array.add(Bin_7);
        Colors_Array.add(Bin_8);
        Colors_Array.add(Bin_9);
        Colors_Array.add(Bin_10);
        Colors_Array.add(Bin_11);
        Colors_Array.add(Bin_12);
        Colors_Array.add(Bin_13);
        Colors_Array.add(Bin_14);
        Colors_Array.add(Bin_15);
        Colors_Array.add(Bin_16);
        Colors_Array.add(Bin_17);
        Colors_Array.add(Bin_18);
        Colors_Array.add(Bin_19);
        Colors_Array.add(Bin_20);
        Colors_Array.add(Bin_21);
        Colors_Array.add(Bin_22);
        Colors_Array.add(Bin_23);
        Colors_Array.add(Bin_24);
        Colors_Array.add(Bin_25);
        Colors_Array.add(Bin_26);
        Colors_Array.add(Bin_27);
        Colors_Array.add(Bin_28);
        Colors_Array.add(Bin_29);
        Colors_Array.add(Bin_30);
        Colors_Array.add(Bin_31);
        Colors_Array.add(Bin_32);
        for (XSSFCellStyle xssfCellStyle : Colors_Array) {
            xssfCellStyle.setFont(font);
        }
    }

    public ExcelInitModel() {
        workbook = new XSSFWorkbook();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(6);
        workbook.setForceFormulaRecalculation(true);
        Center_Style = workbook.createCellStyle();
        Center_Style.setAlignment(HorizontalAlignment.CENTER);
        Center_Style.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFFont centerFont=workbook.createFont();
        centerFont.setFontName("Times New Roman");
        centerFont.setFontHeightInPoints((short)9);
        Center_Style.setFont(centerFont);

        Left_Style = workbook.createCellStyle();
        Left_Style.setAlignment(HorizontalAlignment.LEFT);
        Left_Style.setVerticalAlignment(VerticalAlignment.CENTER);

        Right_Style = workbook.createCellStyle();
        Right_Style.setAlignment(HorizontalAlignment.RIGHT);
        Right_Style.setVerticalAlignment(VerticalAlignment.CENTER);

        Data_Style = workbook.createCellStyle();
        XSSFDataFormat dataFormat = workbook.createDataFormat();
        Data_Style.setDataFormat(dataFormat.getFormat("0.00%"));
        Data_Style.setAlignment(HorizontalAlignment.CENTER);
        Data_Style.setVerticalAlignment(VerticalAlignment.CENTER);
        Data_Style.setFont(centerFont);
        Bin_1 = workbook.createCellStyle();
        Bin_1.setAlignment(HorizontalAlignment.CENTER);
        Bin_1.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_1.setFillForegroundColor(IndexedColors.BRIGHT_GREEN.getIndex());
        Bin_1.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_2 = workbook.createCellStyle();
        Bin_2.setAlignment(HorizontalAlignment.CENTER);
        Bin_2.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_2.setFillForegroundColor(IndexedColors.RED.getIndex());
        Bin_2.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_3 = workbook.createCellStyle();
        Bin_3.setAlignment(HorizontalAlignment.CENTER);
        Bin_3.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_3.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        Bin_3.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_4 = workbook.createCellStyle();
        Bin_4.setAlignment(HorizontalAlignment.CENTER);
        Bin_4.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_4.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
        Bin_4.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_5 = workbook.createCellStyle();
        Bin_5.setAlignment(HorizontalAlignment.CENTER);
        Bin_5.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_5.setFillForegroundColor(IndexedColors.PINK.getIndex());
        Bin_5.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_6 = workbook.createCellStyle();
        Bin_6.setAlignment(HorizontalAlignment.CENTER);
        Bin_6.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_6.setFillForegroundColor(IndexedColors.TURQUOISE.getIndex());
        Bin_6.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_7 = workbook.createCellStyle();
        Bin_7.setAlignment(HorizontalAlignment.CENTER);
        Bin_7.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_7.setFillForegroundColor(IndexedColors.DARK_RED.getIndex());
        Bin_7.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_8 = workbook.createCellStyle();
        Bin_8.setAlignment(HorizontalAlignment.CENTER);
        Bin_8.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_8.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        Bin_8.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_9 = workbook.createCellStyle();
        Bin_9.setAlignment(HorizontalAlignment.CENTER);
        Bin_9.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_9.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        Bin_9.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_10 = workbook.createCellStyle();
        Bin_10.setAlignment(HorizontalAlignment.CENTER);
        Bin_10.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_10.setFillForegroundColor(IndexedColors.DARK_YELLOW.getIndex());
        Bin_10.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_11 = workbook.createCellStyle();
        Bin_11.setAlignment(HorizontalAlignment.CENTER);
        Bin_11.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_11.setFillForegroundColor(IndexedColors.VIOLET.getIndex());
        Bin_11.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_12 = workbook.createCellStyle();
        Bin_12.setAlignment(HorizontalAlignment.CENTER);
        Bin_12.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_12.setFillForegroundColor(IndexedColors.TEAL.getIndex());
        Bin_12.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_13 = workbook.createCellStyle();
        Bin_13.setAlignment(HorizontalAlignment.CENTER);
        Bin_13.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_13.setFillForegroundColor(IndexedColors.CORNFLOWER_BLUE.getIndex());
        Bin_13.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_14 = workbook.createCellStyle();
        Bin_14.setAlignment(HorizontalAlignment.CENTER);
        Bin_14.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_14.setFillForegroundColor(IndexedColors.MAROON.getIndex());
        Bin_14.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_15 = workbook.createCellStyle();
        Bin_15.setAlignment(HorizontalAlignment.CENTER);
        Bin_15.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_15.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
        Bin_15.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_16 = workbook.createCellStyle();
        Bin_16.setAlignment(HorizontalAlignment.CENTER);
        Bin_16.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_16.setFillForegroundColor(IndexedColors.ORCHID.getIndex());
        Bin_16.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_17 = workbook.createCellStyle();
        Bin_17.setAlignment(HorizontalAlignment.CENTER);
        Bin_17.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_17.setFillForegroundColor(IndexedColors.CORAL.getIndex());
        Bin_17.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_18 = workbook.createCellStyle();
        Bin_18.setAlignment(HorizontalAlignment.CENTER);
        Bin_18.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_18.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
        Bin_18.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_19 = workbook.createCellStyle();
        Bin_19.setAlignment(HorizontalAlignment.CENTER);
        Bin_19.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_19.setFillForegroundColor(IndexedColors.LIGHT_CORNFLOWER_BLUE.getIndex());
        Bin_19.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_20 = workbook.createCellStyle();
        Bin_20.setAlignment(HorizontalAlignment.CENTER);
        Bin_20.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_20.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
        Bin_20.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_21 = workbook.createCellStyle();
        Bin_21.setAlignment(HorizontalAlignment.CENTER);
        Bin_21.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_21.setFillForegroundColor(IndexedColors.LIGHT_TURQUOISE.getIndex());
        Bin_21.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_22 = workbook.createCellStyle();
        Bin_22.setAlignment(HorizontalAlignment.CENTER);
        Bin_22.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_22.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
        Bin_22.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_23 = workbook.createCellStyle();
        Bin_23.setAlignment(HorizontalAlignment.CENTER);
        Bin_23.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_23.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
        Bin_23.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_24 = workbook.createCellStyle();
        Bin_24.setAlignment(HorizontalAlignment.CENTER);
        Bin_24.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_24.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
        Bin_24.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_25 = workbook.createCellStyle();
        Bin_25.setAlignment(HorizontalAlignment.CENTER);
        Bin_25.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_25.setFillForegroundColor(IndexedColors.ROSE.getIndex());
        Bin_25.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_26 = workbook.createCellStyle();
        Bin_26.setAlignment(HorizontalAlignment.CENTER);
        Bin_26.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_26.setFillForegroundColor(IndexedColors.LAVENDER.getIndex());
        Bin_26.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_27 = workbook.createCellStyle();
        Bin_27.setAlignment(HorizontalAlignment.CENTER);
        Bin_27.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_27.setFillForegroundColor(IndexedColors.TAN.getIndex());
        Bin_27.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_28 = workbook.createCellStyle();
        Bin_28.setAlignment(HorizontalAlignment.CENTER);
        Bin_28.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_28.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        Bin_28.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_29 = workbook.createCellStyle();
        Bin_29.setAlignment(HorizontalAlignment.CENTER);
        Bin_29.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_29.setFillForegroundColor(IndexedColors.AQUA.getIndex());
        Bin_29.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_30 = workbook.createCellStyle();
        Bin_30.setAlignment(HorizontalAlignment.CENTER);
        Bin_30.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_30.setFillForegroundColor(IndexedColors.GOLD.getIndex());
        Bin_30.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_31 = workbook.createCellStyle();
        Bin_31.setAlignment(HorizontalAlignment.CENTER);
        Bin_31.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_31.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
        Bin_31.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Bin_32 = workbook.createCellStyle();
        Bin_32.setAlignment(HorizontalAlignment.CENTER);
        Bin_32.setVerticalAlignment(VerticalAlignment.CENTER);
        Bin_32.setFillForegroundColor(IndexedColors.BROWN.getIndex());
        Bin_32.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Colors_Array = new ArrayList<>();
        Colors_Array.add(Bin_1);
        Colors_Array.add(Bin_2);
        Colors_Array.add(Bin_3);
        Colors_Array.add(Bin_4);
        Colors_Array.add(Bin_5);
        Colors_Array.add(Bin_6);
        Colors_Array.add(Bin_7);
        Colors_Array.add(Bin_8);
        Colors_Array.add(Bin_9);
        Colors_Array.add(Bin_10);
        Colors_Array.add(Bin_11);
        Colors_Array.add(Bin_12);
        Colors_Array.add(Bin_13);
        Colors_Array.add(Bin_14);
        Colors_Array.add(Bin_15);
        Colors_Array.add(Bin_16);
        Colors_Array.add(Bin_17);
        Colors_Array.add(Bin_18);
        Colors_Array.add(Bin_19);
        Colors_Array.add(Bin_20);
        Colors_Array.add(Bin_21);
        Colors_Array.add(Bin_22);
        Colors_Array.add(Bin_23);
        Colors_Array.add(Bin_24);
        Colors_Array.add(Bin_25);
        Colors_Array.add(Bin_26);
        Colors_Array.add(Bin_27);
        Colors_Array.add(Bin_28);
        Colors_Array.add(Bin_29);
        Colors_Array.add(Bin_30);
        Colors_Array.add(Bin_31);
        Colors_Array.add(Bin_32);
        for (XSSFCellStyle xssfCellStyle : Colors_Array) {
            xssfCellStyle.setFont(font);
        }
    }
}
