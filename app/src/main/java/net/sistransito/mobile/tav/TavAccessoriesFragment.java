package net.sistransito.mobile.tav;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

import net.sistransito.mobile.adapter.AnyArrayAdapter;
import net.sistransito.R;

import java.util.Arrays;

public class TavAccessoriesFragment extends Fragment implements
		OnItemSelectedListener {
	private View view;
	private FilterName filterName;

	private TavData data;
	private Spinner antenna, trunck, seats, baterry, wheelCover,
			airConditioner, fireExtinguisher, headLight,
			taiLight, jack, frontBumper,
			backBumper, driverHood, tires, spareTire,
			radio, rearviewMirror, rightOutsideMirror, carpet,
			triangle, steeringWheel, motorcycleHandlebar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.tav_vehicle_accessories_fragment, null,
				false);
		filterName = new FilterName(getActivity());
		initializedView();
		getAutodeObject();
		return view;
	}

	private void addListener() {
		antenna.setOnItemSelectedListener(this);
		trunck.setOnItemSelectedListener(this);
		seats.setOnItemSelectedListener(this);
		baterry.setOnItemSelectedListener(this);
		wheelCover.setOnItemSelectedListener(this);
		airConditioner.setOnItemSelectedListener(this);
		fireExtinguisher.setOnItemSelectedListener(this);
		headLight.setOnItemSelectedListener(this);
		taiLight.setOnItemSelectedListener(this);
		jack.setOnItemSelectedListener(this);
		frontBumper.setOnItemSelectedListener(this);
		backBumper.setOnItemSelectedListener(this);
		driverHood.setOnItemSelectedListener(this);
		tires.setOnItemSelectedListener(this);
		spareTire.setOnItemSelectedListener(this);
		radio.setOnItemSelectedListener(this);
		rearviewMirror.setOnItemSelectedListener(this);
		rightOutsideMirror.setOnItemSelectedListener(this);
		carpet.setOnItemSelectedListener(this);
		triangle.setOnItemSelectedListener(this);
		steeringWheel.setOnItemSelectedListener(this);
		motorcycleHandlebar.setOnItemSelectedListener(this);

	}

	private void getAutodeObject() {
		data = TavObject.getTAVObject();

	}

	private void initializedView() {
		antenna = (Spinner) view.findViewById(R.id.antena_de_radio);
		trunck = (Spinner) view.findViewById(R.id.bagageiro);
		seats = (Spinner) view.findViewById(R.id.bancos);
		baterry = (Spinner) view.findViewById(R.id.bateria);
		wheelCover = (Spinner) view.findViewById(R.id.calota);
		airConditioner = (Spinner) view
				.findViewById(R.id.condicionador_de_ar);
		fireExtinguisher = (Spinner) view
				.findViewById(R.id.extintor_de_incendio);
		headLight = (Spinner) view
				.findViewById(R.id.farolete_dianteiro);
		taiLight = (Spinner) view.findViewById(R.id.farolete_traseiro);
		jack = (Spinner) view.findViewById(R.id.macaco);
		frontBumper = (Spinner) view
				.findViewById(R.id.para_choque_dianteiro);
		backBumper = (Spinner) view
				.findViewById(R.id.para_choque_traseiro);
		driverHood = (Spinner) view
				.findViewById(R.id.para_sol_do_condutor);
		tires = (Spinner) view.findViewById(R.id.pneus);
		spareTire = (Spinner) view.findViewById(R.id.pneus_estepe);
		radio = (Spinner) view.findViewById(R.id.radio);
		rearviewMirror = (Spinner) view
				.findViewById(R.id.retrovisor_interno);
		rightOutsideMirror = (Spinner) view
				.findViewById(R.id.retrovisor_externo_direito);
		carpet = (Spinner) view.findViewById(R.id.tapete);
		triangle = (Spinner) view.findViewById(R.id.triangulo);
		steeringWheel = (Spinner) view.findViewById(R.id.volante);
		motorcycleHandlebar = (Spinner) view.findViewById(R.id.guidam);
		addAdapter();
		addListener();
	}

	private void addAdapter() {

		antenna.setAdapter(new AnyArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, android.R.id.text1,
				Arrays.asList(getResources().getStringArray(R.array.ac_0))));
		trunck.setAdapter(new AnyArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, android.R.id.text1,
				Arrays.asList(getResources().getStringArray(R.array.ac_1))));
		seats.setAdapter(new AnyArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, android.R.id.text1,
				Arrays.asList(getResources().getStringArray(R.array.ac_2))));
		baterry.setAdapter(new AnyArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, android.R.id.text1,
				Arrays.asList(getResources().getStringArray(R.array.ac_3))));
		wheelCover.setAdapter(new AnyArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, android.R.id.text1,
				Arrays.asList(getResources().getStringArray(R.array.ac_4))));
		airConditioner.setAdapter(new AnyArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item,
				android.R.id.text1, Arrays.asList(getResources()
						.getStringArray(R.array.ac_5))));
		fireExtinguisher.setAdapter(new AnyArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item,
				android.R.id.text1, Arrays.asList(getResources()
						.getStringArray(R.array.ac_6))));
		headLight.setAdapter(new AnyArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item,
				android.R.id.text1, Arrays.asList(getResources()
						.getStringArray(R.array.ac_7))));
		taiLight.setAdapter(new AnyArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, android.R.id.text1,
				Arrays.asList(getResources().getStringArray(R.array.ac_8))));
		jack.setAdapter(new AnyArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, android.R.id.text1,
				Arrays.asList(getResources().getStringArray(R.array.ac_9))));
		frontBumper.setAdapter(new AnyArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item,
				android.R.id.text1, Arrays.asList(getResources()
						.getStringArray(R.array.ac_10))));
		backBumper.setAdapter(new AnyArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item,
				android.R.id.text1, Arrays.asList(getResources()
						.getStringArray(R.array.ac_11))));
		driverHood.setAdapter(new AnyArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item,
				android.R.id.text1, Arrays.asList(getResources()
						.getStringArray(R.array.ac_12))));
		tires.setAdapter(new AnyArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, android.R.id.text1,
				Arrays.asList(getResources().getStringArray(R.array.ac_13))));
		spareTire.setAdapter(new AnyArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, android.R.id.text1,
				Arrays.asList(getResources().getStringArray(R.array.ac_14))));
		radio.setAdapter(new AnyArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, android.R.id.text1,
				Arrays.asList(getResources().getStringArray(R.array.ac_15))));
		rearviewMirror.setAdapter(new AnyArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item,
				android.R.id.text1, Arrays.asList(getResources()
						.getStringArray(R.array.ac_16))));
		rightOutsideMirror.setAdapter(new AnyArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item,
				android.R.id.text1, Arrays.asList(getResources()
						.getStringArray(R.array.ac_17))));
		carpet.setAdapter(new AnyArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, android.R.id.text1,
				Arrays.asList(getResources().getStringArray(R.array.ac_18))));
		triangle.setAdapter(new AnyArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, android.R.id.text1,
				Arrays.asList(getResources().getStringArray(R.array.ac_19))));
		steeringWheel.setAdapter(new AnyArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, android.R.id.text1,
				Arrays.asList(getResources().getStringArray(R.array.ac_20))));
		motorcycleHandlebar.setAdapter(new AnyArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, android.R.id.text1,
				Arrays.asList(getResources().getStringArray(R.array.ac_21))));

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
		String value = (String) parent.getItemAtPosition(pos);
		value = filterName.filter(value);
		switch (parent.getId()) {
		case R.id.antena_de_radio:
			data.setAntenna(value);
			break;
		case R.id.bagageiro:
			data.setTrunk(value);
			break;
		case R.id.bancos:
			data.setSeats(value);
			break;
		case R.id.bateria:
			data.setBaterry(value);
			break;
		case R.id.calota:
			data.setWheelCover(value);
			break;
		case R.id.condicionador_de_ar:
			data.setAirConditioner(value);
			break;
		case R.id.extintor_de_incendio:
			data.setFireExtinguisher(value);
			break;
		case R.id.farolete_dianteiro:
			data.setHeadLight(value);
			break;
		case R.id.farolete_traseiro:
			data.setTaiLight(value);
			break;
		case R.id.macaco:
			data.setJack(value);
			break;
		case R.id.para_choque_dianteiro:
			data.setFrontBumper(value);
			break;
		case R.id.para_choque_traseiro:
			data.setBackBumper(value);
			break;
		case R.id.para_sol_do_condutor:
			data.setDriverSunVisor(value);
			break;
		case R.id.pneus:
			data.setTires(value);
			break;
		case R.id.pneus_estepe:
			data.setSpareTire(value);
			break;
		case R.id.radio:
			data.setRadio(value);
			break;
		case R.id.retrovisor_interno:
			data.setRearviewMirror(value);
			break;
		case R.id.retrovisor_externo_direito:
			data.setRightSideMirror(value);
			break;
		case R.id.tapete:
			data.setCarpet(value);
			break;
		case R.id.triangulo:
			data.setTriangle(value);
			break;
		case R.id.volante:
			data.setSteeringWheel(value);
			break;
		case R.id.guidam:
			data.setMotorcycleHandlebar(value);
			break;

		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
