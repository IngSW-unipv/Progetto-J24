package model.inventory;

public class Position {
	private String line, pod, bin;
	
	public Position(String line, String pod, String bin) {
		this.line=line;
		this.pod=pod;
		this.bin=bin;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Line: "+line+", Pod: "+pod+", Bin: "+ bin;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getPod() {
		return pod;
	}

	public void setPod(String pod) {
		this.pod = pod;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
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
