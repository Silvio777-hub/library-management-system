package com.library.transaction;

import java.sql.Connection;

/**
 * Manages database transactions. STUB - does not actually commit or rollback
 * yet.
 */
public class PostgresTransactionManager implements TransactionManager {

	private final ConnectionFactory connectionFactory;

	public PostgresTransactionManager(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	@Override
	public <T> T doInTransaction(TransactionCode<T> code) {
		try (Connection conn = connectionFactory.getConnection()) {
			// STUB: No transaction handling yet
			return code.execute(conn);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}