package net.sistransito.mobile.cnh.dados;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import net.sistransito.mobile.appconstants.AppConstants;
import net.sistransito.mobile.appobject.AppObject;
import net.sistransito.mobile.database.DatabaseCreator;
import net.sistransito.R;

public class CnhViewFormat {

    private SpannableString resultViewData;

    private String name, register, state, cpf, identity, birthDate, mathersName, cnhCategory,
            cnhValidity, cnhPoints, cnhSituation, chnBlock;
    private boolean isVibrateOrRinging;
    private Context context;

    public CnhViewFormat(DataFromCnh fromCNH, Context context) {
        this.context = context;

        this.name = context.getResources().getString(R.string.cnh_nome_format)
                + fromCNH.getName() + "  ";

        this.register = context.getResources().getString(R.string.cnh_registro_format)
                + fromCNH.getRegister() + "  ";

        this.mathersName = AppConstants.NEW_LINE
                + context.getResources().getString(R.string.cnh_mae_format)
                + fromCNH.getMothersName();

        this.state = AppConstants.NEW_LINE
                + context.getResources().getString(R.string.uf_format)
                + fromCNH.getState();

        this.birthDate = AppConstants.NEW_LINE
                + context.getResources().getString(R.string.cnh_nascimento_format)
                + fromCNH.getBirthDate();

        this.identity = AppConstants.NEW_LINE
                + context.getResources().getString(R.string.cnh_rg_format)
                + fromCNH.getIdentity();

        this.cpf = AppConstants.NEW_LINE
                + context.getResources().getString(R.string.cnh_cpf_format)
                + fromCNH.getCpf();

        this.cnhCategory = AppConstants.NEW_LINE
                + context.getResources().getString(R.string.cnh_categoria_format)
                + fromCNH.getCnhCategory() + AppConstants.NEW_LINE;

        this.cnhValidity = context.getResources().getString(R.string.cnh_validade_format)
                + fromCNH.getCnhValidity() + AppConstants.NEW_LINE;

        this.cnhPoints = context.getResources().getString(R.string.cnh_pontos_format)
                + fromCNH.getCnhPoints() + AppConstants.NEW_LINE;

        this.chnBlock = context.getResources().getString(R.string.cnh_bloqueio_format)
                + fromCNH.getCnhBlock() + AppConstants.TWO_LINES;

        this.cnhSituation = fromCNH.getCnhSituation() + AppConstants.NEW_LINE;

        setResultViewData(fromCNH);
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

    private void setResultViewData(DataFromCnh fromCNH) {

        String result = name + register + state + birthDate + cpf + identity + mathersName + cnhCategory +
                cnhValidity + cnhPoints + chnBlock + cnhSituation;

        resultViewData = new SpannableString(result);

        if (fromCNH.getCnhSituation() != null) {
            int position = result.indexOf(fromCNH.getCnhSituation());

            if ((fromCNH.getCnhSituation()).contains("ATIVA")
                    || (fromCNH.getCnhSituation()).contains("ativa")) {
                resultViewData.setSpan(new ForegroundColorSpan(Color.BLUE),
                        position, (position + (fromCNH
                                .getCnhSituation()).length()), 0);
                setVibrateOrRinging(false);
            } else {
                setVibrateOrRinging(true);
                resultViewData.setSpan(new ForegroundColorSpan(Color.RED),
                        position, (position + (fromCNH
                                .getCnhSituation()).length()), 0);
            }

            /*if (position != -1) {
                resultViewData.setSpan(new RelativeSizeSpan(1.5f), position,
                        (position + (fromCNH.getSituacao())
                                .length()), 0);
            }*/

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
