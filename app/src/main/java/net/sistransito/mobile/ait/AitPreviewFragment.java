package net.sistransito.mobile.ait;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import net.sistransito.mobile.util.User;

import net.sistransito.R;

public class AitPreviewFragment extends Fragment {

    private View view;
    private AitData aitData;
    private User user;

    public static AitPreviewFragment newInstance() {
        return new AitPreviewFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.ait_preview, null, false);

        getAitObject();

        return view;
    }

    private void getAitObject() {
        aitData = ObjectAit.getAitData();
        addListener();
    }

    public void addListener() {

        TextView tvCompanyCode, tvAitNumber, tvPlate, tvStateVehicle, tvChassi,
                 tvVehicleBrand, tvVehicleModel, tvDriverName, tvCnh, tvStateCnh,
                 tvVehicleSpecies, tvVehicleCategory, tvAddress, tvCity, tvAitDate, tvAitTime,
                 tvAitCode, tvLegalProtection, tvDescription, tMeasures, tvObservations, tvAgent,
                 tvRegistrationAgent;

        tvCompanyCode = (TextView) view.findViewById(R.id.tv_company_code);
        tvAitNumber = (TextView) view.findViewById(R.id.tv_ait_number);
        tvPlate = (TextView) view.findViewById(R.id.tv_ait_plate);
        tvStateVehicle = (TextView) view.findViewById(R.id.tv_ait_state_vehicle);
        tvChassi = (TextView) view.findViewById(R.id.tv_ait_chassi);
        tvVehicleBrand = (TextView) view.findViewById(R.id.tv_ait_vehicle_brand);
        tvVehicleModel = (TextView) view.findViewById(R.id.tv_ait_vehicle_model);
        tvVehicleSpecies = (TextView) view.findViewById(R.id.tv_ait_vehicle_species);
        tvVehicleCategory = (TextView) view.findViewById(R.id.tv_ait_vehicle_category);
        tvDriverName = (TextView) view.findViewById(R.id.tv_ait_driver_name);
        tvCnh = (TextView) view.findViewById(R.id.tv_ait_cnh);
        tvStateCnh = (TextView) view.findViewById(R.id.tv_ait_state_cnh);
        tvAddress = (TextView) view.findViewById(R.id.tv_ait_address);
        tvCity = (TextView) view.findViewById(R.id.tv_ait_city);
        tvAitDate = (TextView) view.findViewById(R.id.tv_ait_date);
        tvAitTime = (TextView) view.findViewById(R.id.tv_ait_time);
        tMeasures = (TextView) view.findViewById(R.id.tv_ait_measures);
        tvAitCode = (TextView) view.findViewById(R.id.tv_ait_code);
        tvLegalProtection = (TextView) view.findViewById(R.id.tv_ait_legal_protection);
        tvDescription = (TextView) view.findViewById(R.id.tv_ait_description);
        tvObservations  = (TextView) view.findViewById(R.id.tv_ait_observations);

       // tvCompanyCode.setText(user.getCodigoOrgao());
        tvAitNumber.setText(aitData.getAitNumber());
        tvPlate.setText(aitData.getPlate());
        tvStateVehicle.setText(aitData.getStateVehicle());
        tvChassi.setText(aitData.getChassi());
        tvVehicleBrand.setText(aitData.getVehicleBrand());
        tvVehicleModel.setText(aitData.getVehicleModel());
        tvVehicleSpecies.setText(aitData.getVehicleSpecies());
        tvVehicleCategory.setText(aitData.getVehicleCategory());
        tvDriverName.setText(aitData.getConductorName());
        tvCnh.setText(aitData.getCnhPpd());
        tvStateCnh.setText(aitData.getCnhState());
        tvAddress.setText(aitData.getAddress());
        tvCity.setText(aitData.getCity() + "/" + aitData.getState());
        tvAitDate.setText(aitData.getAitDate());
        tvAitTime.setText(aitData.getAitTime());
        tvAitCode.setText(aitData.getFramingCode() + "-" + aitData.getUnfolding());
        tvLegalProtection.setText(aitData.getArticle());
        tvDescription.setText(aitData.getInfraction());
        tvObservations.setText(aitData.getObservation());

        if(aitData.getRetreat() == null){
            tMeasures.setText(aitData.getProcedures());
        } else if (aitData.getProcedures() == null){
            tMeasures.setText(aitData.getRetreat());
        } else if (aitData.getProcedures() != null && aitData.getRetreat() != null){
            tMeasures.setText(aitData.getRetreat() + ", " + aitData.getProcedures());
        }

    }

}
