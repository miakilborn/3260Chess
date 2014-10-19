package Game;
public class Result{
	final private Boolean result;
	final private String message;


	public Result(){
		this.result = true;
		this.message = "";
	}

	public Result(Boolean result){
		this.result = result;
		this.message = "";
	}


	public Result(Boolean result, String message){
		this.result = result;
		this.message = message;
	}

	public Boolean getBoolean(){
		return result;
	}

	public String getMessage(){
		return message;
	}

	@Override
	public String toString(){
		return getBoolean().toString() + "|" + getMessage();
	}

}