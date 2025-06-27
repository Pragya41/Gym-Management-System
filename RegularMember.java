/**
 * Subclass of GymMember representing a regular member
 * Extends the abstract GymMember class to implement specific functionality
 * for regular members with basic, standard, or deluxe plans
*/
public class RegularMember extends GymMember 
{
     //private attributes for making them only accessible within the declared class
    private final int attendanceLimit; //final keyword to prevent it from being overridden, inherit or modified.
    private boolean isEligibleForUpgrade; // Tracks if member can upgrade their plan
    private String removalReason;// Reason for membership termination if applicable
    private String referralSource; // How the member was referred to the gym
    private String plan; // Current subscription plan (basic, standard, deluxe)
    private double price; // Current price of the subscription

    //Constructor to initialize a RegularMember with default values.
    public RegularMember(int id, String name, String location, String phone, String email, String gender, String DOB, String membershipStartDate, String referralSource) 
    {
        //super keyword refers to superclass(parent) objects and used to call superclass methods and to access superclass constructor
        super(id, name, location, phone, email, gender, DOB, membershipStartDate);
        //this keyword is used for referring the current object in a method or constructor
        this.referralSource = referralSource; // Set referral source
        this.isEligibleForUpgrade = false; // Member is not eligible for an upgrade initially
        this.attendanceLimit = 30;  // Set attendance threshold for upgrade eligibility
        this.plan = "basic"; 
        this.price = 6500; 
        this.removalReason = "";   // Initialize removal reason as empty
    }

     // Getter methods to retrieve private attributes
    public int getAttendanceLimit() 
    {
        return attendanceLimit;// Returns attendance limit
    }

    public boolean getisEligibleForUpgrade()
    {
        return isEligibleForUpgrade;  //Returns Whether the member is eligible for a plan upgrade
    }

    public String getRemovalReason() 
    {
        return removalReason;//Returns the reason for membership termination (if any)
    }

    public String getReferralSource() 
    {
        return referralSource;//Returns The referral source of the member
    }

    public String getPlan() 
    {
        return plan;  //Returns the current subscription plan of the member
    }

    public double getPrice() {
        return price;//Returns the price of the current subscription plan 
    }

    public void setPlan(String plan)
    {
        this.plan = plan;
    }

    public void setPrice(double price)
    {
        this.price = price;
    }

    public void setEligibleForUpgrade(boolean isEligibleForUpgrade)
    {
        this.isEligibleForUpgrade = isEligibleForUpgrade;
    }

      
    /**
     * Overrides markAttendance() method from GymMember.
     * Increases attendance count and adds loyalty points.
     * If attendance reaches or exceeds the limit, marks the member as eligible for an upgrade.
     */
    @Override
    public void markAttendance() 
    {
        this.attendance++; // Increment attendance count
        this.loyaltyPoints += 5; // Add loyalty points for each visit
         // Check if the member qualifies for an upgrade
        if (this.attendance >= attendanceLimit) 
        {
            this.isEligibleForUpgrade = true; 
        }
    }

     // Gets the price for a specified membership plan
    public double getPlanPrice(String plan) 
    {
        // Use switch statement to return appropriate price based on plan name
        switch (plan.toLowerCase()) 
        {
            case "basic":
                return 6500;// Basic plan price
            case "standard":
                return 12500;// Standard plan price
            case "deluxe":
                return 18500; // Deluxe plan price
            default:
                return -1; // Invalid plan indicator
        }
    }
     /**
     * Upgrades the member's subscription plan if they are eligible.
     * 
     * @param plan The new plan to upgrade to
     * @return A message indicating the success or failure of the upgrade
    */
    public String upgradePlan(String plan)
    {
        if (this.isEligibleForUpgrade) 
        {
            double newPrice = getPlanPrice(plan); // Fetch the price of the new plan
            if (newPrice == -1) 
            {
                return "Invalid plan selected. Available plans: basic, standard, deluxe.";
            }
            if (this.plan.equalsIgnoreCase(plan)) // Check if already on the selected plan
            {
                return "You are already subscribed to the " + plan + " plan.";
            }
             // Upgrade the plan
            this.plan = plan;
            this.price = newPrice; 
            return "Plan upgraded to " + plan + " successfully.";
        } 
        else
        {
            return "You are not eligible for an upgrade yet."; // Member hasn't reached attendance threshold for upgrade
        }
    }

    /**
     * Sets the initial plan for the member.
     * @param plan The initial plan to set (basic, standard, deluxe)
     * @return A message indicating the result of setting the plan
     */
    public String setInitialPlan(String plan) {
        double newPrice = getPlanPrice(plan.toLowerCase());
        if (newPrice == -1) 
        {
            return "Invalid plan selected. Available plans: basic, standard, deluxe.";
        }
        if (this.plan.equalsIgnoreCase(plan)) 
        {
            return "You are already subscribed to the " + plan + " plan.";
        }
        this.plan = plan; // Set initial plan
        this.price = newPrice; // Set initial price
        return "Initial plan set to " + plan + " successfully.";
    }

    //Reverts a member to default settings when their membership is terminated.
    public void revertRegularMember(String removalReason)
    {
        super.resetMember();  // Reset member attributes using superclass method
        this.isEligibleForUpgrade = false; // Reset upgrade eligibility
        this.plan = "basic"; // Reset plan to Basic
        this.price = 6500;  // Reset price to default Basic plan price
        this.removalReason = removalReason; // Store the removal reason
    }

    /**
     * Displays the member's details, including regular-specific attributes.
     * @return A formatted string with all member information
     */
    //Overrides display() method from GymMember
    @Override
    public String display() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append(super.display()); // Append base class details
        sb.append("Plan: ").append(plan).append("\n");
        sb.append("Price: ").append(price).append("\n");
        if (!removalReason.isEmpty()) 
        {
            sb.append("Removal Reason: ").append(removalReason).append("\n");
        }
        return sb.toString();
    }
}
