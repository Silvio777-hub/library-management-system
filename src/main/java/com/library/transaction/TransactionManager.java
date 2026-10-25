package com.library.transaction;

public interface TransactionManager {
	<T> T doInTransaction(TransactionCode<T> code);
}