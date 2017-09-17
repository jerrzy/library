package business;

public class LibraryMemberRuleSet implements RuleSet{
    @Override
    public void applyRules(Object ob) throws RuleException {
        LibraryMember member = (LibraryMember)ob;

        Boolean flag = member.getFirstName().isEmpty() || member.getLastName().isEmpty() || member.getTelephone().isEmpty() || member.getAddress().getStreet().isEmpty() || member.getAddress().getCity().isEmpty() || member.getAddress().getState().isEmpty() || member.getAddress().getZip().isEmpty();
        if (flag) {
            throw new RuleException("Please Enter in all fields");
        }
    }
}
