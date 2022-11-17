/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

/**
 *
 * @author varni
 */
public enum BloodTypeEnum {
    O_Positive("0+"),O_Negative("0-"),A_Positive("A+"),A_Negative("A-"),B_Positive("B+"),
    B_Negative("B-"),AB_Positive("AB+"),AB_Negative("AB-"), UNKNOWN("Unknown");

    private final String shortName;
    BloodTypeEnum(String name){
        this.shortName = name;
    }

    public String getShortName(){
        return this.shortName;
    }

    public static BloodTypeEnum findByValue(String bloodType){
        BloodTypeEnum result = BloodTypeEnum.UNKNOWN;
        for (BloodTypeEnum day : values()) {
            if (day.getShortName().equalsIgnoreCase(bloodType)) {
                result = day;
                break;
            }
        }
        return result;
    }
}
