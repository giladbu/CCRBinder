package com.keas.ccr;

import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.astm.ccr.ActorReference;
import org.astm.ccr.ActorRole;
import org.astm.ccr.ActorTypeChoice;
import org.astm.ccr.Actors;
import org.astm.ccr.ActorsActor;
import org.astm.ccr.Body;
import org.astm.ccr.Code;
import org.astm.ccr.ContinuityOfCareRecord;
import org.astm.ccr.CurrentName;
import org.astm.ccr.DateTime;
import org.astm.ccr.Description;
import org.astm.ccr.Direction;
import org.astm.ccr.Directions;
import org.astm.ccr.Dose;
import org.astm.ccr.EMail;
import org.astm.ccr.FamilyHistory;
import org.astm.ccr.FamilyHistoryProblem;
import org.astm.ccr.FamilyMember;
import org.astm.ccr.FamilyProblemHistory;
import org.astm.ccr.Frequency;
import org.astm.ccr.MeasureGroup;
import org.astm.ccr.MedProduct;
import org.astm.ccr.Medication;
import org.astm.ccr.Medications;
import org.astm.ccr.Name;
import org.astm.ccr.Patient;
import org.astm.ccr.Person;
import org.astm.ccr.Problem;
import org.astm.ccr.Problems;
import org.astm.ccr.ProductName;
import org.astm.ccr.Result;
import org.astm.ccr.Results;
import org.astm.ccr.SLRCGroup;
import org.astm.ccr.SocialHistory;
import org.astm.ccr.SocialHistoryElement;
import org.astm.ccr.Source;
import org.astm.ccr.Status;
import org.astm.ccr.TestInResult;
import org.astm.ccr.TestResult;
import org.astm.ccr.Type;
import org.astm.ccr.Units;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

public class CCRBuilder implements ICCRBuilder{
	private static final String PAST = "PAST";
	private static final String STOP_DATE = "Stop Date";
	private static final String COLLECTION_DATE = "Collection Start Date";
	private static final String START_DATE = "Start Date";
	private static final String ACTIVE = "ACTIVE";
	ContinuityOfCareRecord cCCR = new ContinuityOfCareRecord();

	@Override
	public void addFamilyHistory(IFamilyHistoryProblem element){
		FamilyHistory familyHistory = getFamilyHistory();
		FamilyProblemHistory vFamilyProblemHistory = new FamilyProblemHistory();
		vFamilyProblemHistory.addDateTime(getDate(element.getStartDate(), START_DATE));
		vFamilyProblemHistory.addDateTime(getDate(element.getEndDate(), STOP_DATE));
		vFamilyProblemHistory.setStatus(getStatus(element));
		//set problem
		FamilyHistoryProblem vProblem = new FamilyHistoryProblem();
		Description description = new Description();
		description.setCode(getCodes(element));
		description.setText(element.getValue());
		Type type = new Type();
		type.setText(element.getName());
		vProblem.setType(type);
		vProblem.setDescription(description);
		vFamilyProblemHistory.addFamilyHistoryProblem(vProblem);
		//set family member
		FamilyMember vFamilyMember = new FamilyMember();
		ActorRole vActorRole = new ActorRole();
		vActorRole.setText(element.getFailyMemeberDescription());
		vFamilyMember.addActorRole(vActorRole);
		vFamilyProblemHistory.addFamilyMember(vFamilyMember);
		//set source
		vFamilyProblemHistory.setSLRCGroup(getSourceElement(element.getSourceId()));
		familyHistory.addFamilyProblemHistory(vFamilyProblemHistory);
	}
	
	private FamilyHistory getFamilyHistory() {
		Body body = getBody();
		if(body.getFamilyHistory() == null){
			body.setFamilyHistory(new FamilyHistory());
		}
		return body.getFamilyHistory();
	}

	@Override
	public void addProblem(ICondition element){
		Problems problems = getProblems();
		Problem vProblem = new Problem();
		vProblem.addDateTime(getDate(element.getStartDate(), START_DATE));
		vProblem.addDateTime(getDate(element.getEndDate(), STOP_DATE));
		vProblem.setStatus(getStatus(element));
		Description description = new Description();
		description.setCode(getCodes(element));
		description.setText(element.getValue());
		Type type = new Type();
		type.setText(element.getName());
		vProblem.setType(type);
		vProblem.setDescription(description);
		//set source
		vProblem.setSLRCGroup(getSourceElement(element.getSourceId()));
		problems.addProblem(vProblem);
	}

	private Problems getProblems() {
		Body body = getBody();
		if(body.getProblems() == null){
			body.setProblems(new Problems());
		}
		return body.getProblems();
	}

	@Override
	public void addSocialHistory(ICondition element){
		SocialHistory socialHistory = getSocialHistory();
		SocialHistoryElement vSocialHistoryElement = new SocialHistoryElement();
		vSocialHistoryElement.addDateTime(getDate(element.getStartDate(), START_DATE));
		vSocialHistoryElement.addDateTime(getDate(element.getEndDate(), STOP_DATE));
		vSocialHistoryElement.setStatus(getStatus(element));
		Description description = new Description();
		description.setCode(getCodes(element));
		description.setText(element.getValue());
		Type type = new Type();
		type.setText(element.getName());
		vSocialHistoryElement.setType(type);
		vSocialHistoryElement.setDescription(description);
		//set source
		vSocialHistoryElement.setSLRCGroup(getSourceElement(element.getSourceId()));
		socialHistory.addSocialHistoryElement(vSocialHistoryElement);
	}
	
