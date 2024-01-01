package com.suehay.audscifx.model.enums;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import lombok.Getter;

@Getter
public enum ReportFonts {
    CAT_FONT(new Font(
            com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 18,
            com.itextpdf.text.Font.BOLD)),
    RED_FONT(new Font(
            com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12,
            com.itextpdf.text.Font.NORMAL, BaseColor.RED)),
    SUB_FONT(new Font(
            com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 16,
            com.itextpdf.text.Font.BOLD)),
    SMALL_BOLD(new Font(
            com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12,
            com.itextpdf.text.Font.BOLD));
    private final Font font;
    ReportFonts(Font font) {
        this.font = font;
    }
}