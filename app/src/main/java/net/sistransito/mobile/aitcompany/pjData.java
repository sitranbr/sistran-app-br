package net.sistransito.mobile.aitcompany;

import android.content.Context;
import android.database.Cursor;

import net.sistransito.mobile.appconstants.AppConstants;
import net.sistransito.mobile.database.AitPJDatabaseHelper;
import net.sistransito.mobile.timeandime.TimeAndIme;
import net.sistransito.R;

import java.io.Serializable;

public class pjData implements Serializable {

    public static final String IDAuto = "auto_pj";
    public static final long serialVersionUID = 17393745L;
    private String aitNumber;
    private String companySocial;
    private String cpf, cnpj;
    private String address;
    private String city;
    private String state;
    private String place;
    private String aitDate;
    private String aitTime;
    private String infration;
    private String framingCode;
    private String unfolding;
    private String article;
    private String observation;
    private String cancelStatus;
    private String cancelReason;
    private String syncStatus;
    private String latitude;
    private String longitude;
    private String aitDateTime;
    private String completedStatus;

    private boolean isStorePJFullData;
    private boolean isDataisPJNull=false;

    TimeAndIme time;

    public pjData(Context context) {
        time = new TimeAndIme(context);
        setAitDateTime(time.getDate() + time.getTime());
    }

    public boolean isStorePJFullData() {
        return isStorePJFullData;
    }

    public void setStorePJFullData(boolean isStorePJFullData) {
        this.isStorePJFullData = isStorePJFullData;
    }

    public boolean isDataisPJNull() {
        return isDataisPJNull;
    }

    public void setDataisPJNull(boolean isDataisPJNull) {
        this.isDataisPJNull = isDataisPJNull;
    }

    public pjData() {
        super();
        initializedAllData();
    }

    public String getAitNumber() {
        return aitNumber;
    }

    public void setAitNumber(String aitNumber) {
        this.aitNumber = aitNumber;
    }

    public String getCompanySocial() {
        return companySocial;
    }

