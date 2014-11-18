package Game;
public class Result{
    final private Boolean valid;
    final private Boolean gameOver;
    final private String message;


    public Result(){
        this.valid = true;
        this.gameOver = false;
        this.message = "";
    }

    public Result(Boolean valid){
        this.valid = valid;
        this.gameOver = false;
        this.message = "";
    }


    public Result(Boolean valid, String message){
        this.valid = valid;
        this.gameOver = false;
        this.message = message;
    }
    
    public Result(Boolean valid, Boolean gameOver, String message){
        this.valid = valid;
        this.gameOver = gameOver;
        this.message = message;
    }

    public Boolean isValid(){
            return valid;
    }
    
    public Boolean isGameOver(){
        return gameOver;
    }

    public String getMessage(){
            return message;
    }

    @Override
    public String toString(){
            return isValid().toString() + "|" + isGameOver().toString() + "|" + getMessage();
    }

}