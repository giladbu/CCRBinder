package com.keas.ccr;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.astm.ccr.Code;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;

import junit.framework.TestCase;

public class CCRBuilderTest extends TestCase {

	private CCRBuilder cCCRBuilder;
	private IUser cUser;

	@Override
	protected void setUp() throws Exception {
		User mUser = new User();
		mUser.setEmail("test@null.com");
		mUser.setId("userId");
		mUser.setFirstName("test");
		mUser.setLastName("null");
		mUser.setDob(dt("01/01/1970"));
		mUser.setGender(Gender.MALE);
		cUser = mUser;
	}

	public void testAddLab(){
		cCCRBuilder = new CCRBuilder();
		cCCRBuilder.setUser(cUser);
		LabResult lab = new LabResult();
		Code code = new Code();
		code.setCodingSystem("LOINC");
		code.setValue("0000-0");
		lab.setCode(code);
		lab.setDate(new Date());
		lab.setSourceId("Keas");
		lab.setUnits("lbs");
		lab.setName("weight");
		lab.setValue("170");
		cCCRBuilder.addLabResult(lab);
		try {
			String xml = cCCRBuilder.toXml();
			assertTrue(xml.contains("<Patient><ActorID>"+cUser.getId()+"</ActorID></Patient>"));
			assertTrue(xml.contains("<ActorObjectID>"+cUser.getId()+"</ActorObjectID>"));
			assertTrue(xml.contains(
					String.format("<EMail><Value>%s</Value></EMail>", 
							cUser.getEmail()
							)));			
			assertTrue(xml.contains(
					String.format(
							"<CurrentName><Given>%s</Given><Family>%s</Family></CurrentName>", 
							cUser.getFirstName(), cUser.getLastName()
							)));
			assertTrue(xml.contains(
					String.format(
							"<Text>%s</Text><Code><Value>%s</Value><CodingSystem>%s</CodingSystem></Code>",
							lab.getName(), lab.getCode().getValue(), lab.getCode().getCodingSystem()
							)));
			assertTrue(xml.contains(
					String.format(
							"<TestResult><Value>%s</Value><Units><Unit>%s</Unit></Units></TestResult>",
							lab.getValue(), lab.getUnits()
							)));
		} catch (MarshalException e) {
			fail(e.getMessage());
		} catch (ValidationException e) {
			fail(e.getMessage());
		}
	}

	public void testAddMed(){
		cCCRBuilder = new CCRBuilder();
		cCCRBuilder.setUser(cUser);
		IMedication med = new IMedication() {
			
			@Override
			public boolean isActive() {
				return true;
			}
			
			@Override
			public Date getStartDate() {
				try {
					return dt("01/01/2010");
				} catch (ParseException e) {
					return null;
				}
			}
			
			@Override
			public String getName() {
				return "aspirin";
			}
			
			@Override
			public String getFrequency() {
				return "once a day";
			}
			
			@Override
			public Date getEndDate() {
				return null;
			}
			
			@Override
			public BigDecimal getDosage() {
				return new BigDecimal(1);
			}
			
			@Override
			public List<Code> getCodes() {
				return null;
			}

			@Override
			public String getSourceId() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getValue() {
				return null;
			}
		};
		cCCRBuilder.addMedication(med);
		try {
			String xml = cCCRBuilder.toXml();
			assertTrue(
					xml.contains(
							String.format("<DateTime><Type><Text>Start Date</Text></Type><ExactDateTime>%s</ExactDateTime></DateTime>",
									new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(med.getStartDate())
									)));
			assertTrue(
					xml.contains(
							String.format("<Frequency><Value>%s</Value></Frequency>",
									med.getFrequency()
									)));
			assertTrue(
					xml.contains(
							String.format("<ProductName><Text>%s</Text>",
									med.getName()
									)));
			assertTrue(
					xml.contains(
							String.format("><Dose><Value>%s</Value></Dose><",
									med.getDosage().toPlainString()
									)));
			System.out.println(xml);
		} catch (MarshalException e) {
			fail(e.getMessage());
		} catch (ValidationException e) {
			fail(e.getMessage());
		}
	}
	private Date dt(String date) throws ParseException {
		return new SimpleDateFormat("MM/dd/yyyy").parse(date);
	}

}
