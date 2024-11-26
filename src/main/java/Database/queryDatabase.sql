-- 1. Truy vấn để thêm một giao dịch mới (mượn hoặc trả sách)
INSERT INTO transaction (
    transaction_id,
    account_id,
    book_item_id,
    transaction_type,
    transaction_date,
    due_date,
    status
)
VALUES (?, ?, ?, ?, ?, ?, ?);

-- 2. Truy vấn để hiển thị tất cả các giao dịch
SELECT * FROM transaction;

-- 3. Truy vấn để cập nhật trạng thái giao dịch quá hạn
UPDATE transaction
SET status = 'overdue'
WHERE transaction_type = 'borrow'
  AND due_date < ?
  AND status != 'completed';

-- 4. Truy vấn để hiển thị các giao dịch quá hạn
SELECT * FROM transaction
WHERE status = 'overdue';

-- 5. Truy vấn để tìm kiếm giao dịch theo `account_id` (ID của thành viên)
SELECT * FROM transaction
WHERE account_id = ?;

-- 6. Truy vấn để cập nhật phí trễ cho giao dịch (dành cho các giao dịch mượn quá hạn)
UPDATE transaction
SET late_fee = ?
WHERE transaction_id = ?;

-- 7. Truy vấn để cập nhật số dư tài khoản của thành viên khi tính phí trễ
UPDATE account
SET balance = balance - ?
WHERE account_id = ?;
