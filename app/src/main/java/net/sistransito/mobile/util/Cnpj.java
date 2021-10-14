package net.sistransito.mobile.util;

public class Cnpj {

    private static final int[] CPF_WEIGHT = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] CNPJ_WEIGHT = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    private static int calculateDigit(String str, int[] weight) {
        int addUp = 0;
        for (int index=str.length()-1, digit; index >= 0; index-- ) {
            digit = Integer.parseInt(str.substring(index,index+1));
            addUp += digit*weight[weight.length-str.length()+index];
        }
        addUp = 11 - addUp % 11;
        return addUp > 9 ? 0 : addUp;
    }

    public static boolean isValidCpf(String cpf) {
        if ((cpf==null) || (cpf.length()!=11)) return false;

        Integer digitOne = calculateDigit(cpf.substring(0,9), CPF_WEIGHT);
        Integer digitTwo = calculateDigit(cpf.substring(0,9) + digitOne, CPF_WEIGHT);
        return cpf.equals(cpf.substring(0,9) + digitOne.toString() + digitTwo.toString());
    }

    public static boolean isValidCnpj(String cnpj) {
        if ((cnpj==null)||(cnpj.length()!=14)) return false;

        Integer digitOne = calculateDigit(cnpj.substring(0,12), CNPJ_WEIGHT);
        Integer digitTwo = calculateDigit(cnpj.substring(0,12) + digitOne, CNPJ_WEIGHT);
        return cnpj.equals(cnpj.substring(0,12) + digitOne.toString() + digitTwo.toString());
    }

}