// PremiumMember class extends GymMember and represents a premium gym member with additional features.
public class PremiumMember extends GymMember
{
    // Final variable to store the fixed premium charge for the membership
    private final double premiumCharge;
    private String personalTrainer;
    private boolean isFullPayment;
    private double paidAmount;
    private double discountAmount;

     /*
     * Constructor for PremiumMember class.
     * Initializes the member details and sets default values for payment-related fields.
    */
    public PremiumMember(int id, String name, String location, String phone, String email, String gender, String DOB, String membershipStartDate, String personalTrainer) 
    {
        // Call the superclass constructor to initialize common member details
        super(id, name, location, phone, email, gender, DOB, membershipStartDate);
        this.personalTrainer = personalTrainer;
        this.premiumCharge = 50000; 
        this.paidAmount = 0; 
        this.isFullPayment = false; 
        this.discountAmount = 0; 
    }

    // Getter methods to retrieve private attributes
    public double getPremiumCharge() 
    {
        return premiumCharge;
    }
    public String getPersonalTrainer() 
    {
        return personalTrainer;
    }
    public boolean isFullPayment() 
    {
        return isFullPayment;
    }
    public double getPaidAmount() 
    {
        return paidAmount;
    }
    public double getDiscountAmount() 
    {
        return discountAmount;
    }

    public void setPaidAmount(double paidAmount) 
    {
        this.paidAmount = paidAmount;
    }

    public void setFullPayment(boolean isFullPayment)
    {
        this.isFullPayment = isFullPayment;
    }

    public void setDiscountAmount(double discountAmount) 
    {
        this.discountAmount = discountAmount;
    }

     /*
     * Overrides the markAttendance method from GymMember.
     * Increments attendance and adds loyalty points for premium members.
     */
    @Override
    public void markAttendance() 
    {
        this.attendance++; 
        this.loyaltyPoints += 10; 
    }
     /*
     * Method to pay the due amount for the premium membership.
     * 
     * @param paidAmount: The amount paid by the member
     * @return: A message indicating the payment status and remaining amount
    */
    public String payDueAmount(double paidAmount) 
    {
         // Check if full payment is already made
        if (this.isFullPayment) 
        {
            return "Payment is already complete. No due amount.";
        }
         // Calculate total paid amount
        double totalPaid = this.paidAmount + paidAmount;
        // Check if the payment exceeds the premium charge
        if (totalPaid > premiumCharge)
        {
            return "Payment exceeds the premium charge. Maximum amount to pay: " + (premiumCharge - this.paidAmount);
        }
        // Update the paid amount
        this.paidAmount = totalPaid; 
        // Check if full payment is made
        if (this.paidAmount == premiumCharge)
        {
            this.isFullPayment = true; 
        }
         // Calculate and return the remaining amount
        double remainingAmount = premiumCharge - this.paidAmount;
        return "Payment successful. Remaining amount to be paid: " + remainingAmount;
    }

     //Method to calculate the discount for the premium member.
    public void calculateDiscount()
    {
        if (this.isFullPayment) // Check if full payment is made 
        {
            this.discountAmount = premiumCharge * 0.10; // Calculate 10% discount on the premium charge
            System.out.println("Discount calculated successfully. Discount amount: " + discountAmount);
        }
        else
        {
            System.out.println("No discount available. Complete the payment to get 10% discount.");
        }
    }
    
     /*
     * Method to revert the premium member to default state.
     * Resets all member details and payment-related fields.
     */
    public void revertPremiumMember() 
    {
        // Call the superclass method to reset common member details
        super.resetMember(); 
        // Reset premium-specific fields
        this.personalTrainer = ""; 
        this.isFullPayment = false; 
        this.paidAmount = 0; 
        this.discountAmount = 0;
    }

    /**
     * Displays the member's details, including premium-specific attributes.
     * @return A formatted string with all member information
     */
      
    //Overrides the display method from GymMember.
    @Override
    public String display() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append(super.display()); // Append base class details
        sb.append("Personal Trainer: ").append(personalTrainer).append("\n");
        sb.append("Paid Amount: ").append(paidAmount).append("\n");
        sb.append("Full Payment: ").append(isFullPayment ? "Yes" : "No").append("\n");
        double remainingAmount = premiumCharge - paidAmount;
        sb.append("Remaining Amount to be Paid: ").append(remainingAmount).append("\n");
        if (isFullPayment) 
        {
            sb.append("Discount Amount: ").append(discountAmount).append("\n");
        }
        return sb.toString();
    }
}
