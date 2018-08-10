package org.apache.connectors.teiid;

import org.apache.log4j.Logger;

import com.cloudera.sqoop.SqoopOptions;
import com.cloudera.sqoop.manager.ConnManager;
import com.cloudera.sqoop.metastore.JobData;

/**
 * Contains instantiation code for all ConnManager implementations shipped and
 * enabled by default in Sqoop.
 */
public class TeiidManagerFactory extends com.cloudera.sqoop.manager.ManagerFactory {

	private static final Logger LOG = Logger.getLogger(TeiidManager.class.getName());

	public ConnManager accept(JobData data) {
		SqoopOptions options = data.getSqoopOptions();

		String scheme = extractScheme(options);
		if (null == scheme) {
			LOG.warn("Null scheme associated with connect string.");
			return null;
		}

		LOG.debug("Trying with scheme: " + scheme);

		if (scheme.equals("jdbc:teiid")) {
			return new TeiidManager(options);
		} else {
			return null;
		}
	}

	protected String extractScheme(SqoopOptions options) {
		String connectStr = options.getConnectString();

		// java.net.URL follows RFC-2396 literally, which does not allow a ':'
		// character in the scheme component (section 3.1). JDBC connect
		// strings,
		// however, commonly have a multi-scheme addressing system. e.g.,
		// jdbc:mysql://...; so we cannot parse the scheme component via URL
		// objects. Instead, attempt to pull out the scheme as best as we can.

		// First, see if this is of the form [scheme://hostname-and-etc..]
		int schemeStopIdx = connectStr.indexOf("//");
		if (-1 == schemeStopIdx) {
			// If no hostname start marker ("//"), then look for the right-most
			// ':'
			// character.
			schemeStopIdx = connectStr.lastIndexOf(':');
			if (-1 == schemeStopIdx) {
				// Warn that this is nonstandard. But we should be as permissive
				// as possible here and let the ConnectionManagers themselves
				// throw
				// out the connect string if it doesn't make sense to them.
				LOG.warn("Could not determine scheme component of connect string");

				// Use the whole string.
				schemeStopIdx = connectStr.length();
			}
		}

		return connectStr.substring(0, schemeStopIdx);
	}
}
