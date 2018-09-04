package mark.butko.model.criteria;

/**
 * interface that should be implemented by classes that represent criterias by
 * wich query for database will be gathered
 * 
 * @author markg
 *
 */
public interface Criteria {
	/**
	 * 
	 * @return string that has valid SQL
	 */
	String getSQLString();
}
