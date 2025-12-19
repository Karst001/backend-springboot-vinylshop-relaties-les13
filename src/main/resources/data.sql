-- create genres
INSERT INTO genres (name, description, created_date)
VALUES ('Pop', 'Dance away', now()),
       ('Jazz', 'Nice and easy', now()),
       ('Country', 'Red neck Country', now());

--create two publishers
INSERT INTO publishers (name, email_address, contact_details, created_date)
VALUES ('Buma Stemra', 'info@bumastemra.com', 'Tom Peters', now()),
       ('Free Record Shop', 'info@freerecordshop.nl', 'Jan van Dijk', now());


--Insert into Albums using dynamic ID's from Genres and Publishers by using a Select
INSERT INTO albums (title,  release_year, publisher_id, genre_id, created_date)
VALUES ('This is my country', 2023,
        (SELECT id FROM publishers WHERE name = 'Buma Stemra'),
        (SELECT id FROM genres WHERE name = 'Country'),
        now()
       ),
       ('Nice and Easy', 2015,
        (SELECT id FROM publishers WHERE name = 'Free Record Shop'),
        (SELECT id FROM genres WHERE name = 'Jazz'),
        now()
       ),
       ('Bad', 1986,
        (SELECT id FROM publishers WHERE name = 'Free Record Shop'),
        (SELECT id FROM genres WHERE name = 'Pop'),
        now()
       );

-- Insert into artists
INSERT INTO artists (name, biography, created_date)
VALUES ('Micheal Buble', 'Best signer and creative artists with a variety of music', now()),
       ('Shania Twain', 'Canadian artist for pop and country', now()),
       ('Micheal Jackson', 'Pop artist of the 70, 80 and 90', now());

-- insert some stock items
INSERT INTO stock (created_date, edited_date, condition, price, album_id)
VALUES (now(), now(), 'goed', 15., 1),
       (now(), now(), 'licht gebruikt', 12.50, 1),
       (now(), now(), 'goed, krassen laatste nummer', 17.95, 1),
       (now(), now(), 'krassen', 5.95, 2);

-- create relations between album and artists
INSERT INTO albums_artists (album_id, artist_id)
VALUES (2, 1),
       (1, 2),
       (3, 3);

