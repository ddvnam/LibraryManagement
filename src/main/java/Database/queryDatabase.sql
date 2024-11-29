USE librarymanagement;

select book.book_id , title , author, image_url ,price
from book
join book_image on book.book_id = book_image.book_id
join book_item on book.book_id = book_item.book_id
order by book_id asc
limit 10;

SELECT
        author,
        COUNT(book.book_id) AS book_count
    FROM book
    GROUP BY author
    ORDER BY book_count DESC
    LIMIT 5;

WITH author_book_count AS (
        SELECT
            author,
            COUNT(book.book_id) AS book_count
        FROM book
        GROUP BY author
        ORDER BY book_count DESC
        LIMIT 5
)
SELECT
    b.book_id,
    b.title,
    b.author,
    bi.image_url,
    book_item.price,
    b.publication_date
FROM book b
JOIN book_image bi ON b.book_id = bi.book_id
JOIN author_book_count abc ON b.author = abc.author
join book_item on b.book_id = book_item.book_id
HAVING author = 'Stephen King'
ORDER BY author desc;

insert into account(username, password, role)
values ('member1','123','member'),
       ()

insert into account_info(account_id, email);

SELECT author, COUNT(book.book_id) AS book_count
FROM book
WHERE author LIKE '%john%'
GROUP BY author
ORDER BY book_count DESC;
