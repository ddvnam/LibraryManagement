-- Create the transaction table
CREATE TABLE IF NOT EXISTS transaction (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT,      -- ID giao dịch
    account_id INT NOT NULL,                            -- ID của tài khoản (khóa ngoại từ bảng account)
    book_item_id INT NOT NULL,                          -- ID của sách (khóa ngoại từ bảng book_item)
    transaction_type ENUM('borrow', 'return') NOT NULL, -- Loại giao dịch (mượn sách hoặc trả sách)
    transaction_date DATE NOT NULL,                     -- Ngày giao dịch
    due_date DATE NOT NULL,                             -- Ngày trả sách
    return_date DATE,                                   -- Ngày trả sách thực tế (null nếu chưa trả)
    late_fee DECIMAL(10, 2) DEFAULT 0.00,               -- Phí trễ (nếu có)
    status ENUM('completed', 'cancelled') DEFAULT 'completed', -- Trạng thái giao dịch
    FOREIGN KEY (account_id) REFERENCES account(account_id) ON DELETE CASCADE, -- Khóa ngoại từ bảng account
    FOREIGN KEY (book_item_id) REFERENCES book_item(book_item_id) ON DELETE CASCADE  -- Khóa ngoại từ bảng book_item
);
