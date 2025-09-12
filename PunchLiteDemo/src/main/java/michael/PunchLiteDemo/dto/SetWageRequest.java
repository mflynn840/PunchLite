package michael.PunchLiteDemo.dto;

public class SetWageRequest {
    Double newHourlyRate;
    String employeeUsername;
    String managerUsername;

    public SetWageRequest(Double newHourlyRate, String employeeUsername, String managerUsername){
        this.newHourlyRate = newHourlyRate;
        this.employeeUsername = employeeUsername;
        this.managerUsername = managerUsername;
    }

    public Double getNewHourlyRate(){return this.newHourlyRate;}
    public void setNewHourlyRate(Double newHourlyRate){this.newHourlyRate=newHourlyRate;}

    public String getEmployeeUsername(){return this.employeeUsername;}
    public void setEmployeeUsername(String employeeUsername){this.employeeUsername = employeeUsername;}

    public String getManagerUsername(){return this.managerUsername;}
    public void setManagerUsername(String managerUsername){this.managerUsername = managerUsername;}

}