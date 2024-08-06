
-- roles
INSERT INTO `roles` (`name`) VALUES
('admin'),
('user')
;

-- genres
INSERT INTO `genres` (`name`) VALUES
('Action'),
('Comedy'),
('Drama')
;

-- casts
INSERT INTO `casts` (`name`) VALUES
('John Wick'),
('Jack Reacher'),
('Dwayne Johnson'),
('Tom Cruise'),
('Denzel Washington'),
('Tom Hanks')
;

-- productions
INSERT INTO `productions` (`name`) VALUES
('Warner Bros. Pictures'),
('20th Century Fox'),
('Universal Pictures'),
('Paramount Pictures'),
('Columbia Pictures'),
('Walt Disney Pictures')
;


-- series
INSERT INTO `series` (`title`, `summary`, `release_year`, `duration`, `country`, `imdb_rating`, `cover`) VALUES
('The Big Bang Theory', 'A woman and her family are stuck in the time paradox.', 2007, 22, 'USA', 9.5, 'big_bang_theory.jpg'),
('Game of Thrones', 'Nine noble families fight for control over the mythical lands of Westeros.', 2011, 57, 'USA', 9.3, 'game_of_thrones.jpg'),
('House of Cards', 'An antisocial maverick doctor who specializes in cardiology.', 2013, 45, 'USA', 9.1, 'house_of_cards.jpg')
;

-- series_genre
INSERT INTO `series_genre` (`series_id`, `genre_id`) VALUES
(3, 1),
(3, 2)
;

-- series_cast
INSERT INTO `series_cast` (`series_id`, `cast_id`) VALUES
(3, 1),
(3, 2)
;

-- series_production
INSERT INTO `series_production` (`series_id`, `production_id`) VALUES
(3, 1),
(3, 2)
;

-- seasons
INSERT INTO `seasons` (`series_id`, `season_number`, `summary`, `release_year`, `imdb_rating`) VALUES
(3, 1, 'The series starts with the first season of House of Cards.', 2013, 9.1),
(3, 2, 'The series starts with the second season of House of Cards.', 2014, 9.0)
;
