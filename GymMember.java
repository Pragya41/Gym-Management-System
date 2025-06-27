/**
 * Abstract class representing a generic gym member with basic attributes and functionality.
 * This serves as the parent class for specific member types in the gym management system.
*/
public abstract class GymMember
{
    // Protected attributes for encapsulation and for making them accessible within the same package and subclasses.
    protected int id;
    protected String name;
    protected String location;
    protected String phone;
    protected String email;
    protected String gender;
    protected String DOB;
    protected String membershipStartDate;
    protected int attendance;
    protected double loyaltyPoints;
    protected boolean activeStatus;

    /**
     * Constructor to initialize a new gym member with basic information
     * Sets attendance and loyalty points to zero and membership as inactive by default
    */ 
    public GymMember (int id, String name, String location, String phone, String email, String gender, String DOB, String membershipStartDate) 
    {
        //this keyword is used for referring the current object in a method or constructor
        this.id = id;
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.DOB = DOB;
        this.membershipStartDate = membershipStartDate;
        this.attendance = 0;  // Initialize attendance to zero
        this.loyaltyPoints = 0.0;  // Initialize loyalty points to zero
        this.activeStatus = false;  // Initialize membership as inactive
    }

    // Getter methods for accessing private attributes
    public int getId()
    {
        return id;// Returns member's ID
    }
    public String getName() 
    {
        return name;// Returns member's name
    }
    public String getLocation() 
    {
        return location; // Returns member's location
    }
    public String getPhone() 
    {
        return phone; // Returns member's phone number
    }
    public String getEmail() 
    {
        return email;// Returns member's email
    }
    public String getGender() 
    {
        return gender;// Returns member's gender
    }
    public String getDOB()
    {
        return DOB;// Returns member's date of birth
    }
    public String getMembershipStartDate() 
    {
        return membershipStartDate;// Returns membership start date
    }
    public int getAttendance()
    {
        return attendance;// Returns attendance count
    }
    public double getLoyaltyPoints() 
    {
        return loyaltyPoints;// Returns loyalty points
    }
    public boolean getActiveStatus() 
    {
        return activeStatus; // Returns membership active status (true/false)
    }
    /**
     * Abstract method for tracking attendance.
     * Must be implemented in subclasses (RegularMember & PremiumMember).
    */
    public abstract void markAttendance();

    /*
     * Method to activate the membership
    */
    public void activateMembership()
    {
        this.activeStatus = true; // Set activeStatus to true
    }

     /**
     * Deactivates the member's gym membership if currently active
     * Displays a message if membership is already inactive
    */
    public void deactivateMembership() 
    {
        if (this.activeStatus)
        {
            this.activeStatus = false; // Only deactivate if currently active
        } 
        else 
        {
            System.out.println("Membership is already deactivated.");
        }
    }

    /*
     * Method to reset member details
     * Resets attendance, loyalty points, and active status
    */
    public void resetMember() 
    {
        this.activeStatus = false; // Deactivate membership
        this.attendance = 0; // Reset attendance to 0
        this.loyaltyPoints = 0.0; // Reset loyalty points to 0
    }

    /**
     * Displays the member's details as a formatted string.
     * @return A string containing all member information
     */
    public String display() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(id).append("\n");
        sb.append("Name: ").append(name).append("\n");
        sb.append("Location: ").append(location).append("\n");
        sb.append("Phone: ").append(phone).append("\n");
        sb.append("Email: ").append(email).append("\n");
        sb.append("Gender: ").append(gender).append("\n");
        sb.append("DOB: ").append(DOB).append("\n");
        sb.append("Membership Start Date: ").append(membershipStartDate).append("\n");
        sb.append("Attendance: ").append(attendance).append("\n");
        sb.append("Loyalty Points: ").append(loyaltyPoints).append("\n");
        sb.append("Active Status: ").append(activeStatus).append("\n");
        return sb.toString();
    }
}