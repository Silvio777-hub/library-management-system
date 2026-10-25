package com.library.transaction;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Manages database transactions, ensuring atomic commits and rollbacks.
 */
public class PostgresTransactionManager implements TransactionManager {

	private final ConnectionFactory connectionFactory;

	public PostgresTransactionManager(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	@Override
	public <T> T doInTransaction(TransactionCode<T> code) {
		try (Connection conn = connectionFactory.getConnection()) {
			boolean originalAutoCommit = conn.getAutoCommit();
			conn.setAutoCommit(false); // Start transaction

			try {
				T result = code.execute(conn);
				conn.commit(); // Success! Save changes
				return result;
			} catch (Exception e) {
				try {
					conn.rollback(); // Failure! Undo changes
				} catch (SQLException rollbackEx) {
					e.addSuppressed(rollbackEx);
				}

				if (e instanceof RuntimeException runtimeException) {
					throw runtimeException;
				}
				throw new RuntimeException("Transaction failed", e);
			} finally {
				conn.setAutoCommit(originalAutoCommit); // Reset connection state
			}
		} catch (SQLException e) {
			throw new RuntimeException("Could not manage transaction", e);
		}
	}
}