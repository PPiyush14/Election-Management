use Javaproject;

CREATE TABLE voters (
    voter_id INT PRIMARY KEY,
    password INT
);
describe voters;
INSERT INTO voters (voter_id, password) VALUES
(101, 1234),
(102, 2345),
(103, 3456),
(104, 4567),
(105, 5678);

CREATE TABLE candidates (
    candidate_id INT PRIMARY KEY,
    name VARCHAR(100)
);
describe candidates;
INSERT INTO candidates (candidate_id, name) VALUES
(1, 'Narendra Modi'),
(2, 'Rahul Gandhi'),
(3, 'Arvind Kejriwal'),
(4, 'Yogi Adityanath'),
(5, 'Mamata Banerjee'),
(6, 'Shashi Tharoor'),
(7, 'Nirmala Sitharaman'),
(8, 'Pinarayi Vijayan'),
(9, 'Amit Shah'),
(10, 'Akhilesh Yadav');

CREATE TABLE votes (
    vote_id INT PRIMARY KEY AUTO_INCREMENT,
    voter_id INT,
    candidate_id INT,
    vote_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (voter_id) REFERENCES voters(voter_id),
    FOREIGN KEY (candidate_id) REFERENCES candidates(candidate_id)
);
describe votes;
INSERT INTO votes (voter_id, candidate_id) VALUES
(101, 1),  -- Voted for Narendra Modi
(102, 2),  -- Voted for Rahul Gandhi
(103, 3),  -- Voted for Arvind Kejriwal
(104, 1),  -- Another vote for Narendra Modi
(105, 5);  -- Voted for Mamata Banerjee

select* from votes;
select* from voters;
select* from candidates;