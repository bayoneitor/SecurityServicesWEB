package securityservices.core.shared.operations;

public interface Billable {

    public String getCode();

    public String getType();

    public int getClient();

    public String getInitDate();

    public String getFinishDate();

    public double getValue();
}
