package net.sistransito.mobile.aitcompany;

public class ObjectAutoPJ {

    private static pjData dadosAuto;

    private ObjectAutoPJ() {
        dadosAuto = new pjData();
    }

    public static pjData getDadosAuto() {
        return dadosAuto;
    }

    public static void setDadosAuto(pjData data) {
        dadosAuto = data;
    }

}
