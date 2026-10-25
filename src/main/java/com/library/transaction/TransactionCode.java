package com.library.transaction;

import java.sql.Connection;

@FunctionalInterface
public interface TransactionCode<T> {
	T execute(Connection conn) throws Exception;
}