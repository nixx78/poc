CREATE TABLE trans_table(
  id INT DEFAULT 0,
  date DATE DEFAULT NULL,
  amount DECIMAL(10,2) DEFAULT 0,
  source_account VARCHAR(100) DEFAULT NULL,
  target_account VARCHAR(100) DEFAULT NULL,
  is_big_transaction BOOLEAN DEFAULT NULL
);

CREATE TABLE stat_table(
  id INT not null primary key GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
  file_name VARCHAR(100) DEFAULT NULL,
  count INT DEFAULT 0
);

