package com.example.petmanagment.controller.exception;

public class ValidationMessage {
    //animalSpeciesFieldのvalidationのエラーメッセージ
    public static final String animalSpeciesErrorMessage = "「dog」または「cat」で入力してください。";

    //nameFieldのvalidationのエラーメッセージ
    public static final String nameNonBlankMessage = "名前を入力してください。";
    public static final String nameErrorMessage = "20文字以内で記入してください。";

    //birthdayFieldのvalidationのエラーメッセージ
    public static final String birthdayNonBlankMessage = "誕生日を入力してください。";
    public static final String birthdayErrorMessage = "有効な日付の範囲外です、誕生日を入力してください。";

    //weightFieldのvalidationのエラーメッセージ
    public static final String weightNonBlank = "体重を入力してください。";
    public static final String weightPositiveMessage = "正の数値で入力してください。";
    public static final String weightErrorMessage = "有効な数値の範囲外です。整数3桁、小数点以下2桁以内の範囲で入力してください。";

}
