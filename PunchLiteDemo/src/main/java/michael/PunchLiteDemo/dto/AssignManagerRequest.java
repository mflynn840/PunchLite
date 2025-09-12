package michael.PunchLiteDemo.dto;

public class AssignManagerRequest {
    
    private String managerUsername;
    private String userUsername;

    public AssignManagerRequest(String managerUsername, String userUsername){
        this.managerUsername = managerUsername;
        this.userUsername = userUsername;
    }

    public String getManagerUsername(){return this.managerUsername;}
    public void setManagerUsername(String username){this.managerUsername = username;}

    public String getUserUsername(){return this.userUsername;}
    public void setUserUsername(String username){this.userUsername=username;}


}
