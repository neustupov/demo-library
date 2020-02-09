INSERT INTO rack (id) VALUES (100010);
INSERT INTO rack (id) VALUES (100011);
INSERT INTO rack (id) VALUES (100012);

INSERT INTO book (id, level, name, rack_id) VALUES (100000, '1', 'AAA', 100010);
INSERT INTO book (id, level, name, rack_id) VALUES (100001, '2', 'BBB', 100010);
INSERT INTO book (id, level, name, rack_id) VALUES (100002, '3', 'CCC', 100011);
INSERT INTO book (id, level, name, rack_id) VALUES (100003, '1', 'DDD', 100012);

INSERT INTO rack_books (rack_id, books_id) VALUES (100010, 100000);
INSERT INTO rack_books (rack_id, books_id) VALUES (100010, 100001);
INSERT INTO rack_books (rack_id, books_id) VALUES (100011, 100002);
INSERT INTO rack_books (rack_id, books_id) VALUES (100012, 100003);


