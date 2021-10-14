package net.sistransito.mobile.plate.data;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import net.sistransito.mobile.appconstants.AppConstants;
import net.sistransito.mobile.appobject.AppObject;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.R;

public class PlateViewFormat {

    private SpannableString resultViewData;

    private String plate, statePlate, city, brand, model, chassi, renavam, color, type, species, category, licenseYear, licenseDate,
            licenseStatus, ipva, insurance, infractions, infrationAmout, restrictions, dateStatus, engineNumber, sealNumber,
            impediment, theft, yearModel;
    private boolean isVibrateOrRinging;
    private Context context;

    public PlateViewFormat(DataFromPlate dataPlate, Context context) {
        this.context = context;

        this.plate = context.getResources().getString(R.string.placa_format)
                + dataPlate.getPlate() + "  ";

        this.city = AppConstants.NEW_LINE
                + context.getResources().getString(R.string.municipio_format)
                + dataPlate.getCity();

        this.statePlate = "/" + dataPlate.getState();

        this.renavam = AppConstants.NEW_LINE
                + context.getResources().getString(R.string.renavam_format)
                + dataPlate.getRanavam();

        this.chassi = AppConstants.NEW_LINE
                + context.getResources().getString(R.string.chassi_format)
                + dataPlate.getChassi();

        this.brand = AppConstants.NEW_LINE
                + context.getResources().getString(R.string.marca_format)
                + dataPlate.getBrand();

        this.model = AppConstants.NEW_LINE
                + context.getResources().getString(R.string.modelo_format)
                + dataPlate.getModel() + AppConstants.NEW_LINE;

        this.color = context.getResources().getString(R.string.cor_format)
                + dataPlate.getColor() + AppConstants.NEW_LINE;

        this.type = context.getResources().getString(R.string.tipo_format)
                + dataPlate.getType() + AppConstants.NEW_LINE;

        this.species = context.getResources().getString(R.string.especie_format)
                + dataPlate.getSpecies() + AppConstants.NEW_LINE;

        this.category = context.getResources().getString(R.string.categoria_format)
                + dataPlate.getCategory() + AppConstants.NEW_LINE;

        this.yearModel = context.getResources().getString(R.string.ano_modelo_format)
                + dataPlate.getYearManufacture() + "/" + dataPlate.getYearModel() + AppConstants.NEW_LINE;

        this.licenseYear = context.getResources().getString(R.string.ano_licenciamento_format)
                + dataPlate.getLicenceYear() + AppConstants.NEW_LINE;

        this.licenseDate = context.getResources().getString(
                R.string.data_licenciamento_format)
                + dataPlate.getLicenceData() + AppConstants.NEW_LINE;

        this.licenseStatus = dataPlate.getLicenceStatus();

        this.sealNumber = context.getResources().getString(R.string.lacre_format)
                + dataPlate.getSealNumber() + AppConstants.NEW_LINE;

        this.engineNumber = context.getResources().getString(R.string.motor_format)
                + dataPlate.getEngineNumber() + AppConstants.TWO_LINES;

        this.impediment = context.getResources().getString(R.string.impedimento_format)
                + dataPlate.getOffSideRecord() + AppConstants.NEW_LINE;

        this.theft = context.getResources().getString(
                R.string.furto_format)
                + dataPlate.getTheftRecord() + AppConstants.NEW_LINE;

        this.infractions = context.getResources().getString(
                R.string.multas_format)
                + dataPlate.getInfractions() + AppConstants.NEW_LINE;

        this.infrationAmout = context.getResources().getString(
                R.string.valor_multas_format)
                + dataPlate.getInfrationAmout() + AppConstants.NEW_LINE;

        this.restrictions = context.getResources().getString(
                R.string.restricoes_format)
                + dataPlate.getRestrictions() + AppConstants.NEW_LINE;

        this.dateStatus = context.getResources().getString(R.string.date_format)
                + dataPlate.getDate() + AppConstants.NEW_LINE;

        setResultViewData(dataPlate);
    }

    public boolean isVibrateOrRinging() {
        return isVibrateOrRinging;
    }

    private void setVibrateOrRinging(boolean isVibrateOrRinging) {
        this.isVibrateOrRinging = isVibrateOrRinging;
    }

    public SpannableString getResultViewData() {
        return resultViewData;
    }

    private void setResultViewData(DataFromPlate dataPlate) {

        String result = plate + licenseStatus + city + statePlate + renavam + chassi + brand
                + model + color + type + species + category + yearModel + licenseYear + licenseDate
                + impediment + infractions + infrationAmout + restrictions + theft + sealNumber + engineNumber + dateStatus;

        resultViewData = new SpannableString(result);

        if (dataPlate.getLicenceStatus() != null) {
            int position = result.indexOf(dataPlate.getLicenceStatus());

            if ((dataPlate.getLicenceStatus()).equals("NORMAL")
                    || (dataPlate.getLicenceStatus()).equals("normal")) {
                resultViewData.setSpan(new ForegroundColorSpan(Color.BLUE),
                        position, (position + (dataPlate
                                .getLicenceStatus()).length()), 0);
                setVibrateOrRinging(false);
            } else {
                setVibrateOrRinging(true);
                resultViewData.setSpan(new ForegroundColorSpan(Color.RED),
                        position, (position + (dataPlate
                                .getLicenceStatus()).length()), 0);
            }

            if (position != -1) {
                resultViewData.setSpan(new RelativeSizeSpan(1.5f), position,
                        (position + (dataPlate.getLicenceStatus())
                                .length()), 0);
            }

        }

        ////

        if (dataPlate.getTheftRecord() != null) {
            int position = result.indexOf(dataPlate.getTheftRecord());

            if ((dataPlate.getTheftRecord()).equals("Negativo")) {
                resultViewData.setSpan(new ForegroundColorSpan(Color.BLUE),
                        position, (position + (dataPlate
                                .getTheftRecord()).length()), 0);
                setVibrateOrRinging(false);
            } else {
                setVibrateOrRinging(true);
                resultViewData.setSpan(new ForegroundColorSpan(Color.RED),
                        position, (position + (dataPlate
                                .getTheftRecord()).length()), 0);

                if (position != -1) {
                    resultViewData.setSpan(new RelativeSizeSpan(1.5f), position,
                            (position + (dataPlate.getTheftRecord())
                                    .length()), 0);
                }
            }

        }


    }

    public void setWarning() {
        if (isVibrateOrRinging) {
            if (DatabaseCreator.getSettingDatabaseAdapter(context)
                    .getVibrator()) {
                AppObject.startVibrate(context);
            }
            if (DatabaseCreator.getSettingDatabaseAdapter(context)
                    .getRingtone()) {
                AppObject.startWarning(context);
            }
        }
    }

}
