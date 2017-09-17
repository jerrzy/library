package business;

public class AddAuthorRuleSet implements RuleSet{
	 @Override
	    public void applyRules(Object ob) throws RuleException {
	        Author member = (Author)ob;

	        Boolean flag = member.getFirstName().isEmpty() || member.getLastName().isEmpty() || 
	        		member.getTelephone().isEmpty() || member.getAddress().getStreet().isEmpty() || 
	        		member.getAddress().getCity().isEmpty() || member.getAddress().getState().isEmpty() || 
	        		member.getAddress().getZip().isEmpty() || 
	        		member.getCredentials().isEmpty() || member.getBio().isEmpty();
	        if (flag) {
	            throw new RuleException("Please Enter in all fields");
	        }
	    }
}
