package it.unipv.ingsfw.SmartWarehouse.Model.inventory;

public class Position {
	private String line, pod, bin;
	
	public Position(String line, String pod, String bin) throws IllegalArgumentException {
		setLine(line);
		setPod(pod);
		setBin(bin);
	}

	public String getLine() {
		return line;
	}

	/**
	 * Sets after checked that the line exists in the warehouse (must be between A and G)
	 * @param line
	 */
	public void setLine(String line) throws IllegalArgumentException {
		if(checkLine(line)) {
			this.line = line;
		} else {
			throw new IllegalArgumentException("Line value is out of range (must be between A and G)");
		}
		
	}

	public String getPod() {
		return pod;
	}

	/**
	 * Sets after checked that @param pod exists in the warehouse
	 * @param pod
	 */
	public void setPod(String pod) throws IllegalArgumentException {
		if(check(pod)) {
			this.pod = pod;
		} else {
			throw new IllegalArgumentException("Pod value is out of range (must be between A and Z)");
		}
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) throws IllegalArgumentException {
		if(check(bin)) {
			this.bin = bin;
		} else {
			throw new IllegalArgumentException("Bin value is out of range (must be between A and Z)");
		}
	}
	
	private boolean checkLine(String line) {
		return line != null && line.matches("[A-G]");
	}
	
	/**
	 * Checks that pod and bin are in range
	 * @param x
	 * @return
	 */
	private boolean check(String x) {
		return x != null && x.matches("[A-Z]");
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Line: "+line+", Pod: "+pod+", Bin: "+ bin;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
	        return false;
	    }
		Position pos = (Position) obj;
		if(line.equals(pos.getLine()) && pod.equals(pos.getPod()) && bin.equals(pos.getBin())){
			return true;
		}
		return false;
	}
}
