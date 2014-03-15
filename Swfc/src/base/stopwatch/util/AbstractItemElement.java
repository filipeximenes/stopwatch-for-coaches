package base.stopwatch.util;


public abstract class AbstractItemElement {
	private long id;
	private boolean inserted = false;
	private boolean actualized = false;
	private boolean real = false;
	
	public void setId(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}
	
	public boolean wasInserted(){
		return inserted;
	}
	
	public void setInserted(){
		inserted = true;
	}
	
	public void setActualized() {
		this.actualized = true;
	}

	public boolean isActualized() {
		return actualized;
	}
	
	public void setReal(){
		real = true;
	}
	
	public boolean idReal(){
		return real;
	}

	@Override
	public boolean equals(Object obj) {
		AbstractItemElement a = (AbstractItemElement) obj;
		if (a.getId() == this.getId()){
			return true;			
		}else{
			return false;
		}
	}
}