    public void setCompanySocial(String companySocial) {
        this.companySocial = companySocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getAitDate() {
        return aitDate;
    }

    public void setAitDate(String aitDate) {
        this.aitDate = aitDate;
    }

    public String getAitTime() {
        return aitTime;
    }

    public void setAitTime(String aitTime) {
        this.aitTime = aitTime;
    }

    public String getInfration() {
        return infration;
    }

    public void setInfration(String infration) {
        this.infration = infration;
    }

    public String getFramingCode() {
        return framingCode;
    }

    public void setFramingCode(String framingCode) {
        this.framingCode = framingCode;
    }

    public String getUnfolding() {
        return unfolding;
    }

    public void setUnfolding(String unfolding) {
        this.unfolding = unfolding;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(String cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(String syncStatus) {
        this.syncStatus = syncStatus;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAitDateTime() {
        return aitDateTime;
    }

    public void setAitDateTime(String aitDateTime) {
        this.aitDateTime = aitDateTime;
    }

    public String getCompletedStatus() {
        return completedStatus;
    }

    public void setCompletedStatus(String completedStatus) {
        this.completedStatus = completedStatus;
    }

    public static String getIDAuto() {
        return IDAuto;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public void initializedAllData(){
        aitNumber = companySocial = cpf = cnpj = address = city = state = place = aitDate = aitTime
                = infration = framingCode = unfolding = article = observation
                = cancelStatus = cancelReason = syncStatus = latitude
                = longitude = aitDateTime = completedStatus = "";
    }

    public void setAutoDataFromCursor(Cursor myCursor) {

        this.setAitNumber(myCursor.getString(myCursor
                .getColumnIndex(AitPJDatabaseHelper.AIT_NUMBER)));
        this.setCompanySocial(myCursor.getString(myCursor
                .getColumnIndex(AitPJDatabaseHelper.COMPANY_SOCIAL)));
        this.setAddress(myCursor.getString(myCursor.getColumnIndex(AitPJDatabaseHelper.ADDRESS)));
        this.setPlace(myCursor.getString(myCursor
                .getColumnIndex(AitPJDatabaseHelper.PLACE)));
        this.setCity(myCursor.getString(myCursor
                .getColumnIndex(AitPJDatabaseHelper.CITY)));
        this.setState(myCursor.getString(myCursor
                .getColumnIndex(AitPJDatabaseHelper.STATE)));
        this.setAitDate(myCursor.getString(myCursor
                .getColumnIndex(AitPJDatabaseHelper.DATE)));
        this.setAitTime(myCursor.getString(myCursor
                .getColumnIndex(AitPJDatabaseHelper.TIME)));
        this.setInfration(myCursor.getString(myCursor
                .getColumnIndex(AitPJDatabaseHelper.INFRATION)));
        this.setFramingCode(myCursor.getString(myCursor
                .getColumnIndex(AitPJDatabaseHelper.FLAMING_CODE)));
        this.setUnfolding(myCursor.getString(myCursor
                .getColumnIndex(AitPJDatabaseHelper.UNFOLDING)));
        this.setArticle(myCursor.getString(myCursor
                .getColumnIndex(AitPJDatabaseHelper.ARTICLE)));
        this.setObservation(myCursor.getString(myCursor
                .getColumnIndex(AitPJDatabaseHelper.OBSERVATION)));
        this.setCancelStatus(myCursor.getString(myCursor
                .getColumnIndex(AitPJDatabaseHelper.CANCELL_STATUS)));
        this.setCancelReason(myCursor.getString(myCursor
                .getColumnIndex(AitPJDatabaseHelper.CANCELL_REASON)));
        this.setSyncStatus(myCursor.getString(myCursor
                .getColumnIndex(AitPJDatabaseHelper.SYNC_STATUS)));
        this.setAitDateTime(myCursor.getString(myCursor
                .getColumnIndex(AitPJDatabaseHelper.AIT_DATE_TIME)));
        this.setCompletedStatus(myCursor.getString(myCursor
                .getColumnIndex(AitPJDatabaseHelper.COMPLETED_STATUS)));

        this.setStorePJFullData(true);

    }

    public String getAutoListViewData(Context context) {
        String sPjAitCheck =
                context.getResources().getString(R.string.pj_nome)
                        + getNewLine()
                        + companySocial
                        + getTwoLines()
                        + context.getResources().getString(R.string.pj_cnpj)
                        + getNewLine()
                        + cpf
                        + getTwoLines()
                        + context.getResources().getString(R.string.tca_endereco)
                        + getNewLine()
                        + address
                        + getTwoLines()
                        + context.getResources().getString(R.string.auto_municipio)
                        + getNewLine()
                        + city
                        + getTwoLines()
                        + context.getResources().getString(
                        R.string.auto_uf)
                        + getNewLine()
                        + state
                        + getTwoLines()
                        + context.getResources().getString(R.string.local)
                        + getNewLine()
                        + place
                        + getTwoLines()
                        + context.getResources().getString(R.string.data)
                        + getNewLine()
                        + aitDate
                        + getTwoLines()
                        + context.getResources().getString(R.string.hora)
                        + getNewLine()
                        + aitTime
                        + getTwoLines()
                        + context.getResources().getString(R.string.descricao_da_infracao)
                        + getNewLine()
                        + infration
                        + getTwoLines()
                        + context.getResources().getString(R.string.desdob)
                        + getNewLine()
                        + unfolding
                        + getTwoLines()
                        + context.getResources().getString(
                        R.string.enquadra)
                        + getNewLine()
                        + framingCode
                        + getTwoLines()
                        + context.getResources().getString(R.string.amparo_legal)
                        + getNewLine()
                        + article
                        + getTwoLines()
                        + context.getResources().getString(R.string.observacao)
                        + getNewLine()
                        + observation
                        + getTwoLines()
                        + cancelReason +
                        getNewLine();
        return sPjAitCheck;
    }

    public String getAitDataView(Context context) {

        String aitData = context.getResources().getString(R.string.numero_auto) + getNewLine() + aitNumber + getTwoLines()
                + context.getResources().getString(R.string.pj_nome) + getNewLine() + companySocial + getTwoLines()
                //+ context.getResources().getString(R.string.auto_de_UF) + getNewLine() + uf_placa + getTwoLines()
                //+ context.getResources().getString(R.string.model) + getNewLine() + modelo_veiculo + getTwoLines()
                + getAutoListViewData(context);
        return aitData;
    }

    public String getAitDataPreview(Context context) {

        String aitData = context.getResources().getString(R.string.numero_auto) + getNewLine() + aitNumber + getTwoLines()
                + context.getResources().getString(R.string.pj_nome) + getNewLine() + companySocial + getTwoLines()
                //+ context.getResources().getString(R.string.auto_de_UF) + getNewLine() + uf_placa + getTwoLines()
                //+ context.getResources().getString(R.string.model) + getNewLine() + modelo_veiculo + getTwoLines()
                + getAutoListViewData(context);
        return aitData;
    }

    public String getNewLine() {
        return AppConstants.NEW_LINE;
    }

    public String getTwoLines() {
        return AppConstants.NEW_LINE + AppConstants.NEW_LINE;
    }

}
