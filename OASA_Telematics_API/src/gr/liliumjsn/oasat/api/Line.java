package gr.liliumjsn.oasat.api;

import java.util.ArrayList;

import org.json.JSONObject;

public class Line {
	// fields
	private int lineCode;
	private String lineID;
	private int mlCode;
	private int sdcCode;
	private String lineDescr;
	private String lineDescrEng;
	private ArrayList<Route> lineRoutes;

	public Line() {
	}

	public Line(int lineCode, String lineID, String lineDescr, String lineDescrEng) {
		this.setLineCode(lineCode);
		this.setLineID(lineID);
		this.setLineDescr(lineDescr);
		this.setLineDescrEng(lineDescrEng);
	}

	public Line(int lineCode, String lineID, String lineDescr, String lineDescrEng, int mlCode, int sdcCode) {
		this(lineCode, lineID, lineDescr, lineDescrEng);
		this.setMlCode(mlCode);
		this.setSdcCode(sdcCode);
	}

	public Line(int lineCode, String lineID, String lineDescr, String lineDescrEng, int mlCode, int sdcCode,
			ArrayList<Route> lineRoutes) {
		this(lineCode, lineID, lineDescr, lineDescrEng, mlCode, sdcCode);
		this.setLineRoutes(lineRoutes);
	}

	public Route getRoutesForLine() {

		return null;
	}

	public int getLineCode() {
		return lineCode;
	}

	public void setLineCode(int lineCode) {
		this.lineCode = lineCode;
	}

	public String getLineID() {
		return lineID;
	}

	public void setLineID(String lineID) {
		this.lineID = lineID;
	}

	public int getMlCode() {
		return mlCode;
	}

	public void setMlCode(int mlCode) {
		this.mlCode = mlCode;
	}

	public int getSdcCode() {
		return sdcCode;
	}

	public void setSdcCode(int sdcCode) {
		this.sdcCode = sdcCode;
	}

	public String getLineDescr() {
		return lineDescr;
	}

	public void setLineDescr(String lineDescr) {
		this.lineDescr = lineDescr;
	}

	public String getLineDescrEng() {
		return lineDescrEng;
	}

	public void setLineDescrEng(String lineDescrEng) {
		this.lineDescrEng = lineDescrEng;
	}

	public ArrayList<Route> getLineRoutes() {
		return lineRoutes;
	}

	public void setLineRoutes(ArrayList<Route> lineRoutes) {
		this.lineRoutes = lineRoutes;
	}

	public String toString() {
		return getLineID() + " " + getLineDescrEng();
	}

	public static Line parseLine(JSONObject object) {

		try {
			String lineCodeStr = String.valueOf(object.get("line_code"));
			int lineCode = -1;
			if (lineCodeStr != "null")
				lineCode = Integer.parseInt(lineCodeStr);

			String lineID = String.valueOf(object.get("line_id"));
			String lineDescr = String.valueOf(object.get("line_descr"));
			String lineDescrEng = String.valueOf(object.get("line_descr_eng"));

			String mlCodeStr = String.valueOf(object.get("ml_code"));
			int mlCode = -1;
			if (mlCodeStr != "null")
				mlCode = Integer.parseInt(mlCodeStr);

			String sdcCodeStr = String.valueOf(object.get("sdc_code"));
			int sdcCode = -1;
			if (sdcCodeStr != "null")
				sdcCode = Integer.parseInt(sdcCodeStr);

			return new Line(lineCode, lineID, lineDescr, lineDescrEng, mlCode, sdcCode);
			
		} catch (Exception e) {
			String lineCodeStr = String.valueOf(object.get("LineCode"));
			int lineCode = -1;
			if (lineCodeStr != "null")
				lineCode = Integer.parseInt(lineCodeStr);

			String lineID = String.valueOf(object.get("LineID"));
			String lineDescr = String.valueOf(object.get("LineDescr"));
			String lineDescrEng = String.valueOf(object.get("LineDescrEng"));

			String mlCodeStr = String.valueOf(object.get("MasterLineCode"));
			int mlCode = -1;
			if (mlCodeStr != "null")
				mlCode = Integer.parseInt(mlCodeStr);

			return new Line(lineCode, lineID, lineDescr, lineDescrEng, mlCode, 0);
		}
	}

}
