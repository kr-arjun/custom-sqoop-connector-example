package org.apache.connectors.teiid;

import org.apache.log4j.Logger;

import com.cloudera.sqoop.SqoopOptions;

/**
 * Manages connections to teiid. Extends generic SQL manager.
 */
public class TeiidManager extends com.cloudera.sqoop.manager.GenericJdbcManager {

	private static final Logger LOG = Logger.getLogger(TeiidManager.class.getName());

	// driver class to ensure is loaded when making db connection.
	private static final String DRIVER_CLASS = "org.teiid.jdbc.TeiidDriver";

	public TeiidManager(final SqoopOptions opts) {
		super(DRIVER_CLASS, opts);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected String getCurTimestampQuery() {

		return "SELECT NOW()";
	}

}
