package com.example.petmanagment.constants;

public class ValidationMessage {
    //animalSpeciesFieldのvalidationのエラーメッセージ
    public static final String ANIMAL_SPECIES_ERROR_MESSAGE = "「dog」または「cat」で入力してください。";

    //nameFieldのvalidationのエラーメッセージ
    public static final String NAME_NOT_BLANK_MESSAGE = "名前を入力してください。";
    public static final String NAME_ERROR_MESSAGE = "20文字以内で記入してください。";

    //birthdayFieldのvalidationのエラーメッセージ
    public static final String BIRTHDAY_NOT_BLANK_MESSAGE = "誕生日を入力してください。";
    public static final String BIRTHDAY_ERROR_MESSAGE = "有効な日付の範囲外です、誕生日を入力してください。";

    //weightFieldのvalidationのエラーメッセージ
    public static final String WEIGHT_NOT_BLANK_MESSAGE = "体重を入力してください。";
    public static final String WEIGHT_POSITIVE_MESSAGE = "正の数値で入力してください。";
    public static final String WEIGHT_ERROR_MESSAGE = "有効な数値の範囲外です。整数3桁、小数点以下2桁以内の範囲で入力してください。";

}