	private Status getStatus(ICondition element) {
		Status status = new Status();
		status.setText(element.isActive() ? ACTIVE : PAST);
		return status;
	}

	private SocialHistory getSocialHistory() {
		Body body = getBody();
		if(body.getSocialHistory() == null){
			body.setSocialHistory(new SocialHistory());
		}
		return body.getSocialHistory();
	}
	@Override
	public void addMedication(IMedication med){
		Medications meds = getMedications();
		Medication medication = new Medication();
		//add names
		MedProduct vMedProduct = new MedProduct();
		ProductName productName = new ProductName();
		productName.setText(med.getName());
		//add codes
		productName.setCode(getCodes(med));
		vMedProduct.setProductName(productName);
		medication.addMedProduct(vMedProduct);
		//add dates
		medication.addDateTime(getDate(med.getStartDate(), START_DATE));
		medication.addDateTime(getDate(med.getEndDate(), STOP_DATE));
		//add dosage
		Directions vDirections = new Directions();
		Direction vDirection = new Direction();
		Dose vDose = new Dose();
		vDose.setValue(med.getDosage().toPlainString());
		vDirection.addDose(vDose);
		//add frequency
		Frequency vFrequency = new Frequency();
		MeasureGroup measureGroup = new MeasureGroup();
		measureGroup.setValue(med.getFrequency());
		vFrequency.setMeasureGroup(measureGroup);
		vDirection.addFrequency(vFrequency);
		vDirections.addDirection(vDirection);
		medication.addDirections(vDirections);
		
		//set source
		medication.setSLRCGroup(getSourceElement(med.getSourceId()));

		//set status
		medication.setStatus(getStatus(med));
		
		//set medication
		meds.addMedication(medication);
	}

	private Code[] getCodes(ICondition element) {
		Code[] codes = new Code[0];
		codes = element.getCodes() != null ? element.getCodes().toArray(codes) : codes;
		return codes;
	}

	private Medications getMedications() {
		Body body = getBody();
		if(body.getMedications() == null){
			body.setMedications(new Medications());
		}
		return body.getMedications();
	}

	@Override
	public void addLabResult(ILabResult lab) {
		Results resultsSection = getLabs();
		Result result = new Result();
		//set date
		result.addDateTime(getDate(lab.getDate(), COLLECTION_DATE));
		
		TestInResult vTestInResult = new TestInResult();
		
		//set test name
		Description description = new Description();
		description.setText(lab.getName());
		description.addCode(lab.getCode());
		vTestInResult.setDescription(description);
		
		//set value and unit
		TestResult testResult = new TestResult();
		MeasureGroup measureGroup = new MeasureGroup();
		measureGroup.setValue(lab.getValue());
		Units units = new Units();
		units.setUnit(lab.getUnits());
		measureGroup.setUnits(units);
		testResult.setMeasureGroup(measureGroup);
		vTestInResult.setTestResult(testResult);
		result.addTestInResult(vTestInResult);

		//add source
		SLRCGroup mSLRCGroup = getSourceElement(lab.getSourceId());
		vTestInResult.setSLRCGroup(mSLRCGroup);
		resultsSection.addResult(result);
	}

	private SLRCGroup getSourceElement(String sourceId)
			throws IndexOutOfBoundsException {
		SLRCGroup mSLRCGroup= new SLRCGroup();
		Source vSource = new Source();
		ActorReference vActorReference = new ActorReference();
		vActorReference.setActorID(sourceId);
		vSource.addActorReference(vActorReference);
		mSLRCGroup.addSource(vSource);
		return mSLRCGroup;
	}

	private DateTime getDate(Date date, String description) {
		DateTime d = new DateTime();
		Type type = new Type();
		type.setText(description);
		d.setType(type);
		d.setExactDateTime(asISO8601DateFormat(date));
		return d;
	}

	private String asISO8601DateFormat(Date date) {
		if(date == null) return null;
		return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(date);
	}

	private Results getLabs() {
		Body body = getBody();
		if(body.getResults() == null){
			body.setResults(new Results());
		}
		return body.getResults();
	}

	private Body getBody() {
		if(cCCR.getBody() == null){
			cCCR.setBody(new Body());
		}
		return cCCR.getBody();
	}

	@Override
	public void setUser(IUser user) {
		Patient patient = new Patient();
		patient.setActorID(user.getId());
		cCCR.addPatient(patient);
		
		Actors actors = getActors();
		ActorsActor userActor = new ActorsActor();
		userActor.setActorObjectID(user.getId());
		EMail vEMail = new EMail();
		vEMail.setValue(user.getEmail());
		userActor.addEMail(vEMail);
		userActor.addAddress(user.getAddress());
		ActorTypeChoice actorTypeChoice = new ActorTypeChoice();
		Person person = new Person();
		Name name = new Name();
		CurrentName currentName = new CurrentName();
		currentName.addGiven(user.getFirstName());
		currentName.addFamily(user.getLastName());
		name.setCurrentName(currentName);
		person.setName(name);
		actorTypeChoice.setPerson(person);
		userActor.setActorTypeChoice(actorTypeChoice);
		actors.addActorsActor(userActor);
	}
	private Actors getActors() {
		if(cCCR.getActors() == null){
			cCCR.setActors(new Actors());
		}
		return cCCR.getActors();
	}

	@Override
	public String toXml() throws MarshalException, ValidationException {
		StringWriter out = new StringWriter();
		cCCR.marshal(out);
		return out.toString();
	}

	@Override
	public ContinuityOfCareRecord toCCR() {
		return cCCR;
	}
	
}
