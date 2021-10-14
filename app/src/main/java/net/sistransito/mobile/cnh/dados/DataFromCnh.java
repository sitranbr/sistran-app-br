package net.sistransito.mobile.cnh.dados;

import net.sistransito.mobile.timeandime.TimeAndIme;

public class DataFromCnh {

	TimeAndIme time;

	/*public DataFromCNH(Context context) {
		time = new TimeAndIme(context);
		setDate(time.getDate() + "\n" + time.getTime());
	}*/

	private String name, register, state, cpf, identity, birthDate, mothersName, cnhCategory,
			cnhValidity, cnhPoints, cnhObservation, cnhSituation, cnhBlock, cnhDate;

	public TimeAndIme getTime() {
		return time;
	}

	public void setTime(TimeAndIme time) {
		this.time = time;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getCnhSituation() {
		return cnhSituation;
	}

	public void setCnhSituation(String cnhSituation) {
		this.cnhSituation = cnhSituation;
	}

	public String getCnhBlock() {
		return cnhBlock;
	}

	public void setCnhBlock(String cnhBlock) {
		this.cnhBlock = cnhBlock;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegister() {
		return register;
	}

	public void setRegister(String register) {
		this.register = register;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getMothersName() {
		return mothersName;
	}

	public void setMothersName(String mothersName) {
		this.mothersName = mothersName;
	}

	public String getCnhCategory() {
		return cnhCategory;
	}

	public void setCnhCategory(String cnhCategory) {
		this.cnhCategory = cnhCategory;
	}

	public String getCnhValidity() {
		return cnhValidity;
	}

	public void setCnhValidity(String cnhValidity) {
		this.cnhValidity = cnhValidity;
	}

	public String getCnhPoints() {
		return cnhPoints;
	}

	public void setCnhPoints(String cnhPoints) {
		this.cnhPoints = cnhPoints;
	}

	public String getCnhObservation() {
		return cnhObservation;
	}

	public void setCnhObservation(String cnhObservation) {
		this.cnhObservation = cnhObservation;
	}

	public String getCnhDate() {
		return cnhDate;
	}

	public void setCnhDate(String cnhDate) {
		this.cnhDate = cnhDate;
	}
}
